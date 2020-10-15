(ns chromex.app.file-system
  "Use the chrome.fileSystem API to create, read, navigate,
   and write to the user's local file system. With this API, Chrome Apps can
   read and write to a user-selected location. For example, a text editor app
   can use the API to read and write local documents. All failures are notified
   via chrome.runtime.lastError.

     * available since Chrome 38
     * https://developer.chrome.com/apps/fileSystem"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-display-path
  "Get the display path of an Entry object. The display path is based on the full path of the file or directory on the local
   file system, but may be made more readable for display purposes.

     |entry| - https://developer.chrome.com/apps/fileSystem#property-getDisplayPath-entry.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [display-path] where:

     |display-path| - https://developer.chrome.com/apps/fileSystem#property-callback-displayPath.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-getDisplayPath."
  ([entry] (gen-call :function ::get-display-path &form entry)))

(defmacro get-writable-entry
  "Get a writable Entry from another Entry. This call will fail with a runtime error if the application does not have the
   'write' permission under 'fileSystem'. If entry is a DirectoryEntry, this call will fail if the application does not have
   the 'directory' permission under 'fileSystem'.

     |entry| - https://developer.chrome.com/apps/fileSystem#property-getWritableEntry-entry.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entry] where:

     |entry| - https://developer.chrome.com/apps/fileSystem#property-callback-entry.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-getWritableEntry."
  ([entry] (gen-call :function ::get-writable-entry &form entry)))

(defmacro is-writable-entry
  "Gets whether this Entry is writable or not.

     |entry| - https://developer.chrome.com/apps/fileSystem#property-isWritableEntry-entry.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-writable] where:

     |is-writable| - https://developer.chrome.com/apps/fileSystem#property-callback-isWritable.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-isWritableEntry."
  ([entry] (gen-call :function ::is-writable-entry &form entry)))

(defmacro choose-entry
  "Ask the user to choose a file or directory.

     |options| - https://developer.chrome.com/apps/fileSystem#property-chooseEntry-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entry file-entries] where:

     |entry| - https://developer.chrome.com/apps/fileSystem#property-callback-entry.
     |file-entries| - https://developer.chrome.com/apps/fileSystem#property-callback-fileEntries.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-chooseEntry."
  ([options] (gen-call :function ::choose-entry &form options))
  ([] `(choose-entry :omit)))

(defmacro restore-entry
  "Returns the file entry with the given id if it can be restored. This call will fail with a runtime error otherwise.

     |id| - https://developer.chrome.com/apps/fileSystem#property-restoreEntry-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entry] where:

     |entry| - https://developer.chrome.com/apps/fileSystem#property-callback-entry.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-restoreEntry."
  ([id] (gen-call :function ::restore-entry &form id)))

(defmacro is-restorable
  "Returns whether the app has permission to restore the entry with the given id.

     |id| - https://developer.chrome.com/apps/fileSystem#property-isRestorable-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-restorable] where:

     |is-restorable| - https://developer.chrome.com/apps/fileSystem#property-callback-isRestorable.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-isRestorable."
  ([id] (gen-call :function ::is-restorable &form id)))

(defmacro retain-entry
  "Returns an id that can be passed to restoreEntry to regain access to a given file entry. Only the 500 most recently used
   entries are retained, where calls to retainEntry and restoreEntry count as use. If the app has the 'retainEntries'
   permission under 'fileSystem', entries are retained indefinitely. Otherwise, entries are retained only while the app is
   running and across restarts.

     |entry| - https://developer.chrome.com/apps/fileSystem#property-retainEntry-entry.

   https://developer.chrome.com/apps/fileSystem#method-retainEntry."
  ([entry] (gen-call :function ::retain-entry &form entry)))

(defmacro request-file-system
  "Requests access to a file system for a volume represented by  options.volumeId. If options.writable is set to true, then
   the file system will be writable. Otherwise, it will be read-only. The writable option requires the  'fileSystem':
   {'write'} permission in the manifest. Available to kiosk apps running in kiosk session only. For manual-launch kiosk mode,
   a confirmation dialog will be shown on top of the active app window. In case of an error, fileSystem will be undefined, and
   chrome.runtime.lastError will be set.

     |options| - https://developer.chrome.com/apps/fileSystem#property-requestFileSystem-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [file-system] where:

     |file-system| - https://developer.chrome.com/apps/fileSystem#property-callback-fileSystem.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-requestFileSystem."
  ([options] (gen-call :function ::request-file-system &form options)))

(defmacro get-volume-list
  "Returns a list of volumes available for requestFileSystem(). The 'fileSystem': {'requestFileSystem'} manifest permission is
   required. Available to kiosk apps running in the kiosk session only. In case of an error, volumes will be undefined, and
   chrome.runtime.lastError will be set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [volumes] where:

     |volumes| - https://developer.chrome.com/apps/fileSystem#property-callback-volumes.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystem#method-getVolumeList."
  ([] (gen-call :function ::get-volume-list &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-volume-list-changed-events
  "Called when a list of available volumes is changed.

   Events will be put on the |channel| with signature [::on-volume-list-changed [event]] where:

     |event| - https://developer.chrome.com/apps/fileSystem#property-onVolumeListChanged-event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystem#event-onVolumeListChanged."
  ([channel & args] (apply gen-call :event ::on-volume-list-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.file-system namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileSystem",
   :since "38",
   :functions
   [{:id ::get-display-path,
     :name "getDisplayPath",
     :callback? true,
     :params
     [{:name "entry", :type "Entry"}
      {:name "callback", :type :callback, :callback {:params [{:name "display-path", :type "string"}]}}]}
    {:id ::get-writable-entry,
     :name "getWritableEntry",
     :callback? true,
     :params
     [{:name "entry", :type "Entry"}
      {:name "callback", :type :callback, :callback {:params [{:name "entry", :type "Entry"}]}}]}
    {:id ::is-writable-entry,
     :name "isWritableEntry",
     :callback? true,
     :params
     [{:name "entry", :type "Entry"}
      {:name "callback", :type :callback, :callback {:params [{:name "is-writable", :type "boolean"}]}}]}
    {:id ::choose-entry,
     :name "chooseEntry",
     :callback? true,
     :params
     [{:name "options", :optional? true, :type "object"}
      {:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "entry", :optional? true, :type "Entry"}
         {:name "file-entries", :optional? true, :type "[array-of-FileEntrys]"}]}}]}
    {:id ::restore-entry,
     :name "restoreEntry",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "entry", :type "Entry"}]}}]}
    {:id ::is-restorable,
     :name "isRestorable",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "is-restorable", :type "boolean"}]}}]}
    {:id ::retain-entry, :name "retainEntry", :return-type "string", :params [{:name "entry", :type "Entry"}]}
    {:id ::request-file-system,
     :name "requestFileSystem",
     :since "44",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "file-system", :optional? true, :type "FileSystem"}]}}]}
    {:id ::get-volume-list,
     :name "getVolumeList",
     :since "44",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "volumes", :optional? true, :type "[array-of-fileSystem.Volumes]"}]}}]}],
   :events
   [{:id ::on-volume-list-changed, :name "onVolumeListChanged", :since "44", :params [{:name "event", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))