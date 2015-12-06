(ns chromex.app.bluetooth-private
  "Use the chrome.bluetoothPrivate API to control the Bluetooth
   adapter state and handle device pairing.
   NOTE: This IDL is dependent on bluetooth.idl.
   
     * available since Chrome 36
     * https://developer.chrome.com/extensions/bluetoothPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-adapter-state
  "Changes the state of the Bluetooth adapter.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([adapter-state #_callback] (gen-call :function ::set-adapter-state &form adapter-state)))

(defmacro set-pairing-response
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::set-pairing-response &form options)))

(defmacro disconnect-all
  "Tears down all connections to the given device.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::disconnect-all &form device-address)))

(defmacro forget-device
  "Forgets the given device.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::forget-device &form device-address)))

(defmacro set-discovery-filter
  "Set or clear discovery filter.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([discovery-filter #_callback] (gen-call :function ::set-discovery-filter &form discovery-filter)))

(defmacro connect
  "Connects to the given device. This will only throw an error if the device address is invalid or the device is already
   connected. Otherwise this will succeed and invoke |callback| with ConnectResultType.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::connect &form device-address)))

(defmacro pair
  "Pairs the given device.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::pair &form device-address)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-pairing-events
  "Fired when a pairing event occurs.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-pairing &form channel args)))

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
  {:namespace "chrome.bluetoothPrivate",
   :since "36",
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
       :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::pair,
     :name "pair",
     :since "47",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}],
   :events [{:id ::on-pairing, :name "onPairing", :params [{:name "pairing-event", :type "object"}]}]})

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