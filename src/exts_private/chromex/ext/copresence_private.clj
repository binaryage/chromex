(ns chromex.ext.copresence-private
  "Use the chrome.copresencePrivate API to interface with Chrome
   from the whispernet_proxy extension.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro send-initialized
  "Send a boolean indicating whether our initialization was successful.

     |success| - ?"
  ([success] (gen-call :function ::send-initialized &form success)))

(defmacro send-found
  "Sends an array of found tokens to Chrome.

     |client-id| - ?
     |tokens| - ?"
  ([client-id tokens] (gen-call :function ::send-found &form client-id tokens)))

(defmacro send-samples
  "Send an array buffer of samples encoded for the specified token.

     |client-id| - ?
     |token| - ?
     |samples| - ?"
  ([client-id token samples] (gen-call :function ::send-samples &form client-id token samples)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-config-audio-events
  "Fired to request audio configuration of the whisper.net library.

   Events will be put on the |channel| with signature [::on-config-audio [client-id audio-params]] where:

     |client-id| - ?
     |audio-params| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-config-audio &form channel args)))

(defmacro tap-on-encode-token-request-events
  "Fired to request encoding of the given token.

   Events will be put on the |channel| with signature [::on-encode-token-request [client-id encode-params]] where:

     |client-id| - ?
     |encode-params| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-encode-token-request &form channel args)))

(defmacro tap-on-decode-samples-request-events
  "Fired when we have new samples to decode.

   Events will be put on the |channel| with signature [::on-decode-samples-request [client-id decode-params]] where:

     |client-id| - ?
     |decode-params| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-decode-samples-request &form channel args)))

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
  {:namespace "chrome.copresencePrivate",
   :since "38",
   :functions
   [{:id ::send-initialized, :name "sendInitialized", :params [{:name "success", :type "boolean"}]}
    {:id ::send-found,
     :name "sendFound",
     :params [{:name "client-id", :type "string"} {:name "tokens", :type "[array-of-copresencePrivate.Tokens]"}]}
    {:id ::send-samples,
     :name "sendSamples",
     :params
     [{:name "client-id", :type "string"}
      {:name "token", :type "copresencePrivate.Token"}
      {:name "samples", :type "ArrayBuffer"}]}],
   :events
   [{:id ::on-config-audio,
     :name "onConfigAudio",
     :since "41",
     :params [{:name "client-id", :type "string"} {:name "audio-params", :type "object"}]}
    {:id ::on-encode-token-request,
     :name "onEncodeTokenRequest",
     :params [{:name "client-id", :type "string"} {:name "encode-params", :type "object"}]}
    {:id ::on-decode-samples-request,
     :name "onDecodeSamplesRequest",
     :params [{:name "client-id", :type "string"} {:name "decode-params", :type "object"}]}]})

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