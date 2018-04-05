(ns chromex.ext.certificate-provider
  "Use this API to expose certificates to the platform which can use these
   certificates for TLS authentications.

     * available since Chrome 46
     * https://developer.chrome.com/extensions/certificateProvider"

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

     |details| - https://developer.chrome.com/extensions/certificateProvider#property-callback-details.

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/certificateProvider#method-requestPin."
  ([details] (gen-call :function ::request-pin &form details)))

(defmacro stop-pin-request
  "Stops the pin request started by the 'requestPin' function.

     |details| - Contains the details about the reason for stopping the request flow.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/certificateProvider#method-stopPinRequest."
  ([details] (gen-call :function ::stop-pin-request &form details)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-certificates-requested-events
  "This event fires every time the browser requests the current list of certificates provided by this extension. The extension
   must call reportCallback exactly once with the current list of certificates.

   Events will be put on the |channel| with signature [::on-certificates-requested [report-callback]].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/certificateProvider#event-onCertificatesRequested."
  ([channel & args] (apply gen-call :event ::on-certificates-requested &form channel args)))

(defmacro tap-on-sign-digest-requested-events
  "This event fires every time the browser needs to sign a message using a certificate provided by this extension in reply to
   an 'onCertificatesRequested' event. The extension must sign the data in request using the appropriate algorithm and private
   key and return it by calling reportCallback. reportCallback must be called exactly once.

   Events will be put on the |channel| with signature [::on-sign-digest-requested [request report-callback]] where:

     |request| - Contains the details about the sign request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/certificateProvider#event-onSignDigestRequested."
  ([channel & args] (apply gen-call :event ::on-sign-digest-requested &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.certificate-provider namespace."
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
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-certificates-requested,
     :name "onCertificatesRequested",
     :since "47",
     :params [{:name "report-callback", :type :callback}]}
    {:id ::on-sign-digest-requested,
     :name "onSignDigestRequested",
     :params [{:name "request", :type "certificateProvider.SignRequest"} {:name "report-callback", :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))