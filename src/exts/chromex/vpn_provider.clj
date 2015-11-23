(ns chromex.vpn-provider
  "Use the chrome.vpnProvider API to implement a VPN
   client.
   
     * available since Chrome 43
     * https://developer.chrome.com/extensions/vpnProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create-config
  "Creates a new VPN configuration that persists across multiple login sessions of the user.
   
     |name| - The name of the VPN configuration.
     |callback| - Called when the configuration is created or if there is an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([name #_callback] (gen-call :function ::create-config &form name)))

(defmacro destroy-config
  "Destroys a VPN configuration created by the extension.
   
     |id| - ID of the VPN configuration to destroy.
     |callback| - Called when the configuration is destroyed or if there is an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id #_callback] (gen-call :function ::destroy-config &form id)))

(defmacro set-parameters
  "Sets the parameters for the VPN session. This should be called immediately after 'connected' is received from the platform.
   This will succeed only when the VPN session is owned by the extension.
   
     |parameters| - The parameters for the VPN session.
     |callback| - Called when the parameters are set or if there is an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([parameters #_callback] (gen-call :function ::set-parameters &form parameters)))

(defmacro send-packet
  "Sends an IP packet through the tunnel created for the VPN session. This will succeed only when the VPN session is owned by
   the extension.
   
     |data| - The IP packet to be sent to the platform.
     |callback| - Called when the packet is sent or if there is an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([data #_callback] (gen-call :function ::send-packet &form data)))

(defmacro notify-connection-state-changed
  "Notifies the VPN session state to the platform. This will succeed only when the VPN session is owned by the extension.
   
     |state| - The VPN session state of the VPN client.
     |callback| - Called when the notification is complete or if there is an error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([state #_callback] (gen-call :function ::notify-connection-state-changed &form state)))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-platform-message-events
  "Triggered when a message is received from the platform for a VPN configuration owned by the extension."
  ([channel] (gen-call :event ::on-platform-message &form channel)))

(defmacro tap-on-packet-received-events
  "Triggered when an IP packet is received via the tunnel for the VPN session owned by the extension."
  ([channel] (gen-call :event ::on-packet-received &form channel)))

(defmacro tap-on-config-removed-events
  "Triggered when a configuration created by the extension is removed by the platform."
  ([channel] (gen-call :event ::on-config-removed &form channel)))

(defmacro tap-on-config-created-events
  "Triggered when a configuration is created by the platform for the extension."
  ([channel] (gen-call :event ::on-config-created &form channel)))

(defmacro tap-on-ui-event-events
  "Triggered when there is a UI event for the extension. UI events are signals from the platform that indicate to the app that
   a UI dialog needs to be shown to the user."
  ([channel] (gen-call :event ::on-ui-event &form channel)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.vpnProvider",
   :since "43",
   :functions
   [{:id ::create-config,
     :name "createConfig",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "id", :type "string"}]}}]}
    {:id ::destroy-config,
     :name "destroyConfig",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-parameters,
     :name "setParameters",
     :callback? true,
     :params [{:name "parameters", :type "object"} {:name "callback", :type :callback}]}
    {:id ::send-packet,
     :name "sendPacket",
     :callback? true,
     :params [{:name "data", :type "ArrayBuffer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::notify-connection-state-changed,
     :name "notifyConnectionStateChanged",
     :callback? true,
     :params [{:name "state", :type "unknown-type"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-platform-message,
     :name "onPlatformMessage",
     :params [{:name "id", :type "string"} {:name "message", :type "unknown-type"} {:name "error", :type "string"}]}
    {:id ::on-packet-received, :name "onPacketReceived", :params [{:name "data", :type "ArrayBuffer"}]}
    {:id ::on-config-removed, :name "onConfigRemoved", :params [{:name "id", :type "string"}]}
    {:id ::on-config-created,
     :name "onConfigCreated",
     :params [{:name "id", :type "string"} {:name "name", :type "string"} {:name "data", :type "object"}]}
    {:id ::on-ui-event,
     :name "onUIEvent",
     :params [{:name "event", :type "unknown-type"} {:name "id", :optional? true, :type "string"}]}]})

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