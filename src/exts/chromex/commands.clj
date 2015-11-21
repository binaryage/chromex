(ns chromex.commands
  "Use the commands API to add keyboard shortcuts that trigger actions in your extension, for example, an action to
   open the browser action or send a command to the extension.
   
     * available since Chrome 25
     * https://developer.chrome.com/extensions/commands"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-all
  "Returns all the registered extension commands for this extension and their shortcut (if active).
   
     |callback| - Called to return the registered commands."
  [#_callback]
  (gen-call :function ::get-all (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-command-events
  "Fired when a registered command is activated using a keyboard shortcut."
  [channel]
  (gen-call :event ::on-command (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.commands",
   :since "25",
   :functions
   [{:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "commands", :type "[array-of-commands.Commands]"}]}}]}],
   :events [{:id ::on-command, :name "onCommand", :params [{:name "command", :type "string"}]}]})

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