(ns chromex.ext.privacy
  "Use the chrome.privacy API to control usage of the features in Chrome that can affect a user's privacy. This API relies on
   the ChromeSetting prototype of the type API for getting and setting Chrome's configuration.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/privacy"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-network
  "Settings that influence Chrome's handling of network connections in general."
  ([] (gen-call :property ::network &form)))

(defmacro get-services
  "Settings that enable or disable features that require third-party network services provided by Google and your default
   search provider."
  ([] (gen-call :property ::services &form)))

(defmacro get-websites
  "Settings that determine what information Chrome makes available to websites."
  ([] (gen-call :property ::websites &form)))

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
  {:namespace "chrome.privacy",
   :since "18",
   :properties
   [{:id ::network, :name "network", :return-type "object"}
    {:id ::services, :name "services", :return-type "object"}
    {:id ::websites, :name "websites", :return-type "object"}]})

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