(ns chromex.ext.idle
  "Use the chrome.idle API to detect when the machine's idle state changes.

     * available since Chrome 27
     * https://developer.chrome.com/extensions/idle"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro query-state
  "Returns 'locked' if the system is locked, 'idle' if the user has not generated any input for a specified number of seconds,
   or 'active' otherwise.

     |detection-interval-in-seconds| - The system is considered idle if detectionIntervalInSeconds seconds have elapsed
                                       since the last user input detected.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [new-state] where:

     |new-state| - https://developer.chrome.com/extensions/idle#property-callback-newState.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/idle#method-queryState."
  ([detection-interval-in-seconds] (gen-call :function ::query-state &form detection-interval-in-seconds)))

(defmacro set-detection-interval
  "Sets the interval, in seconds, used to determine when the system is in an idle state for onStateChanged events. The default
   interval is 60 seconds.

     |interval-in-seconds| - Threshold, in seconds, used to determine when the system is in an idle state.

   https://developer.chrome.com/extensions/idle#method-setDetectionInterval."
  ([interval-in-seconds] (gen-call :function ::set-detection-interval &form interval-in-seconds)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-state-changed-events
  "Fired when the system changes to an active, idle or locked state. The event fires with 'locked' if the screen is locked or
   the screensaver activates, 'idle' if the system is unlocked and the user has not generated any input for a specified number
   of seconds, and 'active' when the user generates input on an idle system.

   Events will be put on the |channel| with signature [::on-state-changed [new-state]] where:

     |new-state| - https://developer.chrome.com/extensions/idle#property-onStateChanged-newState.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/idle#event-onStateChanged."
  ([channel & args] (apply gen-call :event ::on-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.idle namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.idle",
   :since "27",
   :functions
   [{:id ::query-state,
     :name "queryState",
     :callback? true,
     :params
     [{:name "detection-interval-in-seconds", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "new-state", :type "idle.IdleState"}]}}]}
    {:id ::set-detection-interval,
     :name "setDetectionInterval",
     :params [{:name "interval-in-seconds", :type "integer"}]}],
   :events [{:id ::on-state-changed, :name "onStateChanged", :params [{:name "new-state", :type "idle.IdleState"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))