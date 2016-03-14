(ns chromex.ext.declarative-web-request
  "Note: this API is currently on hold, without concrete plans to move to stable. Use the chrome.declarativeWebRequest API to
   intercept, block, or modify requests in-flight. It is significantly faster than the chrome.webRequest API because you can
   register rules that are evaluated in the browser rather than the JavaScript engine with reduces roundtrip latencies and
   allows higher efficiency.

     * available since Chrome 50
     * https://developer.chrome.com/extensions/declarativeWebRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-request-events
  "
   Events will be put on the |channel| with signature [::on-request []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/declarativeWebRequest#event-onRequest."
  ([channel & args] (apply gen-call :event ::on-request &form channel args)))

(defmacro tap-on-message-events
  "Fired when a message is sent via 'declarativeWebRequest.SendMessageToExtension' from an action of the declarative web
   request API.

   Events will be put on the |channel| with signature [::on-message [details]] where:

     |details| - https://developer.chrome.com/extensions/declarativeWebRequest#property-onMessage-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/declarativeWebRequest#event-onMessage."
  ([channel & args] (apply gen-call :event ::on-message &form channel args)))

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
  {:namespace "chrome.declarativeWebRequest",
   :since "50",
   :events
   [{:id ::on-request, :name "onRequest"}
    {:id ::on-message, :name "onMessage", :params [{:name "details", :type "object"}]}]})

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