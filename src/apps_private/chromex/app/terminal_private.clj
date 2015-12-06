(ns chromex.app.terminal-private
  "  * available since Chrome 18
     * https://developer.chrome.com/extensions/terminalPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-terminal-process
  "Starts new process.
   
     |processName| - Name of the process to open. Initially only 'crosh' is supported. Another processes may be added in
                     future.
     |callback| - Returns pid of the launched process. If no process was launched returns -1.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([process-name #_callback] (gen-call :function ::open-terminal-process &form process-name)))

(defmacro close-terminal-process
  "Closes previousy opened process.
   
     |pid| - Process id of the process we want to close.
     |callback| - Function that gets called when close operation is started for the process. Returns success of the function.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([pid #_callback] (gen-call :function ::close-terminal-process &form pid)))

(defmacro send-input
  "Sends input that will be routed to stdin of the process with the specified pid.
   
     |pid| - The pid of the process to which we want to send input.
     |input| - Input we are sending to the process.
     |callback| - Callback that will be called when sendInput method ends. Returns success.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([pid input #_callback] (gen-call :function ::send-input &form pid input)))

(defmacro on-terminal-resize
  "Notify the process with the id pid that terminal window size has changed.
   
     |pid| - The pid of the process.
     |width| - New window width (as column count).
     |height| - New window height (as row count).
     |callback| - Callback that will be called when sendInput method ends. Returns success.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([pid width height #_callback] (gen-call :function ::on-terminal-resize &form pid width height)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-process-output-events
  "Fired when an opened process writes something to its output.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-process-output &form channel args)))

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
  {:namespace "chrome.terminalPrivate",
   :since "18",
   :functions
   [{:id ::open-terminal-process,
     :name "openTerminalProcess",
     :callback? true,
     :params
     [{:name "process-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "pid", :type "integer"}]}}]}
    {:id ::close-terminal-process,
     :name "closeTerminalProcess",
     :callback? true,
     :params
     [{:name "pid", :type "integer"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::send-input,
     :name "sendInput",
     :callback? true,
     :params
     [{:name "pid", :type "integer"}
      {:name "input", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::on-terminal-resize,
     :name "onTerminalResize",
     :since "19",
     :callback? true,
     :params
     [{:name "pid", :type "integer"}
      {:name "width", :type "integer"}
      {:name "height", :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "success", :type "boolean"}]}}]}],
   :events
   [{:id ::on-process-output,
     :name "onProcessOutput",
     :params
     [{:name "pid", :type "integer"}
      {:name "type", :type "terminalPrivate.OutputType"}
      {:name "text", :type "string"}]}]})

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