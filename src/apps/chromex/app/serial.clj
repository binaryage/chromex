(ns chromex.app.serial
  "Use the chrome.serial API to read from and write to a device
   connected to a serial port.

     * available since Chrome 38
     * https://developer.chrome.com/apps/serial"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-devices
  "Returns information about available serial devices on the system. The list is regenerated each time this method is called.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [ports] where:

     |ports| - https://developer.chrome.com/apps/serial#property-callback-ports.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-getDevices."
  ([] (gen-call :function ::get-devices &form)))

(defmacro connect
  "Connects to a given serial port.

     |path| - The system path of the serial port to open.
     |options| - Port configuration options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [connection-info] where:

     |connection-info| - https://developer.chrome.com/apps/serial#property-callback-connectionInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-connect."
  ([path options] (gen-call :function ::connect &form path options))
  ([path] `(connect ~path :omit)))

(defmacro update
  "Update the option settings on an open serial port connection.

     |connection-id| - The id of the opened connection.
     |options| - Port configuration options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-update."
  ([connection-id options] (gen-call :function ::update &form connection-id options)))

(defmacro disconnect
  "Disconnects from a serial port.

     |connection-id| - The id of the opened connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-disconnect."
  ([connection-id] (gen-call :function ::disconnect &form connection-id)))

(defmacro set-paused
  "Pauses or unpauses an open connection.

     |connection-id| - The id of the opened connection.
     |paused| - Flag to indicate whether to pause or unpause.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-setPaused."
  ([connection-id paused] (gen-call :function ::set-paused &form connection-id paused)))

(defmacro get-info
  "Retrieves the state of a given connection.

     |connection-id| - The id of the opened connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [connection-info] where:

     |connection-info| - https://developer.chrome.com/apps/serial#property-callback-connectionInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-getInfo."
  ([connection-id] (gen-call :function ::get-info &form connection-id)))

(defmacro get-connections
  "Retrieves the list of currently opened serial port connections owned by the application.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [connection-infos] where:

     |connection-infos| - https://developer.chrome.com/apps/serial#property-callback-connectionInfos.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-getConnections."
  ([] (gen-call :function ::get-connections &form)))

(defmacro send
  "Writes data to the given connection.

     |connection-id| - The id of the connection.
     |data| - The data to send.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [send-info] where:

     |send-info| - https://developer.chrome.com/apps/serial#property-callback-sendInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-send."
  ([connection-id data] (gen-call :function ::send &form connection-id data)))

(defmacro flush
  "Flushes all bytes in the given connection's input and output buffers.

     |connection-id| - https://developer.chrome.com/apps/serial#property-flush-connectionId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-flush."
  ([connection-id] (gen-call :function ::flush &form connection-id)))

(defmacro get-control-signals
  "Retrieves the state of control signals on a given connection.

     |connection-id| - The id of the connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [signals] where:

     |signals| - https://developer.chrome.com/apps/serial#property-callback-signals.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-getControlSignals."
  ([connection-id] (gen-call :function ::get-control-signals &form connection-id)))

(defmacro set-control-signals
  "Sets the state of control signals on a given connection.

     |connection-id| - The id of the connection.
     |signals| - The set of signal changes to send to the device.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-setControlSignals."
  ([connection-id signals] (gen-call :function ::set-control-signals &form connection-id signals)))

(defmacro set-break
  "Suspends character transmission on a given connection and places the transmission line in a break state until the
   clearBreak is called.

     |connection-id| - The id of the connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-setBreak."
  ([connection-id] (gen-call :function ::set-break &form connection-id)))

(defmacro clear-break
  "Restore character transmission on a given connection and place the transmission line in a nonbreak state.

     |connection-id| - The id of the connection.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/serial#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/serial#method-clearBreak."
  ([connection-id] (gen-call :function ::clear-break &form connection-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-receive-events
  "Event raised when data has been read from the connection.

   Events will be put on the |channel| with signature [::on-receive [info]] where:

     |info| - Event data.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/serial#event-onReceive."
  ([channel & args] (apply gen-call :event ::on-receive &form channel args)))

(defmacro tap-on-receive-error-events
  "Event raised when an error occurred while the runtime was waiting for data on the serial port. Once this event is raised,
   the connection may be set to paused. A 'timeout' error does not pause the connection.

   Events will be put on the |channel| with signature [::on-receive-error [info]] where:

     |info| - https://developer.chrome.com/apps/serial#property-onReceiveError-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/serial#event-onReceiveError."
  ([channel & args] (apply gen-call :event ::on-receive-error &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.serial namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.serial",
   :since "38",
   :functions
   [{:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "ports", :type "[array-of-objects]"}]}}]}
    {:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "path", :type "string"}
      {:name "options", :optional? true, :type "serial.ConnectionOptions"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-info", :type "serial.ConnectionInfo"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "options", :type "serial.ConnectionOptions"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::set-paused,
     :name "setPaused",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"} {:name "paused", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-info", :type "serial.ConnectionInfo"}]}}]}
    {:id ::get-connections,
     :name "getConnections",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "connection-infos", :type "[array-of-serial.ConnectionInfos]"}]}}]}
    {:id ::send,
     :name "send",
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
   [{:id ::on-receive, :name "onReceive", :params [{:name "info", :type "object"}]}
    {:id ::on-receive-error, :name "onReceiveError", :params [{:name "info", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))