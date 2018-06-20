(ns chromex.app.system.power-source
  "The chrome.system.powerSource API allows Chrome Kiosk Apps to
   query the state of connected power sources.

     * available since Chrome 69
     * https://developer.chrome.com/apps/system.powerSource"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-power-source-info
  "Requests information on attached power sources.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [power-source-info] where:

     |power-source-info| - https://developer.chrome.com/apps/system.powerSource#property-callback-powerSourceInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/system.powerSource#method-getPowerSourceInfo."
  ([] (gen-call :function ::get-power-source-info &form)))

(defmacro request-status-update
  "Requests a power source status update. Resulting power source status updates are observable using 'onPowerChanged'.

   https://developer.chrome.com/apps/system.powerSource#method-requestStatusUpdate."
  ([] (gen-call :function ::request-status-update &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-power-changed-events
  "Event for changes in the set of connected power sources.

   Events will be put on the |channel| with signature [::on-power-changed [power-source-info]] where:

     |power-source-info| - https://developer.chrome.com/apps/system.powerSource#property-onPowerChanged-powerSourceInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/system.powerSource#event-onPowerChanged."
  ([channel & args] (apply gen-call :event ::on-power-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.system.power-source namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.powerSource",
   :since "69",
   :functions
   [{:id ::get-power-source-info,
     :name "getPowerSourceInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "power-source-info", :optional? true, :type "[array-of-system.powerSource.PowerSourceInfos]"}]}}]}
    {:id ::request-status-update, :name "requestStatusUpdate"}],
   :events
   [{:id ::on-power-changed,
     :name "onPowerChanged",
     :params [{:name "power-source-info", :type "[array-of-system.powerSource.PowerSourceInfos]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))