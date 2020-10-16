(ns chromex.ext.processes
  "Use the chrome.processes API to interact with the browser's
   processes.

     * available since Chrome 88
     * https://developer.chrome.com/extensions/processes"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-process-id-for-tab
  "Returns the ID of the renderer process for the specified tab.

     |tab-id| - The ID of the tab for which the renderer process ID is to be returned.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [process-id] where:

     |process-id| - Process ID of the tab's renderer process.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/processes#method-getProcessIdForTab."
  ([tab-id] (gen-call :function ::get-process-id-for-tab &form tab-id)))

(defmacro terminate
  "Terminates the specified renderer process. Equivalent to visiting about:crash, but without changing the tab's URL.

     |process-id| - The ID of the process to be terminated.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [did-terminate] where:

     |did-terminate| - True if terminating the process was successful, and false otherwise.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/processes#method-terminate."
  ([process-id] (gen-call :function ::terminate &form process-id)))

(defmacro get-process-info
  "Retrieves the process information for each process ID specified.

     |process-ids| - The list of process IDs or single process ID for which to return the process information. An empty list
                     indicates all processes are requested.
     |include-memory| - True if detailed memory usage is required. Note, collecting memory usage information incurs extra
                        CPU usage and should only be queried for when needed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [processes] where:

     |processes| - A dictionary of 'Process' objects for each requested process that is a live child process of the current
                   browser process, indexed by process ID. Metrics requiring aggregation over time will not be populated in
                   each Process object.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/processes#method-getProcessInfo."
  ([process-ids include-memory] (gen-call :function ::get-process-info &form process-ids include-memory)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-updated-events
  "Fired each time the Task Manager updates its process statistics, providing the dictionary of updated Process objects,
   indexed by process ID.

   Events will be put on the |channel| with signature [::on-updated [processes]] where:

     |processes| - A dictionary of updated 'Process' objects for each live process in the browser, indexed by process ID.
                   Metrics requiring aggregation over time will be populated in each Process object.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/processes#event-onUpdated."
  ([channel & args] (apply gen-call :event ::on-updated &form channel args)))

(defmacro tap-on-updated-with-memory-events
  "Fired each time the Task Manager updates its process statistics, providing the dictionary of updated Process objects,
   indexed by process ID. Identical to onUpdate, with the addition of memory usage details included in each Process object.
   Note, collecting memory usage information incurs extra CPU usage and should only be listened for when needed.

   Events will be put on the |channel| with signature [::on-updated-with-memory [processes]] where:

     |processes| - A dictionary of updated 'Process' objects for each live process in the browser, indexed by process ID.
                   Memory usage details will be included in each Process object.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/processes#event-onUpdatedWithMemory."
  ([channel & args] (apply gen-call :event ::on-updated-with-memory &form channel args)))

(defmacro tap-on-created-events
  "Fired each time a process is created, providing the corrseponding Process object.

   Events will be put on the |channel| with signature [::on-created [process]] where:

     |process| - Details of the process that was created. Metrics requiring aggregation over time will not be populated in the
                 object.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/processes#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-unresponsive-events
  "Fired each time a process becomes unresponsive, providing the corrseponding Process object.

   Events will be put on the |channel| with signature [::on-unresponsive [process]] where:

     |process| - Details of the unresponsive process. Metrics requiring aggregation over time will not be populated in the
                 object. Only available for renderer processes.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/processes#event-onUnresponsive."
  ([channel & args] (apply gen-call :event ::on-unresponsive &form channel args)))

(defmacro tap-on-exited-events
  "Fired each time a process is terminated, providing the type of exit.

   Events will be put on the |channel| with signature [::on-exited [process-id exit-type exit-code]] where:

     |process-id| - The ID of the process that exited.
     |exit-type| - The type of exit that occurred for the process - normal, abnormal, killed, crashed. Only available for
                   renderer processes.
     |exit-code| - The exit code if the process exited abnormally. Only available for renderer processes.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/processes#event-onExited."
  ([channel & args] (apply gen-call :event ::on-exited &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.processes namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.processes",
   :since "88",
   :functions
   [{:id ::get-process-id-for-tab,
     :name "getProcessIdForTab",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "process-id", :type "integer"}]}}]}
    {:id ::terminate,
     :name "terminate",
     :callback? true,
     :params
     [{:name "process-id", :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "did-terminate", :type "boolean"}]}}]}
    {:id ::get-process-info,
     :name "getProcessInfo",
     :callback? true,
     :params
     [{:name "process-ids", :type "integer-or-[array-of-integers]"}
      {:name "include-memory", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "processes", :type "object"}]}}]}],
   :events
   [{:id ::on-updated, :name "onUpdated", :params [{:name "processes", :type "object"}]}
    {:id ::on-updated-with-memory, :name "onUpdatedWithMemory", :params [{:name "processes", :type "object"}]}
    {:id ::on-created, :name "onCreated", :params [{:name "process", :type "processes.Process"}]}
    {:id ::on-unresponsive, :name "onUnresponsive", :params [{:name "process", :type "processes.Process"}]}
    {:id ::on-exited,
     :name "onExited",
     :params
     [{:name "process-id", :type "integer"}
      {:name "exit-type", :type "integer"}
      {:name "exit-code", :type "integer"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))