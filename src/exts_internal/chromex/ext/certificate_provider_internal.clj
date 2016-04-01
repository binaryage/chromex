(ns chromex.ext.certificate-provider-internal
  "Internal API backing the chrome.certificateProvider API events.
   The internal API associates events with replies to these events using request
   IDs. A custom binding is used to hide these IDs from the public API.
   Before an event hits the extension, the request ID is removed and instead a
   callback is added to the event arguments. On the way back, when the extension
   runs the callback to report its results, the callback magically prepends the
   request ID to the results and calls the respective internal report function
   (reportSignature or reportCertificates).

     * available since Chrome 47"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro report-signature
  "Matches certificateProvider.SignCallback. Must be called without the signature to report an error.

     |request-id| - ?
     |signature| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request-id signature] (gen-call :function ::report-signature &form request-id signature))
  ([request-id] `(report-signature ~request-id :omit)))

(defmacro report-certificates
  "Matches certificateProvider.CertificatesCallback. Must be called without the certificates argument to report an error.

     |request-id| - ?
     |certificates| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [rejected-certificates] where:

     |rejected-certificates| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request-id certificates] (gen-call :function ::report-certificates &form request-id certificates))
  ([request-id] `(report-certificates ~request-id :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.certificate-provider-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.certificateProviderInternal",
   :since "47",
   :functions
   [{:id ::report-signature,
     :name "reportSignature",
     :callback? true,
     :params
     [{:name "request-id", :type "integer"}
      {:name "signature", :optional? true, :type "ArrayBuffer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::report-certificates,
     :name "reportCertificates",
     :callback? true,
     :params
     [{:name "request-id", :type "integer"}
      {:name "certificates", :optional? true, :type "[array-of-certificateProvider.CertificateInfos]"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "rejected-certificates", :type "[array-of-ArrayBuffers]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))