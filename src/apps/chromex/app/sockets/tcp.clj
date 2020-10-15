(ns chromex.app.sockets.tcp
  "Use the chrome.sockets.tcp API to send and receive data over the
   network using TCP connections. This API supersedes the TCP functionality
   previously found in the chrome.socket API.

     * available since Chrome 38
     * https://developer.chrome.com/apps/sockets.tcp"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a TCP socket.

     |properties| - The socket properties (optional).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [create-info] where:

     |create-info| - The result of the socket creation.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-create."
  ([properties] (gen-call :function ::create &form properties))
  ([] `(create :omit)))

(defmacro update
  "Updates the socket properties.

     |socket-id| - The socket identifier.
     |properties| - The properties to update.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-update."
  ([socket-id properties] (gen-call :function ::update &form socket-id properties)))

(defmacro set-paused
  "Enables or disables the application from receiving messages from its peer. The default value is 'false'. Pausing a socket
   is typically used by an application to throttle data sent by its peer. When a socket is paused, no onReceive event is
   raised. When a socket is connected and un-paused, onReceive events are raised again when messages are received.

     |socket-id| - https://developer.chrome.com/apps/sockets.tcp#property-setPaused-socketId.
     |paused| - https://developer.chrome.com/apps/sockets.tcp#property-setPaused-paused.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-setPaused."
  ([socket-id paused] (gen-call :function ::set-paused &form socket-id paused)))

(defmacro set-keep-alive
  "Enables or disables the keep-alive functionality for a TCP connection.

     |socket-id| - The socket identifier.
     |enable| - If true, enable keep-alive functionality.
     |delay| - Set the delay seconds between the last data packet received and the first keepalive probe. Default is 0.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The result code returned from the underlying network call. A negative value indicates an error.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-setKeepAlive."
  ([socket-id enable delay] (gen-call :function ::set-keep-alive &form socket-id enable delay))
  ([socket-id enable] `(set-keep-alive ~socket-id ~enable :omit)))

(defmacro set-no-delay
  "Sets or clears TCP_NODELAY for a TCP connection. Nagle's algorithm will be disabled when TCP_NODELAY is set.

     |socket-id| - The socket identifier.
     |no-delay| - If true, disables Nagle's algorithm.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The result code returned from the underlying network call. A negative value indicates an error.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-setNoDelay."
  ([socket-id no-delay] (gen-call :function ::set-no-delay &form socket-id no-delay)))

(defmacro connect
  "Connects the socket to a remote machine. When the connect operation completes successfully, onReceive events are raised
   when data is received from the peer. If a network error occurs while the runtime is receiving packets, a onReceiveError
   event is raised, at which point no more onReceive event will be raised for this socket until the resume method is called.

     |socket-id| - The socket identifier.
     |peer-address| - The address of the remote machine. DNS name, IPv4 and  IPv6 formats are supported.
     |peer-port| - The port of the remote machine.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The result code returned from the underlying network call. A negative value indicates an error.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-connect."
  ([socket-id peer-address peer-port] (gen-call :function ::connect &form socket-id peer-address peer-port)))

(defmacro disconnect
  "Disconnects the socket.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-disconnect."
  ([socket-id] (gen-call :function ::disconnect &form socket-id)))

(defmacro secure
  "Start a TLS client connection over the connected TCP client socket.

     |socket-id| - The existing, connected socket to use.
     |options| - Constraints and parameters for the TLS connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/sockets.tcp#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-secure."
  ([socket-id options] (gen-call :function ::secure &form socket-id options))
  ([socket-id] `(secure ~socket-id :omit)))

(defmacro send
  "Sends data on the given TCP socket.

     |socket-id| - The socket identifier.
     |data| - The data to send.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [send-info] where:

     |send-info| - Result of the send method.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-send."
  ([socket-id data] (gen-call :function ::send &form socket-id data)))

(defmacro close
  "Closes the socket and releases the address/port the socket is bound to. Each socket created should be closed after use. The
   socket id is no no longer valid as soon at the function is called. However, the socket is guaranteed to be closed only when
   the callback is invoked.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-close."
  ([socket-id] (gen-call :function ::close &form socket-id)))

(defmacro get-info
  "Retrieves the state of the given socket.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [socket-info] where:

     |socket-info| - Object containing the socket information.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-getInfo."
  ([socket-id] (gen-call :function ::get-info &form socket-id)))

(defmacro get-sockets
  "Retrieves the list of currently opened sockets owned by the application.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [socket-infos] where:

     |socket-infos| - Array of object containing socket information.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/sockets.tcp#method-getSockets."
  ([] (gen-call :function ::get-sockets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-receive-events
  "Event raised when data has been received for a given socket.

   Events will be put on the |channel| with signature [::on-receive [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/sockets.tcp#event-onReceive."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))

(defmacro tap-on-receive-error-events
  "Event raised when a network error occured while the runtime was waiting for data on the socket address and port. Once this
   event is raised, the socket is set to paused and no more onReceive events are raised for this socket.

   Events will be put on the |channel| with signature [::on-receive-error [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/sockets.tcp#event-onReceiveError."
  ([channel & args] (apply gen-call :event ::on-receive-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.sockets.tcp namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.sockets.tcp",
   :since "38",
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))