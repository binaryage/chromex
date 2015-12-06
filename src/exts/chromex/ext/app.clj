(ns chromex.ext.app
  "  * available since Chrome 19
     * https://developer.chrome.com/extensions/app"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-is-installed
  "TODO"
  ([] (gen-call :function ::get-is-installed &form)))

(defmacro install-state
  "TODO
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::install-state &form)))

(defmacro running-state
  "TODO"
  ([] (gen-call :function ::running-state &form)))

(defmacro get-details
  "TODO"
  ([] (gen-call :function ::get-details &form)))

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
  {:namespace "chrome.app",
   :since "19",
   :functions
   [{:id ::get-is-installed, :name "getIsInstalled", :return-type "boolean"}
    {:id ::install-state,
     :name "installState",
     :since "20",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "state", :type "unknown-type"}]}}]}
    {:id ::running-state, :name "runningState", :since "20", :return-type "unknown-type"}
    {:id ::get-details, :name "getDetails", :return-type "app.Details"}]})

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