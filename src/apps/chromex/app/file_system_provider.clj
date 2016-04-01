(ns chromex.app.file-system-provider
  "Use the chrome.fileSystemProvider API to create file systems,
   that can be accessible from the file manager on Chrome OS.

     * available since Chrome 40
     * https://developer.chrome.com/apps/fileSystemProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro mount
  "Mounts a file system with the given fileSystemId and displayName. displayName will be shown in the left panel of Files.app.
   displayName can contain any characters including '/', but cannot be an empty string. displayName must be descriptive but
   doesn't have to be unique. The fileSystemId must not be an empty string.Depending on the type of the file system being
   mounted, the source option must be set appropriately.In case of an error, 'runtime.lastError' will be set with a
   corresponding error code.

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-mount-options.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystemProvider#method-mount."
  ([options] (gen-call :function ::mount &form options)))

(defmacro unmount
  "Unmounts a file system with the given fileSystemId. It must be called after 'onUnmountRequested' is invoked. Also, the
   providing extension can decide to perform unmounting if not requested (eg. in case of lost connection, or a file error).In
   case of an error, 'runtime.lastError' will be set with a corresponding error code.

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-unmount-options.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystemProvider#method-unmount."
  ([options] (gen-call :function ::unmount &form options)))

(defmacro get-all
  "Returns all file systems mounted by the extension.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [file-systems] where:

     |file-systems| - https://developer.chrome.com/apps/fileSystemProvider#property-callback-fileSystems.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystemProvider#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro get
  "Returns information about a file system with the passed fileSystemId.

     |file-system-id| - https://developer.chrome.com/apps/fileSystemProvider#property-get-fileSystemId.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [file-system] where:

     |file-system| - https://developer.chrome.com/apps/fileSystemProvider#property-callback-fileSystem.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystemProvider#method-get."
  ([file-system-id] (gen-call :function ::get &form file-system-id)))

(defmacro notify
  "Notifies about changes in the watched directory at observedPath in recursive mode. If the file system is mounted with
   supportsNofityTag, then tag must be provided, and all changes since the last notification always reported, even if the
   system was shutdown. The last tag can be obtained with 'getAll'.To use, the file_system_provider.notify manifest option
   must be set to true.Value of tag can be any string which is unique per call, so it's possible to identify the last
   registered notification. Eg. if the providing extension starts after a reboot, and the last registered notification's tag
   is equal to '123', then it should call 'notify' for all changes which happened since the change tagged as '123'. It cannot
   be an empty string.Not all providers are able to provide a tag, but if the file system has a changelog, then the tag can be
   eg. a change number, or a revision number.Note that if a parent directory is removed, then all descendant entries are also
   removed, and if they are watched, then the API must be notified about the fact. Also, if a directory is renamed, then all
   descendant entries are in fact removed, as there is no entry under their original paths anymore.In case of an error,
   'runtime.lastError' will be set will a corresponding error code.

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-notify-options.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileSystemProvider#method-notify."
  ([options] (gen-call :function ::notify &form options)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-unmount-requested-events
  "Raised when unmounting for the file system with the fileSystemId identifier is requested. In the response, the 'unmount'
   API method must be called together with successCallback. If unmounting is not possible (eg. due to a pending operation),
   then errorCallback must be called.

   Events will be put on the |channel| with signature [::on-unmount-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onUnmountRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onUnmountRequested."
  ([channel & args] (apply gen-call :event ::on-unmount-requested &form channel args)))

(defmacro tap-on-get-metadata-requested-events
  "Raised when metadata of a file or a directory at entryPath is requested. The metadata must be returned with the
   successCallback call. In case of an error, errorCallback must be called.

   Events will be put on the |channel| with signature [::on-get-metadata-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onGetMetadataRequested-options.
     |success-callback| - Success callback for the 'onGetMetadataRequested' event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onGetMetadataRequested."
  ([channel & args] (apply gen-call :event ::on-get-metadata-requested &form channel args)))

(defmacro tap-on-get-actions-requested-events
  "Raised when a list of actions for a set of files or directories at entryPaths is requested. All of the returned actions
   must be applicable to each entry. If there are no such actions, an empty array should be returned. The actions must be
   returned with the successCallback call. In case of an error, errorCallback must be called.

   Events will be put on the |channel| with signature [::on-get-actions-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onGetActionsRequested-options.
     |success-callback| - Success callback for the 'onGetActionsRequested' event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onGetActionsRequested."
  ([channel & args] (apply gen-call :event ::on-get-actions-requested &form channel args)))

(defmacro tap-on-read-directory-requested-events
  "Raised when contents of a directory at directoryPath are requested. The results must be returned in chunks by calling the
   successCallback several times. In case of an error, errorCallback must be called.

   Events will be put on the |channel| with signature [::on-read-directory-requested [options success-callback
   error-callback]] where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onReadDirectoryRequested-options.
     |success-callback| - Success callback for the 'onReadDirectoryRequested' event. If more entries will be returned, then
                          hasMore must be true, and it has to be called again with additional entries. If no more entries are
                          available, then hasMore must be set to false.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onReadDirectoryRequested."
  ([channel & args] (apply gen-call :event ::on-read-directory-requested &form channel args)))

(defmacro tap-on-open-file-requested-events
  "Raised when opening a file at filePath is requested. If the file does not exist, then the operation must fail. Maximum
   number of files opened at once can be specified with MountOptions.

   Events will be put on the |channel| with signature [::on-open-file-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onOpenFileRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onOpenFileRequested."
  ([channel & args] (apply gen-call :event ::on-open-file-requested &form channel args)))

(defmacro tap-on-close-file-requested-events
  "Raised when opening a file previously opened with openRequestId is requested to be closed.

   Events will be put on the |channel| with signature [::on-close-file-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onCloseFileRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onCloseFileRequested."
  ([channel & args] (apply gen-call :event ::on-close-file-requested &form channel args)))

(defmacro tap-on-read-file-requested-events
  "Raised when reading contents of a file opened previously with openRequestId is requested. The results must be returned in
   chunks by calling successCallback several times. In case of an error, errorCallback must be called.

   Events will be put on the |channel| with signature [::on-read-file-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onReadFileRequested-options.
     |success-callback| - Success callback for the 'onReadFileRequested' event. If more data will be returned, then hasMore
                          must be true, and it has to be called again with additional entries. If no more data is available,
                          then hasMore must be set to false.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onReadFileRequested."
  ([channel & args] (apply gen-call :event ::on-read-file-requested &form channel args)))

(defmacro tap-on-create-directory-requested-events
  "Raised when creating a directory is requested. The operation must fail with the EXISTS error if the target directory
   already exists. If recursive is true, then all of the missing directories on the directory path must be created.

   Events will be put on the |channel| with signature [::on-create-directory-requested [options success-callback
   error-callback]] where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onCreateDirectoryRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onCreateDirectoryRequested."
  ([channel & args] (apply gen-call :event ::on-create-directory-requested &form channel args)))

(defmacro tap-on-delete-entry-requested-events
  "Raised when deleting an entry is requested. If recursive is true, and the entry is a directory, then all of the entries
   inside must be recursively deleted as well.

   Events will be put on the |channel| with signature [::on-delete-entry-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onDeleteEntryRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onDeleteEntryRequested."
  ([channel & args] (apply gen-call :event ::on-delete-entry-requested &form channel args)))

(defmacro tap-on-create-file-requested-events
  "Raised when creating a file is requested. If the file already exists, then errorCallback must be called with the 'EXISTS'
   error code.

   Events will be put on the |channel| with signature [::on-create-file-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onCreateFileRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onCreateFileRequested."
  ([channel & args] (apply gen-call :event ::on-create-file-requested &form channel args)))

(defmacro tap-on-copy-entry-requested-events
  "Raised when copying an entry (recursively if a directory) is requested. If an error occurs, then errorCallback must be
   called.

   Events will be put on the |channel| with signature [::on-copy-entry-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onCopyEntryRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onCopyEntryRequested."
  ([channel & args] (apply gen-call :event ::on-copy-entry-requested &form channel args)))

(defmacro tap-on-move-entry-requested-events
  "Raised when moving an entry (recursively if a directory) is requested. If an error occurs, then errorCallback must be
   called.

   Events will be put on the |channel| with signature [::on-move-entry-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onMoveEntryRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onMoveEntryRequested."
  ([channel & args] (apply gen-call :event ::on-move-entry-requested &form channel args)))

(defmacro tap-on-truncate-requested-events
  "Raised when truncating a file to a desired length is requested. If an error occurs, then errorCallback must be called.

   Events will be put on the |channel| with signature [::on-truncate-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onTruncateRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onTruncateRequested."
  ([channel & args] (apply gen-call :event ::on-truncate-requested &form channel args)))

(defmacro tap-on-write-file-requested-events
  "Raised when writing contents to a file opened previously with openRequestId is requested.

   Events will be put on the |channel| with signature [::on-write-file-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onWriteFileRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onWriteFileRequested."
  ([channel & args] (apply gen-call :event ::on-write-file-requested &form channel args)))

(defmacro tap-on-abort-requested-events
  "Raised when aborting an operation with operationRequestId is requested. The operation executed with operationRequestId must
   be immediately stopped and successCallback of this abort request executed. If aborting fails, then errorCallback must be
   called. Note, that callbacks of the aborted operation must not be called, as they will be ignored. Despite calling
   errorCallback, the request may be forcibly aborted.

   Events will be put on the |channel| with signature [::on-abort-requested [options success-callback error-callback]] where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onAbortRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onAbortRequested."
  ([channel & args] (apply gen-call :event ::on-abort-requested &form channel args)))

(defmacro tap-on-configure-requested-events
  "Raised when showing a configuration dialog for fileSystemId is requested. If it's handled, the
   file_system_provider.configurable manfiest option must be set to true.

   Events will be put on the |channel| with signature [::on-configure-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onConfigureRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onConfigureRequested."
  ([channel & args] (apply gen-call :event ::on-configure-requested &form channel args)))

(defmacro tap-on-mount-requested-events
  "Raised when showing a dialog for mounting a new file system is requested. If the extension/app is a file handler, then this
   event shouldn't be handled. Instead app.runtime.onLaunched should be handled in order to mount new file systems when a file
   is opened. For multiple mounts, the file_system_provider.multiple_mounts manifest option must be set to true.

   Events will be put on the |channel| with signature [::on-mount-requested [success-callback error-callback]] where:

     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onMountRequested."
  ([channel & args] (apply gen-call :event ::on-mount-requested &form channel args)))

(defmacro tap-on-add-watcher-requested-events
  "Raised when setting a new directory watcher is requested. If an error occurs, then errorCallback must be called.

   Events will be put on the |channel| with signature [::on-add-watcher-requested [options success-callback error-callback]]
   where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onAddWatcherRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onAddWatcherRequested."
  ([channel & args] (apply gen-call :event ::on-add-watcher-requested &form channel args)))

(defmacro tap-on-remove-watcher-requested-events
  "Raised when the watcher should be removed. If an error occurs, then errorCallback must be called.

   Events will be put on the |channel| with signature [::on-remove-watcher-requested [options success-callback
   error-callback]] where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onRemoveWatcherRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onRemoveWatcherRequested."
  ([channel & args] (apply gen-call :event ::on-remove-watcher-requested &form channel args)))

(defmacro tap-on-execute-action-requested-events
  "Raised when executing an action for a set of files or directories is\\ requested. After the action is completed,
   successCallback must be called. On error, errorCallback must be called.

   Events will be put on the |channel| with signature [::on-execute-action-requested [options success-callback
   error-callback]] where:

     |options| - https://developer.chrome.com/apps/fileSystemProvider#property-onExecuteActionRequested-options.
     |success-callback| - Callback to be called by the providing extension in case of a success.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileSystemProvider#event-onExecuteActionRequested."
  ([channel & args] (apply gen-call :event ::on-execute-action-requested &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.file-system-provider namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileSystemProvider",
   :since "40",
   :functions
   [{:id ::mount,
     :name "mount",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::unmount,
     :name "unmount",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "file-systems", :type "[array-of-fileSystemProvider.FileSystemInfos]"}]}}]}
    {:id ::get,
     :name "get",
     :since "42",
     :callback? true,
     :params
     [{:name "file-system-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "file-system", :type "fileSystemProvider.FileSystemInfo"}]}}]}
    {:id ::notify,
     :name "notify",
     :since "45",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-unmount-requested,
     :name "onUnmountRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-get-metadata-requested,
     :name "onGetMetadataRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-get-actions-requested,
     :name "onGetActionsRequested",
     :since "48",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-read-directory-requested,
     :name "onReadDirectoryRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-open-file-requested,
     :name "onOpenFileRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-close-file-requested,
     :name "onCloseFileRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-read-file-requested,
     :name "onReadFileRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-create-directory-requested,
     :name "onCreateDirectoryRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-delete-entry-requested,
     :name "onDeleteEntryRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-create-file-requested,
     :name "onCreateFileRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-copy-entry-requested,
     :name "onCopyEntryRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-move-entry-requested,
     :name "onMoveEntryRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-truncate-requested,
     :name "onTruncateRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-write-file-requested,
     :name "onWriteFileRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-abort-requested,
     :name "onAbortRequested",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-configure-requested,
     :name "onConfigureRequested",
     :since "44",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-mount-requested,
     :name "onMountRequested",
     :since "44",
     :params [{:name "success-callback", :type "function"} {:name "error-callback", :type :callback}]}
    {:id ::on-add-watcher-requested,
     :name "onAddWatcherRequested",
     :since "45",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-remove-watcher-requested,
     :name "onRemoveWatcherRequested",
     :since "45",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}
    {:id ::on-execute-action-requested,
     :name "onExecuteActionRequested",
     :since "48",
     :params
     [{:name "options", :type "object"}
      {:name "success-callback", :type "function"}
      {:name "error-callback", :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))