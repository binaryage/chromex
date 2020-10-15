(ns chromex.app.bluetooth-private
  "Use the chrome.bluetoothPrivate API to control the Bluetooth
   adapter state and handle device pairing.
   NOTE: This IDL is dependent on bluetooth.idl.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-adapter-state
  "Changes the state of the Bluetooth adapter.

     |adapter-state| - The new state of the adapter.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([adapter-state] (gen-call :function ::set-adapter-state &form adapter-state)))

(defmacro set-pairing-response
  "  |options| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::set-pairing-response &form options)))

(defmacro disconnect-all
  "Tears down all connections to the given device.

     |device-address| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([device-address] (gen-call :function ::disconnect-all &form device-address)))

(defmacro forget-device
  "Forgets the given device.

     |device-address| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([device-address] (gen-call :function ::forget-device &form device-address)))

(defmacro set-discovery-filter
  "Set or clear discovery filter.

     |discovery-filter| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([discovery-filter] (gen-call :function ::set-discovery-filter &form discovery-filter)))

(defmacro connect
  "Connects to the given device. This will only throw an error if the device address is invalid or the device is already
   connected. Otherwise this will succeed and invoke |callback| with ConnectResultType.

     |device-address| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([device-address] (gen-call :function ::connect &form device-address)))

(defmacro pair
  "Pairs the given device.

     |device-address| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([device-address] (gen-call :function ::pair &form device-address)))

(defmacro record-pairing
  "Record that a pairing attempt finished. Ignores cancellations.

     |transport| - ?
     |pairing-duration-ms| - ?
     |result| - ?"
  ([transport pairing-duration-ms result] (gen-call :function ::record-pairing &form transport pairing-duration-ms result))
  ([transport pairing-duration-ms] `(record-pairing ~transport ~pairing-duration-ms :omit)))

(defmacro record-reconnection
  "Record that a user-initiated reconnection attempt to an already paired device finished. Ignores cancellations.

     |result| - ?"
  ([result] (gen-call :function ::record-reconnection &form result))
  ([] `(record-reconnection :omit)))

(defmacro record-device-selection
  "Record that a user selected a device to connect to.

     |selection-duration-ms| - ?
     |was-paired| - ?
     |transport| - ?"
  ([selection-duration-ms was-paired transport] (gen-call :function ::record-device-selection &form selection-duration-ms was-paired transport)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-pairing-events
  "Fired when a pairing event occurs.

   Events will be put on the |channel| with signature [::on-pairing [pairing-event]] where:

     |pairing-event| - A pairing event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-pairing &form channel args)))

(defmacro tap-on-device-address-changed-events
  "Fired when a Bluetooth device changed its address.

   Events will be put on the |channel| with signature [::on-device-address-changed [device old-address]] where:

     |device| - ?
     |old-address| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-device-address-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.bluetooth-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bluetoothPrivate",
   :since "38",
   :functions
   [{:id ::set-adapter-state,
     :name "setAdapterState",
     :callback? true,
     :params [{:name "adapter-state", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-pairing-response,
     :name "setPairingResponse",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::disconnect-all,
     :name "disconnectAll",
     :since "41",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::forget-device,
     :name "forgetDevice",
     :since "48",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-discovery-filter,
     :name "setDiscoveryFilter",
     :since "43",
     :callback? true,
     :params [{:name "discovery-filter", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::connect,
     :name "connect",
     :since "48",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothPrivate.ConnectResultType"}]}}]}
    {:id ::pair,
     :name "pair",
     :since "47",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::record-pairing,
     :name "recordPairing",
     :since "76",
     :params
     [{:name "transport", :type "bluetooth.Transport"}
      {:name "pairing-duration-ms", :since "77", :type "integer"}
      {:name "result", :optional? true, :since "83", :type "bluetoothPrivate.ConnectResultType"}]}
    {:id ::record-reconnection,
     :name "recordReconnection",
     :since "76",
     :params [{:name "result", :optional? true, :since "83", :type "bluetoothPrivate.ConnectResultType"}]}
    {:id ::record-device-selection,
     :name "recordDeviceSelection",
     :since "77",
     :params
     [{:name "selection-duration-ms", :type "integer"}
      {:name "was-paired", :type "boolean"}
      {:name "transport", :type "bluetooth.Transport"}]}],
   :events
   [{:id ::on-pairing, :name "onPairing", :params [{:name "pairing-event", :type "object"}]}
    {:id ::on-device-address-changed,
     :name "onDeviceAddressChanged",
     :since "82",
     :params [{:name "device", :type "bluetooth.Device"} {:name "old-address", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))