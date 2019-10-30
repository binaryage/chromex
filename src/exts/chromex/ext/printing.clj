(ns chromex.ext.printing
  "Use the chrome.printing API to send print jobs to printers
   installed on Chromebook.

     * available since Chrome master
     * https://developer.chrome.com/extensions/printing"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

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
   :since "master",
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