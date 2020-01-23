(ns chromex.ext.printing
  "Use the chrome.printing API to send print jobs to printers
   installed on Chromebook.

     * available since Chrome 81
     * https://developer.chrome.com/extensions/printing"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro submit-job
  "Submits the job for print.

     |request| - https://developer.chrome.com/extensions/printing#property-submitJob-request.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - https://developer.chrome.com/extensions/printing#property-callback-response.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/printing#method-submitJob."
  ([request] (gen-call :function ::submit-job &form request)))

(defmacro get-printers
  "Returns the list of available printers on the device. This includes manually added, enterprise and discovered printers.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [printers] where:

     |printers| - https://developer.chrome.com/extensions/printing#property-callback-printers.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/printing#method-getPrinters."
  ([] (gen-call :function ::get-printers &form)))

(defmacro get-printer-info
  "Returns the status and capabilities of the printer in  CDD format. This call will fail with a runtime error if no printers
   with given id are installed.

     |printer-id| - https://developer.chrome.com/extensions/printing#property-getPrinterInfo-printerId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - https://developer.chrome.com/extensions/printing#property-callback-response.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/printing#method-getPrinterInfo."
  ([printer-id] (gen-call :function ::get-printer-info &form printer-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-job-status-changed-events
  "Event fired when the status of the job is changed. This is only fired for the jobs created by this extension.

   Events will be put on the |channel| with signature [::on-job-status-changed [job-id status]] where:

     |job-id| - https://developer.chrome.com/extensions/printing#property-onJobStatusChanged-jobId.
     |status| - https://developer.chrome.com/extensions/printing#property-onJobStatusChanged-status.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/printing#event-onJobStatusChanged."
  ([channel & args] (apply gen-call :event ::on-job-status-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.printing namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.printing",
   :since "81",
   :functions
   [{:id ::submit-job,
     :name "submitJob",
     :since "master",
     :callback? true,
     :params
     [{:name "request", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "object"}]}}]}
    {:id ::get-printers,
     :name "getPrinters",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "printers", :type "[array-of-objects]"}]}}]}
    {:id ::get-printer-info,
     :name "getPrinterInfo",
     :callback? true,
     :params
     [{:name "printer-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "object"}]}}]}],
   :events
   [{:id ::on-job-status-changed,
     :name "onJobStatusChanged",
     :params [{:name "job-id", :type "string"} {:name "status", :type "unknown-type"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))