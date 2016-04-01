(ns chromex.app.hangouts-private
  "The chrome.hangoutsPrivate API is used by Google Hangouts to
   wait on a request from Chrome to start a hangout.
   TODO(rkc): This API is temporary. We are working on plans which include
   replacing this with a scheme based solution which might be implemented
   using registerProtocolHandler, but we are still finishing that design.
   See crbug.com/306672

     * available since Chrome 32"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-hangout-requested-events
  "Fired when Chrome wants to request a new hangout be opened up with a user (or set of users).

   Events will be put on the |channel| with signature [::on-hangout-requested [request]] where:

     |request| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-hangout-requested &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.hangouts-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.hangoutsPrivate",
   :since "32",
   :events [{:id ::on-hangout-requested, :name "onHangoutRequested", :params [{:name "request", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))