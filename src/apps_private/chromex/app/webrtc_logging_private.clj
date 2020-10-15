(ns chromex.app.webrtc-logging-private
  "Use the chrome.webrtcLoggingPrivate API to control diagnostic
   WebRTC logging.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-meta-data
  "Sets additional custom meta data that will be uploaded along with the log. |metaData| is a dictionary of the metadata (key,
   value).

     |request| - ?
     |security-origin| - ?
     |meta-data| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin meta-data] (gen-call :function ::set-meta-data &form request security-origin meta-data)))

(defmacro start
  "Starts logging. If logging has already been started for this render process, the call will be ignored. |appSessionId| is
   the unique session ID which will be added to the log.

     |request| - ?
     |security-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin] (gen-call :function ::start &form request security-origin)))

(defmacro set-upload-on-render-close
  "Sets whether the log should be uploaded automatically for the case when the render process goes away (tab is closed or
   crashes) and stop has not been called before that. If |shouldUpload| is true it will be uploaded, otherwise it will be
   discarded. The default setting is to discard it.

     |request| - ?
     |security-origin| - ?
     |should-upload| - ?"
  ([request security-origin should-upload] (gen-call :function ::set-upload-on-render-close &form request security-origin should-upload)))

(defmacro stop
  "Stops logging. After stop has finished, either upload() or discard() should be called, otherwise the log will be kept in
   memory until the render process is closed or logging restarted.

     |request| - ?
     |security-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin] (gen-call :function ::stop &form request security-origin)))

(defmacro store
  "Stores the current log without uploading. The log may stay around for as much as 5 days. The application has the option of
   supplying an id for uniquely identifying the log for later upload via a call to uploadStored().

     |request| - ?
     |security-origin| - ?
     |log-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin log-id] (gen-call :function ::store &form request security-origin log-id)))

(defmacro upload-stored
  "Uploads a previously kept log that was stored via a call to store(). The caller needs to know the logId as was originally
   provided in the call to store().

     |request| - ?
     |security-origin| - ?
     |log-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin log-id] (gen-call :function ::upload-stored &form request security-origin log-id)))

(defmacro upload
  "Uploads the log and the RTP dumps, if they exist. Logging and RTP dumping must be stopped before this function is called.

     |request| - ?
     |security-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin] (gen-call :function ::upload &form request security-origin)))

(defmacro discard
  "Discards the log. Logging must be stopped before this function is called.

     |request| - ?
     |security-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin] (gen-call :function ::discard &form request security-origin)))

(defmacro start-rtp-dump
  "Starts RTP dumping. If it has already been started for this render process, the call will be ignored.

     |request| - ?
     |security-origin| - ?
     |incoming| - ?
     |outgoing| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin incoming outgoing] (gen-call :function ::start-rtp-dump &form request security-origin incoming outgoing)))

(defmacro stop-rtp-dump
  "Stops RTP dumping. After stop has finished, the dumps will be uploaded with the log if upload is called. Otherwise, the
   dumps will be discarded.

     |request| - ?
     |security-origin| - ?
     |incoming| - ?
     |outgoing| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin incoming outgoing] (gen-call :function ::stop-rtp-dump &form request security-origin incoming outgoing)))

(defmacro start-audio-debug-recordings
  "Starts audio debug recordings. |seconds| indicates how many seconds of audio to record. |callback| is invoked once
   recording stops. If |seconds| is zero, recording will continue until stopAudioDebugRecordings() is explicitly called. In
   this case, |callback| is invoked once recording starts and will report that recording has not stopped. If |seconds| is
   negative, startAudioDebugRecordings() fails.

     |request| - ?
     |security-origin| - ?
     |seconds| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin seconds] (gen-call :function ::start-audio-debug-recordings &form request security-origin seconds)))

(defmacro stop-audio-debug-recordings
  "Stops audio debug recordings.  |callback| is invoked once recording stops. If there is no recording in progress,
   stopAudioDebugRecordings() fails.

     |request| - ?
     |security-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin] (gen-call :function ::stop-audio-debug-recordings &form request security-origin)))

(defmacro start-event-logging
  "Start remote-bound event logging for a specific peer connection, indicated by its session description's ID. If successful,
   the callback will carry the ID of the log. * |webAppId| must be a number between 1 and 99 (inclusive), which will be
   incorporated into the uploaded log, so as to help distinugish logs   captured by different web-apps. * |outputPeriodMs

   refers to the time between emissions of logs.   Only non-negative values are allowed. If set to zero, logs will be
   produced as soon as an event occurs. If positive, events will be   batched together and emitted approximately every
   |outputPeriodMs| ms.

     |request| - ?
     |security-origin| - ?
     |session-id| - ?
     |max-log-size-bytes| - ?
     |output-period-ms| - ?
     |web-app-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([request security-origin session-id max-log-size-bytes output-period-ms web-app-id] (gen-call :function ::start-event-logging &form request security-origin session-id max-log-size-bytes output-period-ms web-app-id)))

(defmacro get-logs-directory
  "Returns the directory entry for the 'WebRTC Logs' directory. If the directory doesn't exist yet, this will create it. If
   the directory cannot be created, this call will fail with a runtime error.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entry] where:

     |entry| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-logs-directory &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.webrtc-logging-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webrtcLoggingPrivate",
   :since "38",
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
      {:name "callback", :type :callback}]}
    {:id ::start-audio-debug-recordings,
     :name "startAudioDebugRecordings",
     :since "49",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "seconds", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "info", :type "webrtcLoggingPrivate.RecordingInfo"}]}}]}
    {:id ::stop-audio-debug-recordings,
     :name "stopAudioDebugRecordings",
     :since "49",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "info", :type "webrtcLoggingPrivate.RecordingInfo"}]}}]}
    {:id ::start-event-logging,
     :name "startEventLogging",
     :since "67",
     :callback? true,
     :params
     [{:name "request", :type "webrtcLoggingPrivate.RequestInfo"}
      {:name "security-origin", :type "string"}
      {:name "session-id", :since "74", :type "string"}
      {:name "max-log-size-bytes", :type "integer"}
      {:name "output-period-ms", :since "72", :type "integer"}
      {:name "web-app-id", :since "70", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-logs-directory,
     :name "getLogsDirectory",
     :since "64",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "entry", :type "DirectoryEntry"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))