(ns chromex.ext.file-browser-handler-internal
  "  * available since Chrome 21"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro select-file
  "Prompts user to select file path under which a new file will be created. If the user selects file, the file gets created
   or, if it already exists, truncated. The function has to be called with user gesture.

     |selection-params| - Parameters that will be used to create new file.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - Result of the method.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([selection-params] (gen-call :function ::select-file &form selection-params)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.file-browser-handler-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileBrowserHandlerInternal",
   :since "21",
   :functions
   [{:id ::select-file,
     :name "selectFile",
     :callback? true,
     :params
     [{:name "selection-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))