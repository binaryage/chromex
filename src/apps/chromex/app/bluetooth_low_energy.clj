(ns chromex.app.bluetooth-low-energy
  "The chrome.bluetoothLowEnergy API is used to communicate with
   Bluetooth Smart (Low Energy) devices using the
   
   Generic Attribute Profile (GATT).
   
     * available since Chrome 37
     * https://developer.chrome.com/extensions/bluetoothLowEnergy"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro connect
  "Establishes a connection between the application and the device with the given address. A device may be already connected
   and its GATT services available without calling connect, however, an app that wants to access GATT services of a device
   should call this function to make sure that a connection to the device is maintained. If the device is not connected, all
   GATT services of the device will be discovered after a successful call to connect.
   
     |deviceAddress| - The Bluetooth address of the remote device to which a GATT connection should be opened.
     |properties| - Connection properties (optional).
     |callback| - Called when the connect request has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address properties #_callback] (gen-call :function ::connect &form device-address properties))
  ([device-address] `(connect ~device-address :omit)))

(defmacro disconnect
  "Closes the app's connection to the device with the given address. Note that this will not always destroy the physical link
   itself, since there may be other apps with open connections.
   
     |deviceAddress| - The Bluetooth address of the remote device.
     |callback| - Called when the disconnect request has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::disconnect &form device-address)))

(defmacro get-service
  "Get the GATT service with the given instance ID.
   
     |serviceId| - The instance ID of the requested GATT service.
     |callback| - Called with the requested Service object.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([service-id #_callback] (gen-call :function ::get-service &form service-id)))

(defmacro get-services
  "Get all the GATT services that were discovered on the remote device with the given device address.
   
     |deviceAddress| - The Bluetooth address of the remote device whose GATT services should be returned.
     |callback| - Called with the list of requested Service objects.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([device-address #_callback] (gen-call :function ::get-services &form device-address)))

(defmacro get-characteristic
  "Get the GATT characteristic with the given instance ID that belongs to the given GATT service, if the characteristic
   exists.
   
     |characteristicId| - The instance ID of the requested GATT characteristic.
     |callback| - Called with the requested Characteristic object.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id #_callback] (gen-call :function ::get-characteristic &form characteristic-id)))

(defmacro get-characteristics
  "Get a list of all discovered GATT characteristics that belong to the given service.
   
     |serviceId| - The instance ID of the GATT service whose characteristics should be returned.
     |callback| - Called with the list of characteristics that belong to the given service.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([service-id #_callback] (gen-call :function ::get-characteristics &form service-id)))

(defmacro get-included-services
  "Get a list of GATT services that are included by the given service.
   
     |serviceId| - The instance ID of the GATT service whose included services should be returned.
     |callback| - Called with the list of GATT services included from the given service.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([service-id #_callback] (gen-call :function ::get-included-services &form service-id)))

(defmacro get-descriptor
  "Get the GATT characteristic descriptor with the given instance ID.
   
     |descriptorId| - The instance ID of the requested GATT characteristic descriptor.
     |callback| - Called with the requested Descriptor object.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([descriptor-id #_callback] (gen-call :function ::get-descriptor &form descriptor-id)))

(defmacro get-descriptors
  "Get a list of GATT characteristic descriptors that belong to the given characteristic.
   
     |characteristicId| - The instance ID of the GATT characteristic whose descriptors should be returned.
     |callback| - Called with the list of descriptors that belong to the given characteristic.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id #_callback] (gen-call :function ::get-descriptors &form characteristic-id)))

(defmacro read-characteristic-value
  "Retrieve the value of a specified characteristic from a remote peripheral.
   
     |characteristicId| - The instance ID of the GATT characteristic whose value should be read from the remote device.
     |callback| - Called with the Characteristic object whose value was requested. The value field of the returned
                  Characteristic object contains the result of the read request.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id #_callback] (gen-call :function ::read-characteristic-value &form characteristic-id)))

(defmacro write-characteristic-value
  "Write the value of a specified characteristic from a remote peripheral.
   
     |characteristicId| - The instance ID of the GATT characteristic whose value should be written to.
     |value| - The value that should be sent to the remote characteristic as part of the write request.
     |callback| - Called when the write request has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id value #_callback] (gen-call :function ::write-characteristic-value &form characteristic-id value)))

(defmacro start-characteristic-notifications
  "Enable value notifications/indications from the specified characteristic. Once enabled, an application can listen to
   notifications using the 'onCharacteristicValueChanged' event.
   
     |characteristicId| - The instance ID of the GATT characteristic that notifications should be enabled on.
     |properties| - Notification session properties (optional).
     |callback| - Called when the request has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id properties #_callback] (gen-call :function ::start-characteristic-notifications &form characteristic-id properties))
  ([characteristic-id] `(start-characteristic-notifications ~characteristic-id :omit)))

(defmacro stop-characteristic-notifications
  "Disable value notifications/indications from the specified characteristic. After a successful call, the application will
   stop receiving notifications/indications from this characteristic.
   
     |characteristicId| - The instance ID of the GATT characteristic on which this app's notification session should be
                          stopped.
     |callback| - Called when the request has completed (optional).
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([characteristic-id #_callback] (gen-call :function ::stop-characteristic-notifications &form characteristic-id)))

(defmacro read-descriptor-value
  "Retrieve the value of a specified characteristic descriptor from a remote peripheral.
   
     |descriptorId| - The instance ID of the GATT characteristic descriptor whose value should be read from the remote
                      device.
     |callback| - Called with the Descriptor object whose value was requested. The value field of the returned Descriptor
                  object contains the result of the read request.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([descriptor-id #_callback] (gen-call :function ::read-descriptor-value &form descriptor-id)))

(defmacro write-descriptor-value
  "Write the value of a specified characteristic descriptor from a remote peripheral.
   
     |descriptorId| - The instance ID of the GATT characteristic descriptor whose value should be written to.
     |value| - The value that should be sent to the remote descriptor as part of the write request.
     |callback| - Called when the write request has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([descriptor-id value #_callback] (gen-call :function ::write-descriptor-value &form descriptor-id value)))

(defmacro register-advertisement
  "Create an advertisement and register it for advertising. To call this function, the app must have the bluetooth:low_energy
   and bluetooth:peripheral permissions set to true. Additionally this API is only available to auto launched apps in Kiosk
   Mode of by setting the 'enable-ble-advertising-in-apps' flag. See https://developer.chrome.com/apps/manifest/bluetooth
   Note: On some hardware, central and peripheral modes at the same time is supported but on hardware that doesn't support
   this, making this call will switch the device to peripheral mode. In the case of hardware which does not support both
   central and peripheral mode, attempting to use the device in both modes will lead to undefined behavior or prevent other
   central-role applications from behaving correctly (including the discovery of Bluetooth Low Energy devices).
   
     |advertisement| - The advertisement to advertise.
     |callback| - Called once the registeration is done and we've started advertising. Returns the id of the created
                  advertisement.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([advertisement #_callback] (gen-call :function ::register-advertisement &form advertisement)))

(defmacro unregister-advertisement
  "Unregisters an advertisement and stops its advertising. If the advertisement fails to unregister the only way to stop
   advertising might be to restart the device.
   
     |advertisementId| - Id of the advertisement to unregister.
     |callback| - Called once the advertisement is unregistered and is no longer being advertised.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([advertisement-id #_callback] (gen-call :function ::unregister-advertisement &form advertisement-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-service-added-events
  "Fired whan a new GATT service has been discovered on a remote device.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-service-added &form channel args)))
(defmacro tap-on-service-changed-events
  "Fired when the state of a remote GATT service changes. This involves any characteristics and/or descriptors that get added
   or removed from the service, as well as 'ServiceChanged' notifications from the remote device.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-service-changed &form channel args)))
(defmacro tap-on-service-removed-events
  "Fired when a GATT service that was previously discovered on a remote device has been removed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-service-removed &form channel args)))
(defmacro tap-on-characteristic-value-changed-events
  "Fired when the value of a remote GATT characteristic changes, either as a result of a read request, or a value change
   notification/indication This event will only be sent if the app has enabled notifications by calling
   'startCharacteristicNotifications'.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-characteristic-value-changed &form channel args)))
(defmacro tap-on-descriptor-value-changed-events
  "Fired when the value of a remote GATT characteristic descriptor changes, usually as a result of a read request. This event
   exists mostly for convenience and will always be sent after a successful call to 'readDescriptorValue'.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-descriptor-value-changed &form channel args)))

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
  {:namespace "chrome.bluetoothLowEnergy",
   :since "37",
   :functions
   [{:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "properties", :optional? true, :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::disconnect,
     :name "disconnect",
     :callback? true,
     :params [{:name "device-address", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-service,
     :name "getService",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "bluetoothLowEnergy.Service"}]}}]}
    {:id ::get-services,
     :name "getServices",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Services]"}]}}]}
    {:id ::get-characteristic,
     :name "getCharacteristic",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Characteristic"}]}}]}
    {:id ::get-characteristics,
     :name "getCharacteristics",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Characteristics]"}]}}]}
    {:id ::get-included-services,
     :name "getIncludedServices",
     :callback? true,
     :params
     [{:name "service-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Services]"}]}}]}
    {:id ::get-descriptor,
     :name "getDescriptor",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Descriptor"}]}}]}
    {:id ::get-descriptors,
     :name "getDescriptors",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-bluetoothLowEnergy.Descriptors]"}]}}]}
    {:id ::read-characteristic-value,
     :name "readCharacteristicValue",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Characteristic"}]}}]}
    {:id ::write-characteristic-value,
     :name "writeCharacteristicValue",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "value", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}
    {:id ::start-characteristic-notifications,
     :name "startCharacteristicNotifications",
     :callback? true,
     :params
     [{:name "characteristic-id", :type "string"}
      {:name "properties", :optional? true, :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::stop-characteristic-notifications,
     :name "stopCharacteristicNotifications",
     :callback? true,
     :params [{:name "characteristic-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::read-descriptor-value,
     :name "readDescriptorValue",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "bluetoothLowEnergy.Descriptor"}]}}]}
    {:id ::write-descriptor-value,
     :name "writeDescriptorValue",
     :callback? true,
     :params
     [{:name "descriptor-id", :type "string"}
      {:name "value", :type "ArrayBuffer"}
      {:name "callback", :type :callback}]}
    {:id ::register-advertisement,
     :name "registerAdvertisement",
     :since "47",
     :callback? true,
     :params
     [{:name "advertisement", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "advertisement-id", :type "integer"}]}}]}
    {:id ::unregister-advertisement,
     :name "unregisterAdvertisement",
     :since "47",
     :callback? true,
     :params [{:name "advertisement-id", :type "integer"} {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-service-added, :name "onServiceAdded", :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-service-changed,
     :name "onServiceChanged",
     :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-service-removed,
     :name "onServiceRemoved",
     :params [{:name "service", :type "bluetoothLowEnergy.Service"}]}
    {:id ::on-characteristic-value-changed,
     :name "onCharacteristicValueChanged",
     :params [{:name "characteristic", :type "bluetoothLowEnergy.Characteristic"}]}
    {:id ::on-descriptor-value-changed,
     :name "onDescriptorValueChanged",
     :params [{:name "descriptor", :type "bluetoothLowEnergy.Descriptor"}]}]})

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