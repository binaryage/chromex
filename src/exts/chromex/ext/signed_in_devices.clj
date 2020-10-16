(ns chromex.ext.signed-in-devices
  "Use the chrome.signedInDevices API to get a list of devices
   signed into chrome with the same account as the current profile.

     * available since Chrome 88
     * https://developer.chrome.com/extensions/signedInDevices"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets the array of signed in devices, signed into the same account as the current profile.

     |is-local| - If true only return the information for the local device. If false or omitted return the list of all
                  devices including the local device.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [devices] where:

     |devices| - https://developer.chrome.com/extensions/signedInDevices#property-callback-devices.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/signedInDevices#method-get."
  ([is-local] (gen-call :function ::get &form is-local))
  ([] `(get :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-device-info-change-events
  "Fired when the DeviceInfo object of any of the signed in devices changes, or when a device is added or removed.

   Events will be put on the |channel| with signature [::on-device-info-change [devices]] where:

     |devices| - The array of all signed in devices.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/signedInDevices#event-onDeviceInfoChange."
  ([channel & args] (apply gen-call :event ::on-device-info-change &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.signed-in-devices namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.signedInDevices",
   :since "88",
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "is-local", :optional? true, :type "boolean"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-signedInDevices.DeviceInfos]"}]}}]}],
   :events
   [{:id ::on-device-info-change,
     :name "onDeviceInfoChange",
     :params [{:name "devices", :type "[array-of-signedInDevices.DeviceInfos]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))