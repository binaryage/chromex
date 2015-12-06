(ns chromex.app.certificate-provider-internal
  "Internal API backing the chrome.certificateProvider API events.
   The internal API associates events with replies to these events using request
   IDs. A custom binding is used to hide these IDs from the public API.
   Before an event hits the extension, the request ID is removed and instead a
   callback is added to the event arguments. On the way back, when the extension
   runs the callback to report its results, the callback magically prepends the
   request ID to the results and calls the respective internal report function
   (reportSignature or reportCertificates).
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/certificateProviderInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro report-signature
  "Matches certificateProvider.SignCallback. Must be called without the signature to report an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([request-id signature #_callback] (gen-call :function ::report-signature &form request-id signature))
  ([request-id] `(report-signature ~request-id :omit)))

(defmacro report-certificates
  "Matches certificateProvider.CertificatesCallback. Must be called without the certificates argument to report an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([request-id certificates #_callback] (gen-call :function ::report-certificates &form request-id certificates))
  ([request-id] `(report-certificates ~request-id :omit)))

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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))