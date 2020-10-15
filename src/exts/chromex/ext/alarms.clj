(ns chromex.ext.alarms
  "Use the chrome.alarms API to schedule code to run
   periodically or at a specified time in the future.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/alarms"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates an alarm.  Near the time(s) specified by alarmInfo, the onAlarm event is fired. If there is another alarm with the
   same name (or no name if none is specified), it will be cancelled and replaced by this alarm.In order to reduce the load on
   the user's machine, Chrome limits alarms to at most once every 1 minute but may delay them an arbitrary amount more.  That
   is, setting delayInMinutes or periodInMinutes to less than 1 will not be honored and will cause a warning.  when can be set
   to less than 1 minute after 'now' without warning but won't actually cause the alarm to fire for at least 1 minute.To help
   you debug your app or extension, when you've loaded it unpacked, there's no limit to how often the alarm can fire.

     |name| - Optional name to identify this alarm. Defaults to the empty string.
     |alarm-info| - Describes when the alarm should fire.  The initial time must be specified by either when or
                    delayInMinutes (but not both).  If periodInMinutes is set, the alarm will repeat every periodInMinutes
                    minutes after the initial event.  If neither when or delayInMinutes is set for a repeating alarm,
                    periodInMinutes is used as the default for delayInMinutes.

   https://developer.chrome.com/extensions/alarms#method-create."
  ([name alarm-info] (gen-call :function ::create &form name alarm-info)))

(defmacro get
  "Retrieves details about the specified alarm.

     |name| - The name of the alarm to get. Defaults to the empty string.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [alarm] where:

     |alarm| - https://developer.chrome.com/extensions/alarms#property-callback-alarm.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/alarms#method-get."
  ([name] (gen-call :function ::get &form name))
  ([] `(get :omit)))

(defmacro get-all
  "Gets an array of all the alarms.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [alarms] where:

     |alarms| - https://developer.chrome.com/extensions/alarms#property-callback-alarms.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/alarms#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro clear
  "Clears the alarm with the given name.

     |name| - The name of the alarm to clear. Defaults to the empty string.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [was-cleared] where:

     |was-cleared| - https://developer.chrome.com/extensions/alarms#property-callback-wasCleared.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/alarms#method-clear."
  ([name] (gen-call :function ::clear &form name))
  ([] `(clear :omit)))

(defmacro clear-all
  "Clears all alarms.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [was-cleared] where:

     |was-cleared| - https://developer.chrome.com/extensions/alarms#property-callback-wasCleared.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/alarms#method-clearAll."
  ([] (gen-call :function ::clear-all &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-alarm-events
  "Fired when an alarm has elapsed. Useful for event pages.

   Events will be put on the |channel| with signature [::on-alarm [alarm]] where:

     |alarm| - The alarm that has elapsed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/alarms#event-onAlarm."
  ([channel & args] (apply gen-call :event ::on-alarm &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.alarms namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.alarms",
   :since "38",
   :functions
   [{:id ::create,
     :name "create",
     :params [{:name "name", :optional? true, :type "string"} {:name "alarm-info", :type "object"}]}
    {:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "name", :optional? true, :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "alarm", :optional? true, :type "alarms.Alarm"}]}}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "alarms", :type "[array-of-alarms.Alarms]"}]}}]}
    {:id ::clear,
     :name "clear",
     :callback? true,
     :params
     [{:name "name", :optional? true, :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "was-cleared", :type "boolean"}]}}]}
    {:id ::clear-all,
     :name "clearAll",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "was-cleared", :type "boolean"}]}}]}],
   :events [{:id ::on-alarm, :name "onAlarm", :params [{:name "alarm", :type "alarms.Alarm"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))