(ns chromex.cast.streaming.rtp-stream
  "The chrome.cast.streaming.rtpStream API allows configuration
   of encoding parameters and RTP parameters used in a Cast streaming
   session.
   
   Valid stream IDs are positive and non-zero.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/cast.streaming.rtpStream"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro destroy
  "Destroys a Cast RTP stream.
   
     |streamId| - The RTP stream ID."
  ([stream-id] (gen-call :function ::destroy &form stream-id)))

(defmacro get-supported-params
  "Returns an array of supported parameters with default values. This includes a list of supported codecs on this platform and
   corresponding encoding and RTP parameters.
   
     |streamId| - The RTP stream ID."
  ([stream-id] (gen-call :function ::get-supported-params &form stream-id)))

(defmacro start
  "Activates the RTP stream by providing the parameters.
   
     |streamId| - The RTP stream ID.
     |params| - Parameters set for this stream."
  ([stream-id params] (gen-call :function ::start &form stream-id params)))

(defmacro stop
  "Stops activity on the specified stream.
   
     |streamId| - The RTP stream ID."
  ([stream-id] (gen-call :function ::stop &form stream-id)))

(defmacro toggle-logging
  "Enables / disables logging for a stream.
   
     |enable| - If true, enables logging. Otherwise disables logging."
  ([stream-id enable] (gen-call :function ::toggle-logging &form stream-id enable)))

(defmacro get-raw-events
  "Get raw events for a stream in the current session.
   
     |streamId| - Stream to get events for.
     |extraData| - Extra data to attach to the log, e.g. system info or              experiment tags, in key-value JSON
                   string format.
     |callback| - Called with the raw events.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([stream-id extra-data #_callback] (gen-call :function ::get-raw-events &form stream-id extra-data))
  ([stream-id] `(get-raw-events ~stream-id :omit)))

(defmacro get-stats
  "Get stats for a stream in the current session.
   
     |streamId| - Stream to get stats for.
     |callback| - Called with the stats.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([stream-id #_callback] (gen-call :function ::get-stats &form stream-id)))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-started-events
  "Event fired when a Cast RTP stream has started."
  ([channel] (gen-call :event ::on-started &form channel)))

(defmacro tap-on-stopped-events
  "Event fired when a Cast RTP stream has stopped."
  ([channel] (gen-call :event ::on-stopped &form channel)))

(defmacro tap-on-error-events
  "Event fired when a Cast RTP stream has error."
  ([channel] (gen-call :event ::on-error &form channel)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.rtpStream",
   :since "48",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))