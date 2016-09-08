(ns chromex.ext.file-manager-private-internal
  "Internal, used by fileManagerPrivate's custom bindings.

     * available since Chrome 39"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro resolve-isolated-entries
  "  |urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls] (gen-call :function ::resolve-isolated-entries &form urls)))

(defmacro get-entry-properties
  "  |urls| - ?
     |names| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entry-properties] where:

     |entry-properties| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls names] (gen-call :function ::get-entry-properties &form urls names)))

(defmacro add-file-watch
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::add-file-watch &form url)))

(defmacro remove-file-watch
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::remove-file-watch &form url)))

(defmacro get-custom-actions
  "  |urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [actions] where:

     |actions| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls] (gen-call :function ::get-custom-actions &form urls)))

(defmacro execute-custom-action
  "  |urls| - ?
     |action-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls action-id] (gen-call :function ::execute-custom-action &form urls action-id)))

(defmacro compute-checksum
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [checksum] where:

     |checksum| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::compute-checksum &form url)))

(defmacro get-mime-type
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::get-mime-type &form url)))

(defmacro pin-drive-file
  "  |url| - ?
     |pin| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url pin] (gen-call :function ::pin-drive-file &form url pin)))

(defmacro execute-task
  "  |task-id| - ?
     |urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id urls] (gen-call :function ::execute-task &form task-id urls)))

(defmacro set-default-task
  "  |task-id| - ?
     |urls| - ?
     |mime-types| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([task-id urls mime-types] (gen-call :function ::set-default-task &form task-id urls mime-types)))

(defmacro get-file-tasks
  "  |urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [tasks] where:

     |tasks| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls] (gen-call :function ::get-file-tasks &form urls)))

(defmacro get-share-url
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:

     |url| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::get-share-url &form url)))

(defmacro get-download-url
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [url] where:

     |url| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::get-download-url &form url)))

(defmacro request-drive-share
  "  |url| - ?
     |share-type| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url share-type] (gen-call :function ::request-drive-share &form url share-type)))

(defmacro set-entry-tag
  "  |url| - ?
     |visibility| - ?
     |key| - ?
     |value| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url visibility key value] (gen-call :function ::set-entry-tag &form url visibility key value)))

(defmacro cancel-file-transfers
  "  |urls| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([urls] (gen-call :function ::cancel-file-transfers &form urls)))

(defmacro start-copy
  "  |url| - ?
     |parent-url| - ?
     |new-name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [copy-id] where:

     |copy-id| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url parent-url new-name] (gen-call :function ::start-copy &form url parent-url new-name)))

(defmacro zip-selection
  "  |parent-url| - ?
     |urls| - ?
     |dest-name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-url urls dest-name] (gen-call :function ::zip-selection &form parent-url urls dest-name)))

(defmacro validate-path-name-length
  "  |parent-url| - ?
     |name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-url name] (gen-call :function ::validate-path-name-length &form parent-url name)))

(defmacro get-directory-size
  "  |url| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [size] where:

     |size| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([url] (gen-call :function ::get-directory-size &form url)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.file-manager-private-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

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
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::get-directory-size,
     :name "getDirectorySize",
     :since "55",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "size", :type "double"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))