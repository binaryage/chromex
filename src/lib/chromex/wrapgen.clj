(ns chromex.wrapgen
  (:require [clojure.string :as string]
            [chromex.config :refer [get-static-config]]
            [chromex.support :refer [gen-logging-if-verbose
                                     get-item-by-id
                                     get-api-id
                                     print-debug
                                     gen-missing-api-check
                                     gen-call-hook]]))

; This file is responsible for generating code wrapping Chrome API calls.
; Each Chrome API method will get generated one representing ClojureScript stub method (stubs have star postfix).
;
; The gen-wrap-from-table must be called at compile time from a macro and it generates code in 3 layers:
;
;   1) generates code converting callbacks to channels (gen-callback-function-wrap)
;   2) generates code performing marshalling (gen-marshalling)
;   3) generates code performing logging (gen-logging-if-verbose)
;   4) generates code performing actual call/access of native Chrome API (gen-api-access-or-call)
;
; Some implementation notes:
;
; * Generated code slightly differs for different API method types (property, function, event).
; * Generated code also does some sanity checking (optional). See :elide* keys in static config.
; * Marshalling has to deal not only with input parameters and return value, but also arguments passed into callbacks.
; * Logging has to be performed not only before actual Chrome API call, but also in callbacks (wrap-callback-with-logging).
; * Generated code must use string names when doing Javascript interop to be compatible with advanced compilation:
;     https://github.com/binaryage/chromex/#advanced-mode-compilation
; * Chrome API supports optional arguments, but we rely on argument positions for marshalling. That is why we introduced
;   a special parameter value :omit, which marks arguments which should be omitted from final native API call.
;   omitting arguments is done during runtime, see method `prepare-final-args-array`.
;   Note: for convenience we generate arities of API methods with trailing optional arguments omitted,
;         for example see `connect` macro in https://github.com/binaryage/chromex/blob/master/src/exts/chromex/ext/runtime.clj
;
; ---------------------------------------------------------------------------------------------------------------------------

(defn wrap-callback-with-logging [static-config label api config [callback-sym callback-info]]
  (let [{:keys [params]} callback-info
        param-syms (map #(gensym (str "cb-param-" (:name %) "-")) params)]
    `(fn [~@param-syms]
       ~(gen-logging-if-verbose static-config config label api `(into-array [~@param-syms]))
       (~callback-sym ~@param-syms))))

(defn wrap-callback-args-with-logging [static-config api config args params]
  (assert (= (count params) (count args))
          (str "a mismatch between parameters and arguments passed into wrap-callback-args-with-logging\n"
               "api: " api "\n"
               "params: " params "\n"
               "args: " args))
  (for [[callback-sym callback-info] (partition 2 (interleave args (map :callback-info params)))]
    (if callback-info                                                                                                         ; only callback params have callback info,
      (wrap-callback-with-logging static-config "callback:" api config [callback-sym callback-info])
      callback-sym)))

; ---------------------------------------------------------------------------------------------------------------------------

(defn gen-api-access-or-call [static-config api-table descriptor config & args]
  (let [{:keys [namespace]} api-table
        {:keys [name params property?]} descriptor
        api (get-api-id api-table descriptor)
        namespace-path (string/split namespace #"\.")
        wrapped-args (wrap-callback-args-with-logging static-config api config args params)
        param-names (map :name params)
        param-optionalities (map :optional? params)
        arg-descriptors (vec (map vec (partition 3 (interleave wrapped-args param-names param-optionalities))))
        operation (if property? "accessing:" "calling:")
        final-args-array-sym (gensym "final-args-array-")
        ns-sym (gensym "ns-")
        target-sym (gensym "target-")]
    `(let [~final-args-array-sym (chromex.support/prepare-final-args-array ~arg-descriptors ~api)
           ~ns-sym (oops.core/oget (:root ~config) ~@namespace-path)]
       ~(if-not property?
          (gen-missing-api-check static-config config api ns-sym name))
       ~(gen-logging-if-verbose static-config config operation api final-args-array-sym)
       (let [~target-sym (oops.core/oget ~ns-sym ~(str "?" name))]
         ~(if property?
            target-sym
            `(.apply ~target-sym ~ns-sym ~final-args-array-sym))))))

; ---------------------------------------------------------------------------------------------------------------------------

(defn marshall [static-config config & args]
  (let [marshaller (:gen-marshalling static-config)]
    (assert (and marshaller (fn? marshaller))
            (str "invalid :gen-marshalling in static-config\n"
                 "static-config: " static-config))
    (let [marshalled-code (apply marshaller config args)]
      (if (:debug-marshalling static-config)
        (print-debug (str "marshalling request " args " => " marshalled-code)))
      marshalled-code)))

(defn marshall-callback-param [static-config config api [callback-param-sym type]]
  (marshall static-config config :from-chrome api type callback-param-sym))

(defn marshall-callback [static-config config api [callback-sym callback-info]]
  (let [{:keys [params]} callback-info
        param-syms (map #(gensym (str "cb-" (:name %) "-")) params)
        param-types (map :type params)
        sym+type-pairs (partition 2 (interleave param-syms param-types))
        marshalled-params (map (partial marshall-callback-param static-config config api) sym+type-pairs)]
    (if (empty? param-syms)
      callback-sym                                                                                                            ; a special case of callback with no parameters, no marshalling needed
      `(fn [~@param-syms] (~callback-sym ~@marshalled-params)))))

(defn marshall-result [static-config config api [result-sym type]]
  (marshall static-config config :from-chrome api type result-sym))

(defn marshall-param [static-config config api [param-sym type]]
  (let [sym (gensym "omit-test-")]
    `(let [~sym ~param-sym]
       (if (cljs.core/keyword-identical? ~sym :omit)
         :omit
         ~(marshall static-config config :to-chrome api type sym)))))

(defn marshall-function-param [static-config config api [sym param]]
  (let [{:keys [name type]} param
        callback-api (str api ".callback")]
    (assert name (str "parameter has missing 'name': " api " " param))
    (assert type (str "parameter has missing 'type': " api " " param))
    (if (= type :callback)
      (marshall-callback static-config config callback-api [sym (:callback param)])
      (marshall-param static-config config api [sym type]))))

(defn marshall-function-params [static-config config api args params]
  (assert (= (count params) (count args))
          (str "a mismatch between parameters and arguments passed into marshall-function-params\n"
               "api: " api "\n"
               "args: " args
               "params:" params))
  (for [arg+param (partition 2 (interleave args params))]
    (marshall-function-param static-config config api arg+param)))

(defn gen-marshalling [static-config api-table descriptor config & args]
  (let [api (get-api-id api-table descriptor)
        {:keys [params return-type]} descriptor
        marshalled-params (marshall-function-params static-config config api args params)
        marshalled-param-syms (map #(gensym (str "marshalled-" (:name %) "-")) params)
        result-sym (gensym "result-")]
    `(let [~@(interleave marshalled-param-syms marshalled-params)
           ~result-sym ~(apply gen-api-access-or-call static-config api-table descriptor config marshalled-param-syms)]
       ~(marshall-result static-config config api [result-sym return-type]))))

; ---------------------------------------------------------------------------------------------------------------------------

(defn gen-event [static-config api-table descriptor config & [chan extra-args]]
  (let [api (get-api-id api-table descriptor)
        event-id (:id descriptor)
        event-fn-sym (gensym "event-fn-")
        handler-fn-sym (gensym "handler-fn-")
        logging-fn-sym (gensym "logging-fn-")
        event-obj-sym (gensym "event-obj-")
        result-sym (gensym "result-")
        ns-obj-sym (gensym "ns-obj-")
        event-path (string/split api #"\.")
        ns-path (butlast event-path)
        event-key (last event-path)]
    `(let [~event-fn-sym ~(gen-call-hook config :event-listener-factory event-id chan)
           ~handler-fn-sym ~(marshall-callback static-config config (str api ".handler") [event-fn-sym descriptor])
           ~logging-fn-sym ~(wrap-callback-with-logging static-config "event:" api config [handler-fn-sym descriptor])
           ~ns-obj-sym (oops.core/oget (:root ~config) ~@ns-path)]
       ~(gen-missing-api-check static-config config api ns-obj-sym event-key)
       (let [~event-obj-sym (oops.core/oget ~ns-obj-sym ~event-key)
             ~result-sym (chromex.chrome-event-subscription/make-chrome-event-subscription ~event-obj-sym ~logging-fn-sym ~chan)]
         (chromex.protocols/subscribe! ~result-sym ~extra-args)
         ~result-sym))))

; ---------------------------------------------------------------------------------------------------------------------------

(defn gen-callback-function-wrap [static-config api-table descriptor config & args]
  (let [callback-chan-sym (gensym "callback-chan-")
        callback-fn (gen-call-hook config :callback-fn-factory descriptor callback-chan-sym)
        args+callback (concat args [callback-fn])
        marshalled-call-with-callback (apply gen-marshalling static-config api-table descriptor config args+callback)]
    `(let [~callback-chan-sym ~(gen-call-hook config :callback-channel-factory)]
       ~marshalled-call-with-callback
       ~callback-chan-sym)))

(defn gen-plain-function-wrap [static-config api-table descriptor config & args]
  (apply gen-marshalling static-config api-table descriptor config args))

(defn gen-function-wrap [static-config api-table item-id config & args]
  (let [descriptor (get-item-by-id item-id (:functions api-table))
        _ (assert descriptor (str "unable to find function with id " item-id " in:\n" api-table))
        tagged-descriptor (assoc descriptor :function? true)]
    (if (:callback? descriptor)
      (apply gen-callback-function-wrap static-config api-table tagged-descriptor config args)
      (apply gen-plain-function-wrap static-config api-table tagged-descriptor config args))))

(defn gen-property-wrap [static-config api-table item-id config & args]
  (let [descriptor (get-item-by-id item-id (:properties api-table))
        _ (assert descriptor (str "unable to find property with id " item-id " in:\n" api-table))
        tagged-descriptor (assoc descriptor :property? true)]
    (apply gen-marshalling static-config api-table tagged-descriptor config args)))

(defn gen-event-wrap [static-config api-table item-id config & args]
  (let [descriptor (get-item-by-id item-id (:events api-table))
        _ (assert descriptor (str "unable to find event with id " item-id " in:\n" api-table))
        tagged-descriptor (assoc descriptor :event? true)]
    (apply gen-event static-config api-table tagged-descriptor config args)))

; ---------------------------------------------------------------------------------------------------------------------------

(defn gen-wrap-from-table [static-config api-table kind item-id config & args]
  (case kind
    :function (apply gen-function-wrap static-config api-table item-id config args)
    :property (apply gen-property-wrap static-config api-table item-id config args)
    :event (apply gen-event-wrap static-config api-table item-id config args)))

(defn gen-wrap-helper [api-table kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))
