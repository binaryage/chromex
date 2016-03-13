(ns chromex.app.sockets.tcp-server
  "Use the chrome.sockets.tcpServer API to create server
   applications using TCP connections. This API supersedes the TCP functionality
   previously found in the chrome.socket API.
   
     * available since Chrome 33
     * https://developer.chrome.com/extensions/sockets.tcpServer"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a TCP server socket.
   
     |properties| - The socket properties (optional).
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [createInfo] where:
   
     |createInfo| - The result of the socket creation.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-create."
  ([properties #_callback] (gen-call :function ::create &form properties))
  ([] `(create :omit)))

(defmacro update
  "Updates the socket properties.
   
     |socketId| - The socket identifier.
     |properties| - The properties to update.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-update."
  ([socket-id properties #_callback] (gen-call :function ::update &form socket-id properties)))

(defmacro set-paused
  "Enables or disables a listening socket from accepting new connections. When paused, a listening socket accepts new
   connections until its backlog (see listen function) is full then refuses additional connection requests. onAccept events
   are raised only when the socket is un-paused.
   
     |socketId| - See https://developer.chrome.com/extensions/sockets.tcpServer#property-setPaused-socketId.
     |paused| - See https://developer.chrome.com/extensions/sockets.tcpServer#property-setPaused-paused.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-setPaused."
  ([socket-id paused #_callback] (gen-call :function ::set-paused &form socket-id paused)))

(defmacro listen
  "Listens for connections on the specified port and address. If the port/address is in use, the callback indicates a failure.
   
     |socketId| - The socket identifier.
     |address| - The address of the local machine.
     |port| - The port of the local machine. When set to 0, a free port is chosen dynamically. The dynamically allocated
              port can be found by calling getInfo.
     |backlog| - Length of the socket's listen queue. The default value depends on the Operating System (SOMAXCONN), which
                 ensures a reasonable queue length for most applications.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The result code returned from the underlying network call. A negative value indicates an error.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-listen."
  ([socket-id address port backlog #_callback] (gen-call :function ::listen &form socket-id address port backlog))
  ([socket-id address port] `(listen ~socket-id ~address ~port :omit)))

(defmacro disconnect
  "Disconnects the listening socket, i.e. stops accepting new connections and releases the address/port the socket is bound
   to. The socket identifier remains valid, e.g. it can be used with listen to accept connections on a new port and address.
   
     |socketId| - The socket identifier.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-disconnect."
  ([socket-id #_callback] (gen-call :function ::disconnect &form socket-id)))

(defmacro close
  "Disconnects and destroys the socket. Each socket created should be closed after use. The socket id is no longer valid as
   soon at the function is called. However, the socket is guaranteed to be closed only when the callback is invoked.
   
     |socketId| - The socket identifier.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-close."
  ([socket-id #_callback] (gen-call :function ::close &form socket-id)))

(defmacro get-info
  "Retrieves the state of the given socket.
   
     |socketId| - The socket identifier.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [socketInfo] where:
   
     |socketInfo| - Object containing the socket information.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-getInfo."
  ([socket-id #_callback] (gen-call :function ::get-info &form socket-id)))

(defmacro get-sockets
  "Retrieves the list of currently opened sockets owned by the application.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [socketInfos] where:
   
     |socketInfos| - Array of object containing socket information.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#method-getSockets."
  ([#_callback] (gen-call :function ::get-sockets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-accept-events
  "Event raised when a connection has been made to the server socket.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#event-onAccept."
  ([channel & args] (apply gen-call :event ::on-accept &form channel args)))

(defmacro tap-on-accept-error-events
  "Event raised when a network error occured while the runtime was waiting for new connections on the socket address and port.
   Once this event is raised, the socket is set to paused and no more onAccept events are raised for this socket until the
   socket is resumed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/sockets.tcpServer#event-onAcceptError."
  ([channel & args] (apply gen-call :event ::on-accept-error &form channel args)))

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
  {:namespace "chrome.sockets.tcpServer",
   :since "33",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "properties", :optional? true, :type "sockets.tcpServer.SocketProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "create-info", :type "object"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "properties", :type "sockets.tcpServer.SocketProperties"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-paused,
     :name "setPaused",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "paused", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::listen,
     :name "listen",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "backlog", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::close,
     :name "close",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-info", :type "sockets.tcpServer.SocketInfo"}]}}]}
    {:id ::get-sockets,
     :name "getSockets",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-infos", :type "[array-of-sockets.tcpServer.SocketInfos]"}]}}]}],
   :events
   [{:id ::on-accept, :name "onAccept", :params [{:name "info", :type "object"}]}
    {:id ::on-accept-error, :name "onAcceptError", :params [{:name "info", :type "object"}]}]})

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