(ns chromex.app.idle
  "Use the chrome.idle API to detect when the machine's idle state changes.

     * available since Chrome 38
     * https://developer.chrome.com/apps/idle"

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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [new-state] where:

     |new-state| - https://developer.chrome.com/apps/idle#property-callback-newState.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/idle#method-queryState."
  ([detection-interval-in-seconds] (gen-call :function ::query-state &form detection-interval-in-seconds)))

(defmacro set-detection-interval
  "Sets the interval, in seconds, used to determine when the system is in an idle state for onStateChanged events. The default
   interval is 60 seconds.

     |interval-in-seconds| - Threshold, in seconds, used to determine when the system is in an idle state.

   https://developer.chrome.com/apps/idle#method-setDetectionInterval."
  ([interval-in-seconds] (gen-call :function ::set-detection-interval &form interval-in-seconds)))

(defmacro get-auto-lock-delay
  "Gets the time, in seconds, it takes until the screen is locked automatically while idle. Returns a zero duration if the
   screen is never locked automatically. Currently supported on Chrome OS only.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [delay] where:

     |delay| - Time, in seconds, until the screen is locked automatically while idle. This is zero if the screen never locks
               automatically.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/idle#method-getAutoLockDelay."
  ([] (gen-call :function ::get-auto-lock-delay &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-state-changed-events
  "Fired when the system changes to an active, idle or locked state. The event fires with 'locked' if the screen is locked or
   the screensaver activates, 'idle' if the system is unlocked and the user has not generated any input for a specified number
   of seconds, and 'active' when the user generates input on an idle system.

   Events will be put on the |channel| with signature [::on-state-changed [new-state]] where:

     |new-state| - https://developer.chrome.com/apps/idle#property-onStateChanged-newState.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/idle#event-onStateChanged."
  ([channel & args] (apply gen-call :event ::on-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.idle namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.idle",
   :since "38",
   :functions
   [{:id ::query-state,
     :name "queryState",
     :callback? true,
     :params
     [{:name "detection-interval-in-seconds", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "new-state", :type "idle.IdleState"}]}}]}
    {:id ::set-detection-interval,
     :name "setDetectionInterval",
     :params [{:name "interval-in-seconds", :type "integer"}]}
    {:id ::get-auto-lock-delay,
     :name "getAutoLockDelay",
     :since "73",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "delay", :type "integer"}]}}]}],
   :events [{:id ::on-state-changed, :name "onStateChanged", :params [{:name "new-state", :type "idle.IdleState"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))