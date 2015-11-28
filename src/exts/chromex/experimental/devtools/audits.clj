(ns chromex.experimental.devtools.audits
  "Use the chrome.experimental.devtools.audits API to add new audit categories to the Developer Tools' Audit panel.
     * https://developer.chrome.com/extensions/experimental.devtools.audits"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-category
  "Adds an audit category.
   
     |displayName| - A display name for the category.
     |resultCount| - The expected number of audit results in the category."
  ([display-name result-count] (gen-call :function ::add-category &form display-name result-count)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))