(ns chromex.serial
  "Use the chrome.serial API to read from and write to a device
   connected to a serial port.
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/serial"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-devices
  "Returns information about available serial devices on the system. The list is regenerated each time this method is called.
   
     |callback| - Called with the list of DeviceInfo objects.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-devices &form)))

(defmacro connect
  "Connects to a given serial port.
   
     |path| - The system path of the serial port to open.
     |options| - Port configuration options.
     |callback| - Called when the connection has been opened.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([path options #_callback] (gen-call :function ::connect &form path options))
  ([path] `(connect ~path :omit)))

(defmacro update
  "Update the option settings on an open serial port connection.
   
     |connectionId| - The id of the opened connection.
     |options| - Port configuration options.
     |callback| - Called when the configuation has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id options #_callback] (gen-call :function ::update &form connection-id options)))

(defmacro disconnect
  "Disconnects from a serial port.
   
     |connectionId| - The id of the opened connection.
     |callback| - Called when the connection has been closed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::disconnect &form connection-id)))

(defmacro set-paused
  "Pauses or unpauses an open connection.
   
     |connectionId| - The id of the opened connection.
     |paused| - Flag to indicate whether to pause or unpause.
     |callback| - Called when the connection has been successfully paused or              unpaused.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id paused #_callback] (gen-call :function ::set-paused &form connection-id paused)))

(defmacro get-info
  "Retrieves the state of a given connection.
   
     |connectionId| - The id of the opened connection.
     |callback| - Called with connection state information when available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::get-info &form connection-id)))

(defmacro get-connections
  "Retrieves the list of currently opened serial port connections owned by the application.
   
     |callback| - Called with the list of connections when available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-connections &form)))

(defmacro send
  "Writes data to the given connection.
   
     |connectionId| - The id of the connection.
     |data| - The data to send.
     |callback| - Called when the operation has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id data #_callback] (gen-call :function ::send &form connection-id data)))

(defmacro flush
  "Flushes all bytes in the given connection's input and output buffers.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::flush &form connection-id)))

(defmacro get-control-signals
  "Retrieves the state of control signals on a given connection.
   
     |connectionId| - The id of the connection.
     |callback| - Called when the control signals are available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::get-control-signals &form connection-id)))

(defmacro set-control-signals
  "Sets the state of control signals on a given connection.
   
     |connectionId| - The id of the connection.
     |signals| - The set of signal changes to send to the device.
     |callback| - Called once the control signals have been set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id signals #_callback] (gen-call :function ::set-control-signals &form connection-id signals)))

(defmacro set-break
  "Suspends character transmission on a given connection and places the transmission line in a break state until the
   clearBreak is called.
   
     |connectionId| - The id of the connection.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::set-break &form connection-id)))

(defmacro clear-break
  "Restore character transmission on a given connection and place the transmission line in a nonbreak state.
   
     |connectionId| - The id of the connection.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([connection-id #_callback] (gen-call :function ::clear-break &form connection-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-receive-events
  "Event raised when data has been read from the connection.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))
(defmacro tap-on-receive-error-events
  "Event raised when an error occurred while the runtime was waiting for data on the serial port. Once this event is raised,
   the connection may be set to paused. A 'timeout' error does not pause the connection.
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
  {:namespace "chrome.serial",
   :since "23",
   :functions
   [{:id ::get-devices,
     :name "getDevices",
     :since "33",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "ports", :type "[array-of-objects]"}]}}]}
    {:id ::connect,
     :name "connect",
     :since "33",
     :callback? true,
     :params
     [{:name "path", :type "string"}
      {:name "options", :optional? true, :type "serial.ConnectionOptions"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-info", :type "serial.ConnectionInfo"}]}}]}
    {:id ::update,
     :name "update",
     :since "33",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "options", :type "serial.ConnectionOptions"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::disconnect,
     :name "disconnect",
     :since "33",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::set-paused,
     :name "setPaused",
     :since "33",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"} {:name "paused", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::get-info,
     :name "getInfo",
     :since "33",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-info", :type "serial.ConnectionInfo"}]}}]}
    {:id ::get-connections,
     :name "getConnections",
     :since "33",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-infos", :type "[array-of-serial.ConnectionInfos]"}]}}]}
    {:id ::send,
     :name "send",
     :since "33",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback", :type :callback, :callback {:params [{:name "send-info", :type "object"}]}}]}
    {:id ::flush,
     :name "flush",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::get-control-signals,
     :name "getControlSignals",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "signals", :type "object"}]}}]}
    {:id ::set-control-signals,
     :name "setControlSignals",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "signals", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::set-break,
     :name "setBreak",
     :since "45",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::clear-break,
     :name "clearBreak",
     :since "45",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}],
   :events
   [{:id ::on-receive, :name "onReceive", :since "33", :params [{:name "info", :type "object"}]}
    {:id ::on-receive-error, :name "onReceiveError", :since "33", :params [{:name "info", :type "object"}]}]})

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