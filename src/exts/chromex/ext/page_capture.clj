(ns chromex.ext.page-capture
  "Use the chrome.pageCapture API to save a tab as MHTML.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/pageCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro save-as-mhtml
  "Saves the content of the tab with given id as MHTML.

     |details| - https://developer.chrome.com/extensions/pageCapture#property-saveAsMHTML-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [mhtml-data] where:

     |mhtml-data| - The MHTML data as a Blob.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageCapture#method-saveAsMHTML."
  ([details] (gen-call :function ::save-as-mhtml &form details)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.page-capture namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.pageCapture",
   :since "38",
   :functions
   [{:id ::save-as-mhtml,
     :name "saveAsMHTML",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "mhtml-data", :optional? true, :type "binary"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))