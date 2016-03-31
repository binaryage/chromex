(ns chromex.ext.extension-view-internal
  "  * available since Chrome 42"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

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
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extensionViewInternal",
   :since "42",
   :functions
   [{:id ::load-src,
     :name "loadSrc",
     :since "45",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "src", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "has-load-succeeded", :type "boolean"}]}}]}
    {:id ::parse-src,
     :name "parseSrc",
     :since "45",
     :callback? true,
     :params
     [{:name "src", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "is-src-valid", :type "boolean"} {:name "extension-id", :type "string"}]}}]}]})

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