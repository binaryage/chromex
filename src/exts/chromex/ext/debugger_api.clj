(ns chromex.ext.debugger-api
  "The chrome.debugger API serves as an alternate transport for Chrome's remote debugging protocol. Use chrome.debugger to
   attach to one or more tabs to instrument network interaction, debug JavaScript, mutate the DOM and CSS, etc. Use the
   Debuggee tabId to target tabs with sendCommand and route events by tabId from onEvent callbacks.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/debugger"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro attach
  "Attaches debugger to the given target.
   
     |target| - Debugging target to which you want to attach.
     |requiredVersion| - Required debugging protocol version ('0.1'). One can only attach to the debuggee with matching
                         major version and greater or equal minor version. List of the protocol versions can be obtained
                         here.
     |callback| - Called once the attach operation succeeds or fails. Callback receives no arguments. If the attach fails,
                  'runtime.lastError' will be set to the error message.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([target required-version #_callback] (gen-call :function ::attach &form target required-version)))

(defmacro detach
  "Detaches debugger from the given target.
   
     |target| - Debugging target from which you want to detach.
     |callback| - Called once the detach operation succeeds or fails. Callback receives no arguments. If the detach fails,
                  'runtime.lastError' will be set to the error message.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([target #_callback] (gen-call :function ::detach &form target)))

(defmacro send-command
  "Sends given command to the debugging target.
   
     |target| - Debugging target to which you want to send the command.
     |method| - Method name. Should be one of the methods defined by the remote debugging protocol.
     |commandParams| - JSON object with request parameters. This object must conform to the remote debugging params scheme
                       for given method.
     |callback| - Response body. If an error occurs while posting the message, the callback will be called with no arguments
                  and 'runtime.lastError' will be set to the error message.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([target method command-params #_callback] (gen-call :function ::send-command &form target method command-params))
  ([target method] `(send-command ~target ~method :omit)))

(defmacro get-targets
  "Returns the list of available debug targets.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-targets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-event-events
  "Fired whenever debugging target issues instrumentation event.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-event &form channel args)))
(defmacro tap-on-detach-events
  "Fired when browser terminates debugging session for the tab. This happens when either the tab is being closed or Chrome
   DevTools is being invoked for the attached tab.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-detach &form channel args)))

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
  {:namespace "chrome.debugger",
   :since "18",
   :functions
   [{:id ::attach,
     :name "attach",
     :callback? true,
     :params
     [{:name "target", :type "debugger.Debuggee"}
      {:name "required-version", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::detach,
     :name "detach",
     :callback? true,
     :params [{:name "target", :type "debugger.Debuggee"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::send-command,
     :name "sendCommand",
     :callback? true,
     :params
     [{:name "target", :type "debugger.Debuggee"}
      {:name "method", :type "string"}
      {:name "command-params", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :optional? true, :type "object"}]}}]}
    {:id ::get-targets,
     :name "getTargets",
     :since "28",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-debugger.TargetInfos]"}]}}]}],
   :events
   [{:id ::on-event,
     :name "onEvent",
     :params
     [{:name "source", :type "debugger.Debuggee"}
      {:name "method", :type "string"}
      {:name "params", :optional? true, :type "object"}]}
    {:id ::on-detach,
     :name "onDetach",
     :params [{:name "source", :type "debugger.Debuggee"} {:name "reason", :type "debugger.DetachReason"}]}]})

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