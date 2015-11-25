(ns chromex.playground
    (:refer-clojure :only [defmacro defn apply declare meta let macroexpand-1])
    (:require
      [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
      [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
      [chromex-lib.config :refer [get-static-config gen-active-config]]
      [chromex-lib.debug :refer [print-code]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-some-prop []
  (gen-call :property ::some-prop &form))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-something [param1]
  (gen-call :function ::get-something &form param1 #_callback))

(defmacro do-something [param1]
  (gen-call :function ::do-something &form param1))

(defmacro do-something-optional-args
  ([opt-p1 opt-p2 opt-p3] (gen-call :function ::do-something-optional-args &form opt-p1 opt-p2 opt-p3))
  ([opt-p1 opt-p2] `(do-something-optional-args ~opt-p1 ~opt-p2 :omit))
  ([opt-p1] `(do-something-optional-args ~opt-p1 :omit :omit))
  ([] `(do-something-optional-args :omit :omit :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-something-events
  ([chan] (gen-call :event ::on-something &form chan)))

(defmacro tap-on-something-deprecated-events
  ([chan] (gen-call :event ::on-something-deprecated &form chan)))

(defmacro tap-on-event-supporting-filters
  ([chan] (gen-call :event ::on-something-supporting-filters &form chan))
  ([chan filters] (gen-call :event ::on-something-supporting-filters &form chan filters)))

; -- helpers ----------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table &form config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API-TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace  "chrome.playground"
   :since      "15"
   :properties [{:id          ::some-prop
                 :deprecated  "use someOtherProp instead"
                 :name        "someProp"
                 :return-type "prop1-type"}]
   :functions  [{:id          ::do-something
                 :name        "doSomething"
                 :since       "24"
                 :return-type "mock-return"
                 :params      [{:name "param1"
                                :type "mock-param1"}]}
                {:id        ::get-something
                 :name      "getSomething"
                 :since     "10"
                 :callback? true
                 :params    [{:name "param1"
                              :type "mock-param1"}
                             {:name     "callback"
                              :type     :callback
                              :callback {:params [{:name "callback-param"
                                                   :type "mock-callback-param1"}]}}]}
                {:id          ::do-something-optional-args
                 :name        "doSomethingOptionalArgs"
                 :since       "10"
                 :return-type "some-type"
                 :params      [{:name      "op1"
                                :optional? true
                                :type      "some-type"}
                               {:name      "op2"
                                :optional? true
                                :type      "some-type"}
                               {:name      "op3"
                                :optional? true
                                :type      "some-type"}]}]
   :events     [{:id     ::on-something
                 :name   "onSomething"
                 :params [{:name "ep1"
                           :type "event-param-type1"}]}
                {:id         ::on-something-deprecated
                 :name       "onSomethingDeprecated"
                 :deprecated "Use onSomething instead."
                 :params     [{:name "ep1"
                               :type "event-param-type1"}]}
                {:id     ::on-something-supporting-filters
                 :name   "onSomethingSupportingFilters"
                 :params [{:name "ep1"
                           :type "some-type"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))

; ---------------------------------------------------------------------------------------------------------------------------

;(print-code (macroexpand-1 '(gen-wrap :function ::get-something config p1)))