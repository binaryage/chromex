(ns chromex.dns
  "Use the chrome.dns API for dns resolution.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/dns"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro resolve
  "Resolves the given hostname or IP address literal.
   
     |hostname| - The hostname to resolve.
     |callback| - Called when the resolution operation completes.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([hostname #_callback] (gen-call :function ::resolve &form hostname)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.dns",
   :since "48",
   :functions
   [{:id ::resolve,
     :name "resolve",
     :callback? true,
     :params
     [{:name "hostname", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "resolve-info", :type "object"}]}}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))