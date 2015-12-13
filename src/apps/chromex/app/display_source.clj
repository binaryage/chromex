(ns chromex.app.display-source
  "The chrome.displaySource API creates a Display
   session using WebMediaStreamTrack as sources.
   
     * available since Chrome 49
     * https://developer.chrome.com/extensions/displaySource"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-available-sinks
  "Queries the list of the currently available Display sinks.
   
     |callback| - Called when the request is completed. The argument list is empty if no available sinks were found.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-available-sinks &form)))

(defmacro request-authentication
  "Queries authentication data from the sink device.
   
     |sinkId| - Id of the sink
     |callback| - Called when authentication info retrieved from the sink. The argument |method| field contains the
                  authentication method required by the sink for connection; the |data| field can be null or can contain
                  some supplementary data provided by the sink. If authentication info cannot be retrieved from the sink the
                  'chrome.runtime.lastError' property is defined.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([sink-id #_callback] (gen-call :function ::request-authentication &form sink-id)))

(defmacro start-session
  "Creates a Display session using the provided StartSessionInfo instance. The input argument fields must be initialized as
   described below: The |sinkId|  must be a valid id of a sink (obtained via ‘getAvailableSinks’).The |audioTrack| or
   |videoTrack| must be of type MediaStreamTrack. Either |audioTrack| or |videoTrack| can be null but not both. This means
   creating a session with only audio or video.The |authenticationInfo| can be null if no additional authentication data are
   required by the sink; otherwise its |data| field must contain the required authentication data (e.g. PIN value) and its
   |method| field must be the same as one obtained from ‘requestAuthentication’."
  ([session-info] (gen-call :function ::start-session &form session-info)))

(defmacro terminate-session
  "Terminates the active Display session.
   
     |sinkId| - Id of the connected sink.
     |callback| - Called when the session is terminated.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([sink-id #_callback] (gen-call :function ::terminate-session &form sink-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-sinks-updated-events
  "Event fired when the available sinks are modified (either their amount or properties) |sinks| the list of all currently
   available sinks
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-sinks-updated &form channel args)))
(defmacro tap-on-session-started-events
  "Event fired when the Display session is started. |sinkId| Id of the peer sink
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-session-started &form channel args)))
(defmacro tap-on-session-terminated-events
  "Event fired when the Display session is terminated. |sinkId| Id of the peer sink
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-session-terminated &form channel args)))
(defmacro tap-on-session-error-occured-events
  "Event fired when an error occurs. |sinkId| Id of the peer sink |errorInfo| error description
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-session-error-occured &form channel args)))

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
  {:namespace "chrome.displaySource",
   :since "49",
   :functions
   [{:id ::get-available-sinks,
     :name "getAvailableSinks",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-displaySource.SinkInfos]"}]}}]}
    {:id ::request-authentication,
     :name "requestAuthentication",
     :callback? true,
     :params
     [{:name "sink-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "displaySource.AuthenticationInfo"}]}}]}
    {:id ::start-session, :name "startSession", :params [{:name "session-info", :type "object"}]}
    {:id ::terminate-session,
     :name "terminateSession",
     :callback? true,
     :params [{:name "sink-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-sinks-updated,
     :name "onSinksUpdated",
     :params [{:name "sinks", :type "[array-of-displaySource.SinkInfos]"}]}
    {:id ::on-session-started, :name "onSessionStarted", :params [{:name "sink-id", :type "integer"}]}
    {:id ::on-session-terminated, :name "onSessionTerminated", :params [{:name "sink-id", :type "integer"}]}
    {:id ::on-session-error-occured,
     :name "onSessionErrorOccured",
     :params [{:name "sink-id", :type "integer"} {:name "error-info", :type "object"}]}]})

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