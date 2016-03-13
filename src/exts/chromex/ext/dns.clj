(ns chromex.ext.dns
  "Use the chrome.dns API for dns resolution.
   
     * available since Chrome 50
     * https://developer.chrome.com/extensions/dns"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro resolve
  "Resolves the given hostname or IP address literal.
   
     |hostname| - The hostname to resolve.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [resolveInfo] where:
   
     |resolveInfo| - See https://developer.chrome.com/extensions/dns#property-callback-resolveInfo.
   
   See https://developer.chrome.com/extensions/dns#method-resolve."
  ([hostname #_callback] (gen-call :function ::resolve &form hostname)))

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
  {:namespace "chrome.dns",
   :since "50",
   :functions
   [{:id ::resolve,
     :name "resolve",
     :callback? true,
     :params
     [{:name "hostname", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "resolve-info", :type "object"}]}}]}]})

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