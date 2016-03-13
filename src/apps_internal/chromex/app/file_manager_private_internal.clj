(ns chromex.app.file-manager-private-internal
  "Internal, used by fileManagerPrivate's custom bindings.
   
     * available since Chrome 39
     * https://developer.chrome.com/extensions/fileManagerPrivateInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro resolve-isolated-entries
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-resolveIsolatedEntries-urls.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries] where:
   
     |entries| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-entries.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-resolveIsolatedEntries."
  ([urls #_callback] (gen-call :function ::resolve-isolated-entries &form urls)))

(defmacro get-entry-properties
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getEntryProperties-urls.
     |names| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getEntryProperties-names.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entryProperties] where:
   
     |entryProperties| - See
                         https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-entryProperties.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getEntryProperties."
  ([urls names #_callback] (gen-call :function ::get-entry-properties &form urls names)))

(defmacro add-file-watch
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-addFileWatch-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:
   
     |success| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-success.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-addFileWatch."
  ([url #_callback] (gen-call :function ::add-file-watch &form url)))

(defmacro remove-file-watch
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-removeFileWatch-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:
   
     |success| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-success.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-removeFileWatch."
  ([url #_callback] (gen-call :function ::remove-file-watch &form url)))

(defmacro get-custom-actions
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getCustomActions-urls.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [actions] where:
   
     |actions| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-actions.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getCustomActions."
  ([urls #_callback] (gen-call :function ::get-custom-actions &form urls)))

(defmacro execute-custom-action
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-executeCustomAction-urls.
     |actionId| - See
                  https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-executeCustomAction-actionId.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-executeCustomAction."
  ([urls action-id #_callback] (gen-call :function ::execute-custom-action &form urls action-id)))

(defmacro compute-checksum
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-computeChecksum-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [checksum] where:
   
     |checksum| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-checksum.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-computeChecksum."
  ([url #_callback] (gen-call :function ::compute-checksum &form url)))

(defmacro get-mime-type
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getMimeType-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-result.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getMimeType."
  ([url #_callback] (gen-call :function ::get-mime-type &form url)))

(defmacro pin-drive-file
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-pinDriveFile-url.
     |pin| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-pinDriveFile-pin.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-pinDriveFile."
  ([url pin #_callback] (gen-call :function ::pin-drive-file &form url pin)))

(defmacro execute-task
  "  |taskId| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-executeTask-taskId.
     |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-executeTask-urls.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-result.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-executeTask."
  ([task-id urls #_callback] (gen-call :function ::execute-task &form task-id urls)))

(defmacro set-default-task
  "  |taskId| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setDefaultTask-taskId.
     |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setDefaultTask-urls.
     |mimeTypes| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setDefaultTask-mimeTypes.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-setDefaultTask."
  ([task-id urls mime-types #_callback] (gen-call :function ::set-default-task &form task-id urls mime-types)))

(defmacro get-file-tasks
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getFileTasks-urls.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [tasks] where:
   
     |tasks| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-tasks.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getFileTasks."
  ([urls #_callback] (gen-call :function ::get-file-tasks &form urls)))

(defmacro get-share-url
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getShareUrl-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:
   
     |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-url.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getShareUrl."
  ([url #_callback] (gen-call :function ::get-share-url &form url)))

(defmacro get-download-url
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-getDownloadUrl-url.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:
   
     |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-url.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-getDownloadUrl."
  ([url #_callback] (gen-call :function ::get-download-url &form url)))

(defmacro request-drive-share
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-requestDriveShare-url.
     |shareType| - See
                   https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-requestDriveShare-shareType.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-requestDriveShare."
  ([url share-type #_callback] (gen-call :function ::request-drive-share &form url share-type)))

(defmacro set-entry-tag
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setEntryTag-url.
     |visibility| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setEntryTag-visibility.
     |key| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setEntryTag-key.
     |value| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-setEntryTag-value.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-setEntryTag."
  ([url visibility key value #_callback] (gen-call :function ::set-entry-tag &form url visibility key value)))

(defmacro cancel-file-transfers
  "  |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-cancelFileTransfers-urls.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-cancelFileTransfers."
  ([urls #_callback] (gen-call :function ::cancel-file-transfers &form urls)))

(defmacro start-copy
  "  |url| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-startCopy-url.
     |parentUrl| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-startCopy-parentUrl.
     |newName| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-startCopy-newName.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [copyId] where:
   
     |copyId| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-copyId.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-startCopy."
  ([url parent-url new-name #_callback] (gen-call :function ::start-copy &form url parent-url new-name)))

(defmacro zip-selection
  "  |parentUrl| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-zipSelection-parentUrl.
     |urls| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-zipSelection-urls.
     |destName| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-zipSelection-destName.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:
   
     |success| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-success.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-zipSelection."
  ([parent-url urls dest-name #_callback] (gen-call :function ::zip-selection &form parent-url urls dest-name)))

(defmacro validate-path-name-length
  "  |parentUrl| - See
                   https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-validatePathNameLength-parentU
                   rl.
     |name| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-validatePathNameLength-name.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/fileManagerPrivateInternal#property-callback-result.
   
   See https://developer.chrome.com/extensions/fileManagerPrivateInternal#method-validatePathNameLength."
  ([parent-url name #_callback] (gen-call :function ::validate-path-name-length &form parent-url name)))

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
  {:namespace "chrome.fileManagerPrivateInternal",
   :since "39",
   :functions
   [{:id ::resolve-isolated-entries,
     :name "resolveIsolatedEntries",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-objects]"}]}}]}
    {:id ::get-entry-properties,
     :name "getEntryProperties",
     :since "45",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "names", :type "[array-of-fileManagerPrivate.EntryPropertyNames]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "entry-properties", :type "[array-of-fileManagerPrivate.EntryPropertiess]"}]}}]}
    {:id ::add-file-watch,
     :name "addFileWatch",
     :since "45",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::remove-file-watch,
     :name "removeFileWatch",
     :since "45",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::get-custom-actions,
     :name "getCustomActions",
     :since "47",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "actions", :type "[array-of-fileSystemProvider.Actions]"}]}}]}
    {:id ::execute-custom-action,
     :name "executeCustomAction",
     :since "47",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "action-id", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::compute-checksum,
     :name "computeChecksum",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "checksum", :type "string"}]}}]}
    {:id ::get-mime-type,
     :name "getMimeType",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::pin-drive-file,
     :name "pinDriveFile",
     :since "46",
     :callback? true,
     :params [{:name "url", :type "string"} {:name "pin", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::execute-task,
     :name "executeTask",
     :since "46",
     :callback? true,
     :params
     [{:name "task-id", :type "string"}
      {:name "urls", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "fileManagerPrivate.TaskResult"}]}}]}
    {:id ::set-default-task,
     :name "setDefaultTask",
     :since "46",
     :callback? true,
     :params
     [{:name "task-id", :type "string"}
      {:name "urls", :type "[array-of-strings]"}
      {:name "mime-types", :type "[array-of-strings]"}
      {:name "callback", :type :callback}]}
    {:id ::get-file-tasks,
     :name "getFileTasks",
     :since "46",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "tasks", :type "[array-of-fileManagerPrivate.FileTasks]"}]}}]}
    {:id ::get-share-url,
     :name "getShareUrl",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "url", :type "string"}]}}]}
    {:id ::get-download-url,
     :name "getDownloadUrl",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "url", :type "string"}]}}]}
    {:id ::request-drive-share,
     :name "requestDriveShare",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "share-type", :type "fileManagerPrivate.DriveShareType"}
      {:name "callback", :type :callback}]}
    {:id ::set-entry-tag,
     :name "setEntryTag",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "visibility", :type "fileManagerPrivate.EntryTagVisibility"}
      {:name "key", :type "string"}
      {:name "value", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::cancel-file-transfers,
     :name "cancelFileTransfers",
     :since "46",
     :callback? true,
     :params [{:name "urls", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-copy,
     :name "startCopy",
     :since "46",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "parent-url", :type "string"}
      {:name "new-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "copy-id", :type "integer"}]}}]}
    {:id ::zip-selection,
     :name "zipSelection",
     :since "46",
     :callback? true,
     :params
     [{:name "parent-url", :type "string"}
      {:name "urls", :type "[array-of-strings]"}
      {:name "dest-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :optional? true, :type "boolean"}]}}]}
    {:id ::validate-path-name-length,
     :name "validatePathNameLength",
     :since "46",
     :callback? true,
     :params
     [{:name "parent-url", :type "string"}
      {:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}]})

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