(ns chromex.ext.file-manager-private
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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id entries] (gen-call :function ::execute-task &form task-id entries)))

(defmacro set-default-task
  "Sets the default task for the supplied MIME types and path extensions. Lists of MIME types and URLs may contain duplicates.
   Additionally, the list of MIME types can be empty. |taskId| The unique identifier of task to mark as default. |entries

   Array of selected entries to extract path extensions from. |mimeTypes| Array of selected file MIME types. |callback

     |task-id| - ?
     |entries| - ?
     |mime-types| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id entries mime-types] (gen-call :function ::set-default-task &form task-id entries mime-types)))

(defmacro get-file-tasks
  "Gets the list of tasks that can be performed over selected files. |entries| Array of selected entries |callback

     |entries| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tasks] where:

     |tasks| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::get-file-tasks &form entries)))

(defmacro get-mime-type
  "Gets the MIME type of an entry. |entry| The entry to be checked. |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-mime-type &form entry)))

(defmacro get-content-mime-type
  "Gets the content sniffed MIME type of a file. |fileEntry| The file entry to be checked. |callback

     |file-entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([file-entry] (gen-call :function ::get-content-mime-type &form file-entry)))

(defmacro get-content-metadata
  "Gets metadata from an Audio or Video file. |fileEntry| The file entry to be checked. |mimeType| Content sniffed mimeType of
   the file. |includeImages| False returns metadata tags only. True returns     metadata tags and metadata (thumbnail) images.
   |callback

     |file-entry| - ?
     |mime-type| - ?
     |include-images| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([file-entry mime-type include-images] (gen-call :function ::get-content-metadata &form file-entry mime-type include-images)))

(defmacro get-strings
  "Gets localized strings and initialization data. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-strings &form)))

(defmacro add-file-watch
  "Adds file watch. |entry| Entry to watch |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::add-file-watch &form entry)))

(defmacro remove-file-watch
  "Removes file watch. |entry| Watched entry |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry-urls] (gen-call :function ::grant-access &form entry-urls)))

(defmacro select-files
  "Selects multiple files. |selectedPaths| Array of selected paths |shouldReturnLocalPath| true if paths need to be resolved
   to local paths. |callback

     |selected-paths| - ?
     |should-return-local-path| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([selected-paths should-return-local-path] (gen-call :function ::select-files &form selected-paths should-return-local-path)))

(defmacro select-file
  "Selects a file. |selectedPath| A selected path |index| Index of Filter |forOpening| true if paths are selected for opening.
   false if for saving. |shouldReturnLocalPath| true if paths need to be resolved to local paths. |callback

     |selected-path| - ?
     |index| - ?
     |for-opening| - ?
     |should-return-local-path| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([selected-path index for-opening should-return-local-path] (gen-call :function ::select-file &form selected-path index for-opening should-return-local-path)))

(defmacro get-entry-properties
  "Requests additional properties for files. |entries| list of entries |names| list of requested properties by their names.
   |callback| Completion callback. May return less than requested properties     if some are not available. In the same time,
   it can return properties     which were not requested (if it's cheap to compute them).

     |entries| - ?
     |names| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entry-properties] where:

     |entry-properties| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries names] (gen-call :function ::get-entry-properties &form entries names)))

(defmacro pin-drive-file
  "Pins/unpins a Drive file in the cache. |entry| Entry to pin/unpin. |pin| Pass true to pin the file. |callback| Completion
   callback. 'runtime.lastError' will be set if     there was an error.

     |entry| - ?
     |pin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry pin] (gen-call :function ::pin-drive-file &form entry pin)))

(defmacro resolve-isolated-entries
  "Resolves entries in the isolated file system and returns corresponding entries in the external file system mounted to
   Chrome OS file manager backend. If resolving entry fails, the entry will be just ignored and the corresponding entry does
   not appear in the result.

     |entries| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::resolve-isolated-entries &form entries)))

(defmacro add-mount
  "Mounts a resource or a file. |source| Mount point source. For compressed files it is the relative file     path within the
   external file system. |password| Optional password to decrypt the file. |callback| Callback called with the source path of
   the mount.

     |source| - ?
     |password| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [source-path] where:

     |source-path| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([source password] (gen-call :function ::add-mount &form source password))
  ([source] `(add-mount ~source :omit)))

(defmacro remove-mount
  "Unmounts a mounted resource. |volumeId| An ID of the volume.

     |volume-id| - ?"
  ([volume-id] (gen-call :function ::remove-mount &form volume-id)))

(defmacro get-volume-metadata-list
  "Get the list of mounted volumes. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [volume-metadata-list] where:

     |volume-metadata-list| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-volume-metadata-list &form)))

(defmacro copy-image-to-clipboard
  "Copies an image to the system clipboard. |entry| Entry of the image to copy to the system clipboard.

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::copy-image-to-clipboard &form entry)))

(defmacro start-copy
  "Starts to copy an entry. If the source is a directory, the copy is done recursively. |entry| Entry of the source entry to
   be copied. |parentEntry| Entry for the destination (parent) directory. |newName| Name of the new entry. It must not contain
   '/'. |callback| Completion callback.

     |entry| - ?
     |parent-entry| - ?
     |new-name| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [copy-id] where:

     |copy-id| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry parent-entry new-name] (gen-call :function ::start-copy &form entry parent-entry new-name)))

(defmacro cancel-copy
  "Cancels the running copy task. |copyId| ID of the copy task to be cancelled. |callback| Completion callback of the cancel.

     |copy-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([copy-id] (gen-call :function ::cancel-copy &form copy-id)))

(defmacro get-size-stats
  "Retrieves total and remaining size of a mount point. |volumeId| ID of the volume to be checked. |callback

     |volume-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [size-stats] where:

     |size-stats| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id] (gen-call :function ::get-size-stats &form volume-id)))

(defmacro format-volume
  "Formats a mounted volume. |volumeId| ID of the volume to be formatted. |filesystem| Filesystem type to be formatted to.
   |volumeLabel| Label of the drive after formatting.

     |volume-id| - ?
     |filesystem| - ?
     |volume-label| - ?"
  ([volume-id filesystem volume-label] (gen-call :function ::format-volume &form volume-id filesystem volume-label)))

(defmacro single-partition-format
  "Deletes partitions of removable device, creates a new partition and format it. |deviceStoragePath| Storage path of the
   device to be formatted. |filesystem| Filesystem type to be formatted to. |volumeLabel| Label of the drive after formatting.

     |device-storage-path| - ?
     |filesystem| - ?
     |volume-label| - ?"
  ([device-storage-path filesystem volume-label] (gen-call :function ::single-partition-format &form device-storage-path filesystem volume-label)))

(defmacro rename-volume
  "Renames a mounted volume. |volumeId| ID of the volume to be renamed. |newName| New name of the target volume.

     |volume-id| - ?
     |new-name| - ?"
  ([volume-id new-name] (gen-call :function ::rename-volume &form volume-id new-name)))

(defmacro get-preferences
  "Retrieves file manager preferences. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-preferences &form)))

(defmacro set-preferences
  "Sets file manager preferences. |changeInfo

     |change-info| - ?"
  ([change-info] (gen-call :function ::set-preferences &form change-info)))

(defmacro search-drive
  "Performs drive content search. |searchParams| |callback

     |search-params| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries next-feed] where:

     |entries| - ?
     |next-feed| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([search-params] (gen-call :function ::search-drive &form search-params)))

(defmacro search-drive-metadata
  "Performs drive metadata search. |searchParams| |callback

     |search-params| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([search-params] (gen-call :function ::search-drive-metadata &form search-params)))

(defmacro search-files-by-hashes
  "Search files in the volume having |volumeId| by using |hashList|.

     |volume-id| - ?
     |hash-list| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [paths] where:

     |paths| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id hash-list] (gen-call :function ::search-files-by-hashes &form volume-id hash-list)))

(defmacro search-files
  "Search files in My Files.

     |search-params| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([search-params] (gen-call :function ::search-files &form search-params)))

(defmacro zip-selection
  "Create a zip file for the selected files. |parentEntry| Entry of the directory containing the selected files. |entries

   Entries of the selected files. The files must be under the     directory specified by |parentEntry|. |destName| Name of the
   destination zip file. The zip file will be created     under the directory specified by |parentEntry|. |callback

     |entries| - ?
     |parent-entry| - ?
     |dest-name| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries parent-entry dest-name] (gen-call :function ::zip-selection &form entries parent-entry dest-name)))

(defmacro get-drive-connection-state
  "Retrieves the state of the current drive connection. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-drive-connection-state &form)))

(defmacro validate-path-name-length
  "Checks whether the path name length fits in the limit of the filesystem. |parentEntry| The entry of the parent directory
   entry. |name| The name of the file. |callback| Called back when the check is finished.

     |parent-entry| - ?
     |name| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-entry name] (gen-call :function ::validate-path-name-length &form parent-entry name)))

(defmacro zoom
  "Changes the zoom factor of the Files app. |operation| Zooming mode.

     |operation| - ?"
  ([operation] (gen-call :function ::zoom &form operation)))

(defmacro request-web-store-access-token
  "Requests a Webstore API OAuth2 access token. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [access-token] where:

     |access-token| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::request-web-store-access-token &form)))

(defmacro get-download-url
  "Requests a download url to download the file contents. |entry| The entry to download. |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [url] where:

     |url| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-download-url &form entry)))

(defmacro get-profiles
  "Obtains a list of profiles that are logged-in.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [profiles running-profile display-profile] where:

     |profiles| - ?
     |running-profile| - ?
     |display-profile| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-profiles &form)))

(defmacro open-inspector
  "Opens inspector window. |type| InspectionType which specifies how to open inspector.

     |type| - ?"
  ([type] (gen-call :function ::open-inspector &form type)))

(defmacro open-settings-subpage
  "Opens page in Settings window. |sub_page| Name of a sub_page to show.

     |sub-page| - ?"
  ([sub-page] (gen-call :function ::open-settings-subpage &form sub-page)))

(defmacro compute-checksum
  "Computes an MD5 checksum for the given file. |entry| The entry of the file to checksum. |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [checksum] where:

     |checksum| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::compute-checksum &form entry)))

(defmacro get-providers
  "Returns list of available providers.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [extensions] where:

     |extensions| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-providers &form)))

(defmacro add-provided-file-system
  "Requests adding a new provided file system. On failure, sets 'runtime.lastError'.

     |provider-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([provider-id] (gen-call :function ::add-provided-file-system &form provider-id)))

(defmacro configure-volume
  "Requests configuring an existing volume. On failure, sets 'runtime.lastError'.

     |volume-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([volume-id] (gen-call :function ::configure-volume &form volume-id)))

(defmacro get-custom-actions
  "Requests list of custom actions for the specified entries. On failure, sets 'runtime.lastError'.

     |entries| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [actions] where:

     |actions| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::get-custom-actions &form entries)))

(defmacro execute-custom-action
  "Executes a custom action for a set of entries. On failure, sets 'runtime.lastError'.

     |entries| - ?
     |action-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries action-id] (gen-call :function ::execute-custom-action &form entries action-id)))

(defmacro get-directory-size
  "Get the total size of a directory. |entry| Entry of the target directory. |callback

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [size] where:

     |size| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-directory-size &form entry)))

(defmacro get-recent-files
  "Gets recently modified files across file systems. |restriction| Flag to restrict sources of recent files. |fileType

   Requested file type to filter recent files. |callback

     |restriction| - ?
     |file-type| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([restriction file-type] (gen-call :function ::get-recent-files &form restriction file-type)))

(defmacro mount-crostini
  "Starts and mounts crostini container. |callback

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::mount-crostini &form)))

(defmacro share-paths-with-crostini
  "Shares paths with crostini container. |vmName| VM to share path with. |entries| Entries of the files or directories to
   share. |persist| If true, shares will persist across restarts. |callback

     |vm-name| - ?
     |entries| - ?
     |persist| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([vm-name entries persist] (gen-call :function ::share-paths-with-crostini &form vm-name entries persist)))

(defmacro unshare-path-with-crostini
  "Unshares path with crostini container. |vmName| VM to unshare path from. |entry| Entry of the file or directory to unshare.
   |callback

     |vm-name| - ?
     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([vm-name entry] (gen-call :function ::unshare-path-with-crostini &form vm-name entry)))

(defmacro get-crostini-shared-paths
  "Returns list of paths shared with crostini container. |observeFirstForSession| If true, callback provides whether this is
   the |vmName| VM to get shared paths of. first time this function has been called with observeFirstForSession true.

     |observe-first-for-session| - ?
     |vm-name| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries first-for-session] where:

     |entries| - ?
     |first-for-session| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([observe-first-for-session vm-name] (gen-call :function ::get-crostini-shared-paths &form observe-first-for-session vm-name)))

(defmacro get-linux-package-info
  "Requests information about a Linux package. |entry| is a .deb file.

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [linux-package-info] where:

     |linux-package-info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::get-linux-package-info &form entry)))

(defmacro install-linux-package
  "Starts installation of a Linux package.

     |entry| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response failure-reason] where:

     |response| - ?
     |failure-reason| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry] (gen-call :function ::install-linux-package &form entry)))

(defmacro import-crostini-image
  "Imports a Crostini Image File (.tini). This overrides the existing Linux apps and files.

     |entry| - ?"
  ([entry] (gen-call :function ::import-crostini-image &form entry)))

(defmacro get-drive-thumbnail
  "For a file in DriveFS, retrieves its thumbnail. If |cropToSquare| is true, returns a thumbnail appropriate for file list or
   grid views; otherwise, returns a thumbnail appropriate for quickview.

     |entry| - ?
     |crop-to-square| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [thumbnail-data-url] where:

     |thumbnail-data-url| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry crop-to-square] (gen-call :function ::get-drive-thumbnail &form entry crop-to-square)))

(defmacro get-pdf-thumbnail
  "For a local PDF file, retrieves its thumbnail with a given |width| and |height|.

     |entry| - ?
     |width| - ?
     |height| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [thumbnail-data-url] where:

     |thumbnail-data-url| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry width height] (gen-call :function ::get-pdf-thumbnail &form entry width height)))

(defmacro get-arc-documents-provider-thumbnail
  "Retrieves a thumbnail of an ARC DocumentsProvider file, closely matching the hinted size specified by |widthHint| and
   |heightHint|, but not necessarily the exact size. |callback| is called with thumbnail data encoded as a data URL. If the
   document does not support thumbnails, |callback| is called with an empty string. Note: The thumbnail data may originate
   from third-party application code, and is untrustworthy (Security).

     |entry| - ?
     |width-hint| - ?
     |height-hint| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [thumbnail-data-url] where:

     |thumbnail-data-url| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entry width-hint height-hint] (gen-call :function ::get-arc-documents-provider-thumbnail &form entry width-hint height-hint)))

(defmacro detect-character-encoding
  "Returns a guessed character encoding of a hex-encoded string. Every 2 characters of |bytes| represent one byte by 2-digit
   hexadecimal number. The result is preferred MIME name of the detected character encoding system. It is slightly different
   from IANA name. See third_party/ced/src/util/encodings/encodings.cc Returns an empty string if failed.

     |bytes| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([bytes] (gen-call :function ::detect-character-encoding &form bytes)))

(defmacro get-android-picker-apps
  "Returns a list of Android picker apps to be shown in file selector.

     |extensions| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [apps] where:

     |apps| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extensions] (gen-call :function ::get-android-picker-apps &form extensions)))

(defmacro select-android-picker-app
  "Called when the user selects an Android picker app in file selector.

     |android-app| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([android-app] (gen-call :function ::select-android-picker-app &form android-app)))

(defmacro sharesheet-has-targets
  "Return true if sharesheet contains share targets for entries. |entries| Array of selected entries |callback| is called with
   error in case of failure and with no arguments if successfully launched the Sharesheet dialog, but before user has finished
   the sharing.

     |entries| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::sharesheet-has-targets &form entries)))

(defmacro invoke-sharesheet
  "Invoke Sharesheet for selected files. |entries| Array of selected entries. |callback

     |entries| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries] (gen-call :function ::invoke-sharesheet &form entries)))

(defmacro toggle-added-to-holding-space
  "Adds or removes a list of entries to temporary holding space. Any entries whose current holding space state matches the
   intended state will be skipped. |entries| The list of entries whose holding space needs to be updated. |add| Whether items
   should be added or removed from the holding space. |callback| Completion callback.

     |entries| - ?
     |added| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([entries added] (gen-call :function ::toggle-added-to-holding-space &form entries added)))

(defmacro get-holding-space-state
  "Retrieves the current holding space state, for example the list of items the holding space currently contains. |callback

   The result callback.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [state] where:

     |state| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-holding-space-state &form)))

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

(defmacro tap-on-crostini-changed-events
  "
   Events will be put on the |channel| with signature [::on-crostini-changed [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-crostini-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.file-manager-private namespace."
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
      {:name "entries", :since "46", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::set-default-task,
     :name "setDefaultTask",
     :callback? true,
     :params
     [{:name "task-id", :type "string"}
      {:name "entries", :since "46", :type "[array-of-objects]"}
      {:name "mime-types", :type "[array-of-strings]"}
      {:name "callback", :type :callback}]}
    {:id ::get-file-tasks,
     :name "getFileTasks",
     :callback? true,
     :params
     [{:name "entries", :since "46", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "tasks", :type "[array-of-objects]"}]}}]}
    {:id ::get-mime-type,
     :name "getMimeType",
     :since "41",
     :callback? true,
     :params
     [{:name "entry", :since "46", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-content-mime-type,
     :name "getContentMimeType",
     :since "86",
     :callback? true,
     :params
     [{:name "file-entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-content-metadata,
     :name "getContentMetadata",
     :since "86",
     :callback? true,
     :params
     [{:name "file-entry", :type "object"}
      {:name "mime-type", :type "string"}
      {:name "include-images", :since "future", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::add-file-watch,
     :name "addFileWatch",
     :callback? true,
     :params
     [{:name "entry", :since "45", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::remove-file-watch,
     :name "removeFileWatch",
     :callback? true,
     :params
     [{:name "entry", :since "45", :type "object"}
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
     [{:name "entries", :since "45", :type "[array-of-Entrys]"}
      {:name "names", :since "42", :type "[array-of-unknown-types]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "entry-properties", :type "[array-of-objects]"}]}}]}
    {:id ::pin-drive-file,
     :name "pinDriveFile",
     :callback? true,
     :params
     [{:name "entry", :since "46", :type "object"} {:name "pin", :type "boolean"} {:name "callback", :type :callback}]}
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
      {:name "password", :optional? true, :since "86", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "source-path", :type "string"}]}}]}
    {:id ::remove-mount, :name "removeMount", :params [{:name "volume-id", :type "string"}]}
    {:id ::get-volume-metadata-list,
     :name "getVolumeMetadataList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "volume-metadata-list", :type "[array-of-fileManagerPrivate.VolumeMetadatas]"}]}}]}
    {:id ::copy-image-to-clipboard,
     :name "copyImageToClipboard",
     :since "future",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::start-copy,
     :name "startCopy",
     :callback? true,
     :params
     [{:name "entry", :since "46", :type "object"}
      {:name "parent-entry", :since "46", :type "object"}
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
    {:id ::format-volume,
     :name "formatVolume",
     :params
     [{:name "volume-id", :type "string"}
      {:name "filesystem", :since "77", :type "fileManagerPrivate.FormatFileSystemType"}
      {:name "volume-label", :since "77", :type "string"}]}
    {:id ::single-partition-format,
     :name "singlePartitionFormat",
     :since "future",
     :params
     [{:name "device-storage-path", :type "string"}
      {:name "filesystem", :type "fileManagerPrivate.FormatFileSystemType"}
      {:name "volume-label", :type "string"}]}
    {:id ::rename-volume,
     :name "renameVolume",
     :since "62",
     :params [{:name "volume-id", :type "string"} {:name "new-name", :type "string"}]}
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
     [{:name "search-params", :type "fileManagerPrivate.SearchMetadataParams"}
      {:name "callback", :type :callback, :callback {:params [{:name "results", :type "[array-of-objects]"}]}}]}
    {:id ::search-files-by-hashes,
     :name "searchFilesByHashes",
     :since "42",
     :callback? true,
     :params
     [{:name "volume-id", :type "string"}
      {:name "hash-list", :type "[array-of-strings]"}
      {:name "callback", :type :callback, :callback {:params [{:name "paths", :type "object"}]}}]}
    {:id ::search-files,
     :name "searchFiles",
     :since "75",
     :callback? true,
     :params
     [{:name "search-params", :type "fileManagerPrivate.SearchMetadataParams"}
      {:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-Entrys]"}]}}]}
    {:id ::zip-selection,
     :name "zipSelection",
     :callback? true,
     :params
     [{:name "entries", :since "46", :type "[array-of-objects]"}
      {:name "parent-entry", :since "46", :type "object"}
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
     [{:name "parent-entry", :since "46", :type "object"}
      {:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::zoom, :name "zoom", :params [{:name "operation", :type "unknown-type"}]}
    {:id ::request-web-store-access-token,
     :name "requestWebStoreAccessToken",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "access-token", :type "string"}]}}]}
    {:id ::get-download-url,
     :name "getDownloadUrl",
     :callback? true,
     :params
     [{:name "entry", :since "46", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "url", :type "string"}]}}]}
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
    {:id ::open-settings-subpage,
     :name "openSettingsSubpage",
     :since "67",
     :params [{:name "sub-page", :type "string"}]}
    {:id ::compute-checksum,
     :name "computeChecksum",
     :since "41",
     :callback? true,
     :params
     [{:name "entry", :since "46", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "checksum", :type "string"}]}}]}
    {:id ::get-providers,
     :name "getProviders",
     :since "65",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "extensions", :type "[array-of-objects]"}]}}]}
    {:id ::add-provided-file-system,
     :name "addProvidedFileSystem",
     :since "44",
     :callback? true,
     :params [{:name "provider-id", :since "65", :type "string"} {:name "callback", :type :callback}]}
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
     :since "61",
     :callback? true,
     :params
     [{:name "restriction", :type "unknown-type"}
      {:name "file-type", :since "82", :type "unknown-type"}
      {:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-Entrys]"}]}}]}
    {:id ::mount-crostini,
     :name "mountCrostini",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::share-paths-with-crostini,
     :name "sharePathsWithCrostini",
     :since "72",
     :callback? true,
     :params
     [{:name "vm-name", :since "75", :type "string"}
      {:name "entries", :type "[array-of-objects]"}
      {:name "persist", :type "boolean"}
      {:name "callback", :type :callback}]}
    {:id ::unshare-path-with-crostini,
     :name "unsharePathWithCrostini",
     :since "73",
     :callback? true,
     :params
     [{:name "vm-name", :since "75", :type "string"}
      {:name "entry", :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::get-crostini-shared-paths,
     :name "getCrostiniSharedPaths",
     :since "71",
     :callback? true,
     :params
     [{:name "observe-first-for-session", :since "74", :type "boolean"}
      {:name "vm-name", :since "75", :type "string"}
      {:name "callback",
       :type :callback,
       :callback
       {:params [{:name "entries", :type "[array-of-Entrys]"} {:name "first-for-session", :type "boolean"}]}}]}
    {:id ::get-linux-package-info,
     :name "getLinuxPackageInfo",
     :since "72",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "linux-package-info", :type "object"}]}}]}
    {:id ::install-linux-package,
     :name "installLinuxPackage",
     :since "69",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "response", :type "unknown-type"} {:name "failure-reason", :type "string"}]}}]}
    {:id ::import-crostini-image, :name "importCrostiniImage", :since "78", :params [{:name "entry", :type "object"}]}
    {:id ::get-drive-thumbnail,
     :name "getDriveThumbnail",
     :since "future",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "crop-to-square", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "thumbnail-data-url", :type "string"}]}}]}
    {:id ::get-pdf-thumbnail,
     :name "getPdfThumbnail",
     :since "future",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "width", :type "integer"}
      {:name "height", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "thumbnail-data-url", :type "string"}]}}]}
    {:id ::get-arc-documents-provider-thumbnail,
     :name "getArcDocumentsProviderThumbnail",
     :since "master",
     :callback? true,
     :params
     [{:name "entry", :type "object"}
      {:name "width-hint", :type "integer"}
      {:name "height-hint", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "thumbnail-data-url", :type "string"}]}}]}
    {:id ::detect-character-encoding,
     :name "detectCharacterEncoding",
     :since "71",
     :callback? true,
     :return-type "string",
     :params
     [{:name "bytes", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-android-picker-apps,
     :name "getAndroidPickerApps",
     :since "76",
     :callback? true,
     :params
     [{:name "extensions", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "apps", :type "[array-of-fileManagerPrivate.AndroidApps]"}]}}]}
    {:id ::select-android-picker-app,
     :name "selectAndroidPickerApp",
     :since "76",
     :callback? true,
     :params [{:name "android-app", :type "fileManagerPrivate.AndroidApp"} {:name "callback", :type :callback}]}
    {:id ::sharesheet-has-targets,
     :name "sharesheetHasTargets",
     :since "86",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::invoke-sharesheet,
     :name "invokeSharesheet",
     :since "86",
     :callback? true,
     :params [{:name "entries", :type "[array-of-objects]"} {:name "callback", :type :callback}]}
    {:id ::toggle-added-to-holding-space,
     :name "toggleAddedToHoldingSpace",
     :since "future",
     :callback? true,
     :params
     [{:name "entries", :type "[array-of-Entrys]"}
      {:name "added", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-holding-space-state,
     :name "getHoldingSpaceState",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "state", :type "object"}]}}]}],
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
    {:id ::on-apps-updated, :name "onAppsUpdated", :since "57"}
    {:id ::on-crostini-changed, :name "onCrostiniChanged", :since "74", :params [{:name "event", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))