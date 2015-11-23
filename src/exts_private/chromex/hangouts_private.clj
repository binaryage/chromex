(ns chromex.hangouts-private
  "The chrome.hangoutsPrivate API is used by Google Hangouts to
   wait on a request from Chrome to start a hangout.
   TODO(rkc): This API is temporary. We are working on plans which include
   replacing this with a scheme based solution which might be implemented
   using registerProtocolHandler, but we are still finishing that design.
   See crbug.com/306672
   
     * available since Chrome 32
     * https://developer.chrome.com/extensions/hangoutsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-hangout-requested-events
  "Fired when Chrome wants to request a new hangout be opened up with a user (or set of users)."
  ([channel] (gen-call :event ::on-hangout-requested &form channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.hangoutsPrivate",
   :since "32",
   :events
   [{:id ::on-hangout-requested, :name "onHangoutRequested", :params [{:name "request", :type "object"}]}]})

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