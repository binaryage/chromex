(ns chromex.socket
  "Use the chrome.socket API to send and receive data over the
   network using TCP and UDP connections. Note: Starting with Chrome 33,
   this API is deprecated in favor of the 'sockets.udp', 'sockets.tcp' and
   'sockets.tcpServer' APIs.
   
     * available since Chrome 24
     * https://developer.chrome.com/extensions/socket"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a socket of the specified type that will connect to the specified remote machine.
   
     |type| - The type of socket to create. Must be tcp or udp.
     |options| - The socket options.
     |callback| - Called when the socket has been created.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([type options #_callback] (gen-call :function ::create &form type options))
  ([type] `(create ~type :omit)))

(defmacro destroy
  "Destroys the socket. Each socket created should be destroyed after use.
   
     |socketId| - The socketId."
  ([socket-id] (gen-call :function ::destroy &form socket-id)))

(defmacro connect
  "Connects the socket to the remote machine (for a tcp socket). For a udp socket, this sets the default address which packets
   are sent to and read from for read() and write() calls.
   
     |socketId| - The socketId.
     |hostname| - The hostname or IP address of the remote machine.
     |port| - The port of the remote machine.
     |callback| - Called when the connection attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id hostname port #_callback] (gen-call :function ::connect &form socket-id hostname port)))

(defmacro bind
  "Binds the local address for socket. Currently, it does not support TCP socket.
   
     |socketId| - The socketId.
     |address| - The address of the local machine.
     |port| - The port of the local machine.
     |callback| - Called when the bind attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id address port #_callback] (gen-call :function ::bind &form socket-id address port)))

(defmacro disconnect
  "Disconnects the socket. For UDP sockets, disconnect is a non-operation but is safe to call.
   
     |socketId| - The socketId."
  ([socket-id] (gen-call :function ::disconnect &form socket-id)))

(defmacro read
  "Reads data from the given connected socket.
   
     |socketId| - The socketId.
     |bufferSize| - The read buffer size.
     |callback| - Delivers data that was available to be read without blocking.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id buffer-size #_callback] (gen-call :function ::read &form socket-id buffer-size))
  ([socket-id] `(read ~socket-id :omit)))

(defmacro write
  "Writes data on the given connected socket.
   
     |socketId| - The socketId.
     |data| - The data to write.
     |callback| - Called when the write operation completes without blocking or an error occurs.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id data #_callback] (gen-call :function ::write &form socket-id data)))

(defmacro recv-from
  "Receives data from the given UDP socket.
   
     |socketId| - The socketId.
     |bufferSize| - The receive buffer size.
     |callback| - Returns result of the recvFrom operation.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id buffer-size #_callback] (gen-call :function ::recv-from &form socket-id buffer-size))
  ([socket-id] `(recv-from ~socket-id :omit)))

(defmacro send-to
  "Sends data on the given UDP socket to the given address and port.
   
     |socketId| - The socketId.
     |data| - The data to write.
     |address| - The address of the remote machine.
     |port| - The port of the remote machine.
     |callback| - Called when the send operation completes without blocking or an error occurs.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id data address port #_callback] (gen-call :function ::send-to &form socket-id data address port)))

(defmacro listen
  "This method applies to TCP sockets only. Listens for connections on the specified port and address. This effectively makes
   this a server socket, and client socket functions (connect, read, write) can no longer be used on this socket.
   
     |socketId| - The socketId.
     |address| - The address of the local machine.
     |port| - The port of the local machine.
     |backlog| - Length of the socket's listen queue.
     |callback| - Called when listen operation completes.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id address port backlog #_callback] (gen-call :function ::listen &form socket-id address port backlog))
  ([socket-id address port] `(listen ~socket-id ~address ~port :omit)))

(defmacro accept
  "This method applies to TCP sockets only. Registers a callback function to be called when a connection is accepted on this
   listening server socket. Listen must be called first. If there is already an active accept callback, this callback will be
   invoked immediately with an error as the resultCode.
   
     |socketId| - The socketId.
     |callback| - The callback is invoked when a new socket is accepted.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::accept &form socket-id)))

(defmacro set-keep-alive
  "Enables or disables the keep-alive functionality for a TCP connection.
   
     |socketId| - The socketId.
     |enable| - If true, enable keep-alive functionality.
     |delay| - Set the delay seconds between the last data packet received and the first keepalive probe. Default is 0.
     |callback| - Called when the setKeepAlive attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id enable delay #_callback] (gen-call :function ::set-keep-alive &form socket-id enable delay))
  ([socket-id enable] `(set-keep-alive ~socket-id ~enable :omit)))

(defmacro set-no-delay
  "Sets or clears TCP_NODELAY for a TCP connection. Nagle's algorithm will be disabled when TCP_NODELAY is set.
   
     |socketId| - The socketId.
     |noDelay| - If true, disables Nagle's algorithm.
     |callback| - Called when the setNoDelay attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id no-delay #_callback] (gen-call :function ::set-no-delay &form socket-id no-delay)))

(defmacro get-info
  "Retrieves the state of the given socket.
   
     |socketId| - The socketId.
     |callback| - Called when the state is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::get-info &form socket-id)))

(defmacro get-network-list
  "Retrieves information about local adapters on this system.
   
     |callback| - Called when local adapter information is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-network-list &form)))

(defmacro join-group
  "Join the multicast group and start to receive packets from that group. The socket must be of UDP type and must be bound to
   a local port before calling this method.
   
     |socketId| - The socketId.
     |address| - The group address to join. Domain names are not supported.
     |callback| - Called when the join group operation is done with an integer parameter indicating the platform-independent
                  error code.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id address #_callback] (gen-call :function ::join-group &form socket-id address)))

(defmacro leave-group
  "Leave the multicast group previously joined using joinGroup. It's not necessary to leave the multicast group before
   destroying the socket or exiting. This is automatically called by the OS.Leaving the group will prevent the router from
   sending multicast datagrams to the local host, presuming no other process on the host is still joined to the group.
   
     |socketId| - The socketId.
     |address| - The group address to leave. Domain names are not supported.
     |callback| - Called when the leave group operation is done with an integer parameter indicating the
                  platform-independent error code.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id address #_callback] (gen-call :function ::leave-group &form socket-id address)))

(defmacro set-multicast-time-to-live
  "Set the time-to-live of multicast packets sent to the multicast group.Calling this method does not require multicast
   permissions.
   
     |socketId| - The socketId.
     |ttl| - The time-to-live value.
     |callback| - Called when the configuration operation is done.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id ttl #_callback] (gen-call :function ::set-multicast-time-to-live &form socket-id ttl)))

(defmacro set-multicast-loopback-mode
  "Set whether multicast packets sent from the host to the multicast group will be looped back to the host.Note: the behavior
   of setMulticastLoopbackMode is slightly different between Windows and Unix-like systems. The inconsistency happens only
   when there is more than one application on the same host joined to the same multicast group while having different settings
   on multicast loopback mode. On Windows, the applications with loopback off will not RECEIVE the loopback packets; while on
   Unix-like systems, the applications with loopback off will not SEND the loopback packets to other applications on the same
   host. See MSDN: http://goo.gl/6vqbjCalling this method does not require multicast permissions.
   
     |socketId| - The socketId.
     |enabled| - Indicate whether to enable loopback mode.
     |callback| - Called when the configuration operation is done.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id enabled #_callback] (gen-call :function ::set-multicast-loopback-mode &form socket-id enabled)))

(defmacro get-joined-groups
  "Get the multicast group addresses the socket is currently joined to.
   
     |socketId| - The socketId.
     |callback| - Called with an array of strings of the result.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id #_callback] (gen-call :function ::get-joined-groups &form socket-id)))

(defmacro secure
  "Start a TLS client connection over a connected TCP client socket.
   
     |socketId| - The connected socket to use.
     |options| - Constraints and parameters for the TLS connection.
     |callback| - Called when the TLS connection attempt is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([socket-id options #_callback] (gen-call :function ::secure &form socket-id options))
  ([socket-id] `(secure ~socket-id :omit)))

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
  {:namespace "chrome.socket",
   :since "24",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "type", :type "socket.SocketType"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "create-info", :type "object"}]}}]}
    {:id ::destroy, :name "destroy", :params [{:name "socket-id", :type "integer"}]}
    {:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "hostname", :type "string"}
      {:name "port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::bind,
     :name "bind",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::disconnect, :name "disconnect", :params [{:name "socket-id", :type "integer"}]}
    {:id ::read,
     :name "read",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "buffer-size", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "read-info", :type "object"}]}}]}
    {:id ::write,
     :name "write",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback", :type :callback, :callback {:params [{:name "write-info", :type "socket.WriteInfo"}]}}]}
    {:id ::recv-from,
     :name "recvFrom",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "buffer-size", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "recv-from-info", :type "object"}]}}]}
    {:id ::send-to,
     :name "sendTo",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "write-info", :type "socket.WriteInfo"}]}}]}
    {:id ::listen,
     :name "listen",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "port", :type "integer"}
      {:name "backlog", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::accept,
     :name "accept",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "accept-info", :type "object"}]}}]}
    {:id ::set-keep-alive,
     :name "setKeepAlive",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enable", :type "boolean"}
      {:name "delay", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::set-no-delay,
     :name "setNoDelay",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "no-delay", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-network-list,
     :name "getNetworkList",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-objects]"}]}}]}
    {:id ::join-group,
     :name "joinGroup",
     :since "28",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::leave-group,
     :name "leaveGroup",
     :since "28",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-time-to-live,
     :name "setMulticastTimeToLive",
     :since "28",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "ttl", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-loopback-mode,
     :name "setMulticastLoopbackMode",
     :since "28",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::get-joined-groups,
     :name "getJoinedGroups",
     :since "28",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "groups", :type "[array-of-strings]"}]}}]}
    {:id ::secure,
     :name "secure",
     :since "38",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}]})

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