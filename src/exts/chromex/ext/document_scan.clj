(ns chromex.ext.document-scan
  "Use the chrome.documentScan API to discover and retrieve
   images from attached paper document scanners.

     * available since Chrome 44
     * https://developer.chrome.com/extensions/documentScan"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro scan
  "Performs a document scan.  On success, the PNG data will be sent to the callback.

     |options| - Object containing scan parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/documentScan#property-callback-result.

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/documentScan#method-scan."
  ([options] (gen-call :function ::scan &form options)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.document-scan namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.documentScan",
   :since "44",
   :functions
   [{:id ::scan,
     :name "scan",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))