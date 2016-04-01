(ns chromex.app.vpn-provider
  "Use the chrome.vpnProvider API to implement a VPN
   client.

     * available since Chrome 43
     * https://developer.chrome.com/apps/vpnProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create-config
  "Creates a new VPN configuration that persists across multiple login sessions of the user.

     |name| - The name of the VPN configuration.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [id] where:

     |id| - A unique ID for the created configuration, or undefined on failure.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/vpnProvider#method-createConfig."
  ([name] (gen-call :function ::create-config &form name)))

(defmacro destroy-config
  "Destroys a VPN configuration created by the extension.

     |id| - ID of the VPN configuration to destroy.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/vpnProvider#method-destroyConfig."
  ([id] (gen-call :function ::destroy-config &form id)))

(defmacro set-parameters
  "Sets the parameters for the VPN session. This should be called immediately after 'connected' is received from the platform.
   This will succeed only when the VPN session is owned by the extension.

     |parameters| - The parameters for the VPN session.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/vpnProvider#method-setParameters."
  ([parameters] (gen-call :function ::set-parameters &form parameters)))

(defmacro send-packet
  "Sends an IP packet through the tunnel created for the VPN session. This will succeed only when the VPN session is owned by
   the extension.

     |data| - The IP packet to be sent to the platform.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/vpnProvider#method-sendPacket."
  ([data] (gen-call :function ::send-packet &form data)))

(defmacro notify-connection-state-changed
  "Notifies the VPN session state to the platform. This will succeed only when the VPN session is owned by the extension.

     |state| - The VPN session state of the VPN client.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/vpnProvider#method-notifyConnectionStateChanged."
  ([state] (gen-call :function ::notify-connection-state-changed &form state)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-platform-message-events
  "Triggered when a message is received from the platform for a VPN configuration owned by the extension.

   Events will be put on the |channel| with signature [::on-platform-message [id message error]] where:

     |id| - ID of the configuration the message is intended for.
     |message| - The message received from the platform.  Note that new message types may be added in future Chrome versions
                 to support new features.
     |error| - Error message when there is an error.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/vpnProvider#event-onPlatformMessage."
  ([channel & args] (apply gen-call :event ::on-platform-message &form channel args)))

(defmacro tap-on-packet-received-events
  "Triggered when an IP packet is received via the tunnel for the VPN session owned by the extension.

   Events will be put on the |channel| with signature [::on-packet-received [data]] where:

     |data| - The IP packet received from the platform.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/vpnProvider#event-onPacketReceived."
  ([channel & args] (apply gen-call :event ::on-packet-received &form channel args)))

(defmacro tap-on-config-removed-events
  "Triggered when a configuration created by the extension is removed by the platform.

   Events will be put on the |channel| with signature [::on-config-removed [id]] where:

     |id| - ID of the removed configuration.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/vpnProvider#event-onConfigRemoved."
  ([channel & args] (apply gen-call :event ::on-config-removed &form channel args)))

(defmacro tap-on-config-created-events
  "Triggered when a configuration is created by the platform for the extension.

   Events will be put on the |channel| with signature [::on-config-created [id name data]] where:

     |id| - ID of the configuration created.
     |name| - Name of the configuration created.
     |data| - Configuration data provided by the administrator.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/vpnProvider#event-onConfigCreated."
  ([channel & args] (apply gen-call :event ::on-config-created &form channel args)))

(defmacro tap-on-ui-event-events
  "Triggered when there is a UI event for the extension. UI events are signals from the platform that indicate to the app that
   a UI dialog needs to be shown to the user.

   Events will be put on the |channel| with signature [::on-ui-event [event id]] where:

     |event| - The UI event that is triggered.
     |id| - ID of the configuration for which the UI event was triggered.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/vpnProvider#event-onUIEvent."
  ([channel & args] (apply gen-call :event ::on-ui-event &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.vpn-provider namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))