(ns chromex.ext.app
  "  * available since Chrome 38
     * https://developer.chrome.com/extensions/app"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-is-installed
  "TODO

   https://developer.chrome.com/extensions/app#method-getIsInstalled."
  ([] (gen-call :function ::get-is-installed &form)))

(defmacro install-state
  "TODO

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [state] where:

     |state| - https://developer.chrome.com/extensions/app#property-callback-state.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/app#method-installState."
  ([] (gen-call :function ::install-state &form)))

(defmacro running-state
  "TODO

   https://developer.chrome.com/extensions/app#method-runningState."
  ([] (gen-call :function ::running-state &form)))

(defmacro get-details
  "TODO

   https://developer.chrome.com/extensions/app#method-getDetails."
  ([] (gen-call :function ::get-details &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.app namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.app",
   :since "38",
   :functions
   [{:id ::get-is-installed, :name "getIsInstalled", :return-type "boolean"}
    {:id ::install-state,
     :name "installState",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "state", :type "app.InstallState"}]}}]}
    {:id ::running-state, :name "runningState", :return-type "app.RunningState"}
    {:id ::get-details, :name "getDetails", :return-type "app.Details"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))