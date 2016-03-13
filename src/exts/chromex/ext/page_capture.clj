(ns chromex.ext.page-capture
  "Use the chrome.pageCapture API to save a tab as MHTML.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/pageCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro save-as-mhtml
  "Saves the content of the tab with given id as MHTML.
   
     |details| - See https://developer.chrome.com/extensions/pageCapture#property-saveAsMHTML-details.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [mhtmlData] where:
   
     |mhtmlData| - The MHTML data as a Blob.
   
   See https://developer.chrome.com/extensions/pageCapture#method-saveAsMHTML."
  ([details #_callback] (gen-call :function ::save-as-mhtml &form details)))

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
  {:namespace "chrome.pageCapture",
   :since "18",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))