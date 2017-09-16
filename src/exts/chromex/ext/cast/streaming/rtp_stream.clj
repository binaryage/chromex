(ns chromex.ext.cast.streaming.rtp-stream
  "The chrome.cast.streaming.rtpStream API allows configuration
   of encoding parameters and RTP parameters used in a Cast streaming
   session.

   Valid stream IDs are positive and non-zero.

     * available since Chrome 63
     * https://developer.chrome.com/extensions/cast.streaming.rtpStream"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro destroy
  "Destroys a Cast RTP stream.

     |stream-id| - The RTP stream ID.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-destroy."
  ([stream-id] (gen-call :function ::destroy &form stream-id)))

(defmacro get-supported-params
  "Returns an array of supported parameters with default values. This includes a list of supported codecs on this platform and
   corresponding encoding and RTP parameters.

     |stream-id| - The RTP stream ID.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-getSupportedParams."
  ([stream-id] (gen-call :function ::get-supported-params &form stream-id)))

(defmacro start
  "Activates the RTP stream by providing the parameters.

     |stream-id| - The RTP stream ID.
     |params| - Parameters set for this stream.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-start."
  ([stream-id params] (gen-call :function ::start &form stream-id params)))

(defmacro stop
  "Stops activity on the specified stream.

     |stream-id| - The RTP stream ID.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-stop."
  ([stream-id] (gen-call :function ::stop &form stream-id)))

(defmacro toggle-logging
  "Enables / disables logging for a stream.

     |stream-id| - https://developer.chrome.com/extensions/cast.streaming.rtpStream#property-toggleLogging-streamId.
     |enable| - If true, enables logging. Otherwise disables logging.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-toggleLogging."
  ([stream-id enable] (gen-call :function ::toggle-logging &form stream-id enable)))

(defmacro get-raw-events
  "Get raw events for a stream in the current session.

     |stream-id| - Stream to get events for.
     |extra-data| - Extra data to attach to the log, e.g. system info or              experiment tags, in key-value JSON
                    string format.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [raw-events] where:

     |raw-events| - compressed serialized raw bytes containing raw events              recorded for a stream. The compression
                    is in gzip format. The serialization format can be found at  media/cast/logging/log_serializer.cc.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-getRawEvents."
  ([stream-id extra-data] (gen-call :function ::get-raw-events &form stream-id extra-data))
  ([stream-id] `(get-raw-events ~stream-id :omit)))

(defmacro get-stats
  "Get stats for a stream in the current session.

     |stream-id| - Stream to get stats for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [stats] where:

     |stats| - https://developer.chrome.com/extensions/cast.streaming.rtpStream#property-callback-stats.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#method-getStats."
  ([stream-id] (gen-call :function ::get-stats &form stream-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-started-events
  "Event fired when a Cast RTP stream has started.

   Events will be put on the |channel| with signature [::on-started [stream-id]] where:

     |stream-id| - The ID of the RTP stream.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#event-onStarted."
  ([channel & args] (apply gen-call :event ::on-started &form channel args)))

(defmacro tap-on-stopped-events
  "Event fired when a Cast RTP stream has stopped.

   Events will be put on the |channel| with signature [::on-stopped [stream-id]] where:

     |stream-id| - The ID of the RTP stream.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#event-onStopped."
  ([channel & args] (apply gen-call :event ::on-stopped &form channel args)))

(defmacro tap-on-error-events
  "Event fired when a Cast RTP stream has error.

   Events will be put on the |channel| with signature [::on-error [stream-id error-string]] where:

     |stream-id| - The ID of the RTP stream.
     |error-string| - The error info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/cast.streaming.rtpStream#event-onError."
  ([channel & args] (apply gen-call :event ::on-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cast.streaming.rtp-stream namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.rtpStream",
   :since "63",
   :functions
   [{:id ::destroy, :name "destroy", :params [{:name "stream-id", :type "integer"}]}
    {:id ::get-supported-params,
     :name "getSupportedParams",
     :return-type "[array-of-cast.streaming.rtpStream.RtpParamss]",
     :params [{:name "stream-id", :type "integer"}]}
    {:id ::start,
     :name "start",
     :params [{:name "stream-id", :type "integer"} {:name "params", :type "cast.streaming.rtpStream.RtpParams"}]}
    {:id ::stop, :name "stop", :params [{:name "stream-id", :type "integer"}]}
    {:id ::toggle-logging,
     :name "toggleLogging",
     :params [{:name "stream-id", :type "integer"} {:name "enable", :type "boolean"}]}
    {:id ::get-raw-events,
     :name "getRawEvents",
     :callback? true,
     :params
     [{:name "stream-id", :type "integer"}
      {:name "extra-data", :optional? true, :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "raw-events", :type "ArrayBuffer"}]}}]}
    {:id ::get-stats,
     :name "getStats",
     :callback? true,
     :params
     [{:name "stream-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "stats", :type "object"}]}}]}],
   :events
   [{:id ::on-started, :name "onStarted", :params [{:name "stream-id", :type "integer"}]}
    {:id ::on-stopped, :name "onStopped", :params [{:name "stream-id", :type "integer"}]}
    {:id ::on-error,
     :name "onError",
     :params [{:name "stream-id", :type "integer"} {:name "error-string", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))