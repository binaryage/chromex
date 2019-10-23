(ns chromex.ext.login-state
  "Use the chrome.loginState API to read and monitor the login
   state.

     * available since Chrome 78
     * https://developer.chrome.com/extensions/loginState"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-profile-type
  "Gets the type of the profile the extension is in.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/loginState#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginState#method-getProfileType."
  ([] (gen-call :function ::get-profile-type &form)))

(defmacro get-session-state
  "Gets the current session state.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/loginState#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginState#method-getSessionState."
  ([] (gen-call :function ::get-session-state &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-session-state-changed-events
  "Dispatched when the session state changes. sessionState is the new session state.

   Events will be put on the |channel| with signature [::on-session-state-changed [session-state]] where:

     |session-state| - https://developer.chrome.com/extensions/loginState#property-onSessionStateChanged-sessionState.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/loginState#event-onSessionStateChanged."
  ([channel & args] (apply gen-call :event ::on-session-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.login-state namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.loginState",
   :since "78",
   :functions
   [{:id ::get-profile-type,
     :name "getProfileType",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::get-session-state,
     :name "getSessionState",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "loginState.SessionState"}]}}]}],
   :events
   [{:id ::on-session-state-changed,
     :name "onSessionStateChanged",
     :params [{:name "session-state", :type "loginState.SessionState"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))