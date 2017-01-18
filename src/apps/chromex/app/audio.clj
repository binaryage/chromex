(ns chromex.app.audio
  "The chrome.audio API is provided to allow users to
   get information about and control the audio devices attached to the
   system. This API is currently only implemented for ChromeOS.

     * available since Chrome 57
     * https://developer.chrome.com/apps/audio"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Gets the information of all audio output and input devices.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [output-info input-info] where:

     |output-info| - https://developer.chrome.com/apps/audio#property-callback-outputInfo.
     |input-info| - https://developer.chrome.com/apps/audio#property-callback-inputInfo.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-getInfo."
  ([] (gen-call :function ::get-info &form)))

(defmacro set-active-devices
  "Sets lists of active input and/or output devices.

     |ids| - Specifies IDs of devices that should be active. If either the     input or output list is not set, devices in
             that category are     unaffected.          It is an error to pass in a non-existent device ID.     NOTE: While
             the method signature allows device IDs to be     passed as a list of strings, this method of setting active
             devices     is deprecated and should not be relied upon to work. Please use     ' DeviceIdLists' instead.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-setActiveDevices."
  ([ids] (gen-call :function ::set-active-devices &form ids)))

(defmacro set-properties
  "Sets the properties for the input or output device.

     |id| - https://developer.chrome.com/apps/audio#property-setProperties-id.
     |properties| - https://developer.chrome.com/apps/audio#property-setProperties-properties.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-setProperties."
  ([id properties] (gen-call :function ::set-properties &form id properties)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-device-changed-events
  "Fired when anything changes to the audio device configuration.

   Events will be put on the |channel| with signature [::on-device-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-onDeviceChanged."
  ([channel & args] (apply gen-call :event ::on-device-changed &form channel args)))

(defmacro tap-on-level-changed-events
  "Fired when sound level changes for an active audio device.

   Events will be put on the |channel| with signature [::on-level-changed [id level]] where:

     |id| - id of the audio device.
     |level| - new sound level of device(volume for output, gain for input).

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-OnLevelChanged."
  ([channel & args] (apply gen-call :event ::on-level-changed &form channel args)))

(defmacro tap-on-mute-changed-events
  "Fired when the mute state of the audio input or output changes.

   Events will be put on the |channel| with signature [::on-mute-changed [is-input is-muted]] where:

     |is-input| - true indicating audio input; false indicating audio output.
     |is-muted| - new value of mute state.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-OnMuteChanged."
  ([channel & args] (apply gen-call :event ::on-mute-changed &form channel args)))

(defmacro tap-on-devices-changed-events
  "Fired when audio devices change, either new devices being added, or existing devices being removed.

   Events will be put on the |channel| with signature [::on-devices-changed [devices]] where:

     |devices| - List of all present audio devices after the change.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-OnDevicesChanged."
  ([channel & args] (apply gen-call :event ::on-devices-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.audio namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.audio",
   :since "57",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "output-info", :type "[array-of-objects]"} {:name "input-info", :type "[array-of-objects]"}]}}]}
    {:id ::set-active-devices,
     :name "setActiveDevices",
     :callback? true,
     :params [{:name "ids", :type "audio.DeviceIdLists-or-[array-of-strings]"} {:name "callback", :type :callback}]}
    {:id ::set-properties,
     :name "setProperties",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "properties", :type "object"} {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-device-changed, :name "onDeviceChanged"}
    {:id ::on-level-changed,
     :name "OnLevelChanged",
     :params [{:name "id", :type "string"} {:name "level", :type "integer"}]}
    {:id ::on-mute-changed,
     :name "OnMuteChanged",
     :params [{:name "is-input", :type "boolean"} {:name "is-muted", :type "boolean"}]}
    {:id ::on-devices-changed, :name "OnDevicesChanged", :params [{:name "devices", :type "[array-of-objects]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))