(ns chromex.ext.experimental.devtools.audits
  "Use the chrome.experimental.devtools.audits API to add new audit categories to the Developer Tools' Audit panel.
     * https://developer.chrome.com/extensions/experimental.devtools.audits"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-category
  "Adds an audit category.

     |display-name| - A display name for the category.
     |result-count| - The expected number of audit results in the category.

   https://developer.chrome.com/extensions/experimental.devtools.audits#method-addCategory."
  ([display-name result-count] (gen-call :function ::add-category &form display-name result-count)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.experimental.devtools.audits namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.experimental.devtools.audits",
   :functions
   [{:id ::add-category,
     :name "addCategory",
     :return-type "experimental.devtools.audits.AuditCategory",
     :params [{:name "display-name", :type "string"} {:name "result-count", :type "double"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))