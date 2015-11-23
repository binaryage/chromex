(ns chromex.enterprise.platform-keys-private
  "  * available since Chrome 28
     * https://developer.chrome.com/extensions/enterprise.platformKeysPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro challenge-machine-key
  "Challenge a machine key.
   
     |challenge| - Challenge to be signed in base64.
     |callback| - Callback function."
  ([challenge #_callback] (gen-call :function ::challenge-machine-key (meta &form) challenge)))

(defmacro challenge-user-key
  "Challenge an user key.
   
     |challenge| - Challenge to be signed in base64.
     |registerKey| - If true, the key will be registered.
     |callback| - Callback function."
  ([challenge register-key #_callback] (gen-call :function ::challenge-user-key (meta &form) challenge register-key)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.platformKeysPrivate",
   :since "28",
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