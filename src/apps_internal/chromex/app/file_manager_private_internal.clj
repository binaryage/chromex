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
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls #_callback] (gen-call :function ::resolve-isolated-entries &form urls)))

(defmacro get-entry-properties
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls names #_callback] (gen-call :function ::get-entry-properties &form urls names)))

(defmacro add-file-watch
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::add-file-watch &form url)))

(defmacro remove-file-watch
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::remove-file-watch &form url)))

(defmacro get-custom-actions
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls #_callback] (gen-call :function ::get-custom-actions &form urls)))

(defmacro execute-custom-action
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls action-id #_callback] (gen-call :function ::execute-custom-action &form urls action-id)))

(defmacro compute-checksum
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::compute-checksum &form url)))

(defmacro get-mime-type
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::get-mime-type &form url)))

(defmacro pin-drive-file
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url pin #_callback] (gen-call :function ::pin-drive-file &form url pin)))

(defmacro execute-task
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([task-id urls #_callback] (gen-call :function ::execute-task &form task-id urls)))

(defmacro set-default-task
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([task-id urls mime-types #_callback] (gen-call :function ::set-default-task &form task-id urls mime-types)))

(defmacro get-file-tasks
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls #_callback] (gen-call :function ::get-file-tasks &form urls)))

(defmacro get-share-url
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::get-share-url &form url)))

(defmacro get-download-url
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url #_callback] (gen-call :function ::get-download-url &form url)))

(defmacro request-drive-share
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url share-type #_callback] (gen-call :function ::request-drive-share &form url share-type)))

(defmacro set-entry-tag
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url visibility key value #_callback] (gen-call :function ::set-entry-tag &form url visibility key value)))

(defmacro cancel-file-transfers
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([urls #_callback] (gen-call :function ::cancel-file-transfers &form urls)))

(defmacro start-copy
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url parent-url new-name #_callback] (gen-call :function ::start-copy &form url parent-url new-name)))

(defmacro zip-selection
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([parent-url urls dest-name #_callback] (gen-call :function ::zip-selection &form parent-url urls dest-name)))

(defmacro validate-path-name-length
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
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