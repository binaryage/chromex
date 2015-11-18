(ns chromex.proxy
  "Use the chrome.proxy API to manage Chrome's proxy settings. This API relies on the ChromeSetting prototype of the
   type API for getting and setting the proxy configuration.
   
     * available since Chrome 13
     * https://developer.chrome.com/extensions/proxy"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-settings
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example."
  []
  (gen-call :property ::settings (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-proxy-error
  "Notifies about proxy errors."
  [channel]
  (gen-call :event ::on-proxy-error (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.proxy",
   :since "13",
   :properties [{:id ::settings, :name "settings", :return-type "object"}],
   :events [{:id ::on-proxy-error, :name "onProxyError", :params [{:name "details", :type "object"}]}]})

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