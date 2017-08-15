(ns chromex.app.file-manager-private
  "fileManagerPrivate API.
   This is a private API used by the file browser of ChromeOS.

     * available since Chrome 39"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro logout-user-for-reauthentication
  "Logout the current user for navigating to the re-authentication screen for the Google account."
  ([] (gen-call :function ::logout-user-for-reauthentication &form)))

(defmacro cancel-dialog
  "Cancels file selection."
  ([] (gen-call :function ::cancel-dialog &form)))

(defmacro execute-task
  "Executes file browser task over selected files. |taskId| The unique identifier of task to execute. |entries| Array of
   entries |callback

     |task-id| - ?
     |entries| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id entries] (gen-call :function ::execute-task &form task-id entries)))

(defmacro set-default-task
  "Sets the default task for the supplied MIME types and path extensions. Lists of MIME types and URLs may contain duplicates.
   Additionally, the list of MIME types can be empty. |taskId| The unique identifier of task to mark as default. |entries

   Array of selected entries to extract path extensions from. |mimeTypes| Array of selected file MIME types. |callback

     |task-id| - ?
     |entries| - ?
     |mime-types| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id entries mime-types] (gen-call :function ::set-default-task &form task-id entries mime-types)))

(defmacro get-file-tasks
  "Gets the list of tasks that can be performed over selected files. |entries| Array of selected entries |callback

     |entries| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [tasks] where:

     |tasks| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::get-file-tasks &form entries)))

(defmacro get-mime-type
  "Gets the MIME type of a file. |entry| Entry to be checked. |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-mime-type &form entry)))

(defmacro get-strings
  "Gets localized strings and initialization data. |callback

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-strings &form)))

(defmacro add-file-watch
  "Adds file watch. |entry| Entry to watch |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::add-file-watch &form entry)))

(defmacro remove-file-watch
  "Removes file watch. |entry| Watched entry |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::remove-file-watch &form entry)))

(defmacro enable-external-file-scheme
  "Enables the extenal file scheme necessary to initiate drags to the browser window for files on the external backend."
  ([] (gen-call :function ::enable-external-file-scheme &form)))

(defmacro grant-access
  "Requests granting R/W permissions for the passed entries. It's a best effort operation. Some files may not be granted
   access if the url is invalid or not backed by the external file system. |entryUrls| Urls for the entries to be accessed.
   |callback

     |entry-urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry-urls] (gen-call :function ::grant-access &form entry-urls)))

(defmacro select-files
  "Selects multiple files. |selectedPaths| Array of selected paths |shouldReturnLocalPath| true if paths need to be resolved
   to local paths. |callback

     |selected-paths| - ?
     |should-return-local-path| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([selected-paths should-return-local-path] (gen-call :function ::select-files &form selected-paths should-return-local-path)))

(defmacro select-file
  "Selects a file. |selectedPath| A selected path |index| Index of Filter |forOpening| true if paths are selected for opening.
   false if for saving. |shouldReturnLocalPath| true if paths need to be resolved to local paths. |callback

     |selected-path| - ?
     |index| - ?
     |for-opening| - ?
     |should-return-local-path| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([selected-path index for-opening should-return-local-path] (gen-call :function ::select-file &form selected-path index for-opening should-return-local-path)))

(defmacro get-entry-properties
  "Requests additional properties for files. |entries| list of entries |names| list of requested properties by their names.
   |callback| Completion callback. May return less than requested properties     if some are not available. In the same time,
   it can return properties     which were not requested (if it's cheap to compute them).

     |entries| - ?
     |names| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entry-properties] where:

     |entry-properties| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries names] (gen-call :function ::get-entry-properties &form entries names)))

(defmacro pin-drive-file
  "Pins/unpins a Drive file in the cache. |entry| Entry to pin/unpin. |pin| Pass true to pin the file. |callback| Completion
   callback. 'runtime.lastError' will be set if     there was an error.

     |entry| - ?
     |pin| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry pin] (gen-call :function ::pin-drive-file &form entry pin)))

(defmacro resolve-isolated-entries
  "Resolves entries in the isolated file system and returns corresponding entries in the external file system mounted to
   Chrome OS file manager backend. If resolving entry fails, the entry will be just ignored and the corresponding entry does
   not appear in the result.

     |entries| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::resolve-isolated-entries &form entries)))

(defmacro add-mount
  "Mount a resource or a file. |source| Mount point source. For compressed files it is relative file path     within external
   file system |callback

     |source| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [source-path] where:

     |source-path| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([source] (gen-call :function ::add-mount &form source)))

(defmacro remove-mount
  "Unmounts a mounted resource. |volumeId| An ID of the volume.

     |volume-id| - ?"
  ([volume-id] (gen-call :function ::remove-mount &form volume-id)))

(defmacro get-volume-metadata-list
  "Get the list of mounted volumes. |callback

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [volume-metadata-list] where:

     |volume-metadata-list| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-volume-metadata-list &form)))

(defmacro cancel-file-transfers
  "Cancels ongoing file transfers for selected files. |entries| Array of files for which ongoing transfer should be canceled.
   |callback| Completion callback of the cancel.

     |entries| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::cancel-file-transfers &form entries)))

(defmacro cancel-all-file-transfers
  "Cancels all ongoing file transfers. |callback| Completion callback of the cancel.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::cancel-all-file-transfers &form)))

(defmacro start-copy
  "Starts to copy an entry. If the source is a directory, the copy is done recursively. |entry| Entry of the source entry to
   be copied. |parentEntry| Entry for the destination (parent) directory. |newName| Name of the new entry. It must not contain
   '/'. |callback| Completion callback.

     |entry| - ?
     |parent-entry| - ?
     |new-name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [copy-id] where:

     |copy-id| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry parent-entry new-name] (gen-call :function ::start-copy &form entry parent-entry new-name)))

(defmacro cancel-copy
  "Cancels the running copy task. |copyId| ID of the copy task to be cancelled. |callback| Completion callback of the cancel.

     |copy-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([copy-id] (gen-call :function ::cancel-copy &form copy-id)))

(defmacro get-size-stats
  "Retrieves total and remaining size of a mount point. |volumeId| ID of the volume to be checked. |callback

     |volume-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [size-stats] where:

     |size-stats| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id] (gen-call :function ::get-size-stats &form volume-id)))

(defmacro format-volume
  "Formats a mounted volume. |volumeId| ID of the volume to be formatted.

     |volume-id| - ?"
  ([volume-id] (gen-call :function ::format-volume &form volume-id)))

(defmacro get-preferences
  "Retrieves file manager preferences. |callback

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-preferences &form)))

(defmacro set-preferences
  "Sets file manager preferences. |changeInfo

     |change-info| - ?"
  ([change-info] (gen-call :function ::set-preferences &form change-info)))

(defmacro search-drive
  "Performs drive content search. |searchParams| |callback

     |search-params| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries next-feed] where:

     |entries| - ?
     |next-feed| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([search-params] (gen-call :function ::search-drive &form search-params)))

(defmacro search-drive-metadata
  "Performs drive metadata search. |searchParams| |callback

     |search-params| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [results] where:

     |results| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([search-params] (gen-call :function ::search-drive-metadata &form search-params)))

(defmacro search-files-by-hashes
  "Search files in the volume having |volumeId| by using |hashList|. sub-directories) the given |targetDirectoryUrl|.

     |volume-id| - ?
     |hash-list| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [urls] where:

     |urls| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id hash-list] (gen-call :function ::search-files-by-hashes &form volume-id hash-list)))

(defmacro zip-selection
  "Create a zip file for the selected files. |parentEntry| Entry of the directory containing the selected files. |entries

   Entries of the selected files. The files must be under the     directory specified by |parentEntry|. |destName| Name of the
   destination zip file. The zip file will be created     under the directory specified by |parentEntry|. |callback

   TODO(mtomasz): Swap order of |entries| and |parentEntry|.

     |parent-entry| - ?
     |entries| - ?
     |dest-name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-entry entries dest-name] (gen-call :function ::zip-selection &form parent-entry entries dest-name)))

(defmacro get-drive-connection-state
  "Retrieves the state of the current drive connection. |callback

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-drive-connection-state &form)))

(defmacro validate-path-name-length
  "Checks whether the path name length fits in the limit of the filesystem. |parentEntry| The entry of the parent directory
   entry. |name| The name of the file. |callback| Called back when the check is finished.

     |parent-entry| - ?
     |name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-entry name] (gen-call :function ::validate-path-name-length &form parent-entry name)))

(defmacro zoom
  "Changes the zoom factor of the Files app. |operation| Zooming mode.

     |operation| - ?"
  ([operation] (gen-call :function ::zoom &form operation)))

(defmacro request-access-token
  "Requests a Drive API OAuth2 access token. |refresh| Whether the token should be refetched instead of using the cached
   one. |callback

     |refresh| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [access-token] where:

     |access-token| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([refresh] (gen-call :function ::request-access-token &form refresh)))

(defmacro request-web-store-access-token
  "Requests a Webstore API OAuth2 access token. |callback

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [access-token] where:

     |access-token| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::request-web-store-access-token &form)))

(defmacro get-share-url
  "Requests a share dialog url for the specified file. |entry| The entry to share. |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:

     |url| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-share-url &form entry)))

(defmacro get-download-url
  "Requests a download url to download the file contents. |entry| The entry to download. |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:

     |url| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-download-url &form entry)))

(defmacro request-drive-share
  "Requests to share drive files. |entry| Entry to be shared. |shareType| Type of access that is getting granted.

     |entry| - ?
     |share-type| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry share-type] (gen-call :function ::request-drive-share &form entry share-type)))

(defmacro get-profiles
  "Obtains a list of profiles that are logged-in.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [profiles running-profile display-profile] where:

     |profiles| - ?
     |running-profile| - ?
     |display-profile| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-profiles &form)))

(defmacro open-inspector
  "Opens inspector window. |type| InspectionType which specifies how to open inspector.

     |type| - ?"
  ([type] (gen-call :function ::open-inspector &form type)))

(defmacro compute-checksum
  "Computes an MD5 checksum for the given file. |entry| The entry of the file to checksum. |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [checksum] where:

     |checksum| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::compute-checksum &form entry)))

(defmacro is-uma-enabled
  "Is UMA enabled?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-uma-enabled &form)))

(defmacro set-entry-tag
  "Sets a tag on a file or a directory. Only Drive files are supported.

     |entry| - ?
     |visibility| - ?
     |key| - ?
     |value| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry visibility key value] (gen-call :function ::set-entry-tag &form entry visibility key value)))

(defmacro is-piex-loader-enabled
  "Returns if Piex loader is enabled.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-piex-loader-enabled &form)))

(defmacro get-providing-extensions
  "Returns list of available providing extensions.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [extensions] where:

     |extensions| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-providing-extensions &form)))

(defmacro add-provided-file-system
  "Requests adding a new provided file system. If not possible, then an error via chrome.runtime.lastError is returned.

     |extension-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id] (gen-call :function ::add-provided-file-system &form extension-id)))

(defmacro configure-volume
  "Requests configuring an existing volume. If not possible, then returns an error via chrome.runtime.lastError.

     |volume-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id] (gen-call :function ::configure-volume &form volume-id)))

(defmacro get-custom-actions
  "Requests list of custom actions for the specified entries. If not possible, then an error via chrome.runtime.lastError is
   returned.

     |entries| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [actions] where:

     |actions| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::get-custom-actions &form entries)))

(defmacro execute-custom-action
  "Executes a custom action for a set of entries. If not possible, then an error via chrome.runtime.lastError is returned.

     |entries| - ?
     |action-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries action-id] (gen-call :function ::execute-custom-action &form entries action-id)))

(defmacro get-directory-size
  "Get the total size of a directory. |entry| Entry of the target directory. |callback

     |entry| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [size] where:

     |size| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-directory-size &form entry)))

(defmacro get-recent-files
  "Gets recently modified files across file systems.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-recent-files &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-mount-completed-events
  "
   Events will be put on the |channel| with signature [::on-mount-completed [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-mount-completed &form channel args)))

(defmacro tap-on-file-transfers-updated-events
  "
   Events will be put on the |channel| with signature [::on-file-transfers-updated [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-file-transfers-updated &form channel args)))

(defmacro tap-on-copy-progress-events
  "
   Events will be put on the |channel| with signature [::on-copy-progress [copy-id status]] where:

     |copy-id| - ?
     |status| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-copy-progress &form channel args)))

(defmacro tap-on-directory-changed-events
  "
   Events will be put on the |channel| with signature [::on-directory-changed [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-directory-changed &form channel args)))

(defmacro tap-on-preferences-changed-events
  "
   Events will be put on the |channel| with signature [::on-preferences-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-preferences-changed &form channel args)))

(defmacro tap-on-drive-connection-status-changed-events
  "
   Events will be put on the |channel| with signature [::on-drive-connection-status-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-drive-connection-status-changed &form channel args)))

(defmacro tap-on-device-changed-events
  "
   Events will be put on the |channel| with signature [::on-device-changed [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-device-changed &form channel args)))

(defmacro tap-on-drive-sync-error-events
  "
   Events will be put on the |channel| with signature [::on-drive-sync-error [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-drive-sync-error &form channel args)))

(defmacro tap-on-apps-updated-events
  "
   Events will be put on the |channel| with signature [::on-apps-updated []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-apps-updated &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.file-manager-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileManagerPrivate",
   :since "39",
   :functions
   [{:id ::logout-user-for-reauthentication, :name "logoutUserForReauthentication"}
    {:id ::cancel-dialog, :name "cancelDialog"}
    {:id ::execute-task,
     :name "executeTask",
     :callback? true,
     :params
     [{:name "task-id", :type "string"}
      {:name "entries", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::set-default-task,
     :name "setDefaultTask",
     :callback? true,
     :params
     [{:name "task-id", :type "string"}
      {:name "entries", :type "[array-of-objects]"}
      {:name "mime-types", :type "[array-of-strings]"}
      {:name "callback", :type :callback}]}
    {:id ::get-file-tasks,
     :name "getFileTasks",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "tasks", :type "[array-of-objects]"}]}}]}
    {:id ::get-mime-type,
     :name "getMimeType",
     :since "41",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::add-file-watch,
     :name "addFileWatch",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::remove-file-watch,
     :name "removeFileWatch",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::enable-external-file-scheme, :name "enableExternalFileScheme", :since "43"}
    {:id ::grant-access,
     :name "grantAccess",
     :since "43",
     :callback? true,
     :params [{:name "entry-urls", :type "[array-of-strings]"} {:name "callback", :type :callback}]}
    {:id ::select-files,
     :name "selectFiles",
     :callback? true,
     :params
     [{:name "selected-paths", :type "[array-of-strings]"}
      {:name "should-return-local-path", :type "boolean"}
      {:name "callback", :type :callback}]}
    {:id ::select-file,
     :name "selectFile",
     :callback? true,
     :params
     [{:name "selected-path", :type "string"}
      {:name "index", :type "integer"}
      {:name "for-opening", :type "boolean"}
      {:name "should-return-local-path", :type "boolean"}
      {:name "callback", :type :callback}]}
    {:id ::get-entry-properties,
     :name "getEntryProperties",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-Entrys]"}
      {:name "names", :type "[array-of-unknown-types]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "entry-properties", :type "[array-of-objects]"}]}}]}
    {:id ::pin-drive-file,
     :name "pinDriveFile",
     :callback? true,
     :params [{:name "entry", :type "object"} {:name "pin", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::resolve-isolated-entries,
     :name "resolveIsolatedEntries",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-Entrys]"}
      {:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-Entrys]"}]}}]}
    {:id ::add-mount,
     :name "addMount",
     :callback? true,
     :params
     [{:name "source", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "source-path", :type "string"}]}}]}
    {:id ::remove-mount, :name "removeMount", :params [{:name "volume-id", :type "string"}]}
    {:id ::get-volume-metadata-list,
     :name "getVolumeMetadataList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "volume-metadata-list", :type "[array-of-fileManagerPrivate.VolumeMetadatas]"}]}}]}
    {:id ::cancel-file-transfers,
     :name "cancelFileTransfers",
     :callback? true,
     :params [{:name "entries", :type "[array-of-objects]"} {:name "callback", :type :callback}]}
    {:id ::cancel-all-file-transfers,
     :name "cancelAllFileTransfers",
     :since "46",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::start-copy,
     :name "startCopy",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "parent-entry", :type "object"}
      {:name "new-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "copy-id", :type "integer"}]}}]}
    {:id ::cancel-copy,
     :name "cancelCopy",
     :callback? true,
     :params [{:name "copy-id", :type "integer"} {:name "callback", :type :callback}]}
    {:id ::get-size-stats,
     :name "getSizeStats",
     :callback? true,
     :params
     [{:name "volume-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "size-stats", :optional? true, :type "object"}]}}]}
    {:id ::format-volume, :name "formatVolume", :params [{:name "volume-id", :type "string"}]}
    {:id ::get-preferences,
     :name "getPreferences",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::set-preferences, :name "setPreferences", :params [{:name "change-info", :type "object"}]}
    {:id ::search-drive,
     :name "searchDrive",
     :callback? true,
     :params
     [{:name "search-params", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "entries", :type "[array-of-Entrys]"} {:name "next-feed", :type "string"}]}}]}
    {:id ::search-drive-metadata,
     :name "searchDriveMetadata",
     :callback? true,
     :params
     [{:name "search-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "results", :type "[array-of-objects]"}]}}]}
    {:id ::search-files-by-hashes,
     :name "searchFilesByHashes",
     :since "42",
     :callback? true,
     :params
     [{:name "volume-id", :type "string"}
      {:name "hash-list", :type "[array-of-strings]"}
      {:name "callback", :type :callback, :callback {:params [{:name "urls", :type "object"}]}}]}
    {:id ::zip-selection,
     :name "zipSelection",
     :callback? true,
     :params
     [{:name "parent-entry", :type "object"}
      {:name "entries", :type "[array-of-objects]"}
      {:name "dest-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::get-drive-connection-state,
     :name "getDriveConnectionState",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::validate-path-name-length,
     :name "validatePathNameLength",
     :callback? true,
     :params
     [{:name "parent-entry", :type "object"}
      {:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::zoom, :name "zoom", :params [{:name "operation", :type "unknown-type"}]}
    {:id ::request-access-token,
     :name "requestAccessToken",
     :callback? true,
     :params
     [{:name "refresh", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "access-token", :type "string"}]}}]}
    {:id ::request-web-store-access-token,
     :name "requestWebStoreAccessToken",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "access-token", :type "string"}]}}]}
    {:id ::get-share-url,
     :name "getShareUrl",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "url", :type "string"}]}}]}
    {:id ::get-download-url,
     :name "getDownloadUrl",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "url", :type "string"}]}}]}
    {:id ::request-drive-share,
     :name "requestDriveShare",
     :callback? true,
     :params
     [{:name "entry", :type "object"} {:name "share-type", :type "unknown-type"} {:name "callback", :type :callback}]}
    {:id ::get-profiles,
     :name "getProfiles",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "profiles", :type "[array-of-fileManagerPrivate.ProfileInfos]"}
         {:name "running-profile", :type "string"}
         {:name "display-profile", :type "string"}]}}]}
    {:id ::open-inspector, :name "openInspector", :params [{:name "type", :type "unknown-type"}]}
    {:id ::compute-checksum,
     :name "computeChecksum",
     :since "41",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "checksum", :type "string"}]}}]}
    {:id ::is-uma-enabled,
     :name "isUMAEnabled",
     :since "42",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::set-entry-tag,
     :name "setEntryTag",
     :since "43",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "visibility", :type "unknown-type"}
      {:name "key", :type "string"}
      {:name "value", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::is-piex-loader-enabled,
     :name "isPiexLoaderEnabled",
     :since "43",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::get-providing-extensions,
     :name "getProvidingExtensions",
     :since "44",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "extensions", :type "[array-of-objects]"}]}}]}
    {:id ::add-provided-file-system,
     :name "addProvidedFileSystem",
     :since "44",
     :callback? true,
     :params [{:name "extension-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::configure-volume,
     :name "configureVolume",
     :since "44",
     :callback? true,
     :params [{:name "volume-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::get-custom-actions,
     :name "getCustomActions",
     :since "47",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-objects]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "actions", :type "[array-of-fileSystemProvider.Actions]"}]}}]}
    {:id ::execute-custom-action,
     :name "executeCustomAction",
     :since "47",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-objects]"}
      {:name "action-id", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::get-directory-size,
     :name "getDirectorySize",
     :since "55",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "size", :type "double"}]}}]}
    {:id ::get-recent-files,
     :name "getRecentFiles",
     :since "master",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-Entrys]"}]}}]}],
   :events
   [{:id ::on-mount-completed, :name "onMountCompleted", :params [{:name "event", :type "object"}]}
    {:id ::on-file-transfers-updated, :name "onFileTransfersUpdated", :params [{:name "event", :type "object"}]}
    {:id ::on-copy-progress,
     :name "onCopyProgress",
     :params [{:name "copy-id", :type "integer"} {:name "status", :type "object"}]}
    {:id ::on-directory-changed, :name "onDirectoryChanged", :params [{:name "event", :type "object"}]}
    {:id ::on-preferences-changed, :name "onPreferencesChanged"}
    {:id ::on-drive-connection-status-changed, :name "onDriveConnectionStatusChanged"}
    {:id ::on-device-changed, :name "onDeviceChanged", :params [{:name "event", :type "object"}]}
    {:id ::on-drive-sync-error, :name "onDriveSyncError", :params [{:name "event", :type "object"}]}
    {:id ::on-apps-updated, :name "onAppsUpdated", :since "57"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))