(ns chromex.app.gcm
  "Use chrome.gcm to enable apps and extensions to send and receive messages through the Google Cloud Messaging Service.

     * available since Chrome 38
     * https://developer.chrome.com/apps/gcm"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-message-size
  "The maximum size (in bytes) of all key/value pairs in a message.

   https://developer.chrome.com/apps/gcm#property-MAX_MESSAGE_SIZE."
  ([] (gen-call :property ::max-message-size &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro register
  "Registers the application with GCM. The registration ID will be returned by the callback. If register is called again with
   the same list of senderIds, the same registration ID will be returned.

     |sender-ids| - A list of server IDs that are allowed to send messages to the application. It should contain at least
                    one and no more than 100 sender IDs.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [registration-id] where:

     |registration-id| - A registration ID assigned to the application by the GCM.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/gcm#method-register."
  ([sender-ids] (gen-call :function ::register &form sender-ids)))

(defmacro unregister
  "Unregisters the application from GCM.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/gcm#method-unregister."
  ([] (gen-call :function ::unregister &form)))

(defmacro send
  "Sends a message according to its contents.

     |message| - A message to send to the other party via GCM.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [message-id] where:

     |message-id| - The ID of the message that the callback was issued for.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/gcm#method-send."
  ([message] (gen-call :function ::send &form message)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-message-events
  "Fired when a message is received through GCM.

   Events will be put on the |channel| with signature [::on-message [message]] where:

     |message| - A message received from another party via GCM.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/gcm#event-onMessage."
  ([channel & args] (apply gen-call :event ::on-message &form channel args)))

(defmacro tap-on-messages-deleted-events
  "Fired when a GCM server had to delete messages sent by an app server to the application. See Messages deleted event section
   of Cloud Messaging documentation for details on handling this event.

   Events will be put on the |channel| with signature [::on-messages-deleted []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/gcm#event-onMessagesDeleted."
  ([channel & args] (apply gen-call :event ::on-messages-deleted &form channel args)))

(defmacro tap-on-send-error-events
  "Fired when it was not possible to send a message to the GCM server.

   Events will be put on the |channel| with signature [::on-send-error [error]] where:

     |error| - An error that occured while trying to send the message either in Chrome or on the GCM server. Application can
               retry sending the message with a reasonable backoff and possibly longer time-to-live.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/gcm#event-onSendError."
  ([channel & args] (apply gen-call :event ::on-send-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.gcm namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.gcm",
   :since "38",
   :properties [{:id ::max-message-size, :name "MAX_MESSAGE_SIZE", :return-type "unknown-type"}],
   :functions
   [{:id ::register,
     :name "register",
     :callback? true,
     :params
     [{:name "sender-ids", :type "[array-of-strings]"}
      {:name "callback", :type :callback, :callback {:params [{:name "registration-id", :type "string"}]}}]}
    {:id ::unregister, :name "unregister", :callback? true, :params [{:name "callback", :type :callback}]}
    {:id ::send,
     :name "send",
     :callback? true,
     :params
     [{:name "message", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "message-id", :type "string"}]}}]}],
   :events
   [{:id ::on-message, :name "onMessage", :params [{:name "message", :type "object"}]}
    {:id ::on-messages-deleted, :name "onMessagesDeleted"}
    {:id ::on-send-error, :name "onSendError", :params [{:name "error", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))