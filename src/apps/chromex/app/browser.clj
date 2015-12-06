(ns chromex.app.browser
  "Use the chrome.browser API to interact with the Chrome browser
   associated with the current application and Chrome profile.
   
     * available since Chrome 42
     * https://developer.chrome.com/extensions/browser"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-tab
  "Opens a new tab in a browser window associated with the current application and Chrome profile. If no browser window for
   the Chrome profile is opened, a new one is opened prior to creating the new tab. |options| - Configures how the tab should
   be opened. |callback| - Called when the tab was successfully created, or failed to be created. If failed,
   'runtime.lastError' will be set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::open-tab &form options)))

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
  {:namespace "chrome.browser",
   :since "42",
   :functions
   [{:id ::open-tab,
     :name "openTab",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}]})

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