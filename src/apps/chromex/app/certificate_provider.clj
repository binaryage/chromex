(ns chromex.app.certificate-provider
  "Use this API to expose certificates to the platform which can use these
   certificates for TLS authentications.

     * available since Chrome 46
     * https://developer.chrome.com/apps/certificateProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro request-pin
  "Requests the PIN from the user. Only one ongoing request at a time is allowed. The requests issued while another flow is
   ongoing are rejected. It's the extension's responsibility to try again later if another flow is in progress.

     |details| - Contains the details about the requested dialog.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [details] where:

     |details| - https://developer.chrome.com/apps/certificateProvider#property-callback-details.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/certificateProvider#method-requestPin."
  ([details] (gen-call :function ::request-pin &form details)))

(defmacro stop-pin-request
  "Stops the pin request started by the 'requestPin' function.

     |details| - Contains the details about the reason for stopping the request flow.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/certificateProvider#method-stopPinRequest."
  ([details] (gen-call :function ::stop-pin-request &form details)))

(defmacro set-certificates
  "Sets a list of certificates to use in the browser. The extension should call this function after initialization and on
   every change in the set of currently available certificates. The extension should also call this function in response to
   'onCertificatesUpdateRequested' every time this event is received.

     |details| - The certificates to set. Invalid certificates will be ignored.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/certificateProvider#method-setCertificates."
  ([details] (gen-call :function ::set-certificates &form details)))

(defmacro report-signature
  "Should be called as a response to 'onSignatureRequested'. The extension must eventually call this function for every
   'onSignatureRequested' event; the API implementation will stop waiting for this call after some time and respond with a
   timeout error when this function is called.

     |details| - https://developer.chrome.com/apps/certificateProvider#property-reportSignature-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/certificateProvider#method-reportSignature."
  ([details] (gen-call :function ::report-signature &form details)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-certificates-update-requested-events
  "This event fires if the certificates set via 'setCertificates' are insufficient or the browser requests updated
   information. The extension must call 'setCertificates' with the updated list of certificates and the received
   certificatesRequestId.

   Events will be put on the |channel| with signature [::on-certificates-update-requested [request]] where:

     |request| - https://developer.chrome.com/apps/certificateProvider#property-onCertificatesUpdateRequested-request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/certificateProvider#event-onCertificatesUpdateRequested."
  ([channel & args] (apply gen-call :event ::on-certificates-update-requested &form channel args)))

(defmacro tap-on-signature-requested-events
  "This event fires every time the browser needs to sign a message using a certificate provided by this extension via
   'setCertificates'. The extension must sign the input data from request using the appropriate algorithm and private key and
   return it by calling 'reportSignature' with the received signRequestId.

   Events will be put on the |channel| with signature [::on-signature-requested [request]] where:

     |request| - https://developer.chrome.com/apps/certificateProvider#property-onSignatureRequested-request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/certificateProvider#event-onSignatureRequested."
  ([channel & args] (apply gen-call :event ::on-signature-requested &form channel args)))

(defmacro tap-on-certificates-requested-events
  "This event fires every time the browser requests the current list of certificates provided by this extension. The extension
   must call reportCallback exactly once with the current list of certificates.

   Events will be put on the |channel| with signature [::on-certificates-requested [report-callback]].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/certificateProvider#event-onCertificatesRequested."
  ([channel & args] (apply gen-call :event ::on-certificates-requested &form channel args)))

(defmacro tap-on-sign-digest-requested-events
  "This event fires every time the browser needs to sign a message using a certificate provided by this extension in reply to
   an 'onCertificatesRequested' event. The extension must sign the data in request using the appropriate algorithm and private
   key and return it by calling reportCallback. reportCallback must be called exactly once.

   Events will be put on the |channel| with signature [::on-sign-digest-requested [request report-callback]] where:

     |request| - Contains the details about the sign request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/certificateProvider#event-onSignDigestRequested."
  ([channel & args] (apply gen-call :event ::on-sign-digest-requested &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.certificate-provider namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.certificateProvider",
   :since "46",
   :functions
   [{:id ::request-pin,
     :name "requestPin",
     :since "57",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :optional? true, :type "object"}]}}]}
    {:id ::stop-pin-request,
     :name "stopPinRequest",
     :since "57",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::set-certificates,
     :name "setCertificates",
     :since "86",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::report-signature,
     :name "reportSignature",
     :since "86",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-certificates-update-requested,
     :name "onCertificatesUpdateRequested",
     :since "86",
     :params [{:name "request", :type "object"}]}
    {:id ::on-signature-requested,
     :name "onSignatureRequested",
     :since "86",
     :params [{:name "request", :type "object"}]}
    {:id ::on-certificates-requested,
     :name "onCertificatesRequested",
     :since "86",
     :deprecated "Use 'onCertificatesUpdateRequested' instead.",
     :params [{:name "report-callback", :type :callback}]}
    {:id ::on-sign-digest-requested,
     :name "onSignDigestRequested",
     :since "86",
     :deprecated "Use 'onSignatureRequested' instead.",
     :params [{:name "request", :type "object"} {:name "report-callback", :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))