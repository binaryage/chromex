(ns chromex.ext.cast.streaming.session
  "The chrome.cast.streaming.session API creates a Cast
   session using WebMediaStreamTrack as sources. The session is composed
   by RTP streams and a network transport.

   Calling this API will generate corresponding resources for use with
   chrome.cast.streaming.rtpStream and chrome.cast.streaming.udpTransport
   APIs.

   Valid resource IDs are positive and non-zero.

     * available since Chrome 56
     * https://developer.chrome.com/extensions/cast.streaming.session"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a Cast session using the provided audio and video track as source. The tracks must be of type MediaStreamTrack.
   This will create two RTP streams and a UDP transport that builds the session. Either |audioTrack| or |videoTrack| can be
   null but not both. This means creating a session with only audio or video. If a given track is null then the created stream
   ID will be null.

     |audio-track| - the source audio track.
     |video-track| - the source video track.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [audio-stream-id video-stream-id udp-transport-id] where:

     |audio-stream-id| - The audio RTP stream ID.
     |video-stream-id| - The video RTP stream ID.
     |udp-transport-id| - The UDP transport ID.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cast.streaming.session#method-create."
  ([audio-track video-track] (gen-call :function ::create &form audio-track video-track))
  ([audio-track] `(create ~audio-track :omit))
  ([] `(create :omit :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cast.streaming.session namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.session",
   :since "56",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "audio-track", :optional? true, :type "MediaStreamTrack"}
      {:name "video-track", :optional? true, :type "MediaStreamTrack"}
      {:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "audio-stream-id", :type "integer"}
         {:name "video-stream-id", :type "integer"}
         {:name "udp-transport-id", :type "integer"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))