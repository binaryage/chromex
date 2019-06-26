(ns chromex.ext.terminal-private
  "  * available since Chrome 31"

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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [id] where:

     |id| - Id of the launched process.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([process-name args] (gen-call :function ::open-terminal-process &form process-name args))
  ([process-name] `(open-terminal-process ~process-name :omit)))

(defmacro close-terminal-process
  "Closes previously opened process.

     |id| - Unique id of the process we want to close.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::close-terminal-process &form id)))

(defmacro send-input
  "Sends input that will be routed to stdin of the process with the specified id.

     |id| - The id of the process to which we want to send input.
     |input| - Input we are sending to the process.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id input] (gen-call :function ::send-input &form id input)))

(defmacro on-terminal-resize
  "Notify the process with the id id that terminal window size has changed.

     |id| - The id of the process.
     |width| - New window width (as column count).
     |height| - New window height (as row count).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id width height] (gen-call :function ::on-terminal-resize &form id width height)))

(defmacro ack-output
  "Called from |onProcessOutput| when the event is dispatched to terminal extension. Observing the terminal process output
   will be paused after |onProcessOutput| is dispatched until this method is called.

     |tab-id| - Tab ID from |onProcessOutput| event.
     |id| - The id of the process to which |onProcessOutput| was dispatched."
  ([tab-id id] (gen-call :function ::ack-output &form tab-id id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-process-output-events
  "Fired when an opened process writes something to its output. Observing further process output will be blocked until
   |ackOutput| for the terminal is called. Internally, first event argument will be ID of the tab that contains terminal
   instance for which this event is intended. This argument will be stripped before passing the event to the extension.

   Events will be put on the |channel| with signature [::on-process-output [id type text]] where:

     |id| - Id of the process from which the output came.
     |type| - Type of the output stream from which output came. When process exits, output type will be set to exit
     |text| - Text that was written to the output stream.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-process-output &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.terminal-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.terminalPrivate",
   :since "31",
   :functions
   [{:id ::open-terminal-process,
     :name "openTerminalProcess",
     :callback? true,
     :params
     [{:name "process-name", :type "string"}
      {:name "args", :optional? true, :since "66", :type "[array-of-strings]"}
      {:name "callback", :type :callback, :callback {:params [{:name "id", :type "string"}]}}]}
    {:id ::close-terminal-process,
     :name "closeTerminalProcess",
     :callback? true,
     :params
     [{:name "id", :since "74", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::send-input,
     :name "sendInput",
     :callback? true,
     :params
     [{:name "id", :since "74", :type "string"}
      {:name "input", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::on-terminal-resize,
     :name "onTerminalResize",
     :callback? true,
     :params
     [{:name "id", :since "74", :type "string"}
      {:name "width", :type "integer"}
      {:name "height", :type "integer"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::ack-output,
     :name "ackOutput",
     :since "49",
     :params [{:name "tab-id", :type "integer"} {:name "id", :since "74", :type "string"}]}],
   :events
   [{:id ::on-process-output,
     :name "onProcessOutput",
     :params
     [{:name "id", :since "74", :type "string"}
      {:name "type", :type "terminalPrivate.OutputType"}
      {:name "text", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))