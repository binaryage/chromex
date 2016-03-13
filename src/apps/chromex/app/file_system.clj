(ns chromex.app.file-system
  "Use the chrome.fileSystem API to create, read, navigate,
   and write to the user's local file system. With this API, Chrome Apps can
   read and write to a user-selected location. For example, a text editor app
   can use the API to read and write local documents. All failures are notified
   via chrome.runtime.lastError.
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/fileSystem"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-display-path
  "Get the display path of an Entry object. The display path is based on the full path of the file or directory on the local
   file system, but may be made more readable for display purposes.
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-getDisplayPath-entry.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [displayPath] where:
   
     |displayPath| - See https://developer.chrome.com/extensions/fileSystem#property-callback-displayPath.
   
   See https://developer.chrome.com/extensions/fileSystem#method-getDisplayPath."
  ([entry #_callback] (gen-call :function ::get-display-path &form entry)))

(defmacro get-writable-entry
  "Get a writable Entry from another Entry. This call will fail with a runtime error if the application does not have the
   'write' permission under 'fileSystem'. If entry is a DirectoryEntry, this call will fail if the application does not have
   the 'directory' permission under 'fileSystem'.
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-getWritableEntry-entry.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entry] where:
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-callback-entry.
   
   See https://developer.chrome.com/extensions/fileSystem#method-getWritableEntry."
  ([entry #_callback] (gen-call :function ::get-writable-entry &form entry)))

(defmacro is-writable-entry
  "Gets whether this Entry is writable or not.
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-isWritableEntry-entry.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [isWritable] where:
   
     |isWritable| - See https://developer.chrome.com/extensions/fileSystem#property-callback-isWritable.
   
   See https://developer.chrome.com/extensions/fileSystem#method-isWritableEntry."
  ([entry #_callback] (gen-call :function ::is-writable-entry &form entry)))

(defmacro choose-entry
  "Ask the user to choose a file or directory.
   
     |options| - See https://developer.chrome.com/extensions/fileSystem#property-chooseEntry-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entry fileEntries] where:
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-callback-entry.
     |fileEntries| - See https://developer.chrome.com/extensions/fileSystem#property-callback-fileEntries.
   
   See https://developer.chrome.com/extensions/fileSystem#method-chooseEntry."
  ([options #_callback] (gen-call :function ::choose-entry &form options))
  ([] `(choose-entry :omit)))

(defmacro restore-entry
  "Returns the file entry with the given id if it can be restored. This call will fail with a runtime error otherwise.
   
     |id| - See https://developer.chrome.com/extensions/fileSystem#property-restoreEntry-id.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entry] where:
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-callback-entry.
   
   See https://developer.chrome.com/extensions/fileSystem#method-restoreEntry."
  ([id #_callback] (gen-call :function ::restore-entry &form id)))

(defmacro is-restorable
  "Returns whether the app has permission to restore the entry with the given id.
   
     |id| - See https://developer.chrome.com/extensions/fileSystem#property-isRestorable-id.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [isRestorable] where:
   
     |isRestorable| - See https://developer.chrome.com/extensions/fileSystem#property-callback-isRestorable.
   
   See https://developer.chrome.com/extensions/fileSystem#method-isRestorable."
  ([id #_callback] (gen-call :function ::is-restorable &form id)))

(defmacro retain-entry
  "Returns an id that can be passed to restoreEntry to regain access to a given file entry. Only the 500 most recently used
   entries are retained, where calls to retainEntry and restoreEntry count as use. If the app has the 'retainEntries'
   permission under 'fileSystem', entries are retained indefinitely. Otherwise, entries are retained only while the app is
   running and across restarts.
   
     |entry| - See https://developer.chrome.com/extensions/fileSystem#property-retainEntry-entry.
   
   See https://developer.chrome.com/extensions/fileSystem#method-retainEntry."
  ([entry] (gen-call :function ::retain-entry &form entry)))

(defmacro request-file-system
  "Requests access to a file system for a volume represented by  options.volumeId. If options.writable is set to true, then
   the file system will be writable. Otherwise, it will be read-only. The writable option requires the  'fileSystem':
   {'write'} permission in the manifest. Available to kiosk apps running in kiosk session only. For manual-launch kiosk mode,
   a confirmation dialog will be shown on top of the active app window. In case of an error, fileSystem will be undefined, and
   chrome.runtime.lastError will be set.
   
     |options| - See https://developer.chrome.com/extensions/fileSystem#property-requestFileSystem-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [fileSystem] where:
   
     |fileSystem| - See https://developer.chrome.com/extensions/fileSystem#property-callback-fileSystem.
   
   See https://developer.chrome.com/extensions/fileSystem#method-requestFileSystem."
  ([options #_callback] (gen-call :function ::request-file-system &form options)))

(defmacro get-volume-list
  "Returns a list of volumes available for requestFileSystem(). The 'fileSystem': {'requestFileSystem'} manifest permission is
   required. Available to kiosk apps running in the kiosk session only. In case of an error, volumes will be undefined, and
   chrome.runtime.lastError will be set.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [volumes] where:
   
     |volumes| - See https://developer.chrome.com/extensions/fileSystem#property-callback-volumes.
   
   See https://developer.chrome.com/extensions/fileSystem#method-getVolumeList."
  ([#_callback] (gen-call :function ::get-volume-list &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-volume-list-changed-events
  "Called when a list of available volumes is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/fileSystem#event-onVolumeListChanged."
  ([channel & args] (apply gen-call :event ::on-volume-list-changed &form channel args)))

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
  {:namespace "chrome.fileSystem",
   :since "23",
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
     :since "29",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "entry", :type "Entry"}]}}]}
    {:id ::is-restorable,
     :name "isRestorable",
     :since "29",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "is-restorable", :type "boolean"}]}}]}
    {:id ::retain-entry,
     :name "retainEntry",
     :since "29",
     :return-type "string",
     :params [{:name "entry", :type "Entry"}]}
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))