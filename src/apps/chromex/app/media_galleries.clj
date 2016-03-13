(ns chromex.app.media-galleries
  "Use the chrome.mediaGalleries API to access media files (audio,
   images, video) from the user's local disks (with the user's consent).
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/mediaGalleries"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-media-file-systems
  "Get the media galleries configured in this user agent. If none are configured or available, the callback will receive an
   empty array.
   
     |details| - See https://developer.chrome.com/extensions/mediaGalleries#property-getMediaFileSystems-details.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [mediaFileSystems] where:
   
     |mediaFileSystems| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-mediaFileSystems.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-getMediaFileSystems."
  ([details #_callback] (gen-call :function ::get-media-file-systems &form details))
  ([] `(get-media-file-systems :omit)))

(defmacro add-user-selected-folder
  "Present a directory picker to the user and add the selected directory as a gallery. If the user cancels the picker,
   selectedFileSystemName will be empty. A user gesture is required for the dialog to display. Without a user gesture, the
   callback will run as though the user canceled.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [mediaFileSystems selectedFileSystemName] where:
   
     |mediaFileSystems| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-mediaFileSystems.
     |selectedFileSystemName| - See
   
   https://developer.chrome.com/extensions/mediaGalleries#property-callback-selectedFileSystemName
                                .
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-addUserSelectedFolder."
  ([#_callback] (gen-call :function ::add-user-selected-folder &form)))

(defmacro drop-permission-for-media-file-system
  "Give up access to a given media gallery.
   
     |galleryId| - See
                   https://developer.chrome.com/extensions/mediaGalleries#property-dropPermissionForMediaFileSystem-galleryId
                   .
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-dropPermissionForMediaFileSystem."
  ([gallery-id #_callback] (gen-call :function ::drop-permission-for-media-file-system &form gallery-id)))

(defmacro start-media-scan
  "Start a scan of the user's hard disks for directories containing media. The scan may take a long time so progress and
   completion is communicated by events. No permission is granted as a result of the scan, see addScanResults.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-startMediaScan."
  ([] (gen-call :function ::start-media-scan &form)))

(defmacro cancel-media-scan
  "Cancel any pending media scan.  Well behaved apps should provide a way for the user to cancel scans they start.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-cancelMediaScan."
  ([] (gen-call :function ::cancel-media-scan &form)))

(defmacro add-scan-results
  "Show the user the scan results and let them add any or all of them as galleries. This should be used after the 'finish'
   onScanProgress() event has happened. All galleries the app has access to are returned, not just the newly added galleries.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [mediaFileSystems] where:
   
     |mediaFileSystems| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-mediaFileSystems.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-addScanResults."
  ([#_callback] (gen-call :function ::add-scan-results &form)))

(defmacro get-media-file-system-metadata
  "Get metadata about a specific media file system.
   
     |mediaFileSystem| - See
                         https://developer.chrome.com/extensions/mediaGalleries#property-getMediaFileSystemMetadata-mediaFile
                         System.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-getMediaFileSystemMetadata."
  ([media-file-system] (gen-call :function ::get-media-file-system-metadata &form media-file-system)))

(defmacro get-all-media-file-system-metadata
  "Get metadata for all available media galleries.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [metadata] where:
   
     |metadata| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-metadata.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-getAllMediaFileSystemMetadata."
  ([#_callback] (gen-call :function ::get-all-media-file-system-metadata &form)))

(defmacro get-metadata
  "Gets the media-specific metadata for a media file. This should work for files in media galleries as well as other DOM
   filesystems.
   
     |mediaFile| - See https://developer.chrome.com/extensions/mediaGalleries#property-getMetadata-mediaFile.
     |options| - See https://developer.chrome.com/extensions/mediaGalleries#property-getMetadata-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [metadata] where:
   
     |metadata| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-metadata.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-getMetadata."
  ([media-file options #_callback] (gen-call :function ::get-metadata &form media-file options))
  ([media-file] `(get-metadata ~media-file :omit)))

(defmacro add-gallery-watch
  "Adds a gallery watch for the gallery with the specified gallery ID. The given callback is then fired with a success or
   failure result.
   
     |galleryId| - See https://developer.chrome.com/extensions/mediaGalleries#property-addGalleryWatch-galleryId.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-result.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-addGalleryWatch."
  ([gallery-id #_callback] (gen-call :function ::add-gallery-watch &form gallery-id)))

(defmacro remove-gallery-watch
  "Removes a gallery watch for the gallery with the specified gallery ID.
   
     |galleryId| - See https://developer.chrome.com/extensions/mediaGalleries#property-removeGalleryWatch-galleryId.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-removeGalleryWatch."
  ([gallery-id] (gen-call :function ::remove-gallery-watch &form gallery-id)))

(defmacro get-all-gallery-watch
  "Notifies which galleries are being watched via the given callback.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [galleryIds] where:
   
     |galleryIds| - See https://developer.chrome.com/extensions/mediaGalleries#property-callback-galleryIds.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-getAllGalleryWatch."
  ([#_callback] (gen-call :function ::get-all-gallery-watch &form)))

(defmacro remove-all-gallery-watch
  "Removes all gallery watches.
   
   See https://developer.chrome.com/extensions/mediaGalleries#method-removeAllGalleryWatch."
  ([] (gen-call :function ::remove-all-gallery-watch &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-gallery-changed-events
  "Fired when a media gallery is changed or a gallery watch is dropped.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/mediaGalleries#event-onGalleryChanged."
  ([channel & args] (apply gen-call :event ::on-gallery-changed &form channel args)))

(defmacro tap-on-scan-progress-events
  "The pending media scan has changed state. See details for more information.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/mediaGalleries#event-onScanProgress."
  ([channel & args] (apply gen-call :event ::on-scan-progress &form channel args)))

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
  {:namespace "chrome.mediaGalleries",
   :since "23",
   :functions
   [{:id ::get-media-file-systems,
     :name "getMediaFileSystems",
     :callback? true,
     :params
     [{:name "details", :optional? true, :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "media-file-systems", :type "[array-of-DOMFileSystems]"}]}}]}
    {:id ::add-user-selected-folder,
     :name "addUserSelectedFolder",
     :since "34",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "media-file-systems", :type "[array-of-DOMFileSystems]"}
         {:name "selected-file-system-name", :type "string"}]}}]}
    {:id ::drop-permission-for-media-file-system,
     :name "dropPermissionForMediaFileSystem",
     :since "master",
     :deprecated "The user can manually drop access to galleries\\n    via the permissions dialog.",
     :callback? true,
     :params [{:name "gallery-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-media-scan,
     :name "startMediaScan",
     :since "master",
     :deprecated "The mediaGalleries API no longer supports scanning."}
    {:id ::cancel-media-scan,
     :name "cancelMediaScan",
     :since "master",
     :deprecated "The mediaGalleries API no longer supports scanning."}
    {:id ::add-scan-results,
     :name "addScanResults",
     :since "master",
     :deprecated "The mediaGalleries API no longer supports scanning.",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "media-file-systems", :type "[array-of-DOMFileSystems]"}]}}]}
    {:id ::get-media-file-system-metadata,
     :name "getMediaFileSystemMetadata",
     :since "26",
     :return-type "object",
     :params [{:name "media-file-system", :type "DOMFileSystem"}]}
    {:id ::get-all-media-file-system-metadata,
     :name "getAllMediaFileSystemMetadata",
     :since "master",
     :deprecated "Use getMediaFileSystemMetadata instead.",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "metadata", :type "[array-of-objects]"}]}}]}
    {:id ::get-metadata,
     :name "getMetadata",
     :since "38",
     :callback? true,
     :params
     [{:name "media-file", :type "Blob"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "metadata", :type "object"}]}}]}
    {:id ::add-gallery-watch,
     :name "addGalleryWatch",
     :since "39",
     :callback? true,
     :params
     [{:name "gallery-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::remove-gallery-watch,
     :name "removeGalleryWatch",
     :since "39",
     :params [{:name "gallery-id", :type "string"}]}
    {:id ::get-all-gallery-watch,
     :name "getAllGalleryWatch",
     :since "master",
     :deprecated "Applications should store their own gallery watches\\n    as they are added.",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "gallery-ids", :type "[array-of-strings]"}]}}]}
    {:id ::remove-all-gallery-watch,
     :name "removeAllGalleryWatch",
     :since "master",
     :deprecated "Use removeGalleryWatch instead."}],
   :events
   [{:id ::on-gallery-changed, :name "onGalleryChanged", :since "38", :params [{:name "details", :type "object"}]}
    {:id ::on-scan-progress,
     :name "onScanProgress",
     :since "master",
     :deprecated "The mediaGalleries API no longer supports scanning.",
     :params [{:name "details", :type "object"}]}]})

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