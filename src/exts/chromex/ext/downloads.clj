(ns chromex.ext.downloads
  "Use the chrome.downloads API to programmatically initiate,
   monitor, manipulate, and search for downloads.
   
     * available since Chrome 31
     * https://developer.chrome.com/extensions/downloads"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro download
  "Download a URL. If the URL uses the HTTP[S] protocol, then the request will include all cookies currently set for its
   hostname. If both filename and saveAs are specified, then the Save As dialog will be displayed, pre-populated with the
   specified filename. If the download started successfully, callback will be called with the new 'DownloadItem''s downloadId.
   If there was an error starting the download, then callback will be called with downloadId=undefined and 'runtime.lastError'
   will contain a descriptive string. The error strings are not guaranteed to remain backwards compatible between releases.
   Extensions must not parse it.
   
     |options| - What to download and how.
     |callback| - Called with the id of the new 'DownloadItem'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::download &form options)))

(defmacro search
  "Find 'DownloadItem'. Set query to the empty object to get all 'DownloadItem'. To get a specific 'DownloadItem', set only
   the id field. To page through a large number of items, set orderBy: ['-startTime'], set limit to the number of items per
   page, and set startedAfter to the startTime of the last item from the last page.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([query #_callback] (gen-call :function ::search &form query)))

(defmacro pause
  "Pause the download. If the request was successful the download is in a paused state. Otherwise 'runtime.lastError' contains
   an error message. The request will fail if the download is not active.
   
     |downloadId| - The id of the download to pause.
     |callback| - Called when the pause request is completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id #_callback] (gen-call :function ::pause &form download-id)))

(defmacro resume
  "Resume a paused download. If the request was successful the download is in progress and unpaused. Otherwise
   'runtime.lastError' contains an error message. The request will fail if the download is not active.
   
     |downloadId| - The id of the download to resume.
     |callback| - Called when the resume request is completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id #_callback] (gen-call :function ::resume &form download-id)))

(defmacro cancel
  "Cancel a download. When callback is run, the download is cancelled, completed, interrupted or doesn't exist anymore.
   
     |downloadId| - The id of the download to cancel.
     |callback| - Called when the cancel request is completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id #_callback] (gen-call :function ::cancel &form download-id)))

(defmacro get-file-icon
  "Retrieve an icon for the specified download. For new downloads, file icons are available after the 'onCreated' event has
   been received. The image returned by this function while a download is in progress may be different from the image returned
   after the download is complete. Icon retrieval is done by querying the underlying operating system or toolkit depending on
   the platform. The icon that is returned will therefore depend on a number of factors including state of the download,
   platform, registered file types and visual theme. If a file icon cannot be determined, 'runtime.lastError' will contain an
   error message.
   
     |downloadId| - The identifier for the download.
     |callback| - A URL to an image that represents the download.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id options #_callback] (gen-call :function ::get-file-icon &form download-id options))
  ([download-id] `(get-file-icon ~download-id :omit)))

(defmacro open
  "Open the downloaded file now if the 'DownloadItem' is complete; otherwise returns an error through 'runtime.lastError'.
   Requires the 'downloads.open' permission in addition to the 'downloads' permission. An 'onChanged' event will fire when the
   item is opened for the first time.
   
     |downloadId| - The identifier for the downloaded file."
  ([download-id] (gen-call :function ::open &form download-id)))

(defmacro show
  "Show the downloaded file in its folder in a file manager.
   
     |downloadId| - The identifier for the downloaded file."
  ([download-id] (gen-call :function ::show &form download-id)))

(defmacro show-default-folder
  "Show the default Downloads folder in a file manager."
  ([] (gen-call :function ::show-default-folder &form)))

(defmacro erase
  "Erase matching 'DownloadItem' from history without deleting the downloaded file. An 'onErased' event will fire for each
   'DownloadItem' that matches query, then callback will be called.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([query #_callback] (gen-call :function ::erase &form query)))

(defmacro remove-file
  "Remove the downloaded file if it exists and the 'DownloadItem' is complete; otherwise return an error through
   'runtime.lastError'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id #_callback] (gen-call :function ::remove-file &form download-id)))

(defmacro accept-danger
  "Prompt the user to accept a dangerous download. Can only be called from a visible context (tab, window, or page/browser
   action popup). Does not automatically accept dangerous downloads. If the download is accepted, then an 'onChanged' event
   will fire, otherwise nothing will happen. When all the data is fetched into a temporary file and either the download is not
   dangerous or the danger has been accepted, then the temporary file is renamed to the target filename, the |state| changes
   to 'complete', and 'onChanged' fires.
   
     |downloadId| - The identifier for the 'DownloadItem'.
     |callback| - Called when the danger prompt dialog closes.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([download-id #_callback] (gen-call :function ::accept-danger &form download-id)))

(defmacro drag
  "Initiate dragging the downloaded file to another application. Call in a javascript ondragstart handler."
  ([download-id] (gen-call :function ::drag &form download-id)))

(defmacro set-shelf-enabled
  "Enable or disable the gray shelf at the bottom of every window associated with the current browser profile. The shelf will
   be disabled as long as at least one extension has disabled it. Enabling the shelf while at least one other extension has
   disabled it will return an error through 'runtime.lastError'. Requires the 'downloads.shelf' permission in addition to the
   'downloads' permission."
  ([enabled] (gen-call :function ::set-shelf-enabled &form enabled)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "This event fires with the 'DownloadItem' object when a download begins.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-erased-events
  "Fires with the downloadId when a download is erased from history.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-erased &form channel args)))

(defmacro tap-on-changed-events
  "When any of a 'DownloadItem''s properties except bytesReceived and estimatedEndTime changes, this event fires with the
   downloadId and an object containing the properties that changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

(defmacro tap-on-determining-filename-events
  "During the filename determination process, extensions will be given the opportunity to override the target
   'DownloadItem.filename'. Each extension may not register more than one listener for this event. Each listener must call
   suggest exactly once, either synchronously or asynchronously. If the listener calls suggest asynchronously, then it must
   return true. If the listener neither calls suggest synchronously nor returns true, then suggest will be called
   automatically. The 'DownloadItem' will not complete until all listeners have called suggest. Listeners may call suggest
   without any arguments in order to allow the download to use downloadItem.filename for its filename, or pass a suggestion
   object to suggest in order to override the target filename. If more than one extension overrides the filename, then the
   last extension installed whose listener passes a suggestion object to suggest wins. In order to avoid confusion regarding
   which extension will win, users should not install extensions that may conflict. If the download is initiated by 'download'
   and the target filename is known before the MIME type and tentative filename have been determined, pass filename to
   'download' instead.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-determining-filename &form channel args)))

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
  {:namespace "chrome.downloads",
   :since "31",
   :functions
   [{:id ::download,
     :name "download",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "download-id", :type "integer"}]}}]}
    {:id ::search,
     :name "search",
     :callback? true,
     :params
     [{:name "query", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-downloads.DownloadItems]"}]}}]}
    {:id ::pause,
     :name "pause",
     :callback? true,
     :params [{:name "download-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::resume,
     :name "resume",
     :callback? true,
     :params [{:name "download-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::cancel,
     :name "cancel",
     :callback? true,
     :params [{:name "download-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-file-icon,
     :name "getFileIcon",
     :callback? true,
     :params
     [{:name "download-id", :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "icon-url", :optional? true, :type "string"}]}}]}
    {:id ::open, :name "open", :params [{:name "download-id", :type "integer"}]}
    {:id ::show, :name "show", :params [{:name "download-id", :type "integer"}]}
    {:id ::show-default-folder, :name "showDefaultFolder"}
    {:id ::erase,
     :name "erase",
     :callback? true,
     :params
     [{:name "query", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "erased-ids", :type "[array-of-integers]"}]}}]}
    {:id ::remove-file,
     :name "removeFile",
     :callback? true,
     :params [{:name "download-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::accept-danger,
     :name "acceptDanger",
     :callback? true,
     :params [{:name "download-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::drag, :name "drag", :params [{:name "download-id", :type "integer"}]}
    {:id ::set-shelf-enabled, :name "setShelfEnabled", :params [{:name "enabled", :type "boolean"}]}],
   :events
   [{:id ::on-created, :name "onCreated", :params [{:name "download-item", :type "downloads.DownloadItem"}]}
    {:id ::on-erased, :name "onErased", :params [{:name "download-id", :type "integer"}]}
    {:id ::on-changed, :name "onChanged", :params [{:name "download-delta", :type "object"}]}
    {:id ::on-determining-filename,
     :name "onDeterminingFilename",
     :params [{:name "download-item", :type "downloads.DownloadItem"} {:name "suggest", :type :callback}]}]})

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