(ns chromex.ext.proxy
  "Use the chrome.proxy API to manage Chrome's proxy settings. This API relies on the ChromeSetting prototype of the type API
   for getting and setting the proxy configuration.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/proxy"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-settings
  "Proxy settings to be used. The value of this setting is a ProxyConfig object.

   https://developer.chrome.com/extensions/proxy#property-settings."
  ([] (gen-call :property ::settings &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-proxy-error-events
  "Notifies about proxy errors.

   Events will be put on the |channel| with signature [::on-proxy-error [details]] where:

     |details| - https://developer.chrome.com/extensions/proxy#property-onProxyError-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/proxy#event-onProxyError."
  ([channel & args] (apply gen-call :event ::on-proxy-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.proxy namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.proxy",
   :since "38",
   :properties [{:id ::settings, :name "settings", :return-type "object"}],
   :events [{:id ::on-proxy-error, :name "onProxyError", :params [{:name "details", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))