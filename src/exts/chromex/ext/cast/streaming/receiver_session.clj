(ns chromex.ext.cast.streaming.receiver-session
  "The chrome.cast.streaming.receiverSession API creates a Cast
   receiver session and adds the resulting audio and video tracks to a
   MediaStream.

     * available since Chrome 50
     * https://developer.chrome.com/extensions/cast.streaming.receiverSession"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create-and-bind
  "Creates a Cast receiver session which receives data from a UDP socket. The receiver will decode the incoming data into an
   audio and a video track which will be added to the provided media stream. The |audioParams| and |videoParams| are generally
   provided by the sender through some other messaging channel.

     |audio-params| - Audio stream parameters.
     |video-params| - Video stream parameters.
     |local-endpoint| - Local IP and port to bind to.
     |max-width| - https://developer.chrome.com/extensions/cast.streaming.receiverSession#property-createAndBind-maxWidth.
     |max-height| - https://developer.chrome.com/extensions/cast.streaming.receiverSession#property-createAndBind-maxHeight.
     |max-frame-rate| - Max video frame rate.
     |media-stream-url| - URL of MediaStream to add the audio and video to.
     |transport-options| - Optional transport settings.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [error] where:

     |error| - https://developer.chrome.com/extensions/cast.streaming.receiverSession#property-error_callback-error.

   https://developer.chrome.com/extensions/cast.streaming.receiverSession#method-createAndBind."
  ([audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url transport-options] (gen-call :function ::create-and-bind &form audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url transport-options))
  ([audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url] `(create-and-bind ~audio-params ~video-params ~local-endpoint ~max-width ~max-height ~max-frame-rate ~media-stream-url :omit)))

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
  {:namespace "chrome.cast.streaming.receiverSession",
   :since "50",
   :functions
   [{:id ::create-and-bind,
     :name "createAndBind",
     :callback? true,
     :params
     [{:name "audio-params", :type "cast.streaming.receiverSession.RtpReceiverParams"}
      {:name "video-params", :type "cast.streaming.receiverSession.RtpReceiverParams"}
      {:name "local-endpoint", :type "object"}
      {:name "max-width", :type "integer"}
      {:name "max-height", :type "integer"}
      {:name "max-frame-rate", :type "double"}
      {:name "media-stream-url", :type "string"}
      {:name "transport-options", :optional? true, :type "object"}
      {:name "error-callback", :type :callback, :callback {:params [{:name "error", :type "string"}]}}]}]})

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