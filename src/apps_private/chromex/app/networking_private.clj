(ns chromex.app.networking-private
  "The chrome.networkingPrivate API is used for configuring
   network connections (Cellular, Ethernet, VPN or WiFi). This private
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

     * available since Chrome 38"

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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::get-properties &form network-guid)))

(defmacro get-managed-properties
  "Gets the merged properties of the network with id networkGuid from the sources: User settings, shared settings, user
   policy, device policy and the currently active settings.

     |network-guid| - The GUID of the network to get properties for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::get-managed-properties &form network-guid)))

(defmacro get-state
  "Gets the cached read-only properties of the network with id networkGuid. This is meant to be a higher performance function
   than 'getProperties', which requires a round trip to query the networking subsystem. The following properties are returned
   for all networks: GUID, Type, Name, WiFi.Security. Additional properties are provided for visible networks:
   ConnectionState, ErrorState, WiFi.SignalStrength, Cellular.NetworkTechnology, Cellular.ActivationState,
   Cellular.RoamingState.

     |network-guid| - The GUID of the network to get properties for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::get-state &form network-guid)))

(defmacro set-properties
  "Sets the properties of the network with id |networkGuid|. This is only valid for configured networks (Source != None).
   Unconfigured visible networks should use createNetwork instead.

     |network-guid| - The GUID of the network to set properties for.
     |properties| - The properties to set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid properties] (gen-call :function ::set-properties &form network-guid properties)))

(defmacro create-network
  "Creates a new network configuration from properties. If a matching configured network already exists, this will fail.
   Otherwise returns the guid of the new network.

     |shared| - If true, share this network configuration with other users.
     |properties| - The properties to configure the new network with.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([shared properties] (gen-call :function ::create-network &form shared properties)))

(defmacro forget-network
  "Forgets a network configuration by clearing any configured properties for the network with GUID 'networkGuid'. This may
   also include any other networks with matching identifiers (e.g. WiFi SSID and Security). If no such configuration exists,
   an error will be set and the operation will fail.

     |network-guid| - The GUID of the network to forget.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::forget-network &form network-guid)))

(defmacro get-networks
  "Returns a list of network objects with the same properties provided by 'networkingPrivate.getState'. A filter is provided
   to specify the type of networks returned and to limit the number of networks. Networks are ordered by the system based on
   their priority, with connected or connecting networks listed first.

     |filter| - Describes which networks to return.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([filter] (gen-call :function ::get-networks &form filter)))

(defmacro get-visible-networks
  "Deprecated. Please use 'networkingPrivate.getNetworks' with filter.visible = true instead.

     |network-type| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-type] (gen-call :function ::get-visible-networks &form network-type)))

(defmacro get-enabled-network-types
  "Deprecated. Please use 'networkingPrivate.getDeviceStates' instead.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-enabled-network-types &form)))

(defmacro get-device-states
  "Returns a list of 'networkingPrivate.DeviceStateProperties' objects.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-device-states &form)))

(defmacro enable-network-type
  "Enables any devices matching the specified network type. Note, the type might represent multiple network types (e.g.
   'Wireless').

     |network-type| - The type of network to enable."
  ([network-type] (gen-call :function ::enable-network-type &form network-type)))

(defmacro disable-network-type
  "Disables any devices matching the specified network type. See note for 'networkingPrivate.enableNetworkType'.

     |network-type| - The type of network to disable."
  ([network-type] (gen-call :function ::disable-network-type &form network-type)))

(defmacro request-network-scan
  "Requests that the networking subsystem scan for new networks and update the list returned by 'getVisibleNetworks'. This is
   only a request: the network subsystem can choose to ignore it.  If the list is updated, then the 'onNetworkListChanged'
   event will be fired.

     |network-type| - If provided, requests a scan specific to the type.     For Cellular a mobile network scan will be
                      requested if supported."
  ([network-type] (gen-call :function ::request-network-scan &form network-type))
  ([] `(request-network-scan :omit)))

(defmacro start-connect
  "Starts a connection to the network with networkGuid.

     |network-guid| - The GUID of the network to connect to.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::start-connect &form network-guid)))

(defmacro start-disconnect
  "Starts a disconnect from the network with networkGuid.

     |network-guid| - The GUID of the network to disconnect from.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::start-disconnect &form network-guid)))

(defmacro start-activate
  "Starts activation of the Cellular network with networkGuid. If called for a network that is already activated, or for a
   network with a carrier that can not be directly activated, this will show the account details page for the carrier if
   possible.

     |network-guid| - The GUID of the Cellular network to activate.
     |carrier| - Optional name of carrier to activate.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid carrier] (gen-call :function ::start-activate &form network-guid carrier))
  ([network-guid] `(start-activate ~network-guid :omit)))

(defmacro get-captive-portal-status
  "Returns captive portal status for the network matching 'networkGuid'.

     |network-guid| - The GUID of the network to get captive portal status for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid] (gen-call :function ::get-captive-portal-status &form network-guid)))

(defmacro unlock-cellular-sim
  "Unlocks a Cellular SIM card. * If the SIM is PIN locked, |pin| will be used to unlock the SIM and   the |puk| argument will
   be ignored if provided. * If the SIM is PUK locked, |puk| and |pin| must be provided. If the   operation succeeds (|puk| is
   valid), the PIN will be set to |pin|.   (If |pin| is empty or invalid the operation will fail).

     |network-guid| - The GUID of the cellular network to unlock.     If empty, the default cellular device will be used.
     |pin| - The current SIM PIN, or the new PIN if PUK is provided.
     |puk| - The operator provided PUK for unblocking a blocked SIM.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid pin puk] (gen-call :function ::unlock-cellular-sim &form network-guid pin puk))
  ([network-guid pin] `(unlock-cellular-sim ~network-guid ~pin :omit)))

(defmacro set-cellular-sim-state
  "Sets whether or not SIM locking is enabled (i.e a PIN will be required when the device is powered) and changes the PIN if a
   new PIN is specified. If the new PIN is provided but not valid (e.g. too short) the operation will fail. This will not lock
   the SIM; that is handled automatically by the device. NOTE: If the SIM is locked, it must first be unlocked with
   unlockCellularSim() before this can be called (otherwise it will fail and 'runtime.lastError' will be set to
   Error.SimLocked).

     |network-guid| - The GUID of the cellular network to set the SIM state of.     If empty, the default cellular device
                      will be used.
     |sim-state| - The SIM state to set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid sim-state] (gen-call :function ::set-cellular-sim-state &form network-guid sim-state)))

(defmacro select-cellular-mobile-network
  "Selects which Cellular Mobile Network to use. |networkId| must be the NetworkId property of a member of
   Cellular.FoundNetworks from the network properties for the specified Cellular network.

     |network-guid| - The GUID of the cellular network to select the network     for. If empty, the default cellular device
                      will be used.
     |network-id| - The networkId to select.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([network-guid network-id] (gen-call :function ::select-cellular-mobile-network &form network-guid network-id)))

(defmacro get-global-policy
  "Gets the global policy properties. These properties are not expected to change during a session.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-global-policy &form)))

(defmacro get-certificate-lists
  "Gets the lists of certificates available for network configuration.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-certificate-lists &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-networks-changed-events
  "Fired when the properties change on any of the networks.  Sends a list of GUIDs for networks whose properties have changed.

   Events will be put on the |channel| with signature [::on-networks-changed [changes]] where:

     |changes| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-networks-changed &form channel args)))

(defmacro tap-on-network-list-changed-events
  "Fired when the list of networks has changed.  Sends a complete list of GUIDs for all the current networks.

   Events will be put on the |channel| with signature [::on-network-list-changed [changes]] where:

     |changes| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-network-list-changed &form channel args)))

(defmacro tap-on-device-state-list-changed-events
  "Fired when the list of devices has changed or any device state properties have changed.

   Events will be put on the |channel| with signature [::on-device-state-list-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-device-state-list-changed &form channel args)))

(defmacro tap-on-portal-detection-completed-events
  "Fired when a portal detection for a network completes. Sends the guid of the network and the corresponding captive portal
   status.

   Events will be put on the |channel| with signature [::on-portal-detection-completed [network-guid status]] where:

     |network-guid| - ?
     |status| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-portal-detection-completed &form channel args)))

(defmacro tap-on-certificate-lists-changed-events
  "Fired when any certificate list has changed.

   Events will be put on the |channel| with signature [::on-certificate-lists-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-certificate-lists-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.networking-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.networkingPrivate",
   :since "38",
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
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::create-network,
     :name "createNetwork",
     :callback? true,
     :params
     [{:name "shared", :type "boolean"}
      {:name "properties", :type "networkingPrivate.NetworkConfigProperties"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::forget-network,
     :name "forgetNetwork",
     :since "43",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-networks,
     :name "getNetworks",
     :callback? true,
     :params
     [{:name "filter", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-networkingPrivate.NetworkStatePropertiess]"}]}}]}
    {:id ::get-visible-networks,
     :name "getVisibleNetworks",
     :since "38",
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
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-objects]"}]}}]}
    {:id ::enable-network-type,
     :name "enableNetworkType",
     :params [{:name "network-type", :type "networkingPrivate.NetworkType"}]}
    {:id ::disable-network-type,
     :name "disableNetworkType",
     :params [{:name "network-type", :type "networkingPrivate.NetworkType"}]}
    {:id ::request-network-scan,
     :name "requestNetworkScan",
     :params [{:name "network-type", :optional? true, :since "63", :type "networkingPrivate.NetworkType"}]}
    {:id ::start-connect,
     :name "startConnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-disconnect,
     :name "startDisconnect",
     :callback? true,
     :params [{:name "network-guid", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-activate,
     :name "startActivate",
     :since "43",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "carrier", :optional? true, :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-captive-portal-status,
     :name "getCaptivePortalStatus",
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
      {:name "puk", :optional? true, :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-cellular-sim-state,
     :name "setCellularSimState",
     :since "46",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "sim-state", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::select-cellular-mobile-network,
     :name "selectCellularMobileNetwork",
     :since "63",
     :callback? true,
     :params
     [{:name "network-guid", :type "string"}
      {:name "network-id", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-global-policy,
     :name "getGlobalPolicy",
     :since "57",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-certificate-lists,
     :name "getCertificateLists",
     :since "60",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}],
   :events
   [{:id ::on-networks-changed, :name "onNetworksChanged", :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-network-list-changed,
     :name "onNetworkListChanged",
     :params [{:name "changes", :type "[array-of-strings]"}]}
    {:id ::on-device-state-list-changed, :name "onDeviceStateListChanged", :since "44"}
    {:id ::on-portal-detection-completed,
     :name "onPortalDetectionCompleted",
     :params [{:name "network-guid", :type "string"} {:name "status", :type "networkingPrivate.CaptivePortalStatus"}]}
    {:id ::on-certificate-lists-changed, :name "onCertificateListsChanged", :since "60"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))