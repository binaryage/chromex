(ns chromex.ext.declarative-web-request
  "Note: this API is currently on hold, without concrete plans to move to stable. Use the chrome.declarativeWebRequest API to
   intercept, block, or modify requests in-flight. It is significantly faster than the chrome.webRequest API because you can
   register rules that are evaluated in the browser rather than the JavaScript engine, which reduces roundtrip latencies and
   allows higher efficiency.

     * available since Chrome 87
     * https://developer.chrome.com/extensions/declarativeWebRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

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
  "Taps all valid non-deprecated events in chromex.ext.declarative-web-request namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.declarativeWebRequest",
   :since "87",
   :events
   [{:id ::on-request, :name "onRequest"}
    {:id ::on-message, :name "onMessage", :params [{:name "details", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))