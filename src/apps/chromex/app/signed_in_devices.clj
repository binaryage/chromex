(ns chromex.app.signed-in-devices
  "Use the chrome.signedInDevices API to get a list of devices
   signed into chrome with the same account as the current profile.

     * available since Chrome 51
     * https://developer.chrome.com/apps/signedInDevices"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets the array of signed in devices, signed into the same account as the current profile.

     |is-local| - If true only return the information for the local device. If false or omitted return the list of all
                  devices including the local device.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [devices] where:

     |devices| - https://developer.chrome.com/apps/signedInDevices#property-callback-devices.

   https://developer.chrome.com/apps/signedInDevices#method-get."
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

   https://developer.chrome.com/apps/signedInDevices#event-onDeviceInfoChange."
  ([channel & args] (apply gen-call :event ::on-device-info-change &form channel args)))

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
  {:namespace "chrome.signedInDevices",
   :since "51",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))