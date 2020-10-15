(ns chromex.ext.image-writer-private
  "Use the chrome.image_writer API to write images to
   removable media.

   See the design doc for a detailed description of this API.
   https://goo.gl/KzMEFq

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro write-from-url
  "Write an image to the disk downloaded from the provided URL.  The callback will be called when the entire operation
   completes, either successfully or on error.

     |storage-unit-id| - The identifier for the storage unit
     |image-url| - The url of the image to download which will be written to the storage unit identified by |storageUnitId

     |options| - Optional parameters if comparing the download with a given hash or saving the download to the users
                 Downloads folder instead of a temporary directory is desired

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([storage-unit-id image-url options] (gen-call :function ::write-from-url &form storage-unit-id image-url options))
  ([storage-unit-id image-url] `(write-from-url ~storage-unit-id ~image-url :omit)))

(defmacro write-from-file
  "Write an image to the disk, prompting the user to supply the image from a local file.  The callback will be called when the
   entire operation completes, either successfully or on error.

     |storage-unit-id| - The identifier for the storage unit
     |file-entry| - The FileEntry object of the image to be burned.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([storage-unit-id file-entry] (gen-call :function ::write-from-file &form storage-unit-id file-entry)))

(defmacro cancel-write
  "Cancel a current write operation.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::cancel-write &form)))

(defmacro destroy-partitions
  "Destroys the partition table of a disk, effectively erasing it.  This is a fairly quick operation and so it does not have
   complex stages or progress information, just a write phase.

     |storage-unit-id| - The identifier of the storage unit to wipe

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([storage-unit-id] (gen-call :function ::destroy-partitions &form storage-unit-id)))

(defmacro list-removable-storage-devices
  "List all the removable block devices currently attached to the system.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [devices] where:

     |devices| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::list-removable-storage-devices &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-write-progress-events
  "Fires periodically throughout the writing operation and at least once per stage.

   Events will be put on the |channel| with signature [::on-write-progress [info]] where:

     |info| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-write-progress &form channel args)))

(defmacro tap-on-write-complete-events
  "Fires when the write operation has completely finished, such as all devices being finalized and resources released.

   Events will be put on the |channel| with signature [::on-write-complete []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-write-complete &form channel args)))

(defmacro tap-on-write-error-events
  "Fires when an error occured during writing, passing the 'ProgressInfo' of the operation at the time the error occured.

   Events will be put on the |channel| with signature [::on-write-error [info error]] where:

     |info| - ?
     |error| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-write-error &form channel args)))

(defmacro tap-on-device-inserted-events
  "Fires when a removable storage device is inserted.

   Events will be put on the |channel| with signature [::on-device-inserted [device]] where:

     |device| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-device-inserted &form channel args)))

(defmacro tap-on-device-removed-events
  "Fires when a removable storage device is removed.

   Events will be put on the |channel| with signature [::on-device-removed [device]] where:

     |device| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-device-removed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.image-writer-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.imageWriterPrivate",
   :since "38",
   :functions
   [{:id ::write-from-url,
     :name "writeFromUrl",
     :callback? true,
     :params
     [{:name "storage-unit-id", :type "string"}
      {:name "image-url", :type "string"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::write-from-file,
     :name "writeFromFile",
     :callback? true,
     :params
     [{:name "storage-unit-id", :type "string"}
      {:name "file-entry", :type "FileEntry"}
      {:name "callback", :type :callback}]}
    {:id ::cancel-write,
     :name "cancelWrite",
     :callback? true,
     :return-type "boolean",
     :params [{:name "callback", :type :callback}]}
    {:id ::destroy-partitions,
     :name "destroyPartitions",
     :callback? true,
     :params [{:name "storage-unit-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::list-removable-storage-devices,
     :name "listRemovableStorageDevices",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "devices", :type "[array-of-imageWriterPrivate.RemovableStorageDevices]"}]}}]}],
   :events
   [{:id ::on-write-progress,
     :name "onWriteProgress",
     :params [{:name "info", :type "imageWriterPrivate.ProgressInfo"}]}
    {:id ::on-write-complete, :name "onWriteComplete"}
    {:id ::on-write-error,
     :name "onWriteError",
     :params [{:name "info", :type "imageWriterPrivate.ProgressInfo"} {:name "error", :type "string"}]}
    {:id ::on-device-inserted,
     :name "onDeviceInserted",
     :params [{:name "device", :type "imageWriterPrivate.RemovableStorageDevice"}]}
    {:id ::on-device-removed,
     :name "onDeviceRemoved",
     :params [{:name "device", :type "imageWriterPrivate.RemovableStorageDevice"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))