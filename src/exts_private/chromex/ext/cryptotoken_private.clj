(ns chromex.ext.cryptotoken-private
  "chrome.cryptotokenPrivate API that provides hooks to Chrome to
   be used by cryptotoken component extension.
   In the context of this API, an AppId is roughly an origin and is formally
   defined in

   the FIDO spec

     * available since Chrome 41"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro can-origin-assert-app-id
  "Checks whether the origin is allowed to assert the appId, according to the same origin policy defined at
   http://fidoalliance.org/specs/fido-u2f-v1.0-ps-20141009/     fido-appid-and-facets-ps-20141009.html |securityOrigin| is the
   origin as seen by the extension, and |appIdUrl| is the appId being asserted by the origin.

     |security-origin| - ?
     |app-id-url| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([security-origin app-id-url] (gen-call :function ::can-origin-assert-app-id &form security-origin app-id-url)))

(defmacro is-app-id-hash-in-enterprise-context
  "Checks whether the given appId is specified in the SecurityKeyPermitAttestation policy. This causes a signal to be sent to
   the token that informs it that an individually-identifying attestation certificate may be used. Without that signal, the
   token is required to use its batch attestation certificate.

     |app-id-hash| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id-hash] (gen-call :function ::is-app-id-hash-in-enterprise-context &form app-id-hash)))

(defmacro can-app-id-get-attestation
  "Checks whether the given appId may receive attestation data that identifies the token. If not, the attestation from the
   token must be substituted with a randomly generated certificate since webauthn and U2F require that some attestation be
   provided.

     |options| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::can-app-id-get-attestation &form options)))

(defmacro record-register-request
  "Increments the WebFeature::kU2FCryptotokenRegister UseCounter for the main frame associated with |tabId|.

     |tab-id| - ?
     |frame-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([tab-id frame-id] (gen-call :function ::record-register-request &form tab-id frame-id)))

(defmacro record-sign-request
  "Increments the WebFeature::kU2FCryptotokenSign UseCounter for the main frame associated with |tabId|.

     |tab-id| - ?
     |frame-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([tab-id frame-id] (gen-call :function ::record-sign-request &form tab-id frame-id)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cryptotoken-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cryptotokenPrivate",
   :since "41",
   :functions
   [{:id ::can-origin-assert-app-id,
     :name "canOriginAssertAppId",
     :since "42",
     :callback? true,
     :params
     [{:name "security-origin", :type "string"}
      {:name "app-id-url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::is-app-id-hash-in-enterprise-context,
     :name "isAppIdHashInEnterpriseContext",
     :since "65",
     :callback? true,
     :params
     [{:name "app-id-hash", :type "ArrayBuffer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::can-app-id-get-attestation,
     :name "canAppIdGetAttestation",
     :since "65",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::record-register-request,
     :name "recordRegisterRequest",
     :since "83",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "frame-id", :type "integer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::record-sign-request,
     :name "recordSignRequest",
     :since "83",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "frame-id", :type "integer"}
      {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))