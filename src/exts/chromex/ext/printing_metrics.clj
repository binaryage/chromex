(ns chromex.ext.printing-metrics
  "Use the chrome.printingMetrics API to fetch data about
   printing usage.

     * available since Chrome master
     * https://developer.chrome.com/extensions/printingMetrics"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-print-jobs
  "Returns the list of the finished print jobs.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [jobs] where:

     |jobs| - https://developer.chrome.com/extensions/printingMetrics#property-callback-jobs.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/printingMetrics#method-getPrintJobs."
  ([] (gen-call :function ::get-print-jobs &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-print-job-finished-events
  "Event fired when the print job is finished. This includes any of termination statuses: FAILED, CANCELED and PRINTED.

   Events will be put on the |channel| with signature [::on-print-job-finished [job-info]] where:

     |job-info| - https://developer.chrome.com/extensions/printingMetrics#property-onPrintJobFinished-jobInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/printingMetrics#event-onPrintJobFinished."
  ([channel & args] (apply gen-call :event ::on-print-job-finished &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.printing-metrics namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.printingMetrics",
   :since "master",
   :functions
   [{:id ::get-print-jobs,
     :name "getPrintJobs",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "jobs", :type "[array-of-printingMetrics.PrintJobInfos]"}]}}]}],
   :events
   [{:id ::on-print-job-finished,
     :name "onPrintJobFinished",
     :params [{:name "job-info", :type "printingMetrics.PrintJobInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))