(ns chromex.bluetooth-private
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

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-adapter-state
  "Changes the state of the Bluetooth adapter."
  ([adapter-state #_callback] (gen-call :function ::set-adapter-state (meta &form) adapter-state)))

(defmacro set-pairing-response ([options #_callback] (gen-call :function ::set-pairing-response (meta &form) options)))

(defmacro disconnect-all
  "Tears down all connections to the given device."
  ([device-address #_callback] (gen-call :function ::disconnect-all (meta &form) device-address)))

(defmacro forget-device
  "Forgets the given device."
  ([device-address #_callback] (gen-call :function ::forget-device (meta &form) device-address)))

(defmacro set-discovery-filter
  "Set or clear discovery filter."
  ([discovery-filter #_callback] (gen-call :function ::set-discovery-filter (meta &form) discovery-filter)))

(defmacro connect
  "Connects to the given device. This will only throw an error if the device address is invalid or the device is
   already connected. Otherwise this will succeed and invoke |callback| with ConnectResultType."
  ([device-address #_callback] (gen-call :function ::connect (meta &form) device-address)))

(defmacro pair
  "Pairs the given device."
  ([device-address #_callback] (gen-call :function ::pair (meta &form) device-address)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-pairing-events
  "Fired when a pairing event occurs."
  [channel]
  (gen-call :event ::on-pairing (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

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

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))