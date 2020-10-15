(ns chromex.ext.enterprise.platform-keys-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro challenge-machine-key
  "Challenge a machine key.

     |challenge| - Challenge to be signed in base64.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - Response in base64.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([challenge] (gen-call :function ::challenge-machine-key &form challenge)))

(defmacro challenge-user-key
  "Challenge an user key.

     |challenge| - Challenge to be signed in base64.
     |register-key| - If true, the key will be registered.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - Response in base64.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([challenge register-key] (gen-call :function ::challenge-user-key &form challenge register-key)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.enterprise.platform-keys-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.platformKeysPrivate",
   :since "38",
   :functions
   [{:id ::challenge-machine-key,
     :name "challengeMachineKey",
     :callback? true,
     :params
     [{:name "challenge", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "string"}]}}]}
    {:id ::challenge-user-key,
     :name "challengeUserKey",
     :callback? true,
     :params
     [{:name "challenge", :type "string"}
      {:name "register-key", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))