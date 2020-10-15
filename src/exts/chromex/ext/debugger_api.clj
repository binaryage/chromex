(ns chromex.ext.debugger-api
  "The chrome.debugger API serves as an alternate transport for Chrome's remote debugging protocol. Use chrome.debugger to
   attach to one or more tabs to instrument network interaction, debug JavaScript, mutate the DOM and CSS, etc. Use the
   Debuggee tabId to target tabs with sendCommand and route events by tabId from onEvent callbacks.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/debugger"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro attach
  "Attaches debugger to the given target.

     |target| - Debugging target to which you want to attach.
     |required-version| - Required debugging protocol version ('0.1'). One can only attach to the debuggee with matching
                          major version and greater or equal minor version. List of the protocol versions can be obtained
                          here.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/debugger#method-attach."
  ([target required-version] (gen-call :function ::attach &form target required-version)))

(defmacro detach
  "Detaches debugger from the given target.

     |target| - Debugging target from which you want to detach.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/debugger#method-detach."
  ([target] (gen-call :function ::detach &form target)))

(defmacro send-command
  "Sends given command to the debugging target.

     |target| - Debugging target to which you want to send the command.
     |method| - Method name. Should be one of the methods defined by the remote debugging protocol.
     |command-params| - JSON object with request parameters. This object must conform to the remote debugging params scheme
                        for given method.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - JSON object with the response. Structure of the response varies depending on the method name and is defined by
                the 'returns' attribute of the command description in the remote debugging protocol.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/debugger#method-sendCommand."
  ([target method command-params] (gen-call :function ::send-command &form target method command-params))
  ([target method] `(send-command ~target ~method :omit)))

(defmacro get-targets
  "Returns the list of available debug targets.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Array of TargetInfo objects corresponding to the available debug targets.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/debugger#method-getTargets."
  ([] (gen-call :function ::get-targets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-event-events
  "Fired whenever debugging target issues instrumentation event.

   Events will be put on the |channel| with signature [::on-event [source method params]] where:

     |source| - The debuggee that generated this event.
     |method| - Method name. Should be one of the notifications defined by the remote debugging protocol.
     |params| - JSON object with the parameters. Structure of the parameters varies depending on the method name and is
                defined by the 'parameters' attribute of the event description in the remote debugging protocol.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/debugger#event-onEvent."
  ([channel & args] (apply gen-call :event ::on-event &form channel args)))

(defmacro tap-on-detach-events
  "Fired when browser terminates debugging session for the tab. This happens when either the tab is being closed or Chrome
   DevTools is being invoked for the attached tab.

   Events will be put on the |channel| with signature [::on-detach [source reason]] where:

     |source| - The debuggee that was detached.
     |reason| - Connection termination reason.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/debugger#event-onDetach."
  ([channel & args] (apply gen-call :event ::on-detach &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.debugger-api namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.debugger",
   :since "38",
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))