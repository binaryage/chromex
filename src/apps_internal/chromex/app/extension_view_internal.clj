(ns chromex.app.extension-view-internal
  "  * available since Chrome 46"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro load-src
  "  |instance-id| - ?
     |src| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [has-load-succeeded] where:

     |has-load-succeeded| - Whether or not loading the src has succeeded.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id src] (gen-call :function ::load-src &form instance-id src)))

(defmacro parse-src
  "  |src| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [is-src-valid extension-id] where:

     |is-src-valid| - Whether or not the src is valid.
     |extension-id| - The extension ID of the src.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([src] (gen-call :function ::parse-src &form src)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.extension-view-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extensionViewInternal",
   :since "46",
   :functions
   [{:id ::load-src,
     :name "loadSrc",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "src", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "has-load-succeeded", :type "boolean"}]}}]}
    {:id ::parse-src,
     :name "parseSrc",
     :callback? true,
     :params
     [{:name "src", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "is-src-valid", :type "boolean"} {:name "extension-id", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))