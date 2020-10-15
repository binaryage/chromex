(ns chromex.ext.downloads
  "Use the chrome.downloads API to programmatically initiate,
   monitor, manipulate, and search for downloads.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/downloads"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [download-id] where:

     |download-id| - https://developer.chrome.com/extensions/downloads#property-callback-downloadId.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-download."
  ([options] (gen-call :function ::download &form options)))

(defmacro search
  "Find 'DownloadItem'. Set query to the empty object to get all 'DownloadItem'. To get a specific 'DownloadItem', set only
   the id field. To page through a large number of items, set orderBy: ['-startTime'], set limit to the number of items per
   page, and set startedAfter to the startTime of the last item from the last page.

     |query| - https://developer.chrome.com/extensions/downloads#property-search-query.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/downloads#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-search."
  ([query] (gen-call :function ::search &form query)))

(defmacro pause
  "Pause the download. If the request was successful the download is in a paused state. Otherwise 'runtime.lastError' contains
   an error message. The request will fail if the download is not active.

     |download-id| - The id of the download to pause.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-pause."
  ([download-id] (gen-call :function ::pause &form download-id)))

(defmacro resume
  "Resume a paused download. If the request was successful the download is in progress and unpaused. Otherwise
   'runtime.lastError' contains an error message. The request will fail if the download is not active.

     |download-id| - The id of the download to resume.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-resume."
  ([download-id] (gen-call :function ::resume &form download-id)))

(defmacro cancel
  "Cancel a download. When callback is run, the download is cancelled, completed, interrupted or doesn't exist anymore.

     |download-id| - The id of the download to cancel.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-cancel."
  ([download-id] (gen-call :function ::cancel &form download-id)))

(defmacro get-file-icon
  "Retrieve an icon for the specified download. For new downloads, file icons are available after the 'onCreated' event has
   been received. The image returned by this function while a download is in progress may be different from the image returned
   after the download is complete. Icon retrieval is done by querying the underlying operating system or toolkit depending on
   the platform. The icon that is returned will therefore depend on a number of factors including state of the download,
   platform, registered file types and visual theme. If a file icon cannot be determined, 'runtime.lastError' will contain an
   error message.

     |download-id| - The identifier for the download.
     |options| - https://developer.chrome.com/extensions/downloads#property-getFileIcon-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [icon-url] where:

     |icon-url| - https://developer.chrome.com/extensions/downloads#property-callback-iconURL.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-getFileIcon."
  ([download-id options] (gen-call :function ::get-file-icon &form download-id options))
  ([download-id] `(get-file-icon ~download-id :omit)))

(defmacro open
  "Open the downloaded file now if the 'DownloadItem' is complete; otherwise returns an error through 'runtime.lastError'.
   Requires the 'downloads.open' permission in addition to the 'downloads' permission. An 'onChanged' event will fire when the
   item is opened for the first time.

     |download-id| - The identifier for the downloaded file.

   https://developer.chrome.com/extensions/downloads#method-open."
  ([download-id] (gen-call :function ::open &form download-id)))

(defmacro show
  "Show the downloaded file in its folder in a file manager.

     |download-id| - The identifier for the downloaded file.

   https://developer.chrome.com/extensions/downloads#method-show."
  ([download-id] (gen-call :function ::show &form download-id)))

(defmacro show-default-folder
  "Show the default Downloads folder in a file manager.

   https://developer.chrome.com/extensions/downloads#method-showDefaultFolder."
  ([] (gen-call :function ::show-default-folder &form)))

(defmacro erase
  "Erase matching 'DownloadItem' from history without deleting the downloaded file. An 'onErased' event will fire for each
   'DownloadItem' that matches query, then callback will be called.

     |query| - https://developer.chrome.com/extensions/downloads#property-erase-query.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [erased-ids] where:

     |erased-ids| - https://developer.chrome.com/extensions/downloads#property-callback-erasedIds.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-erase."
  ([query] (gen-call :function ::erase &form query)))

(defmacro remove-file
  "Remove the downloaded file if it exists and the 'DownloadItem' is complete; otherwise return an error through
   'runtime.lastError'.

     |download-id| - https://developer.chrome.com/extensions/downloads#property-removeFile-downloadId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-removeFile."
  ([download-id] (gen-call :function ::remove-file &form download-id)))

(defmacro accept-danger
  "Prompt the user to accept a dangerous download. Can only be called from a visible context (tab, window, or page/browser
   action popup). Does not automatically accept dangerous downloads. If the download is accepted, then an 'onChanged' event
   will fire, otherwise nothing will happen. When all the data is fetched into a temporary file and either the download is not
   dangerous or the danger has been accepted, then the temporary file is renamed to the target filename, the |state| changes
   to 'complete', and 'onChanged' fires.

     |download-id| - The identifier for the 'DownloadItem'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/downloads#method-acceptDanger."
  ([download-id] (gen-call :function ::accept-danger &form download-id)))

(defmacro set-shelf-enabled
  "Enable or disable the gray shelf at the bottom of every window associated with the current browser profile. The shelf will
   be disabled as long as at least one extension has disabled it. Enabling the shelf while at least one other extension has
   disabled it will return an error through 'runtime.lastError'. Requires the 'downloads.shelf' permission in addition to the
   'downloads' permission.

     |enabled| - https://developer.chrome.com/extensions/downloads#property-setShelfEnabled-enabled.

   https://developer.chrome.com/extensions/downloads#method-setShelfEnabled."
  ([enabled] (gen-call :function ::set-shelf-enabled &form enabled)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "This event fires with the 'DownloadItem' object when a download begins.

   Events will be put on the |channel| with signature [::on-created [download-item]] where:

     |download-item| - https://developer.chrome.com/extensions/downloads#property-onCreated-downloadItem.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/downloads#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-erased-events
  "Fires with the downloadId when a download is erased from history.

   Events will be put on the |channel| with signature [::on-erased [download-id]] where:

     |download-id| - The id of the 'DownloadItem' that was erased.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/downloads#event-onErased."
  ([channel & args] (apply gen-call :event ::on-erased &form channel args)))

(defmacro tap-on-changed-events
  "When any of a 'DownloadItem''s properties except bytesReceived and estimatedEndTime changes, this event fires with the
   downloadId and an object containing the properties that changed.

   Events will be put on the |channel| with signature [::on-changed [download-delta]] where:

     |download-delta| - https://developer.chrome.com/extensions/downloads#property-onChanged-downloadDelta.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/downloads#event-onChanged."
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

   Events will be put on the |channel| with signature [::on-determining-filename [download-item suggest]] where:

     |download-item| - https://developer.chrome.com/extensions/downloads#property-onDeterminingFilename-downloadItem.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/downloads#event-onDeterminingFilename."
  ([channel & args] (apply gen-call :event ::on-determining-filename &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.downloads namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.downloads",
   :since "38",
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))