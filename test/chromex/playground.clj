(ns chromex.playground
    (:refer-clojure :only [defmacro defn apply declare meta let partial macroexpand-1])
    (:require [chromex.wrapgen :refer [gen-wrap-helper gen-wrap-from-table]]
      [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]
      [chromex.debug :refer [print-code]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-some-prop []
  (gen-call :property ::some-prop &form))

(defmacro get-some-missing-prop []
  (gen-call :property ::some-missing-prop &form))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-something [param1]
  (gen-call :function ::get-something &form param1 #_callback))

(defmacro get-something-causing-error [param1]
  (gen-call :function ::get-something-causing-error &form param1 #_callback))

(defmacro do-something [param1]
  (gen-call :function ::do-something &form param1))

(defmacro do-something-optional-args
  ([opt-p1 opt-p2 opt-p3] (gen-call :function ::do-something-optional-args &form opt-p1 opt-p2 opt-p3))
  ([opt-p1 opt-p2] `(do-something-optional-args ~opt-p1 ~opt-p2 :omit))
  ([opt-p1] `(do-something-optional-args ~opt-p1 :omit :omit))
  ([] `(do-something-optional-args :omit :omit :omit)))

(defmacro do-something-missing []
  (gen-call :function ::do-something-missing &form))

(defmacro get-storage-area []
  (gen-call :function ::get-storage-area &form))

(defmacro get-port []
  (gen-call :function ::get-port &form))

(defmacro call-future-api []
  (gen-call :function ::call-future-api &form))

(defmacro call-master-api []
  (gen-call :function ::call-master-api &form))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-something-events
  ([chan & args] (apply gen-call :event ::on-something &form chan args)))

(defmacro tap-on-something-deprecated-events
  ([chan & args] (apply gen-call :event ::on-something-deprecated &form chan args)))

(defmacro tap-on-something-else-events
  ([chan & args] (apply gen-call :event ::on-something-else &form chan args)))

(defmacro tap-on-something-missing-events
  ([chan & args] (apply gen-call :event ::on-something-missing &form chan args)))

; -- helpers ----------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.tabs namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API-TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.playground"
   :since     "15"
   :properties
              [{:id          ::some-prop
                :deprecated  "use someOtherProp instead"
                :name        "someProp"
                :return-type "marshalled-type"}
               {:id          ::some-missing-prop
                :name        "someMissingProp"
                :return-type "some-type"}]
   :functions
              [{:id          ::do-something
                :name        "doSomething"
                :since       "24"
                :return-type "marshalled-type"
                :params      [{:name "param1"
                               :type "marshalled-type"}]}
               {:id        ::get-something
                :name      "getSomething"
                :since     "10"
                :callback? true
                :params    [{:name "param1"
                             :type "marshalled-type"}
                            {:name     "callback"
                             :type     :callback
                             :callback {:params [{:name "callback-param"
                                                  :type "marshalled-type"}]}}]}
               {:id        ::get-something-causing-error
                :name      "getSomethingCausingError"
                :callback? true
                :params    [{:name "param1"
                             :type "some-type"}
                            {:name     "callback"
                             :type     :callback
                             :callback {:params []}}]}
               {:id          ::do-something-optional-args
                :name        "doSomethingOptionalArgs"
                :since       "10"
                :return-type "some-type"
                :params      [{:name      "op1"
                               :optional? true
                               :type      "some-type"}
                              {:name      "op2"
                               :optional? true
                               :type      "marshalled-type"}
                              {:name      "op3"
                               :optional? true
                               :type      "some-type"}]}
               {:id          ::do-something-missing
                :name        "doSomethingMissing"
                :return-type "some-type"
                :params      []}
               {:id          ::get-storage-area
                :name        "getStorageArea"
                :return-type "storage.StorageArea"
                :params      []}
               {:id          ::get-port
                :name        "getPort"
                :return-type "runtime.Port"
                :params      []}
               {:id     ::call-future-api
                :name   "callFutureApi"
                :since  "100"
                :params []}
               {:id     ::call-master-api
                :name   "callMasterApi"
                :since  "master"
                :params []}]
   :events
              [{:id     ::on-something
                :name   "onSomething"
                :params [{:name "ep1"
                          :type "marshalled-type"}]}
               {:id         ::on-something-deprecated
                :name       "onSomethingDeprecated"
                :deprecated "Use onSomething instead."
                :params     [{:name "ep1"
                              :type "some-type"}]}
               {:id     ::on-something-else
                :name   "onSomethingElse"
                :params [{:name "ep1"
                          :type "some-type"}
                         {:name "ep2"
                          :type "marshalled-type"}]}
               {:id         ::on-something-missing
                :name       "onSomethingMissing"
                :deprecated "Use onSomethingElse instead."
                :params     [{:name "ep1"
                              :type "some-type"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))

; ---------------------------------------------------------------------------------------------------------------------------

;(print-code (macroexpand-1 '(gen-wrap :function ::get-something config p1)))
;(print-code (gen-call :event ::on-something {} 'chan 1 2 3))
;(print-code (macroexpand-1 '(gen-wrap :event ::on-something config chan (1 2 3))))