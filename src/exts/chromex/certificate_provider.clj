(ns chromex.certificate-provider
  "Use this API to expose certificates to the platform which can use these
   certificates for TLS authentications.
   
     * available since Chrome 46
     * https://developer.chrome.com/extensions/certificateProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-certificates-requested
  "This event fires every time the browser requests the current list of certificates provided by this extension. The
   extension must call reportCallback exactly once with the current list of certificates."
  [channel]
  (gen-call :event ::on-certificates-requested (meta &form) channel))

(defmacro tap-on-sign-digest-requested
  "This event fires every time the browser needs to sign a message using a certificate provided by this extension in
   reply to an 'onCertificatesRequested' event. The extension must sign the data in request using the appropriate
   algorithm and private key and return it by calling reportCallback. reportCallback must be called exactly once."
  [channel]
  (gen-call :event ::on-sign-digest-requested (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.certificateProvider",
   :since "46",
   :events
   [{:id ::on-certificates-requested,
     :name "onCertificatesRequested",
     :since "47",
     :params [{:name "report-callback", :type :callback}]}
    {:id ::on-sign-digest-requested,
     :name "onSignDigestRequested",
     :params
     [{:name "request", :type "certificateProvider.SignRequest"} {:name "report-callback", :type :callback}]}]})

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