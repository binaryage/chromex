(ns chromex.ext.experimental.devtools.console
  "Use the chrome.experimental.devtools.console API to retrieve messages from the inspected page console and post messages
   there.
     * https://developer.chrome.com/extensions/experimental.devtools.console"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

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
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))