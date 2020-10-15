(ns chromex.app.socket
  "Use the chrome.socket API to send and receive data over the
   network using TCP and UDP connections. Note: Starting with Chrome 33,
   this API is deprecated in favor of the 'sockets.udp', 'sockets.tcp' and
   'sockets.tcpServer' APIs.

     * available since Chrome 38
     * https://developer.chrome.com/apps/socket"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a socket of the specified type that will connect to the specified remote machine.

     |type| - The type of socket to create. Must be tcp or udp.
     |options| - The socket options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [create-info] where:

     |create-info| - https://developer.chrome.com/apps/socket#property-callback-createInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-create."
  ([type options] (gen-call :function ::create &form type options))
  ([type] `(create ~type :omit)))

(defmacro destroy
  "Destroys the socket. Each socket created should be destroyed after use.

     |socket-id| - The socketId.

   https://developer.chrome.com/apps/socket#method-destroy."
  ([socket-id] (gen-call :function ::destroy &form socket-id)))

(defmacro connect
  "Connects the socket to the remote machine (for a tcp socket). For a udp socket, this sets the default address which packets
   are sent to and read from for read() and write() calls.

     |socket-id| - The socketId.
     |hostname| - The hostname or IP address of the remote machine.
     |port| - The port of the remote machine.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-connect."
  ([socket-id hostname port] (gen-call :function ::connect &form socket-id hostname port)))

(defmacro bind
  "Binds the local address for socket. Currently, it does not support TCP socket.

     |socket-id| - The socketId.
     |address| - The address of the local machine.
     |port| - The port of the local machine.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-bind."
  ([socket-id address port] (gen-call :function ::bind &form socket-id address port)))

(defmacro disconnect
  "Disconnects the socket. For UDP sockets, disconnect is a non-operation but is safe to call.

     |socket-id| - The socketId.

   https://developer.chrome.com/apps/socket#method-disconnect."
  ([socket-id] (gen-call :function ::disconnect &form socket-id)))

(defmacro read
  "Reads data from the given connected socket.

     |socket-id| - The socketId.
     |buffer-size| - The read buffer size.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [read-info] where:

     |read-info| - https://developer.chrome.com/apps/socket#property-callback-readInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-read."
  ([socket-id buffer-size] (gen-call :function ::read &form socket-id buffer-size))
  ([socket-id] `(read ~socket-id :omit)))

(defmacro write
  "Writes data on the given connected socket.

     |socket-id| - The socketId.
     |data| - The data to write.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [write-info] where:

     |write-info| - https://developer.chrome.com/apps/socket#property-callback-writeInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-write."
  ([socket-id data] (gen-call :function ::write &form socket-id data)))

(defmacro recv-from
  "Receives data from the given UDP socket.

     |socket-id| - The socketId.
     |buffer-size| - The receive buffer size.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [recv-from-info] where:

     |recv-from-info| - https://developer.chrome.com/apps/socket#property-callback-recvFromInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-recvFrom."
  ([socket-id buffer-size] (gen-call :function ::recv-from &form socket-id buffer-size))
  ([socket-id] `(recv-from ~socket-id :omit)))

(defmacro send-to
  "Sends data on the given UDP socket to the given address and port.

     |socket-id| - The socketId.
     |data| - The data to write.
     |address| - The address of the remote machine.
     |port| - The port of the remote machine.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [write-info] where:

     |write-info| - https://developer.chrome.com/apps/socket#property-callback-writeInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-sendTo."
  ([socket-id data address port] (gen-call :function ::send-to &form socket-id data address port)))

(defmacro listen
  "This method applies to TCP sockets only. Listens for connections on the specified port and address. This effectively makes
   this a server socket, and client socket functions (connect, read, write) can no longer be used on this socket.

     |socket-id| - The socketId.
     |address| - The address of the local machine.
     |port| - The port of the local machine.
     |backlog| - Length of the socket's listen queue.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-listen."
  ([socket-id address port backlog] (gen-call :function ::listen &form socket-id address port backlog))
  ([socket-id address port] `(listen ~socket-id ~address ~port :omit)))

(defmacro accept
  "This method applies to TCP sockets only. Registers a callback function to be called when a connection is accepted on this
   listening server socket. Listen must be called first. If there is already an active accept callback, this callback will be
   invoked immediately with an error as the resultCode.

     |socket-id| - The socketId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [accept-info] where:

     |accept-info| - https://developer.chrome.com/apps/socket#property-callback-acceptInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-accept."
  ([socket-id] (gen-call :function ::accept &form socket-id)))

(defmacro set-keep-alive
  "Enables or disables the keep-alive functionality for a TCP connection.

     |socket-id| - The socketId.
     |enable| - If true, enable keep-alive functionality.
     |delay| - Set the delay seconds between the last data packet received and the first keepalive probe. Default is 0.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-setKeepAlive."
  ([socket-id enable delay] (gen-call :function ::set-keep-alive &form socket-id enable delay))
  ([socket-id enable] `(set-keep-alive ~socket-id ~enable :omit)))

(defmacro set-no-delay
  "Sets or clears TCP_NODELAY for a TCP connection. Nagle's algorithm will be disabled when TCP_NODELAY is set.

     |socket-id| - The socketId.
     |no-delay| - If true, disables Nagle's algorithm.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-setNoDelay."
  ([socket-id no-delay] (gen-call :function ::set-no-delay &form socket-id no-delay)))

(defmacro get-info
  "Retrieves the state of the given socket.

     |socket-id| - The socketId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-getInfo."
  ([socket-id] (gen-call :function ::get-info &form socket-id)))

(defmacro get-network-list
  "Retrieves information about local adapters on this system.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-getNetworkList."
  ([] (gen-call :function ::get-network-list &form)))

(defmacro join-group
  "Join the multicast group and start to receive packets from that group. The socket must be of UDP type and must be bound to
   a local port before calling this method.

     |socket-id| - The socketId.
     |address| - The group address to join. Domain names are not supported.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-joinGroup."
  ([socket-id address] (gen-call :function ::join-group &form socket-id address)))

(defmacro leave-group
  "Leave the multicast group previously joined using joinGroup. It's not necessary to leave the multicast group before
   destroying the socket or exiting. This is automatically called by the OS.Leaving the group will prevent the router from
   sending multicast datagrams to the local host, presuming no other process on the host is still joined to the group.

     |socket-id| - The socketId.
     |address| - The group address to leave. Domain names are not supported.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-leaveGroup."
  ([socket-id address] (gen-call :function ::leave-group &form socket-id address)))

(defmacro set-multicast-time-to-live
  "Set the time-to-live of multicast packets sent to the multicast group.Calling this method does not require multicast
   permissions.

     |socket-id| - The socketId.
     |ttl| - The time-to-live value.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-setMulticastTimeToLive."
  ([socket-id ttl] (gen-call :function ::set-multicast-time-to-live &form socket-id ttl)))

(defmacro set-multicast-loopback-mode
  "Set whether multicast packets sent from the host to the multicast group will be looped back to the host.Note: the behavior
   of setMulticastLoopbackMode is slightly different between Windows and Unix-like systems. The inconsistency happens only
   when there is more than one application on the same host joined to the same multicast group while having different settings
   on multicast loopback mode. On Windows, the applications with loopback off will not RECEIVE the loopback packets; while on
   Unix-like systems, the applications with loopback off will not SEND the loopback packets to other applications on the same
   host. See MSDN: http://goo.gl/6vqbjCalling this method does not require multicast permissions.

     |socket-id| - The socketId.
     |enabled| - Indicate whether to enable loopback mode.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-setMulticastLoopbackMode."
  ([socket-id enabled] (gen-call :function ::set-multicast-loopback-mode &form socket-id enabled)))

(defmacro get-joined-groups
  "Get the multicast group addresses the socket is currently joined to.

     |socket-id| - The socketId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [groups] where:

     |groups| - https://developer.chrome.com/apps/socket#property-callback-groups.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-getJoinedGroups."
  ([socket-id] (gen-call :function ::get-joined-groups &form socket-id)))

(defmacro secure
  "Start a TLS client connection over a connected TCP client socket.

     |socket-id| - The connected socket to use.
     |options| - Constraints and parameters for the TLS connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/socket#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/socket#method-secure."
  ([socket-id options] (gen-call :function ::secure &form socket-id options))
  ([socket-id] `(secure ~socket-id :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.socket namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.socket",
   :since "38",
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
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::leave-group,
     :name "leaveGroup",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-time-to-live,
     :name "setMulticastTimeToLive",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "ttl", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::set-multicast-loopback-mode,
     :name "setMulticastLoopbackMode",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}
    {:id ::get-joined-groups,
     :name "getJoinedGroups",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "groups", :type "[array-of-strings]"}]}}]}
    {:id ::secure,
     :name "secure",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "integer"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))