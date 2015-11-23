(ns chromex.web-request
  "Use the chrome.webRequest API to observe and analyze traffic and to intercept, block, or modify requests in-flight.
   
     * available since Chrome 17
     * https://developer.chrome.com/extensions/webRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-max-handler-behavior-changed-calls-per10-minutes
  "The maximum number of times that handlerBehaviorChanged can be called per 10 minute sustained interval.
   handlerBehaviorChanged is an expensive function call that shouldn't be called often."
  []
  (gen-call :property ::max-handler-behavior-changed-calls-per10-minutes (meta &form)))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro handler-behavior-changed
  "Needs to be called when the behavior of the webRequest handlers has changed to prevent incorrect handling due to
   caching. This function call is expensive. Don't call it often."
  ([#_callback] (gen-call :function ::handler-behavior-changed (meta &form))))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-before-request-events
  "Fired when a request is about to occur."
  [channel]
  (gen-call :event ::on-before-request (meta &form) channel))

(defmacro tap-on-before-send-headers-events
  "Fired before sending an HTTP request, once the request headers are available. This may occur after a TCP connection
   is made to the server, but before any HTTP data is sent."
  [channel]
  (gen-call :event ::on-before-send-headers (meta &form) channel))

(defmacro tap-on-send-headers-events
  "Fired just before a request is going to be sent to the server (modifications of previous onBeforeSendHeaders
   callbacks are visible by the time onSendHeaders is fired)."
  [channel]
  (gen-call :event ::on-send-headers (meta &form) channel))

(defmacro tap-on-headers-received-events
  "Fired when HTTP response headers of a request have been received."
  [channel]
  (gen-call :event ::on-headers-received (meta &form) channel))

(defmacro tap-on-auth-required-events
  "Fired when an authentication failure is received. The listener has three options: it can provide authentication
   credentials, it can cancel the request and display the error page, or it can take no action on the challenge. If
   bad user credentials are provided, this may be called multiple times for the same request."
  [channel]
  (gen-call :event ::on-auth-required (meta &form) channel))

(defmacro tap-on-response-started-events
  "Fired when the first byte of the response body is received. For HTTP requests, this means that the status line and
   response headers are available."
  [channel]
  (gen-call :event ::on-response-started (meta &form) channel))

(defmacro tap-on-before-redirect-events
  "Fired when a server-initiated redirect is about to occur."
  [channel]
  (gen-call :event ::on-before-redirect (meta &form) channel))

(defmacro tap-on-completed-events
  "Fired when a request is completed."
  [channel]
  (gen-call :event ::on-completed (meta &form) channel))

(defmacro tap-on-error-occurred-events
  "Fired when an error occurs."
  [channel]
  (gen-call :event ::on-error-occurred (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webRequest",
   :since "17",
   :properties
   [{:id ::max-handler-behavior-changed-calls-per10-minutes,
     :name "MAX_HANDLER_BEHAVIOR_CHANGED_CALLS_PER_10_MINUTES",
     :since "23",
     :return-type "unknown-type"}],
   :functions
   [{:id ::handler-behavior-changed,
     :name "handlerBehaviorChanged",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-before-request, :name "onBeforeRequest", :params [{:name "details", :type "object"}]}
    {:id ::on-before-send-headers, :name "onBeforeSendHeaders", :params [{:name "details", :type "object"}]}
    {:id ::on-send-headers, :name "onSendHeaders", :params [{:name "details", :type "object"}]}
    {:id ::on-headers-received, :name "onHeadersReceived", :params [{:name "details", :type "object"}]}
    {:id ::on-auth-required,
     :name "onAuthRequired",
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::on-response-started, :name "onResponseStarted", :params [{:name "details", :type "object"}]}
    {:id ::on-before-redirect, :name "onBeforeRedirect", :params [{:name "details", :type "object"}]}
    {:id ::on-completed, :name "onCompleted", :params [{:name "details", :type "object"}]}
    {:id ::on-error-occurred, :name "onErrorOccurred", :params [{:name "details", :type "object"}]}]})

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