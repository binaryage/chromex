(ns chromex.ext.browsing-data
  "Use the chrome.browsingData API to remove browsing data from a user's local profile.
   
     * available since Chrome 19
     * https://developer.chrome.com/extensions/browsingData"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro settings
  "Reports which types of data are currently selected in the 'Clear browsing data' settings UI.  Note: some of the data types
   included in this API are not available in the settings UI, and some UI settings control more than one data type listed
   here.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/browsingData#property-callback-result.
   
   See https://developer.chrome.com/extensions/browsingData#method-settings."
  ([#_callback] (gen-call :function ::settings &form)))

(defmacro remove
  "Clears various types of browsing data stored in a user's profile.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-remove-options.
     |dataToRemove| - The set of data types to remove.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-remove."
  ([options data-to-remove #_callback] (gen-call :function ::remove &form options data-to-remove)))

(defmacro remove-appcache
  "Clears websites' appcache data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeAppcache-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeAppcache."
  ([options #_callback] (gen-call :function ::remove-appcache &form options)))

(defmacro remove-cache
  "Clears the browser's cache.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeCache-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeCache."
  ([options #_callback] (gen-call :function ::remove-cache &form options)))

(defmacro remove-cookies
  "Clears the browser's cookies and server-bound certificates modified within a particular timeframe.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeCookies-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeCookies."
  ([options #_callback] (gen-call :function ::remove-cookies &form options)))

(defmacro remove-downloads
  "Clears the browser's list of downloaded files (not the downloaded files themselves).
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeDownloads-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeDownloads."
  ([options #_callback] (gen-call :function ::remove-downloads &form options)))

(defmacro remove-file-systems
  "Clears websites' file system data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeFileSystems-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeFileSystems."
  ([options #_callback] (gen-call :function ::remove-file-systems &form options)))

(defmacro remove-form-data
  "Clears the browser's stored form data (autofill).
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeFormData-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeFormData."
  ([options #_callback] (gen-call :function ::remove-form-data &form options)))

(defmacro remove-history
  "Clears the browser's history.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeHistory-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeHistory."
  ([options #_callback] (gen-call :function ::remove-history &form options)))

(defmacro remove-indexed-db
  "Clears websites' IndexedDB data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeIndexedDB-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeIndexedDB."
  ([options #_callback] (gen-call :function ::remove-indexed-db &form options)))

(defmacro remove-local-storage
  "Clears websites' local storage data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeLocalStorage-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeLocalStorage."
  ([options #_callback] (gen-call :function ::remove-local-storage &form options)))

(defmacro remove-plugin-data
  "Clears plugins' data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removePluginData-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removePluginData."
  ([options #_callback] (gen-call :function ::remove-plugin-data &form options)))

(defmacro remove-passwords
  "Clears the browser's stored passwords.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removePasswords-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removePasswords."
  ([options #_callback] (gen-call :function ::remove-passwords &form options)))

(defmacro remove-web-sql
  "Clears websites' WebSQL data.
   
     |options| - See https://developer.chrome.com/extensions/browsingData#property-removeWebSQL-options.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/browsingData#method-removeWebSQL."
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