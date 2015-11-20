(ns chromex.experimental.devtools.console
  "Use the chrome.experimental.devtools.console API to retrieve messages from the inspected page console and post
   messages there.
     * https://developer.chrome.com/extensions/experimental.devtools.console"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro add-message
  "Adds a message to the console.
   
     |severity| - The severity of the message.
     |text| - The text of the message."
  [severity text]
  (gen-call :function ::add-message (meta &form) severity text))

(defmacro get-messages
  "Retrieves console messages.
   
     |callback| - A function that receives console messages when the request completes."
  [#_callback]
  (gen-call :function ::get-messages (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-message-added
  "Fired when a new message is added to the console."
  [channel]
  (gen-call :event ::on-message-added (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.experimental.devtools.console",
   :functions
   [{:id ::add-message,
     :name "addMessage",
     :params
     [{:name "severity", :type "experimental.devtools.console.Severity"} {:name "text", :type "string"}]}
    {:id ::get-messages,
     :name "getMessages",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params [{:name "messages", :type "[array-of-experimental.devtools.console.ConsoleMessages]"}]}}]}],
   :events
   [{:id ::on-message-added,
     :name "onMessageAdded",
     :params [{:name "message", :type "experimental.devtools.console.ConsoleMessage"}]}]})

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