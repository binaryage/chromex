(ns chromex.enterprise.platform-keys-internal
  "Internal API for platform keys and certificate management.
   
     * available since Chrome 37
     * https://developer.chrome.com/extensions/enterprise.platformKeysInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-tokens
  "Internal version of entrprise.platformKeys.getTokens. Returns a list of token IDs instead of token objects.
   
     |callback| - Invoked by getTokens. |tokenIds| The list of IDs of the avialable Tokens."
  [#_callback]
  (gen-call :function ::get-tokens (meta &form)))

(defmacro generate-key
  "Internal version of Token.generateKey, currently supporting only RSASSA-PKCS1-v1_5. |tokenId| The id of a Token
   returned by |getTokens|. |modulusLength| The length, in bits, of the RSA modulus. |callback| Called back with the
   Subject Public Key Info of the generated     key.
   
     |callback| - Invoked by generateKey. |publicKey| The Subject Public Key Info (see X.509) of the generated key
                  in DER encoding."
  [token-id modulus-length #_callback]
  (gen-call :function ::generate-key (meta &form) token-id modulus-length))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.platformKeysInternal",
   :since "37",
   :functions
   [{:id ::get-tokens,
     :name "getTokens",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "token-ids", :type "[array-of-strings]"}]}}]}
    {:id ::generate-key,
     :name "generateKey",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "modulus-length", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "public-key", :type "ArrayBuffer"}]}}]}]})

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