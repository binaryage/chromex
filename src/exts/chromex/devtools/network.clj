(ns chromex.devtools.network
  "Use the chrome.devtools.network API to retrieve the information about network requests displayed by the Developer
   Tools in the Network panel.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/devtools.network"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-har
  "Returns HAR log that contains all known network requests.
   
     |callback| - A function that receives the HAR log when the request completes."
  [#_callback]
  (gen-call :function ::get-har (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-request-finished
  "Fired when a network request is finished and all request data are available."
  [channel]
  (gen-call :event ::on-request-finished (meta &form) channel))

(defmacro tap-on-navigated
  "Fired when the inspected window navigates to a new page."
  [channel]
  (gen-call :event ::on-navigated (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.network",
   :since "18",
   :functions
   [{:id ::get-har,
     :name "getHAR",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "har-log", :type "object"}]}}]}],
   :events
   [{:id ::on-request-finished,
     :name "onRequestFinished",
     :params [{:name "request", :type "devtools.network.Request"}]}
    {:id ::on-navigated, :name "onNavigated", :params [{:name "url", :type "string"}]}]})

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