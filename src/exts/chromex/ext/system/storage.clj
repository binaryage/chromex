(ns chromex.ext.system.storage
  "Use the chrome.system.storage API to query storage device
   information and be notified when a removable storage device is attached and
   detached.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/system.storage"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Get the storage information from the system. The argument passed to the callback is an array of StorageUnitInfo objects.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - https://developer.chrome.com/extensions/system.storage#property-callback-info.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.storage#method-getInfo."
  ([] (gen-call :function ::get-info &form)))

(defmacro eject-device
  "Ejects a removable storage device.

     |id| - https://developer.chrome.com/extensions/system.storage#property-ejectDevice-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/system.storage#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.storage#method-ejectDevice."
  ([id] (gen-call :function ::eject-device &form id)))

(defmacro get-available-capacity
  "Get the available capacity of a specified |id| storage device. The |id| is the transient device ID from StorageUnitInfo.

     |id| - https://developer.chrome.com/extensions/system.storage#property-getAvailableCapacity-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - https://developer.chrome.com/extensions/system.storage#property-callback-info.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.storage#method-getAvailableCapacity."
  ([id] (gen-call :function ::get-available-capacity &form id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-attached-events
  "Fired when a new removable storage is attached to the system.

   Events will be put on the |channel| with signature [::on-attached [info]] where:

     |info| - https://developer.chrome.com/extensions/system.storage#property-onAttached-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/system.storage#event-onAttached."
  ([channel & args] (apply gen-call :event ::on-attached &form channel args)))

(defmacro tap-on-detached-events
  "Fired when a removable storage is detached from the system.

   Events will be put on the |channel| with signature [::on-detached [id]] where:

     |id| - https://developer.chrome.com/extensions/system.storage#property-onDetached-id.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/system.storage#event-onDetached."
  ([channel & args] (apply gen-call :event ::on-detached &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.system.storage namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.storage",
   :since "38",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "info", :type "[array-of-system.storage.StorageUnitInfos]"}]}}]}
    {:id ::eject-device,
     :name "ejectDevice",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::get-available-capacity,
     :name "getAvailableCapacity",
     :since "88",
     :callback? true,
     :params
     [{:name "id", :since "38", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}],
   :events
   [{:id ::on-attached, :name "onAttached", :params [{:name "info", :type "system.storage.StorageUnitInfo"}]}
    {:id ::on-detached, :name "onDetached", :params [{:name "id", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))