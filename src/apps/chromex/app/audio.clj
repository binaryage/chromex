(ns chromex.app.audio
  "The chrome.audio API is provided to allow users to
   get information about and control the audio devices attached to the
   system. This API is currently only implemented for ChromeOS.

     * available since Chrome 58
     * https://developer.chrome.com/apps/audio"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-devices
  "Gets a list of audio devices filtered based on |filter|.

     |filter| - Device properties by which to filter the list of returned     audio devices. If the filter is not set or set
                to {},     returned device list will contain all available audio devices.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [devices] where:

     |devices| - https://developer.chrome.com/apps/audio#property-callback-devices.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-getDevices."
  ([filter] (gen-call :function ::get-devices &form filter))
  ([] `(get-devices :omit)))

(defmacro set-active-devices
  "Sets lists of active input and/or output devices.

     |ids| - Specifies IDs of devices that should be active. If either the     input or output list is not set, devices in
             that category are     unaffected.          It is an error to pass in a non-existent device ID.     NOTE: While
             the method signature allows device IDs to be     passed as a list of strings, this method of setting active
             devices     is deprecated and should not be relied upon to work. Please use     'DeviceIdLists' instead.

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

(defmacro get-mute
  "Gets the system-wide mute state for the specified stream type.

     |stream-type| - Stream type for which mute state should be fetched.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [value] where:

     |value| - https://developer.chrome.com/apps/audio#property-callback-value.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-getMute."
  ([stream-type] (gen-call :function ::get-mute &form stream-type)))

(defmacro set-mute
  "Sets mute state for a stream type. The mute state will apply to all audio devices with the specified audio stream type.

     |stream-type| - Stream type for which mute state should be set.
     |is-muted| - New mute value.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/audio#method-setMute."
  ([stream-type is-muted] (gen-call :function ::set-mute &form stream-type is-muted)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-level-changed-events
  "Fired when sound level changes for an active audio device.

   Events will be put on the |channel| with signature [::on-level-changed [event]] where:

     |event| - https://developer.chrome.com/apps/audio#property-onLevelChanged-event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-onLevelChanged."
  ([channel & args] (apply gen-call :event ::on-level-changed &form channel args)))

(defmacro tap-on-mute-changed-events
  "Fired when the mute state of the audio input or output changes. Note that mute state is system-wide and the new value
   applies to every audio device with specified stream type.

   Events will be put on the |channel| with signature [::on-mute-changed [event]] where:

     |event| - https://developer.chrome.com/apps/audio#property-onMuteChanged-event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-onMuteChanged."
  ([channel & args] (apply gen-call :event ::on-mute-changed &form channel args)))

(defmacro tap-on-device-list-changed-events
  "Fired when audio devices change, either new devices being added, or existing devices being removed.

   Events will be put on the |channel| with signature [::on-device-list-changed [devices]] where:

     |devices| - List of all present audio devices after the change.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/audio#event-onDeviceListChanged."
  ([channel & args] (apply gen-call :event ::on-device-list-changed &form channel args)))

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
   :since "58",
   :functions
   [{:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-audio.AudioDeviceInfos]"}]}}]}
    {:id ::set-active-devices,
     :name "setActiveDevices",
     :callback? true,
     :params [{:name "ids", :type "audio.DeviceIdLists-or-[array-of-strings]"} {:name "callback", :type :callback}]}
    {:id ::set-properties,
     :name "setProperties",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "properties", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-mute,
     :name "getMute",
     :callback? true,
     :params
     [{:name "stream-type", :type "audio.StreamType"}
      {:name "callback", :type :callback, :callback {:params [{:name "value", :type "boolean"}]}}]}
    {:id ::set-mute,
     :name "setMute",
     :callback? true,
     :params
     [{:name "stream-type", :type "audio.StreamType"}
      {:name "is-muted", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-level-changed, :name "onLevelChanged", :params [{:name "event", :type "object"}]}
    {:id ::on-mute-changed, :name "onMuteChanged", :params [{:name "event", :type "object"}]}
    {:id ::on-device-list-changed,
     :name "onDeviceListChanged",
     :params [{:name "devices", :type "[array-of-audio.AudioDeviceInfos]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))