(ns chromex.ext.cast.streaming.receiver-session
  "The chrome.cast.streaming.receiverSession API creates a Cast
   receiver session and adds the resulting audio and video tracks to a
   MediaStream.

     * available since Chrome 64
     * https://developer.chrome.com/extensions/cast.streaming.receiverSession"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

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
     |error-callback| - https://developer.chrome.com/extensions/cast.streaming.receiverSession#property-createAndBind-error_callback.
     |transport-options| - Optional transport settings.

   https://developer.chrome.com/extensions/cast.streaming.receiverSession#method-createAndBind."
  ([audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url error-callback transport-options] (gen-call :function ::create-and-bind &form audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url error-callback transport-options))
  ([audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url error-callback] `(create-and-bind ~audio-params ~video-params ~local-endpoint ~max-width ~max-height ~max-frame-rate ~media-stream-url ~error-callback :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cast.streaming.receiver-session namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.receiverSession",
   :since "64",
   :functions
   [{:id ::create-and-bind,
     :name "createAndBind",
     :params
     [{:name "audio-params", :type "cast.streaming.receiverSession.RtpReceiverParams"}
      {:name "video-params", :type "cast.streaming.receiverSession.RtpReceiverParams"}
      {:name "local-endpoint", :type "object"}
      {:name "max-width", :type "integer"}
      {:name "max-height", :type "integer"}
      {:name "max-frame-rate", :type "double"}
      {:name "media-stream-url", :type "string"}
      {:name "error-callback", :type "function"}
      {:name "transport-options", :optional? true, :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))