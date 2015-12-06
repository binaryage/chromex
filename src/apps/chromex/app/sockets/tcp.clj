(ns chromex.app.sockets.tcp
  "Use the chrome.sockets.tcp API to send and receive data over the
   network using TCP connections. This API supersedes the TCP functionality
   previously found in the chrome.socket API.
   
     * available since Chrome 33
     * https://developer.chrome.com/extensions/sockets.tcp"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a TCP socket.
   
     |properties| - The socket properties (optional).
     |callback| - Called when the socket has been created.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([properties #_callback] (gen-call :function ::create &form properties))
  ([] `(create :omit)))

(defmacro update
  "Updates the socket properties.
   
     |socketId| - The socket identifier.
     |properties| - The properties to update.
     |callback| - Called when the properties are updated.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id properties #_callback] (gen-call :function ::update &form socket-id properties)))

(defmacro set-paused
  "Enables or disables the application from receiving messages from its peer. The default value is 'false'. Pausing a socket
   is typically used by an application to throttle data sent by its peer. When a socket is paused, no onReceive event is
   raised. When a socket is connected and un-paused, onReceive events are raised again when messages are received.
   
     |callback| - Callback from the setPaused method.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id paused #_callback] (gen-call :function ::set-paused &form socket-id paused)))

(defmacro set-keep-alive
  "Enables or disables the keep-alive functionality for a TCP connection.
   
     |socketId| - The socket identifier.
     |enable| - If true, enable keep-alive functionality.
     |delay| - Set the delay seconds between the last data packet received and the first keepalive probe. Default is 0.
     |callback| - Called when the setKeepAlive attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id enable delay #_callback] (gen-call :function ::set-keep-alive &form socket-id enable delay))
  ([socket-id enable] `(set-keep-alive ~socket-id ~enable :omit)))

(defmacro set-no-delay
  "Sets or clears TCP_NODELAY for a TCP connection. Nagle's algorithm will be disabled when TCP_NODELAY is set.
   
     |socketId| - The socket identifier.
     |noDelay| - If true, disables Nagle's algorithm.
     |callback| - Called when the setNoDelay attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id no-delay #_callback] (gen-call :function ::set-no-delay &form socket-id no-delay)))

(defmacro connect
  "Connects the socket to a remote machine. When the connect operation completes successfully, onReceive events are raised
   when data is received from the peer. If a network error occurs while the runtime is receiving packets, a onReceiveError
   event is raised, at which point no more onReceive event will be raised for this socket until the resume method is called.
   
     |socketId| - The socket identifier.
     |peerAddress| - The address of the remote machine. DNS name, IPv4 and  IPv6 formats are supported.
     |peerPort| - The port of the remote machine.
     |callback| - Called when the connect attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id peer-address peer-port #_callback] (gen-call :function ::connect &form socket-id peer-address peer-port)))

(defmacro disconnect
  "Disconnects the socket.
   
     |socketId| - The socket identifier.
     |callback| - Called when the disconnect attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::disconnect &form socket-id)))

(defmacro secure
  "Start a TLS client connection over the connected TCP client socket.
   
     |socketId| - The existing, connected socket to use.
     |options| - Constraints and parameters for the TLS connection.
     |callback| - Called when the connection attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id options #_callback] (gen-call :function ::secure &form socket-id options))
  ([socket-id] `(secure ~socket-id :omit)))

(defmacro send
  "Sends data on the given TCP socket.
   
     |socketId| - The socket identifier.
     |data| - The data to send.
     |callback| - Called when the send operation completes.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id data #_callback] (gen-call :function ::send &form socket-id data)))

(defmacro close
  "Closes the socket and releases the address/port the socket is bound to. Each socket created should be closed after use. The
   socket id is no no longer valid as soon at the function is called. However, the socket is guaranteed to be closed only when
   the callback is invoked.
   
     |socketId| - The socket identifier.
     |callback| - Called when the close operation completes.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::close &form socket-id)))

(defmacro get-info
  "Retrieves the state of the given socket.
   
     |socketId| - The socket identifier.
     |callback| - Called when the socket state is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::get-info &form socket-id)))

(defmacro get-sockets
  "Retrieves the list of currently opened sockets owned by the application.
   
     |callback| - Called when the list of sockets is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-sockets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-receive-events
  "Event raised when data has been received for a given socket.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))
(defmacro tap-on-receive-error-events
  "Event raised when a network error occured while the runtime was waiting for data on the socket address and port. Once this
   event is raised, the socket is set to paused and no more onReceive events are raised for this socket.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-receive-error &form channel args)))

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
  {:namespace "chrome.sockets.tcp",
   :since "33",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "properties", :optional? true, :type "sockets.tcp.SocketProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "create-info", :type "object"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "properties", :type "sockets.tcp.SocketProperties"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-paused,
     :name "setPaused",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "paused", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-keep-alive,
     :name "setKeepAlive",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enable", :type "boolean"}
      {:name "delay", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-no-delay,
     :name "setNoDelay",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "no-delay", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "peer-address", :type "string"}
      {:name "peer-port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::secure,
     :name "secure",
     :since "38",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::send,
     :name "send",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback", :type :callback, :callback {:params [{:name "send-info", :type "object"}]}}]}
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
       :callback {:params [{:name "socket-info", :type "sockets.tcp.SocketInfo"}]}}]}
    {:id ::get-sockets,
     :name "getSockets",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-infos", :type "[array-of-sockets.tcp.SocketInfos]"}]}}]}],
   :events
   [{:id ::on-receive, :name "onReceive", :params [{:name "info", :type "object"}]}
    {:id ::on-receive-error, :name "onReceiveError", :params [{:name "info", :type "object"}]}]})

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