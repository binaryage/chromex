(ns chromex.app.networking.onc
  "
     The chrome.networking.onc API is used for configuring
     network connections (Cellular, Ethernet, VPN, WiFi or WiMAX).
     This API is available in Chrome OS kiosk sessions.

     Network connection configurations are specified following
     
     Open Network Configuration (ONC) specification.

     NOTE: Most dictionary properties and enum values use UpperCamelCase
     to match the ONC specification instead of the JavaScript lowerCamelCase
     convention.

     * available since Chrome 59
     * https://developer.chrome.com/apps/networking.onc"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-properties
  "Gets all the properties of the network with id networkGuid. Includes all properties of the network (read-only and
   read/write values).

     |network-guid| - The GUID of the network to get properties for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getProperties."
  ([network-guid] (gen-call :function ::get-properties &form network-guid)))

(defmacro get-managed-properties
  "Gets the merged properties of the network with id networkGuid from the sources: User settings, shared settings, user
   policy, device policy and the currently active settings.

     |network-guid| - The GUID of the network to get properties for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getManagedProperties."
  ([network-guid] (gen-call :function ::get-managed-properties &form network-guid)))

(defmacro get-state
  "Gets the cached read-only properties of the network with id networkGuid. This is meant to be a higher performance function
   than 'getProperties', which requires a round trip to query the networking subsystem. The following properties are returned
   for all networks: GUID, Type, Name, WiFi.Security. Additional properties are provided for visible networks:
   ConnectionState, ErrorState, WiFi.SignalStrength, Cellular.NetworkTechnology, Cellular.ActivationState,
   Cellular.RoamingState.

     |network-guid| - The GUID of the network to get properties for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getState."
  ([network-guid] (gen-call :function ::get-state &form network-guid)))

(defmacro set-properties
  "Sets the properties of the network with id networkGuid.    In kiosk sessions, calling this method on a shared network will
   fail.

     |network-guid| - The GUID of the network to set properties for.
     |properties| - The properties to set.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-setProperties."
  ([network-guid properties] (gen-call :function ::set-properties &form network-guid properties)))

(defmacro create-network
  "Creates a new network configuration from properties. If a matching configured network already exists, this will fail.
   Otherwise returns the GUID of the new network.

     |shared| - If true, share this network configuration with     other users.                 This option is exposed
                only to Chrome's Web UI.       When called by apps, false is the only allowed value.
     |properties| - The properties to configure the new network with.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-createNetwork."
  ([shared properties] (gen-call :function ::create-network &form shared properties)))

(defmacro forget-network
  "Forgets a network configuration by clearing any configured properties   for the network with GUID networkGuid. This may
   also   include any other networks with matching identifiers (e.g. WiFi SSID   and Security). If no such configuration
   exists, an error will be set   and the operation will fail.     In kiosk sessions, this method will not be able to forget
   shared      network configurations.

     |network-guid| - The GUID of the network to forget.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-forgetNetwork."
  ([network-guid] (gen-call :function ::forget-network &form network-guid)))

(defmacro get-networks
  "Returns a list of network objects with the same properties provided by 'getState'. A filter is provided to specify the type
   of networks returned and to limit the number of networks. Networks are ordered by the system based on their priority, with
   connected or connecting networks listed first.

     |filter| - Describes which networks to return.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getNetworks."
  ([filter] (gen-call :function ::get-networks &form filter)))

(defmacro get-device-states
  "Returns states of available networking devices.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getDeviceStates."
  ([] (gen-call :function ::get-device-states &form)))

(defmacro enable-network-type
  "Enables any devices matching the specified network type. Note, the type might represent multiple network types (e.g.
   'Wireless').

     |network-type| - The type of network to enable.

   https://developer.chrome.com/apps/networking.onc#method-enableNetworkType."
  ([network-type] (gen-call :function ::enable-network-type &form network-type)))

(defmacro disable-network-type
  "Disables any devices matching the specified network type. See note for 'enableNetworkType'.

     |network-type| - The type of network to disable.

   https://developer.chrome.com/apps/networking.onc#method-disableNetworkType."
  ([network-type] (gen-call :function ::disable-network-type &form network-type)))

(defmacro request-network-scan
  "Requests that the networking subsystem scan for new networks and update the list returned by 'getVisibleNetworks'. This is
   only a request: the network subsystem can choose to ignore it.  If the list is updated, then the 'onNetworkListChanged'
   event will be fired.

     |network-type| - If provided, requests a scan specific to the type.     For Cellular a mobile network scan will be
                      requested if supported.

   https://developer.chrome.com/apps/networking.onc#method-requestNetworkScan."
  ([network-type] (gen-call :function ::request-network-scan &form network-type))
  ([] `(request-network-scan :omit)))

(defmacro start-connect
  "Starts a connection to the network with networkGuid.

     |network-guid| - The GUID of the network to connect to.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-startConnect."
  ([network-guid] (gen-call :function ::start-connect &form network-guid)))

(defmacro start-disconnect
  "Starts a disconnect from the network with networkGuid.

     |network-guid| - The GUID of the network to disconnect from.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-startDisconnect."
  ([network-guid] (gen-call :function ::start-disconnect &form network-guid)))

(defmacro get-captive-portal-status
  "Returns captive portal status for the network matching 'networkGuid'.

     |network-guid| - The GUID of the network to get captive portal status for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getCaptivePortalStatus."
  ([network-guid] (gen-call :function ::get-captive-portal-status &form network-guid)))

(defmacro get-global-policy
  "Gets the global policy properties. These properties are not expected to change during a session.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/networking.onc#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/networking.onc#method-getGlobalPolicy."
  ([] (gen-call :function ::get-global-policy &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-networks-changed-events
  "Fired when the properties change on any of the networks.  Sends a list of GUIDs for networks whose properties have changed.

   Events will be put on the |channel| with signature [::on-networks-changed [changes]] where:

     |changes| - https://developer.chrome.com/apps/networking.onc#property-onNetworksChanged-changes.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/networking.onc#event-onNetworksChanged."
  ([channel & args] (apply gen-call :event ::on-networks-changed &form channel args)))

(defmacro tap-on-network-list-changed-events
  "Fired when the list of networks has changed.  Sends a complete list of GUIDs for all the current networks.

   Events will be put on the |channel| with signature [::on-network-list-changed [changes]] where:

     |changes| - https://developer.chrome.com/apps/networking.onc#property-onNetworkListChanged-changes.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/networking.onc#event-onNetworkListChanged."
  ([channel & args] (apply gen-call :event ::on-network-list-changed &form channel args)))

(defmacro tap-on-device-state-list-changed-events
  "Fired when the list of devices has changed or any device state properties have changed.

   Events will be put on the |channel| with signature [::on-device-state-list-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/networking.onc#event-onDeviceStateListChanged."
  ([channel & args] (apply gen-call :event ::on-device-state-list-changed &form channel args)))

(defmacro tap-on-portal-detection-completed-events
  "Fired when a portal detection for a network completes. Sends the GUID of the network and the corresponding captive portal
   status.

   Events will be put on the |channel| with signature [::on-portal-detection-completed [network-guid status]] where:

     |network-guid| - https://developer.chrome.com/apps/networking.onc#property-onPortalDetectionCompleted-networkGuid.
     |status| - https://developer.chrome.com/apps/networking.onc#property-onPortalDetectionCompleted-status.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/networking.onc#event-onPortalDetectionCompleted."
  ([channel & args] (apply gen-call :event ::on-portal-detection-completed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.networking.onc namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.networking.onc",
   :since "59",
   :functions
   [{:id ::get-properties,
     :name "getProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networking.onc.NetworkProperties"}]}}]}
    {:id ::get-managed-properties,
     :name "getManagedProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networking.onc.ManagedProperties"}]}}]}
    {:id ::get-state,
     :name "getState",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networking.onc.NetworkStateProperties"}]}}]}
    {:id ::set-properties,
     :name "setProperties",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "properties", :type "networking.onc.NetworkConfigProperties"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::create-network,
     :name "createNetwork",
     :callback? true,
     :params
     [{:name "shared", :type "boolean"}
      {:name "properties", :type "networking.onc.NetworkConfigProperties"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::forget-network,
     :name "forgetNetwork",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-networks,
     :name "getNetworks",
     :callback? true,
     :params
     [{:name "filter", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-networking.onc.NetworkStatePropertiess]"}]}}]}
    {:id ::get-device-states,
     :name "getDeviceStates",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-objects]"}]}}]}
    {:id ::enable-network-type,
     :name "enableNetworkType",
     :params [{:name "network-type", :type "networking.onc.NetworkType"}]}
    {:id ::disable-network-type,
     :name "disableNetworkType",
     :params [{:name "network-type", :type "networking.onc.NetworkType"}]}
    {:id ::request-network-scan,
     :name "requestNetworkScan",
     :params [{:name "network-type", :optional? true, :type "networking.onc.NetworkType"}]}
    {:id ::start-connect,
     :name "startConnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-disconnect,
     :name "startDisconnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-captive-portal-status,
     :name "getCaptivePortalStatus",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "networking.onc.CaptivePortalStatus"}]}}]}
    {:id ::get-global-policy,
     :name "getGlobalPolicy",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}],
   :events
   [{:id ::on-networks-changed, :name "onNetworksChanged", :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-network-list-changed,
     :name "onNetworkListChanged",
     :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-device-state-list-changed, :name "onDeviceStateListChanged"}
    {:id ::on-portal-detection-completed,
     :name "onPortalDetectionCompleted",
     :params [{:name "network-guid", :type "string"} {:name "status", :type "networking.onc.CaptivePortalStatus"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))