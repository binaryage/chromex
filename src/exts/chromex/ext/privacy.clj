(ns chromex.ext.privacy
  "Use the chrome.privacy API to control usage of the features in Chrome that can affect a user's privacy. This API relies on
   the ChromeSetting prototype of the type API for getting and setting Chrome's configuration.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/privacy"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-network
  "Settings that influence Chrome's handling of network connections in general.

   https://developer.chrome.com/extensions/privacy#property-network."
  ([] (gen-call :property ::network &form)))

(defmacro get-services
  "Settings that enable or disable features that require third-party network services provided by Google and your default
   search provider.

   https://developer.chrome.com/extensions/privacy#property-services."
  ([] (gen-call :property ::services &form)))

(defmacro get-websites
  "Settings that determine what information Chrome makes available to websites.

   https://developer.chrome.com/extensions/privacy#property-websites."
  ([] (gen-call :property ::websites &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.privacy namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.privacy",
   :since "38",
   :properties
   [{:id ::network, :name "network", :return-type "object"}
    {:id ::services, :name "services", :return-type "object"}
    {:id ::websites, :name "websites", :return-type "object"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))