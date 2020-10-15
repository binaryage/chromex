(ns chromex.app.bluetooth-socket
  "Use the chrome.bluetoothSocket API to send and receive data
   to Bluetooth devices using RFCOMM and L2CAP connections.

     * available since Chrome 38
     * https://developer.chrome.com/apps/bluetoothSocket"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a Bluetooth socket.

     |properties| - The socket properties (optional).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [create-info] where:

     |create-info| - The result of the socket creation.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-create."
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

   https://developer.chrome.com/apps/bluetoothSocket#method-update."
  ([socket-id properties] (gen-call :function ::update &form socket-id properties)))

(defmacro set-paused
  "Enables or disables a connected socket from receiving messages from its peer, or a listening socket from accepting new
   connections. The default value is 'false'. Pausing a connected socket is typically used by an application to throttle data
   sent by its peer. When a connected socket is paused, no onReceiveevent is raised. When a socket is connected and un-paused,
   onReceive events are raised again when messages are received. When a listening socket is paused, new connections are
   accepted until its backlog is full then additional connection requests are refused. onAccept events are raised only when
   the socket is un-paused.

     |socket-id| - https://developer.chrome.com/apps/bluetoothSocket#property-setPaused-socketId.
     |paused| - https://developer.chrome.com/apps/bluetoothSocket#property-setPaused-paused.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-setPaused."
  ([socket-id paused] (gen-call :function ::set-paused &form socket-id paused)))

(defmacro listen-using-rfcomm
  "Listen for connections using the RFCOMM protocol.

     |socket-id| - The socket identifier.
     |uuid| - Service UUID to listen on.
     |options| - Optional additional options for the service.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-listenUsingRfcomm."
  ([socket-id uuid options] (gen-call :function ::listen-using-rfcomm &form socket-id uuid options))
  ([socket-id uuid] `(listen-using-rfcomm ~socket-id ~uuid :omit)))

(defmacro listen-using-l2cap
  "Listen for connections using the L2CAP protocol.

     |socket-id| - The socket identifier.
     |uuid| - Service UUID to listen on.
     |options| - Optional additional options for the service.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-listenUsingL2cap."
  ([socket-id uuid options] (gen-call :function ::listen-using-l2cap &form socket-id uuid options))
  ([socket-id uuid] `(listen-using-l2cap ~socket-id ~uuid :omit)))

(defmacro connect
  "Connects the socket to a remote Bluetooth device. When the connect operation completes successfully, onReceive events are
   raised when data is received from the peer. If a network error occur while the runtime is receiving packets, a
   onReceiveError event is raised, at which point no more onReceive event will be raised for this socket until the
   setPaused(false) method is called.

     |socket-id| - The socket identifier.
     |address| - The address of the Bluetooth device.
     |uuid| - The UUID of the service to connect to.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-connect."
  ([socket-id address uuid] (gen-call :function ::connect &form socket-id address uuid)))

(defmacro disconnect
  "Disconnects the socket. The socket identifier remains valid.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-disconnect."
  ([socket-id] (gen-call :function ::disconnect &form socket-id)))

(defmacro close
  "Disconnects and destroys the socket. Each socket created should be closed after use. The socket id is no longer valid as
   soon at the function is called. However, the socket is guaranteed to be closed only when the callback is invoked.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-close."
  ([socket-id] (gen-call :function ::close &form socket-id)))

(defmacro send
  "Sends data on the given Bluetooth socket.

     |socket-id| - The socket identifier.
     |data| - The data to send.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [bytes-sent] where:

     |bytes-sent| - The number of bytes sent.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-send."
  ([socket-id data] (gen-call :function ::send &form socket-id data)))

(defmacro get-info
  "Retrieves the state of the given socket.

     |socket-id| - The socket identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [socket-info] where:

     |socket-info| - Object containing the socket information.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-getInfo."
  ([socket-id] (gen-call :function ::get-info &form socket-id)))

(defmacro get-sockets
  "Retrieves the list of currently opened sockets owned by the application.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [sockets] where:

     |sockets| - https://developer.chrome.com/apps/bluetoothSocket#property-callback-sockets.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetoothSocket#method-getSockets."
  ([] (gen-call :function ::get-sockets &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-accept-events
  "Event raised when a connection has been established for a given socket.

   Events will be put on the |channel| with signature [::on-accept [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothSocket#event-onAccept."
  ([channel & args] (apply gen-call :event ::on-accept &form channel args)))

(defmacro tap-on-accept-error-events
  "Event raised when a network error occurred while the runtime was waiting for new connections on the given socket. Once this
   event is raised, the socket is set to paused and no more onAccept events are raised for this socket.

   Events will be put on the |channel| with signature [::on-accept-error [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothSocket#event-onAcceptError."
  ([channel & args] (apply gen-call :event ::on-accept-error &form channel args)))

(defmacro tap-on-receive-events
  "Event raised when data has been received for a given socket.

   Events will be put on the |channel| with signature [::on-receive [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothSocket#event-onReceive."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))

(defmacro tap-on-receive-error-events
  "Event raised when a network error occured while the runtime was waiting for data on the socket. Once this event is raised,
   the socket is set to paused and no more onReceive events are raised for this socket.

   Events will be put on the |channel| with signature [::on-receive-error [info]] where:

     |info| - The event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetoothSocket#event-onReceiveError."
  ([channel & args] (apply gen-call :event ::on-receive-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.bluetooth-socket namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bluetoothSocket",
   :since "38",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "properties", :optional? true, :type "bluetoothSocket.SocketProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "create-info", :type "object"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "properties", :type "bluetoothSocket.SocketProperties"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-paused,
     :name "setPaused",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "paused", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::listen-using-rfcomm,
     :name "listenUsingRfcomm",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "uuid", :type "string"}
      {:name "options", :optional? true, :type "bluetoothSocket.ListenOptions"}
      {:name "callback", :type :callback}]}
    {:id ::listen-using-l2cap,
     :name "listenUsingL2cap",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "uuid", :type "string"}
      {:name "options", :optional? true, :type "bluetoothSocket.ListenOptions"}
      {:name "callback", :type :callback}]}
    {:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "address", :type "string"}
      {:name "uuid", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::close,
     :name "close",
     :callback? true,
     :params [{:name "socket-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::send,
     :name "send",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "bytes-sent", :type "integer"}]}}]}
    {:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "socket-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "socket-info", :type "bluetoothSocket.SocketInfo"}]}}]}
    {:id ::get-sockets,
     :name "getSockets",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "sockets", :type "[array-of-bluetoothSocket.SocketInfos]"}]}}]}],
   :events
   [{:id ::on-accept, :name "onAccept", :params [{:name "info", :type "object"}]}
    {:id ::on-accept-error, :name "onAcceptError", :params [{:name "info", :type "object"}]}
    {:id ::on-receive, :name "onReceive", :params [{:name "info", :type "object"}]}
    {:id ::on-receive-error, :name "onReceiveError", :params [{:name "info", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))