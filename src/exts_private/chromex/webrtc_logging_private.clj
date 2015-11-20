(ns chromex.webrtc-logging-private
  "Use the chrome.webrtcLoggingPrivate API to control diagnostic
   WebRTC logging.
   
     * available since Chrome 32
     * https://developer.chrome.com/extensions/webrtcLoggingPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-meta-data
  "Sets additional custom meta data that will be uploaded along with the log. |metaData| is a dictionary of the
   metadata (key, value)."
  [request security-origin meta-data #_callback]
  (gen-call :function ::set-meta-data (meta &form) request security-origin meta-data))

(defmacro start
  "Starts logging. If logging has already been started for this render process, the call will be ignored.
   |appSessionId| is the unique session ID which will be added to the log."
  [request security-origin #_callback]
  (gen-call :function ::start (meta &form) request security-origin))

(defmacro set-upload-on-render-close
  "Sets whether the log should be uploaded automatically for the case when the render process goes away (tab is closed
   or crashes) and stop has not been called before that. If |shouldUpload| is true it will be uploaded, otherwise it
   will be discarded. The default setting is to discard it."
  [request security-origin should-upload]
  (gen-call :function ::set-upload-on-render-close (meta &form) request security-origin should-upload))

(defmacro stop
  "Stops logging. After stop has finished, either upload() or discard() should be called, otherwise the log will be
   kept in memory until the render process is closed or logging restarted."
  [request security-origin #_callback]
  (gen-call :function ::stop (meta &form) request security-origin))

(defmacro store
  "Stores the current log without uploading. The log may stay around for as much as 5 days. The application has the
   option of supplying an id for uniquely identifying the log for later upload via a call to uploadStored()."
  [request security-origin log-id #_callback]
  (gen-call :function ::store (meta &form) request security-origin log-id))

(defmacro upload-stored
  "Uploads a previously kept log that was stored via a call to store(). The caller needs to know the logId as was
   originally provided in the call to store()."
  [request security-origin log-id #_callback]
  (gen-call :function ::upload-stored (meta &form) request security-origin log-id))

(defmacro upload
  "Uploads the log and the RTP dumps, if they exist. Logging and RTP dumping must be stopped before this function is
   called."
  [request security-origin #_callback]
  (gen-call :function ::upload (meta &form) request security-origin))

(defmacro discard
  "Discards the log. Logging must be stopped before this function is called."
  [request security-origin #_callback]
  (gen-call :function ::discard (meta &form) request security-origin))

(defmacro start-rtp-dump
  "Starts RTP dumping. If it has already been started for this render process, the call will be ignored."
  [request security-origin incoming outgoing #_callback]
  (gen-call :function ::start-rtp-dump (meta &form) request security-origin incoming outgoing))

(defmacro stop-rtp-dump
  "Stops RTP dumping. After stop has finished, the dumps will be uploaded with the log if upload is called. Otherwise,
   the dumps will be discarded."
  [request security-origin incoming outgoing #_callback]
  (gen-call :function ::stop-rtp-dump (meta &form) request security-origin incoming outgoing))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webrtcLoggingPrivate",
   :since "32",
   :functions
   [{:id ::set-meta-data,
     :name "setMetaData",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "meta-data", :type "[array-of-objects]"}
      {:name "callback", :type :callback}]}
    {:id ::start,
     :name "start",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::set-upload-on-render-close,
     :name "setUploadOnRenderClose",
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "should-upload", :type "boolean"}]}
    {:id ::stop,
     :name "stop",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::store,
     :name "store",
     :since "42",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "log-id", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::upload-stored,
     :name "uploadStored",
     :since "42",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "log-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "webrtcLoggingPrivate.UploadResult"}]}}]}
    {:id ::upload,
     :name "upload",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "webrtcLoggingPrivate.UploadResult"}]}}]}
    {:id ::discard,
     :name "discard",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::start-rtp-dump,
     :name "startRtpDump",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "incoming", :type "boolean"}
      {:name "outgoing", :type "boolean"}
      {:name "callback", :type :callback}]}
    {:id ::stop-rtp-dump,
     :name "stopRtpDump",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "incoming", :type "boolean"}
      {:name "outgoing", :type "boolean"}
      {:name "callback", :type :callback}]}]})

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