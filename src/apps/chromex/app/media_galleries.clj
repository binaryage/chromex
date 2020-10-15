(ns chromex.app.media-galleries
  "Use the chrome.mediaGalleries API to access media files (audio,
   images, video) from the user's local disks (with the user's consent).

     * available since Chrome 38
     * https://developer.chrome.com/apps/mediaGalleries"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-media-file-systems
  "Get the media galleries configured in this user agent. If none are configured or available, the callback will receive an
   empty array.

     |details| - https://developer.chrome.com/apps/mediaGalleries#property-getMediaFileSystems-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [media-file-systems] where:

     |media-file-systems| - https://developer.chrome.com/apps/mediaGalleries#property-callback-mediaFileSystems.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/mediaGalleries#method-getMediaFileSystems."
  ([details] (gen-call :function ::get-media-file-systems &form details))
  ([] `(get-media-file-systems :omit)))

(defmacro add-user-selected-folder
  "Present a directory picker to the user and add the selected directory as a gallery. If the user cancels the picker,
   selectedFileSystemName will be empty. A user gesture is required for the dialog to display. Without a user gesture, the
   callback will run as though the user canceled.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [media-file-systems selected-file-system-name] where:

     |media-file-systems| - https://developer.chrome.com/apps/mediaGalleries#property-callback-mediaFileSystems.
     |selected-file-system-name| - https://developer.chrome.com/apps/mediaGalleries#property-callback-selectedFileSystemName.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/mediaGalleries#method-addUserSelectedFolder."
  ([] (gen-call :function ::add-user-selected-folder &form)))

(defmacro get-media-file-system-metadata
  "Get metadata about a specific media file system.

     |media-file-system| - https://developer.chrome.com/apps/mediaGalleries#property-getMediaFileSystemMetadata-mediaFileSystem.

   https://developer.chrome.com/apps/mediaGalleries#method-getMediaFileSystemMetadata."
  ([media-file-system] (gen-call :function ::get-media-file-system-metadata &form media-file-system)))

(defmacro get-metadata
  "Gets the media-specific metadata for a media file. This should work for files in media galleries as well as other DOM
   filesystems.

     |media-file| - https://developer.chrome.com/apps/mediaGalleries#property-getMetadata-mediaFile.
     |options| - https://developer.chrome.com/apps/mediaGalleries#property-getMetadata-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [metadata] where:

     |metadata| - https://developer.chrome.com/apps/mediaGalleries#property-callback-metadata.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/mediaGalleries#method-getMetadata."
  ([media-file options] (gen-call :function ::get-metadata &form media-file options))
  ([media-file] `(get-metadata ~media-file :omit)))

(defmacro add-gallery-watch
  "Adds a gallery watch for the gallery with the specified gallery ID. The given callback is then fired with a success or
   failure result.

     |gallery-id| - https://developer.chrome.com/apps/mediaGalleries#property-addGalleryWatch-galleryId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/mediaGalleries#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/mediaGalleries#method-addGalleryWatch."
  ([gallery-id] (gen-call :function ::add-gallery-watch &form gallery-id)))

(defmacro remove-gallery-watch
  "Removes a gallery watch for the gallery with the specified gallery ID.

     |gallery-id| - https://developer.chrome.com/apps/mediaGalleries#property-removeGalleryWatch-galleryId.

   https://developer.chrome.com/apps/mediaGalleries#method-removeGalleryWatch."
  ([gallery-id] (gen-call :function ::remove-gallery-watch &form gallery-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-gallery-changed-events
  "Fired when a media gallery is changed or a gallery watch is dropped.

   Events will be put on the |channel| with signature [::on-gallery-changed [details]] where:

     |details| - https://developer.chrome.com/apps/mediaGalleries#property-onGalleryChanged-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/mediaGalleries#event-onGalleryChanged."
  ([channel & args] (apply gen-call :event ::on-gallery-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.media-galleries namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mediaGalleries",
   :since "38",
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
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "media-file-systems", :type "[array-of-DOMFileSystems]"}
         {:name "selected-file-system-name", :type "string"}]}}]}
    {:id ::get-media-file-system-metadata,
     :name "getMediaFileSystemMetadata",
     :return-type "object",
     :params [{:name "media-file-system", :type "DOMFileSystem"}]}
    {:id ::get-metadata,
     :name "getMetadata",
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
     :params [{:name "gallery-id", :type "string"}]}],
   :events [{:id ::on-gallery-changed, :name "onGalleryChanged", :params [{:name "details", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))