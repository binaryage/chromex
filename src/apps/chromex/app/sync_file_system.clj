(ns chromex.app.sync-file-system
  "Use the chrome.syncFileSystem API to save and synchronize data
   on Google Drive. This API is NOT for accessing arbitrary user docs stored in
   Google Drive. It provides app-specific syncable storage for offline and
   caching usage so that the same data can be available across different
   clients. Read Manage Data for more on using
   this API.
   
     * available since Chrome 27
     * https://developer.chrome.com/extensions/syncFileSystem"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro request-file-system
  "Returns a syncable filesystem backed by Google Drive. The returned DOMFileSystem instance can be operated on in the same
   way as the Temporary and Persistant file systems (see  http://dev.w3.org/2009/dap/file-system/file-dir-sys.html).Calling
   this multiple times from the same app will return the same handle to the same file system.Note this call can fail. For
   example, if the user is not signed in to Chrome or if there is no network operation. To handle these errors it is important
   chrome.runtime.lastError is checked in the callback.
   
     |callback| - A callback type for requestFileSystem.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::request-file-system &form)))

(defmacro set-conflict-resolution-policy
  "Sets the default conflict resolution policy for the 'syncable' file storage for the app. By default it is set to
   'last_write_win'. When conflict resolution policy is set to 'last_write_win' conflicts for existing files are automatically
   resolved next time the file is updated. |callback| can be optionally given to know if the request has succeeded or not.
   
     |callback| - A generic result callback to indicate success or failure.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([policy #_callback] (gen-call :function ::set-conflict-resolution-policy &form policy)))

(defmacro get-conflict-resolution-policy
  "Gets the current conflict resolution policy.
   
     |callback| - A callback type for getConflictResolutionPolicy.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-conflict-resolution-policy &form)))

(defmacro get-usage-and-quota
  "Returns the current usage and quota in bytes for the 'syncable' file storage for the app.
   
     |callback| - A callback type for getUsageAndQuota.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([file-system #_callback] (gen-call :function ::get-usage-and-quota &form file-system)))

(defmacro get-file-status
  "Returns the 'FileStatus' for the given fileEntry. The status value can be 'synced', 'pending' or 'conflicting'. Note that
   'conflicting' state only happens when the service's conflict resolution policy is set to 'manual'.
   
     |callback| - A callback type for getFileStatus.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([file-entry #_callback] (gen-call :function ::get-file-status &form file-entry)))

(defmacro get-file-statuses
  "Returns each 'FileStatus' for the given fileEntry array. Typically called with the result from dirReader.readEntries().
   
     |callback| - A callback type for getFileStatuses.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([file-entries #_callback] (gen-call :function ::get-file-statuses &form file-entries)))

(defmacro get-service-status
  "Returns the current sync backend status.
   
     |callback| - A callback type for getServiceStatus.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-service-status &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-service-status-changed-events
  "Fired when an error or other status change has happened in the sync backend (for example, when the sync is temporarily
   disabled due to network or authentication error).
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-service-status-changed &form channel args)))
(defmacro tap-on-file-status-changed-events
  "Fired when a file has been updated by the background sync service.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-file-status-changed &form channel args)))

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
  {:namespace "chrome.syncFileSystem",
   :since "27",
   :functions
   [{:id ::request-file-system,
     :name "requestFileSystem",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "file-system", :type "DOMFileSystem"}]}}]}
    {:id ::set-conflict-resolution-policy,
     :name "setConflictResolutionPolicy",
     :callback? true,
     :params
     [{:name "policy", :type "syncFileSystem.ConflictResolutionPolicy"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-conflict-resolution-policy,
     :name "getConflictResolutionPolicy",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "policy", :type "syncFileSystem.ConflictResolutionPolicy"}]}}]}
    {:id ::get-usage-and-quota,
     :name "getUsageAndQuota",
     :callback? true,
     :params
     [{:name "file-system", :type "DOMFileSystem"}
      {:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::get-file-status,
     :name "getFileStatus",
     :callback? true,
     :params
     [{:name "file-entry", :type "Entry"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "syncFileSystem.FileStatus"}]}}]}
    {:id ::get-file-statuses,
     :name "getFileStatuses",
     :callback? true,
     :params
     [{:name "file-entries", :type "[array-of-objects]"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "[array-of-objects]"}]}}]}
    {:id ::get-service-status,
     :name "getServiceStatus",
     :since "31",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "syncFileSystem.ServiceStatus"}]}}]}],
   :events
   [{:id ::on-service-status-changed, :name "onServiceStatusChanged", :params [{:name "detail", :type "object"}]}
    {:id ::on-file-status-changed, :name "onFileStatusChanged", :params [{:name "detail", :type "object"}]}]})

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