(ns chromex.idle
  "Use the chrome.idle API to detect when the machine's idle state changes.
   
     * available since Chrome 6
     * https://developer.chrome.com/extensions/idle"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro query-state
  "Returns 'locked' if the system is locked, 'idle' if the user has not generated any input for a specified number of seconds,
   or 'active' otherwise.
   
     |detectionIntervalInSeconds| - The system is considered idle if detectionIntervalInSeconds seconds have elapsed since
                                    the last user input detected.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([detection-interval-in-seconds #_callback] (gen-call :function ::query-state &form detection-interval-in-seconds)))

(defmacro set-detection-interval
  "Sets the interval, in seconds, used to determine when the system is in an idle state for onStateChanged events. The default
   interval is 60 seconds.
   
     |intervalInSeconds| - Threshold, in seconds, used to determine when the system is in an idle state."
  ([interval-in-seconds] (gen-call :function ::set-detection-interval &form interval-in-seconds)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-state-changed-events
  "Fired when the system changes to an active, idle or locked state. The event fires with 'locked' if the screen is locked or
   the screensaver activates, 'idle' if the system is unlocked and the user has not generated any input for a specified number
   of seconds, and 'active' when the user generates input on an idle system.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.idle",
   :since "6",
   :functions
   [{:id ::query-state,
     :name "queryState",
     :callback? true,
     :params
     [{:name "detection-interval-in-seconds", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "new-state", :type "idle.IdleState"}]}}]}
    {:id ::set-detection-interval,
     :name "setDetectionInterval",
     :since "25",
     :params [{:name "interval-in-seconds", :type "integer"}]}],
   :events [{:id ::on-state-changed, :name "onStateChanged", :params [{:name "new-state", :type "idle.IdleState"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))