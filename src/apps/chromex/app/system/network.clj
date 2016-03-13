(ns chromex.app.system.network
  "Use the chrome.system.network API.
   
     * available since Chrome 33
     * https://developer.chrome.com/extensions/system.network"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-network-interfaces
  "Retrieves information about local adapters on this system.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [networkInterfaces] where:
   
     |networkInterfaces| - Array of object containing network interfaces information.
   
   See https://developer.chrome.com/extensions/system.network#method-getNetworkInterfaces."
  ([#_callback] (gen-call :function ::get-network-interfaces &form)))

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
  {:namespace "chrome.system.network",
   :since "33",
   :functions
   [{:id ::get-network-interfaces,
     :name "getNetworkInterfaces",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "network-interfaces", :type "[array-of-objects]"}]}}]}]})

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