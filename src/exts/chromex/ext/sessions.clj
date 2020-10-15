(ns chromex.ext.sessions
  "Use the chrome.sessions API to query and restore tabs and windows from a browsing session.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/sessions"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-session-results
  "The maximum number of 'sessions.Session' that will be included in a requested list.

   https://developer.chrome.com/extensions/sessions#property-MAX_SESSION_RESULTS."
  ([] (gen-call :property ::max-session-results &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-recently-closed
  "Gets the list of recently closed tabs and/or windows.

     |filter| - https://developer.chrome.com/extensions/sessions#property-getRecentlyClosed-filter.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [sessions] where:

     |sessions| - The list of closed entries in reverse order that they were closed (the most recently closed tab or window
                  will be at index 0). The entries may contain either tabs or windows.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/sessions#method-getRecentlyClosed."
  ([filter] (gen-call :function ::get-recently-closed &form filter))
  ([] `(get-recently-closed :omit)))

(defmacro get-devices
  "Retrieves all devices with synced sessions.

     |filter| - https://developer.chrome.com/extensions/sessions#property-getDevices-filter.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [devices] where:

     |devices| - The list of 'sessions.Device' objects for each synced session, sorted in order from device with most recently
                 modified session to device with least recently modified session. 'tabs.Tab' objects are sorted by recency in
                 the 'windows.Window' of the 'sessions.Session' objects.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/sessions#method-getDevices."
  ([filter] (gen-call :function ::get-devices &form filter))
  ([] `(get-devices :omit)))

(defmacro restore
  "Reopens a 'windows.Window' or 'tabs.Tab', with an optional callback to run when the entry has been restored.

     |session-id| - The 'windows.Window.sessionId', or 'tabs.Tab.sessionId' to restore. If this parameter is not specified,
                    the most recently closed session is restored.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [restored-session] where:

     |restored-session| - A 'sessions.Session' containing the restored 'windows.Window' or 'tabs.Tab' object.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/sessions#method-restore."
  ([session-id] (gen-call :function ::restore &form session-id))
  ([] `(restore :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when recently closed tabs and/or windows are changed. This event does not monitor synced sessions changes.

   Events will be put on the |channel| with signature [::on-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/sessions#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.sessions namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.sessions",
   :since "38",
   :properties [{:id ::max-session-results, :name "MAX_SESSION_RESULTS", :return-type "unknown-type"}],
   :functions
   [{:id ::get-recently-closed,
     :name "getRecentlyClosed",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "sessions", :type "[array-of-sessions.Sessions]"}]}}]}
    {:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-sessions.Devices]"}]}}]}
    {:id ::restore,
     :name "restore",
     :callback? true,
     :params
     [{:name "session-id", :optional? true, :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "restored-session", :type "sessions.Session"}]}}]}],
   :events [{:id ::on-changed, :name "onChanged"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))