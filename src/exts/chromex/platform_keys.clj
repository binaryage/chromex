(ns chromex.platform-keys
  "Use the chrome.platformKeys API to access client certificates
   managed by the platform. If the user or policy grants the permission, an
   extension can use such a certficate in its custom authentication protocol.
   E.g. this allows usage of platform managed certificates in third party VPNs
   (see 'vpnProvider chrome.vpnProvider').
   
     * available since Chrome 45
     * https://developer.chrome.com/extensions/platformKeys"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro select-client-certificates
  "This function filters from a list of client certificates the ones that are known to the platform, match request and
   for which the extension has permission to access the certificate and its private key. If interactive is true, the
   user is presented a dialog where he can select from matching certificates and grant the extension access to the
   certificate. The selected/filtered client certificates will be passed to callback."
  [details #_callback]
  (gen-call :function ::select-client-certificates (meta &form) details))

(defmacro get-key-pair
  "Passes the key pair of certificate for usage with 'platformKeys.subtleCrypto' to callback.
   
     |certificate| - The certificate of a 'Match' returned by 'selectClientCertificates'.
     |parameters| - Determines signature/hash algorithm parameters additionally to the parameters fixed by the key
                    itself. The same parameters are accepted as by WebCrypto's importKey function, e.g.
                    RsaHashedImportParams for a RSASSA-PKCS1-v1_5 key. For RSASSA-PKCS1-v1_5 keys, additionally the
                    parameters { 'hash': { 'name': 'none' } } are supported. The sign function will then apply
                    PKCS#1 v1.5 padding and but not hash the given data. Currently, this function only supports the
                    'RSASSA-PKCS1-v1_5' algorithm with one of the hashing algorithms 'none', 'SHA-1', 'SHA-256',
                    'SHA-384', and 'SHA-512'.
     |callback| - The public and private CryptoKey of a certificate which can only be used with
                  'platformKeys.subtleCrypto'."
  [certificate parameters #_callback]
  (gen-call :function ::get-key-pair (meta &form) certificate parameters))

(defmacro subtle-crypto
  "An implementation of WebCrypto's  SubtleCrypto that allows crypto operations on keys of client certificates that
   are available to this extension."
  []
  (gen-call :function ::subtle-crypto (meta &form)))

(defmacro verify-tls-server-certificate
  "Checks whether details.serverCertificateChain can be trusted for details.hostname according to the trust settings
   of the platform. Note: The actual behavior of the trust verification is not fully specified and might change in the
   future. The API implementation verifies certificate expiration, validates the certification path and checks trust
   by a known CA. The implementation is supposed to respect the EKU serverAuth and to support subject alternative
   names."
  [details #_callback]
  (gen-call :function ::verify-tls-server-certificate (meta &form) details))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.platformKeys",
   :since "45",
   :functions
   [{:id ::select-client-certificates,
     :name "selectClientCertificates",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "matches", :type "[array-of-platformKeys.Matchs]"}]}}]}
    {:id ::get-key-pair,
     :name "getKeyPair",
     :callback? true,
     :params
     [{:name "certificate", :type "ArrayBuffer"}
      {:name "parameters", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "public-key", :type "object"} {:name "private-key", :type "object"}]}}]}
    {:id ::subtle-crypto, :name "subtleCrypto", :return-type "object"}
    {:id ::verify-tls-server-certificate,
     :name "verifyTLSServerCertificate",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

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