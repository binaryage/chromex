(ns chromex.ext.enterprise.platform-keys-internal
  "Internal API for platform keys and certificate management.

     * available since Chrome 37"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-tokens
  "Internal version of entrprise.platformKeys.getTokens. Returns a list of token IDs instead of token objects.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [token-ids] where:

     |token-ids| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-tokens &form)))

(defmacro generate-key
  "Internal version of Token.generateKey, currently supporting only RSASSA-PKCS1-v1_5. |tokenId| The id of a Token returned by
   |getTokens|. |modulusLength| The length, in bits, of the RSA modulus. |callback| Called back with the Subject Public Key
   Info of the generated     key.

     |token-id| - ?
     |modulus-length| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [public-key] where:

     |public-key| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([token-id modulus-length] (gen-call :function ::generate-key &form token-id modulus-length)))

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
  {:namespace "chrome.enterprise.platformKeysInternal",
   :since "37",
   :functions
   [{:id ::get-tokens,
     :name "getTokens",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "token-ids", :type "[array-of-strings]"}]}}]}
    {:id ::generate-key,
     :name "generateKey",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "modulus-length", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "public-key", :type "ArrayBuffer"}]}}]}]})

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