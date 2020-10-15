(ns chromex.app.app.runtime
  "Use the chrome.app.runtime API to manage the app lifecycle.
   The app runtime manages app installation, controls the event page, and can
   shut down the app at anytime.

     * available since Chrome 38
     * https://developer.chrome.com/apps/app.runtime"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-embed-requested-events
  "Fired when an embedding app requests to embed this app. This event is only available on dev channel with the flag
   --enable-app-view.

   Events will be put on the |channel| with signature [::on-embed-requested [request]] where:

     |request| - https://developer.chrome.com/apps/app.runtime#property-onEmbedRequested-request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.runtime#event-onEmbedRequested."
  ([channel & args] (apply gen-call :event ::on-embed-requested &form channel args)))

(defmacro tap-on-launched-events
  "Fired when an app is launched from the launcher.

   Events will be put on the |channel| with signature [::on-launched [launch-data]] where:

     |launch-data| - https://developer.chrome.com/apps/app.runtime#property-onLaunched-launchData.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.runtime#event-onLaunched."
  ([channel & args] (apply gen-call :event ::on-launched &form channel args)))

(defmacro tap-on-restarted-events
  "Fired at Chrome startup to apps that were running when Chrome last shut down, or when apps have been requested to restart
   from their previous state for other reasons (e.g. when the user revokes access to an app's retained files the runtime will
   restart the app). In these situations if apps do not have an onRestarted handler they will be sent an onLaunched  event
   instead.

   Events will be put on the |channel| with signature [::on-restarted []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.runtime#event-onRestarted."
  ([channel & args] (apply gen-call :event ::on-restarted &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.app.runtime namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.app.runtime",
   :since "38",
   :events
   [{:id ::on-embed-requested, :name "onEmbedRequested", :params [{:name "request", :type "object"}]}
    {:id ::on-launched, :name "onLaunched", :params [{:name "launch-data", :optional? true, :type "object"}]}
    {:id ::on-restarted, :name "onRestarted"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))