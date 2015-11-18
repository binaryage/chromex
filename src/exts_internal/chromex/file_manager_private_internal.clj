(ns chromex.file-manager-private-internal
  "Internal, used by fileManagerPrivate's custom bindings.
   
     * available since Chrome 39
     * https://developer.chrome.com/extensions/fileManagerPrivateInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro resolve-isolated-entries [urls #_callback]
  (gen-call :function ::resolve-isolated-entries (meta &form) urls))

(defmacro get-entry-properties [urls names #_callback]
  (gen-call :function ::get-entry-properties (meta &form) urls names))

(defmacro add-file-watch [url #_callback]
  (gen-call :function ::add-file-watch (meta &form) url))

(defmacro remove-file-watch [url #_callback]
  (gen-call :function ::remove-file-watch (meta &form) url))

(defmacro get-custom-actions [urls #_callback]
  (gen-call :function ::get-custom-actions (meta &form) urls))

(defmacro execute-custom-action [urls action-id #_callback]
  (gen-call :function ::execute-custom-action (meta &form) urls action-id))

(defmacro compute-checksum [url #_callback]
  (gen-call :function ::compute-checksum (meta &form) url))

(defmacro get-mime-type [url #_callback]
  (gen-call :function ::get-mime-type (meta &form) url))

(defmacro pin-drive-file [url pin #_callback]
  (gen-call :function ::pin-drive-file (meta &form) url pin))

(defmacro execute-task [task-id urls #_callback]
  (gen-call :function ::execute-task (meta &form) task-id urls))

(defmacro set-default-task [task-id urls mime-types #_callback]
  (gen-call :function ::set-default-task (meta &form) task-id urls mime-types))

(defmacro get-file-tasks [urls #_callback]
  (gen-call :function ::get-file-tasks (meta &form) urls))

(defmacro get-share-url [url #_callback]
  (gen-call :function ::get-share-url (meta &form) url))

(defmacro get-download-url [url #_callback]
  (gen-call :function ::get-download-url (meta &form) url))

(defmacro request-drive-share [url share-type #_callback]
  (gen-call :function ::request-drive-share (meta &form) url share-type))

(defmacro set-entry-tag [url visibility key value #_callback]
  (gen-call :function ::set-entry-tag (meta &form) url visibility key value))

(defmacro cancel-file-transfers [urls #_callback]
  (gen-call :function ::cancel-file-transfers (meta &form) urls))

(defmacro start-copy [url parent-url new-name #_callback]
  (gen-call :function ::start-copy (meta &form) url parent-url new-name))

(defmacro zip-selection [parent-url urls dest-name #_callback]
  (gen-call :function ::zip-selection (meta &form) parent-url urls dest-name))

(defmacro validate-path-name-length [parent-url name #_callback]
  (gen-call :function ::validate-path-name-length (meta &form) parent-url name))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileManagerPrivateInternal",
   :since "39",
   :functions
   [{:id ::resolve-isolated-entries,
     :name "resolveIsolatedEntries",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "entries", :type "[array-of-objects]"}]}}]}
    {:id ::get-entry-properties,
     :name "getEntryProperties",
     :since "45",
     :callback? true,
     :params
     [{:name "urls", :type "[array-of-strings]"}
      {:name "names", :type "[array-of-fileManagerPrivate.EntryPropertyNames]"}
      {:name "callback",
       :type :callback,
       :callback
       {:params [{:name "entry-properties", :type "[array-of-fileManagerPrivate.EntryPropertiess]"}]}}]}
    {:id ::add-file-watch,
     :name "addFileWatch",
     :since "45",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::remove-file-watch,
     :name "removeFileWatch",
     :since "45",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
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
     :params
     [{:name "url", :type "string"} {:name "pin", :type "boolean"} {:name "callback", :type :callback}]}
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
     :params [{:name "urls", :type "[array-of-strings]"} {:name "callback", :type :callback}]}
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
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::validate-path-name-length,
     :name "validatePathNameLength",
     :since "46",
     :callback? true,
     :params
     [{:name "parent-url", :type "string"}
      {:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))