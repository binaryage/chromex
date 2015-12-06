(ns chromex.ext.browsing-data
  "Use the chrome.browsingData API to remove browsing data from a user's local profile.
   
     * available since Chrome 19
     * https://developer.chrome.com/extensions/browsingData"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro settings
  "Reports which types of data are currently selected in the 'Clear browsing data' settings UI.  Note: some of the data types
   included in this API are not available in the settings UI, and some UI settings control more than one data type listed
   here.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::settings &form)))

(defmacro remove
  "Clears various types of browsing data stored in a user's profile.
   
     |dataToRemove| - The set of data types to remove.
     |callback| - Called when deletion has completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options data-to-remove #_callback] (gen-call :function ::remove &form options data-to-remove)))

(defmacro remove-appcache
  "Clears websites' appcache data.
   
     |callback| - Called when websites' appcache data has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-appcache &form options)))

(defmacro remove-cache
  "Clears the browser's cache.
   
     |callback| - Called when the browser's cache has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-cache &form options)))

(defmacro remove-cookies
  "Clears the browser's cookies and server-bound certificates modified within a particular timeframe.
   
     |callback| - Called when the browser's cookies and server-bound certificates have been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-cookies &form options)))

(defmacro remove-downloads
  "Clears the browser's list of downloaded files (not the downloaded files themselves).
   
     |callback| - Called when the browser's list of downloaded files has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-downloads &form options)))

(defmacro remove-file-systems
  "Clears websites' file system data.
   
     |callback| - Called when websites' file systems have been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-file-systems &form options)))

(defmacro remove-form-data
  "Clears the browser's stored form data (autofill).
   
     |callback| - Called when the browser's form data has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-form-data &form options)))

(defmacro remove-history
  "Clears the browser's history.
   
     |callback| - Called when the browser's history has cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-history &form options)))

(defmacro remove-indexed-db
  "Clears websites' IndexedDB data.
   
     |callback| - Called when websites' IndexedDB data has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-indexed-db &form options)))

(defmacro remove-local-storage
  "Clears websites' local storage data.
   
     |callback| - Called when websites' local storage has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-local-storage &form options)))

(defmacro remove-plugin-data
  "Clears plugins' data.
   
     |callback| - Called when plugins' data has been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-plugin-data &form options)))

(defmacro remove-passwords
  "Clears the browser's stored passwords.
   
     |callback| - Called when the browser's passwords have been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-passwords &form options)))

(defmacro remove-web-sql
  "Clears websites' WebSQL data.
   
     |callback| - Called when websites' WebSQL databases have been cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::remove-web-sql &form options)))

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
  {:namespace "chrome.browsingData",
   :since "19",
   :functions
   [{:id ::settings,
     :name "settings",
     :since "26",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"}
      {:name "data-to-remove", :type "browsingData.DataTypeSet"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-appcache,
     :name "removeAppcache",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-cache,
     :name "removeCache",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-cookies,
     :name "removeCookies",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-downloads,
     :name "removeDownloads",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-file-systems,
     :name "removeFileSystems",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-form-data,
     :name "removeFormData",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-history,
     :name "removeHistory",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-indexed-db,
     :name "removeIndexedDB",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-local-storage,
     :name "removeLocalStorage",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-plugin-data,
     :name "removePluginData",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-passwords,
     :name "removePasswords",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-web-sql,
     :name "removeWebSQL",
     :callback? true,
     :params
     [{:name "options", :type "browsingData.RemovalOptions"} {:name "callback", :optional? true, :type :callback}]}]})

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