(ns chromex.app.enterprise.platform-keys
  "Use the chrome.enterprise.platformKeys API to generate
   hardware-backed keys and to install certificates for these keys. The
   certificates will be managed by the platform and can be used for TLS
   authentication, network access or by other extension through
   'platformKeys chrome.platformKeys'.

     * available since Chrome 38
     * https://developer.chrome.com/apps/enterprise.platformKeys"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-tokens
  "Returns the available Tokens. In a regular user's session the list will always contain the user's token with id 'user'. If
   a system-wide TPM token is available, the returned list will also contain the system-wide token with id 'system'. The
   system-wide token will be the same for all sessions on this device (device in the sense of e.g. a Chromebook).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tokens] where:

     |tokens| - The list of available tokens.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-getTokens."
  ([] (gen-call :function ::get-tokens &form)))

(defmacro get-certificates
  "Returns the list of all client certificates available from the given token. Can be used to check for the existence and
   expiration of client certificates that are usable for a certain authentication.

     |token-id| - The id of a Token returned by getTokens.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [certificates] where:

     |certificates| - The list of certificates, each in DER encoding of a X.509     certificate.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-getCertificates."
  ([token-id] (gen-call :function ::get-certificates &form token-id)))

(defmacro import-certificate
  "Imports certificate to the given token if the certified key is already stored in this token. After a successful
   certification request, this function should be used to store the obtained certificate and to make it available to the
   operating system and browser for authentication.

     |token-id| - The id of a Token returned by getTokens.
     |certificate| - The DER encoding of a X.509 certificate.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-importCertificate."
  ([token-id certificate] (gen-call :function ::import-certificate &form token-id certificate)))

(defmacro remove-certificate
  "Removes certificate from the given token if present. Should be used to remove obsolete certificates so that they are not
   considered during authentication and do not clutter the certificate choice. Should be used to free storage in the
   certificate store.

     |token-id| - The id of a Token returned by getTokens.
     |certificate| - The DER encoding of a X.509 certificate.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-removeCertificate."
  ([token-id certificate] (gen-call :function ::remove-certificate &form token-id certificate)))

(defmacro challenge-machine-key
  "Challenges a hardware-backed Enterprise Machine Key and emits the response as part of a remote attestation protocol. Only
   useful on Chrome OS and in conjunction with the Verified Access Web API which both issues challenges and verifies
   responses. A successful verification by the Verified Access Web API is a strong signal of all of the following: * The
   current device is a legitimate Chrome OS device. * The current device is managed by the domain specified during
   verification. * The current signed-in user is managed by the domain specified during   verification. * The current device
   state complies with enterprise device policy. For   example, a policy may specify that the device must not be in developer
   mode. * Any device identity emitted by the verification is tightly bound to the   hardware of the current device. This
   function is highly restricted and will fail if the current device is not managed, the current user is not managed, or if
   this operation has not explicitly been enabled for the caller by enterprise device policy. The Enterprise Machine Key does
   not reside in the 'system' token and is not accessible by any other API.

     |challenge| - A challenge as emitted by the Verified Access Web API.
     |register-key| - If set, the current Enterprise Machine Key is registered                with the 'system' token and
                      relinquishes the                Enterprise Machine Key role. The key can then be
                      associated with a certificate and used like any other                signing key. This key is 2048-bit
                      RSA. Subsequent calls                to this function will then generate a new Enterprise
                      Machine Key.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The challenge response.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-challengeMachineKey."
  ([challenge register-key] (gen-call :function ::challenge-machine-key &form challenge register-key))
  ([challenge] `(challenge-machine-key ~challenge :omit)))

(defmacro challenge-user-key
  "Challenges a hardware-backed Enterprise User Key and emits the response as part of a remote attestation protocol. Only
   useful on Chrome OS and in conjunction with the Verified Access Web API which both issues challenges and verifies
   responses. A successful verification by the Verified Access Web API is a strong signal of all of the following: * The
   current device is a legitimate Chrome OS device. * The current device is managed by the domain specified during
   verification. * The current signed-in user is managed by the domain specified during   verification. * The current device
   state complies with enterprise user policy. For   example, a policy may specify that the device must not be in developer
   mode. * The public key emitted by the verification is tightly bound to the   hardware of the current device and to the
   current signed-in user. This function is highly restricted and will fail if the current device is not managed, the current
   user is not managed, or if this operation has not explicitly been enabled for the caller by enterprise user policy. The
   Enterprise User Key does not reside in the 'user' token and is not accessible by any other API.

     |challenge| - A challenge as emitted by the Verified Access Web API.
     |register-key| - If set, the current Enterprise User Key is registered with                the 'user' token and
                      relinquishes the                Enterprise User Key role. The key can then be associated
                      with a certificate and used like any other signing key.                This key is 2048-bit RSA.
                      Subsequent calls to this                function will then generate a new Enterprise User Key.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The challenge response.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/enterprise.platformKeys#method-challengeUserKey."
  ([challenge register-key] (gen-call :function ::challenge-user-key &form challenge register-key)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.enterprise.platform-keys namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.platformKeys",
   :since "38",
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
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-certificate,
     :name "removeCertificate",
     :callback? true,
     :params
     [{:name "token-id", :type "string"}
      {:name "certificate", :type "ArrayBuffer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::challenge-machine-key,
     :name "challengeMachineKey",
     :since "50",
     :callback? true,
     :params
     [{:name "challenge", :type "ArrayBuffer"}
      {:name "register-key", :optional? true, :since "59", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "ArrayBuffer"}]}}]}
    {:id ::challenge-user-key,
     :name "challengeUserKey",
     :since "50",
     :callback? true,
     :params
     [{:name "challenge", :type "ArrayBuffer"}
      {:name "register-key", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "ArrayBuffer"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))