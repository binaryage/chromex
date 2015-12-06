(ns chromex.app.app.runtime
  "Use the chrome.app.runtime API to manage the app lifecycle.
   The app runtime manages app installation, controls the event page, and can
   shut down the app at anytime.
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/app.runtime"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-embed-requested-events
  "Fired when an embedding app requests to embed this app. This event is only available on dev channel with the flag
   --enable-app-view.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-embed-requested &form channel args)))
(defmacro tap-on-launched-events
  "Fired when an app is launched from the launcher.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-launched &form channel args)))
(defmacro tap-on-restarted-events
  "Fired at Chrome startup to apps that were running when Chrome last shut down, or when apps have been requested to restart
   from their previous state for other reasons (e.g. when the user revokes access to an app's retained files the runtime will
   restart the app). In these situations if apps do not have an onRestarted handler they will be sent an onLaunched  event
   instead.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-restarted &form channel args)))

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
  {:namespace "chrome.app.runtime",
   :since "23",
   :events
   [{:id ::on-embed-requested, :name "onEmbedRequested", :since "38", :params [{:name "request", :type "object"}]}
    {:id ::on-launched, :name "onLaunched", :params [{:name "launch-data", :optional? true, :type "object"}]}
    {:id ::on-restarted, :name "onRestarted", :since "24"}]})

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