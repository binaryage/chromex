(ns chromex.enterprise.platform-keys
  "Use the chrome.enterprise.platformKeys API to generate
   hardware-backed keys and to install certificates for these keys. The
   certificates will be managed by the platform and can be used for TLS
   authentication, network access or by other extension through
   'platformKeys chrome.platformKeys'.
   
     * available since Chrome 37
     * https://developer.chrome.com/extensions/enterprise.platformKeys"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-tokens
  "Returns the available Tokens. In a regular user's session the list will always contain the user's token with id
   'user'. If a system-wide TPM token is available, the returned list will also contain the system-wide token with id
   'system'. The system-wide token will be the same for all sessions on this device (device in the sense of e.g. a
   Chromebook).
   
     |callback| - Invoked by getTokens with the list of available Tokens."
  [#_callback]
  (gen-call :function ::get-tokens (meta &form)))

(defmacro get-certificates
  "Returns the list of all client certificates available from the given token. Can be used to check for the existence
   and expiration of client certificates that are usable for a certain authentication.
   
     |tokenId| - The id of a Token returned by getTokens.
     |callback| - Called back with the list of the available certificates."
  [token-id #_callback]
  (gen-call :function ::get-certificates (meta &form) token-id))

(defmacro import-certificate
  "Imports certificate to the given token if the certified key is already stored in this token. After a successful
   certification request, this function should be used to store the obtained certificate and to make it available to
   the operating system and browser for authentication.
   
     |tokenId| - The id of a Token returned by getTokens.
     |certificate| - The DER encoding of a X.509 certificate.
     |callback| - Called back when this operation is finished."
  [token-id certificate #_callback]
  (gen-call :function ::import-certificate (meta &form) token-id certificate))

(defmacro remove-certificate
  "Removes certificate from the given token if present. Should be used to remove obsolete certificates so that they
   are not considered during authentication and do not clutter the certificate choice. Should be used to free storage
   in the certificate store.
   
     |tokenId| - The id of a Token returned by getTokens.
     |certificate| - The DER encoding of a X.509 certificate.
     |callback| - Called back when this operation is finished."
  [token-id certificate #_callback]
  (gen-call :function ::remove-certificate (meta &form) token-id certificate))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.platformKeys",
   :since "37",
   :functions
   [{:id ::get-tokens,
     :name "getTokens",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "tokens", :type "[array-of-enterprise.platformKeys.Tokens]"}]}}]}
    {:id ::get-certificates,
     :name "getCertificates",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "certificates", :type "[array-of-ArrayBuffers]"}]}}]}
    {:id ::import-certificate,
     :name "importCertificate",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "certificate", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}
    {:id ::remove-certificate,
     :name "removeCertificate",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "certificate", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}]})

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