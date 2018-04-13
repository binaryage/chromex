(ns chromex.ext.enterprise.reporting-private
  "Private API for reporting Chrome browser status to admin console.

     * available since Chrome master"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro upload-chrome-desktop-report
  "Uploads the status of Chrome browser to the admin console by sending request to the DMServer. Sets runtime.lastError on
   failure.

     |report| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([report] (gen-call :function ::upload-chrome-desktop-report &form report)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.enterprise.reporting-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.reportingPrivate",
   :since "master",
   :functions
   [{:id ::upload-chrome-desktop-report,
     :name "uploadChromeDesktopReport",
     :callback? true,
     :params [{:name "report", :type "object"} {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))