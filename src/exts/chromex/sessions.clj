(ns chromex.sessions
  "Use the chrome.sessions API to query and restore tabs and windows from a browsing session.
   
     * available since Chrome 37
     * https://developer.chrome.com/extensions/sessions"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-max-session-results
  "The maximum number of 'sessions.Session' that will be included in a requested list."
  []
  (gen-call :property ::max-session-results (meta &form)))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-recently-closed
  "Gets the list of recently closed tabs and/or windows."
  [filter #_callback]
  (gen-call :function ::get-recently-closed (meta &form) filter))

(defmacro get-devices
  "Retrieves all devices with synced sessions."
  [filter #_callback]
  (gen-call :function ::get-devices (meta &form) filter))

(defmacro restore
  "Reopens a 'windows.Window' or 'tabs.Tab', with an optional callback to run when the entry has been restored.
   
     |sessionId| - The 'windows.Window.sessionId', or 'tabs.Tab.sessionId' to restore. If this parameter is not
                   specified, the most recently closed session is restored."
  [session-id #_callback]
  (gen-call :function ::restore (meta &form) session-id))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-changed
  "Fired when recently closed tabs and/or windows are changed. This event does not monitor synced sessions changes."
  [channel]
  (gen-call :event ::on-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.sessions",
   :since "37",
   :properties [{:id ::max-session-results, :name "MAX_SESSION_RESULTS", :return-type "unknown-type"}],
   :functions
   [{:id ::get-recently-closed,
     :name "getRecentlyClosed",
     :callback? true,
     :params
     [{:name "filter", :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "sessions", :type "[array-of-sessions.Sessions]"}]}}]}
    {:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params
     [{:name "filter", :type "sessions.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-sessions.Devices]"}]}}]}
    {:id ::restore,
     :name "restore",
     :callback? true,
     :params
     [{:name "session-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "restored-session", :type "sessions.Session"}]}}]}],
   :events [{:id ::on-changed, :name "onChanged"}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))