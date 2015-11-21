(ns chromex.networking-private
  "The chrome.networkingPrivate API is used for configuring
   network connections (Cellular, Ethernet, VPN, WiFi or WiMAX). This private
   API is only valid if called from a browser or app associated with the
   primary user. See the Open Network Configuration (ONC) documentation for
   descriptions of properties:
   
   src/components/onc/docs/onc_spec.html, or the
   
   Open Network Configuration page at chromium.org.
   
   NOTE: Most dictionary properties and enum values use UpperCamelCase to match
   the ONC spec instead of the JavaScript lowerCamelCase convention.
   
   'State' properties describe just the ONC properties returned by
   'networkingPrivate.getState' and 'networkingPrivate.getNetworks'.
   
   'Config' properties describe just the ONC properties that can be configured
   through this API. NOTE: Not all configuration properties are exposed at this
   time, only those currently required by the Chrome Settings UI.
   TODO(stevenjb): Provide all configuration properties and types,
   crbug.com/380937.
   
   TODO(stevenjb/pneubeck): Merge the ONC documentation with this document and
   use it as the ONC specification.
   
     * available since Chrome 28
     * https://developer.chrome.com/extensions/networkingPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-properties
  "Gets all the properties of the network with id networkGuid. Includes all properties of the network (read-only and
   read/write values).
   
     |networkGuid| - The GUID of the network to get properties for.
     |callback| - Called with the network properties when received."
  [network-guid #_callback]
  (gen-call :function ::get-properties (meta &form) network-guid))

(defmacro get-managed-properties
  "Gets the merged properties of the network with id networkGuid from the sources: User settings, shared settings,
   user policy, device policy and the currently active settings.
   
     |networkGuid| - The GUID of the network to get properties for.
     |callback| - Called with the managed network properties when received."
  [network-guid #_callback]
  (gen-call :function ::get-managed-properties (meta &form) network-guid))

(defmacro get-state
  "Gets the cached read-only properties of the network with id networkGuid. This is meant to be a higher performance
   function than 'getProperties', which requires a round trip to query the networking subsystem. The following
   properties are returned for all networks: GUID, Type, Name, WiFi.Security. Additional properties are provided for
   visible networks: ConnectionState, ErrorState, WiFi.SignalStrength, Cellular.NetworkTechnology,
   Cellular.ActivationState, Cellular.RoamingState.
   
     |networkGuid| - The GUID of the network to get properties for.
     |callback| - Called immediately with the network state properties."
  [network-guid #_callback]
  (gen-call :function ::get-state (meta &form) network-guid))

(defmacro set-properties
  "Sets the properties of the network with id networkGuid.
   
     |networkGuid| - The GUID of the network to set properties for.
     |properties| - The properties to set.
     |callback| - Called when the operation has completed."
  [network-guid properties #_callback]
  (gen-call :function ::set-properties (meta &form) network-guid properties))

(defmacro create-network
  "Creates a new network configuration from properties. If a matching configured network already exists, this will
   fail. Otherwise returns the guid of the new network.
   
     |shared| - If true, share this network configuration with other users.
     |properties| - The properties to configure the new network with.
     |callback| - Called with the GUID for the new network configuration once     the network has been created."
  [shared properties #_callback]
  (gen-call :function ::create-network (meta &form) shared properties))

(defmacro forget-network
  "Forgets a network configuration by clearing any configured properties for the network with GUID 'networkGuid'. This
   may also include any other networks with matching identifiers (e.g. WiFi SSID and Security). If no such
   configuration exists, an error will be set and the operation will fail.
   
     |networkGuid| - The GUID of the network to forget.
     |callback| - Called when the operation has completed."
  [network-guid #_callback]
  (gen-call :function ::forget-network (meta &form) network-guid))

(defmacro get-networks
  "Returns a list of network objects with the same properties provided by 'networkingPrivate.getState'. A filter is
   provided to specify the type of networks returned and to limit the number of networks. Networks are ordered by the
   system based on their priority, with connected or connecting networks listed first.
   
     |filter| - Describes which networks to return.
     |callback| - Called with a dictionary of networks and their state     properties when received."
  [filter #_callback]
  (gen-call :function ::get-networks (meta &form) filter))

(defmacro get-visible-networks
  "Deprecated. Please use 'networkingPrivate.getNetworks' with filter.visible = true instead."
  [network-type #_callback]
  (gen-call :function ::get-visible-networks (meta &form) network-type))

(defmacro get-enabled-network-types
  "Deprecated. Please use 'networkingPrivate.getDeviceStates' instead."
  [#_callback]
  (gen-call :function ::get-enabled-network-types (meta &form)))

(defmacro get-device-states
  "Returns a list of 'networkingPrivate.DeviceStateProperties' objects.
   
     |callback| - Called with a list of devices and their state."
  [#_callback]
  (gen-call :function ::get-device-states (meta &form)))

(defmacro enable-network-type
  "Enables any devices matching the specified network type. Note, the type might represent multiple network types
   (e.g. 'Wireless').
   
     |networkType| - The type of network to enable."
  [network-type]
  (gen-call :function ::enable-network-type (meta &form) network-type))

(defmacro disable-network-type
  "Disables any devices matching the specified network type. See note for 'networkingPrivate.enableNetworkType'.
   
     |networkType| - The type of network to disable."
  [network-type]
  (gen-call :function ::disable-network-type (meta &form) network-type))

(defmacro request-network-scan
  "Requests that the networking subsystem scan for new networks and update the list returned by 'getVisibleNetworks'.
   This is only a request: the network subsystem can choose to ignore it.  If the list is updated, then the
   'onNetworkListChanged' event will be fired."
  []
  (gen-call :function ::request-network-scan (meta &form)))

(defmacro start-connect
  "Starts a connection to the network with networkGuid.
   
     |networkGuid| - The GUID of the network to connect to.
     |callback| - Called when the connect request has been sent. Note: the     connection may not have completed.
                  Observe 'onNetworksChanged'     to be notified when a network state changes."
  [network-guid #_callback]
  (gen-call :function ::start-connect (meta &form) network-guid))

(defmacro start-disconnect
  "Starts a disconnect from the network with networkGuid.
   
     |networkGuid| - The GUID of the network to disconnect from.
     |callback| - Called when the disconnect request has been sent. See note     for 'startConnect'."
  [network-guid #_callback]
  (gen-call :function ::start-disconnect (meta &form) network-guid))

(defmacro start-activate
  "Starts activation of the Cellular network with networkGuid. If called for a network that is already activated, or
   for a network with a carrier that can not be directly activated, this will show the account details page for the
   carrier if possible.
   
     |networkGuid| - The GUID of the Cellular network to activate.
     |carrier| - Optional name of carrier to activate.
     |callback| - Called when the activation request has been sent. See note     for 'startConnect'."
  [network-guid carrier #_callback]
  (gen-call :function ::start-activate (meta &form) network-guid carrier))

(defmacro verify-destination
  "Verifies that the device is a trusted device.
   
     |properties| - Properties of the destination to use in verifying that it     is a trusted device.
     |callback| - A callback function that indicates whether or not the device     is a trusted device."
  [properties #_callback]
  (gen-call :function ::verify-destination (meta &form) properties))

(defmacro verify-and-encrypt-credentials
  "Verifies that the device is a trusted device and retrieves encrypted network credentials.
   
     |properties| - Properties of the destination to use in verifying that it     is a trusted device.
     |networkGuid| - The GUID of the Cellular network to activate.
     |callback| - A callback function that receives base64-encoded encrypted     credential data to send to a
                  trusted device."
  [properties network-guid #_callback]
  (gen-call :function ::verify-and-encrypt-credentials (meta &form) properties network-guid))

(defmacro verify-and-encrypt-data
  "Verifies that the device is a trusted device and encrypts supplied data with device public key.
   
     |properties| - Properties of the destination to use in verifying that it     is a trusted device.
     |data| - A string containing the base64-encoded data to encrypt.
     |callback| - A callback function that receives base64-encoded encrypted     data to send to a trusted device."
  [properties data #_callback]
  (gen-call :function ::verify-and-encrypt-data (meta &form) properties data))

(defmacro set-wifi-tdls-enabled-state
  "Enables TDLS for WiFi traffic with a specified peer if available.
   
     |ip_or_mac_address| - The IP or MAC address of the peer with which to     enable a TDLS connection. |enabled

                           If true, enable TDLS, otherwise disable TDLS.
     |callback| - A callback function that receives a string with an error or     the current TDLS status. 'Failed'
                  indicates that the request failed     (e.g. MAC address lookup failed). 'Timeout' indicates that
                  the lookup     timed out. Otherwise a valid status is returned (see     'getWifiTDLSStatus')."
  [ip-or-mac-address enabled #_callback]
  (gen-call :function ::set-wifi-tdls-enabled-state (meta &form) ip-or-mac-address enabled))

(defmacro get-wifi-tdls-status
  "Returns the current TDLS status for the specified peer.
   
     |ip_or_mac_address| - The IP or MAC address of the peer.
     |callback| - A callback function that receives a string with the current     TDLS status which can be
                  'Connected', 'Disabled', 'Disconnected',     'Nonexistent', or 'Unknown'."
  [ip-or-mac-address #_callback]
  (gen-call :function ::get-wifi-tdls-status (meta &form) ip-or-mac-address))

(defmacro get-captive-portal-status
  "Returns captive portal status for the network matching 'networkGuid'.
   
     |networkGuid| - The GUID of the network to get captive portal status for.
     |callback| - A callback function that returns the results of the query for     network captive portal status."
  [network-guid #_callback]
  (gen-call :function ::get-captive-portal-status (meta &form) network-guid))

(defmacro unlock-cellular-sim
  "Unlocks a Cellular SIM card. * If the SIM is PIN locked, |pin| will be used to unlock the SIM and   the |puk

   argument will be ignored if provided. * If the SIM is PUK locked, |puk| and |pin| must be provided. If the
   operation succeeds (|puk| is valid), the PIN will be set to |pin|.   (If |pin| is empty or invalid the operation
   will fail).
   
     |networkGuid| - The GUID of the cellular network to unlock.
     |pin| - The current SIM PIN, or the new PIN if PUK is provided.
     |puk| - The operator provided PUK for unblocking a blocked SIM.
     |callback| - Called when the operation has completed."
  [network-guid pin puk #_callback]
  (gen-call :function ::unlock-cellular-sim (meta &form) network-guid pin puk))

(defmacro set-cellular-sim-state
  "Sets whether or not SIM locking is enabled (i.e a PIN will be required when the device is powered) and changes the
   PIN if a new PIN is specified. If the new PIN is provided but not valid (e.g. too short) the operation will fail.
   This will not lock the SIM; that is handled automatically by the device. NOTE: If the SIM is locked, it must first
   be unlocked with unlockCellularSim() before this can be called (otherwise it will fail and chrome.runtime.lastError
   will be set to Error.SimLocked).
   
     |networkGuid| - The GUID of the cellular network to set the SIM state of.
     |simState| - The SIM state to set.
     |callback| - Called when the operation has completed."
  [network-guid sim-state #_callback]
  (gen-call :function ::set-cellular-sim-state (meta &form) network-guid sim-state))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-networks-changed-events
  "Fired when the properties change on any of the networks.  Sends a list of GUIDs for networks whose properties have
   changed."
  [channel]
  (gen-call :event ::on-networks-changed (meta &form) channel))

(defmacro tap-on-network-list-changed-events
  "Fired when the list of networks has changed.  Sends a complete list of GUIDs for all the current networks."
  [channel]
  (gen-call :event ::on-network-list-changed (meta &form) channel))

(defmacro tap-on-device-state-list-changed-events
  "Fired when the list of devices has changed or any device state properties have changed."
  [channel]
  (gen-call :event ::on-device-state-list-changed (meta &form) channel))

(defmacro tap-on-portal-detection-completed-events
  "Fired when a portal detection for a network completes. Sends the guid of the network and the corresponding captive
   portal status."
  [channel]
  (gen-call :event ::on-portal-detection-completed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.networkingPrivate",
   :since "28",
   :functions
   [{:id ::get-properties,
     :name "getProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-managed-properties,
     :name "getManagedProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-state,
     :name "getState",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networkingPrivate.NetworkStateProperties"}]}}]}
    {:id ::set-properties,
     :name "setProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "properties", :type "networkingPrivate.NetworkConfigProperties"}
      {:name "callback", :type :callback}]}
    {:id ::create-network,
     :name "createNetwork",
     :since "31",
     :callback? true,
     :params
     [{:name "shared", :type "boolean"}
      {:name "properties", :type "networkingPrivate.NetworkConfigProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::forget-network,
     :name "forgetNetwork",
     :since "43",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :type :callback}]}
    {:id ::get-networks,
     :name "getNetworks",
     :since "37",
     :callback? true,
     :params
     [{:name "filter", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-networkingPrivate.NetworkStatePropertiess]"}]}}]}
    {:id ::get-visible-networks,
     :name "getVisibleNetworks",
     :since "37",
     :deprecated "Use getNetworks.",
     :callback? true,
     :params
     [{:name "network-type", :type "networkingPrivate.NetworkType"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-networkingPrivate.NetworkStatePropertiess]"}]}}]}
    {:id ::get-enabled-network-types,
     :name "getEnabledNetworkTypes",
     :since "44",
     :deprecated "Use getDeviceStates.",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-networkingPrivate.NetworkTypes]"}]}}]}
    {:id ::get-device-states,
     :name "getDeviceStates",
     :since "44",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-objects]"}]}}]}
    {:id ::enable-network-type,
     :name "enableNetworkType",
     :since "32",
     :params [{:name "network-type", :type "networkingPrivate.NetworkType"}]}
    {:id ::disable-network-type,
     :name "disableNetworkType",
     :since "32",
     :params [{:name "network-type", :type "networkingPrivate.NetworkType"}]}
    {:id ::request-network-scan, :name "requestNetworkScan"}
    {:id ::start-connect,
     :name "startConnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :type :callback}]}
    {:id ::start-disconnect,
     :name "startDisconnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :type :callback}]}
    {:id ::start-activate,
     :name "startActivate",
     :since "43",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "carrier", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::verify-destination,
     :name "verifyDestination",
     :callback? true,
     :params
     [{:name "properties", :type "networkingPrivate.VerificationProperties"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::verify-and-encrypt-credentials,
     :name "verifyAndEncryptCredentials",
     :callback? true,
     :params
     [{:name "properties", :type "networkingPrivate.VerificationProperties"}
      {:name "network-guid", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::verify-and-encrypt-data,
     :name "verifyAndEncryptData",
     :callback? true,
     :params
     [{:name "properties", :type "networkingPrivate.VerificationProperties"}
      {:name "data", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-wifi-tdls-enabled-state,
     :name "setWifiTDLSEnabledState",
     :since "34",
     :callback? true,
     :params
     [{:name "ip-or-mac-address", :type "string"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-wifi-tdls-status,
     :name "getWifiTDLSStatus",
     :since "34",
     :callback? true,
     :params
     [{:name "ip-or-mac-address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-captive-portal-status,
     :name "getCaptivePortalStatus",
     :since "36",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networkingPrivate.CaptivePortalStatus"}]}}]}
    {:id ::unlock-cellular-sim,
     :name "unlockCellularSim",
     :since "46",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "pin", :type "string"}
      {:name "puk", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::set-cellular-sim-state,
     :name "setCellularSimState",
     :since "46",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "sim-state", :type "object"}
      {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-networks-changed,
     :name "onNetworksChanged",
     :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-network-list-changed,
     :name "onNetworkListChanged",
     :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-device-state-list-changed, :name "onDeviceStateListChanged", :since "44"}
    {:id ::on-portal-detection-completed,
     :name "onPortalDetectionCompleted",
     :since "36",
     :params
     [{:name "network-guid", :type "string"} {:name "status", :type "networkingPrivate.CaptivePortalStatus"}]}]})

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