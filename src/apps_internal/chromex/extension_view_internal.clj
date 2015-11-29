(ns chromex.extension-view-internal
  "  * available since Chrome 46
     * https://developer.chrome.com/extensions/extensionViewInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro load-src
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([instance-id src #_callback] (gen-call :function ::load-src &form instance-id src)))

(defmacro parse-src
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([src #_callback] (gen-call :function ::parse-src &form src)))

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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))