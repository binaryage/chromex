(ns chromex.ext.experimental.devtools.console
  "Use the chrome.experimental.devtools.console API to retrieve messages from the inspected page console and post messages
   there.
     * https://developer.chrome.com/extensions/experimental.devtools.console"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-message
  "Adds a message to the console.

     |severity| - The severity of the message.
     |text| - The text of the message.

   https://developer.chrome.com/extensions/experimental.devtools.console#method-addMessage."
  ([severity text] (gen-call :function ::add-message &form severity text)))

(defmacro get-messages
  "Retrieves console messages.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [messages] where:

     |messages| - Console messages.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/experimental.devtools.console#method-getMessages."
  ([] (gen-call :function ::get-messages &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-message-added-events
  "Fired when a new message is added to the console.

   Events will be put on the |channel| with signature [::on-message-added [message]] where:

     |message| - https://developer.chrome.com/extensions/experimental.devtools.console#property-onMessageAdded-message.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/experimental.devtools.console#event-onMessageAdded."
  ([channel & args] (apply gen-call :event ::on-message-added &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.experimental.devtools.console namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.experimental.devtools.console",
   :functions
   [{:id ::add-message,
     :name "addMessage",
     :params [{:name "severity", :type "experimental.devtools.console.Severity"} {:name "text", :type "string"}]}
    {:id ::get-messages,
     :name "getMessages",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "messages", :type "[array-of-experimental.devtools.console.ConsoleMessages]"}]}}]}],
   :events
   [{:id ::on-message-added,
     :name "onMessageAdded",
     :params [{:name "message", :type "experimental.devtools.console.ConsoleMessage"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))