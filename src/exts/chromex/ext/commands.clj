(ns chromex.ext.commands
  "Use the commands API to add keyboard shortcuts that trigger actions in your extension, for example, an action to open the
   browser action or send a command to the extension.

     * available since Chrome 25
     * https://developer.chrome.com/extensions/commands"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-all
  "Returns all the registered extension commands for this extension and their shortcut (if active).

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [commands] where:

     |commands| - https://developer.chrome.com/extensions/commands#property-callback-commands.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/commands#method-getAll."
  ([] (gen-call :function ::get-all &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-command-events
  "Fired when a registered command is activated using a keyboard shortcut.

   Events will be put on the |channel| with signature [::on-command [command]] where:

     |command| - https://developer.chrome.com/extensions/commands#property-onCommand-command.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/commands#event-onCommand."
  ([channel & args] (apply gen-call :event ::on-command &form channel args)))

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
  {:namespace "chrome.commands",
   :since "25",
   :functions
   [{:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "commands", :type "[array-of-commands.Commands]"}]}}]}],
   :events [{:id ::on-command, :name "onCommand", :params [{:name "command", :type "string"}]}]})

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