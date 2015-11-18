(ns chromex.power
  "Use the chrome.power API to override the system's power
   management features.
   
     * available since Chrome 27
     * https://developer.chrome.com/extensions/power"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro request-keep-awake
  "Requests that power management be temporarily disabled. |level| describes the degree to which power management
   should be disabled. If a request previously made by the same app is still active, it will be replaced by the new
   request."
  [level]
  (gen-call :function ::request-keep-awake (meta &form) level))

(defmacro release-keep-awake
  "Releases a request previously made via requestKeepAwake()."
  []
  (gen-call :function ::release-keep-awake (meta &form)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.power",
   :since "27",
   :functions
   [{:id ::request-keep-awake, :name "requestKeepAwake", :params [{:name "level", :type "power.Level"}]}
    {:id ::release-keep-awake, :name "releaseKeepAwake"}]})

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