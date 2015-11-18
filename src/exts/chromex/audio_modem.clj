(ns chromex.audio-modem
  "Use the chrome.audio_modem API
   to transmit and receive short tokens over audio.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/audioModem"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro transmit
  "Transmit a token. Only one can be transmitted at a time. Transmission of any previous tokens (by this app) will
   stop.
   
     |callback| - A callback to report the status of a request."
  [params token #_callback]
  (gen-call :function ::transmit (meta &form) params token))

(defmacro stop-transmit
  "Stop any active transmission on the specified band.
   
     |callback| - A callback to report the status of a request."
  [band #_callback]
  (gen-call :function ::stop-transmit (meta &form) band))

(defmacro receive
  "Start listening for audio tokens. For now, only one app will be able to listen at a time.
   
     |callback| - A callback to report the status of a request."
  [params #_callback]
  (gen-call :function ::receive (meta &form) params))

(defmacro stop-receive
  "Stop any active listening on the specified band.
   
     |callback| - A callback to report the status of a request."
  [band #_callback]
  (gen-call :function ::stop-receive (meta &form) band))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-received
  "Audio tokens have been received."
  [channel]
  (gen-call :event ::on-received (meta &form) channel))

(defmacro tap-on-transmit-fail
  "Transmit could not be confirmed. The speaker volume might be too low."
  [channel]
  (gen-call :event ::on-transmit-fail (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.audioModem",
   :since "48",
   :functions
   [{:id ::transmit,
     :name "transmit",
     :callback? true,
     :params
     [{:name "params", :type "audioModem.RequestParams"}
      {:name "token", :type "ArrayBuffer"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "audioModem.Status"}]}}]}
    {:id ::stop-transmit,
     :name "stopTransmit",
     :callback? true,
     :params
     [{:name "band", :type "audioModem.Audioband"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "audioModem.Status"}]}}]}
    {:id ::receive,
     :name "receive",
     :callback? true,
     :params
     [{:name "params", :type "audioModem.RequestParams"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "audioModem.Status"}]}}]}
    {:id ::stop-receive,
     :name "stopReceive",
     :callback? true,
     :params
     [{:name "band", :type "audioModem.Audioband"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "audioModem.Status"}]}}]}],
   :events
   [{:id ::on-received, :name "onReceived", :params [{:name "tokens", :type "[array-of-objects]"}]}
    {:id ::on-transmit-fail, :name "onTransmitFail", :params [{:name "band", :type "audioModem.Audioband"}]}]})

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