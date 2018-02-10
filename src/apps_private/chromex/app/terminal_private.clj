(ns chromex.app.terminal-private
  "  * available since Chrome 22"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-terminal-process
  "Starts new process.

     |process-name| - Name of the process to open. May be 'crosh' or 'vmshell'.
     |args| - Command line arguments to pass to the process.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [pid] where:

     |pid| - Id of the launched process.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([process-name args] (gen-call :function ::open-terminal-process &form process-name args))
  ([process-name] `(open-terminal-process ~process-name :omit)))

(defmacro close-terminal-process
  "Closes previously opened process.

     |pid| - Process id of the process we want to close.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([pid] (gen-call :function ::close-terminal-process &form pid)))

(defmacro send-input
  "Sends input that will be routed to stdin of the process with the specified id.

     |pid| - The id of the process to which we want to send input.
     |input| - Input we are sending to the process.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([pid input] (gen-call :function ::send-input &form pid input)))

(defmacro on-terminal-resize
  "Notify the process with the id id that terminal window size has changed.

     |pid| - The id of the process.
     |width| - New window width (as column count).
     |height| - New window height (as row count).

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([pid width height] (gen-call :function ::on-terminal-resize &form pid width height)))

(defmacro ack-output
  "Called from |onProcessOutput| when the event is dispatched to terminal extension. Observing the terminal process output
   will be paused after |onProcessOutput| is dispatched until this method is called.

     |tab-id| - Tab ID from |onProcessOutput| event.
     |pid| - The id of the process to which |onProcessOutput| was dispatched."
  ([tab-id pid] (gen-call :function ::ack-output &form tab-id pid)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-process-output-events
  "Fired when an opened process writes something to its output. Observing further process output will be blocked until
   |ackOutput| for the terminal is called. Internally, first event argument will be ID of the tab that contains terminal
   instance for which this event is intended. This argument will be stripped before passing the event to the extension.

   Events will be put on the |channel| with signature [::on-process-output [pid type text]] where:

     |pid| - Id of the process from which the output came.
     |type| - Type of the output stream from which output came. When process exits, output type will be set to exit
     |text| - Text that was written to the output stream.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-process-output &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.terminal-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.terminalPrivate",
   :since "22",
   :functions
   [{:id ::open-terminal-process,
     :name "openTerminalProcess",
     :callback? true,
     :params
     [{:name "process-name", :type "string"}
      {:name "args", :optional? true, :type "[array-of-strings]"}
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
     :callback? true,
     :params
     [{:name "pid", :type "integer"}
      {:name "width", :type "integer"}
      {:name "height", :type "integer"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::ack-output,
     :name "ackOutput",
     :since "49",
     :params [{:name "tab-id", :type "integer"} {:name "pid", :type "integer"}]}],
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))