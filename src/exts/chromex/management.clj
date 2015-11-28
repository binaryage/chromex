(ns chromex.management
  "The chrome.management API provides ways to manage the list of extensions/apps that are installed and running. It is
   particularly useful for extensions that override the built-in New Tab page.
   
     * available since Chrome 8
     * https://developer.chrome.com/extensions/management"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-all
  "Returns a list of information about installed extensions and apps.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-all &form)))

(defmacro get
  "Returns information about the installed extension, app, or theme that has the given ID.
   
     |id| - The ID from an item of 'management.ExtensionInfo'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id #_callback] (gen-call :function ::get &form id)))

(defmacro get-self
  "Returns information about the calling extension, app, or theme. Note: This function can be used without requesting the
   'management' permission in the manifest.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-self &form)))

(defmacro get-permission-warnings-by-id
  "Returns a list of permission warnings for the given extension id.
   
     |id| - The ID of an already installed extension.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id #_callback] (gen-call :function ::get-permission-warnings-by-id &form id)))

(defmacro get-permission-warnings-by-manifest
  "Returns a list of permission warnings for the given extension manifest string. Note: This function can be used without
   requesting the 'management' permission in the manifest.
   
     |manifestStr| - Extension manifest JSON string.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([manifest-str #_callback] (gen-call :function ::get-permission-warnings-by-manifest &form manifest-str)))

(defmacro set-enabled
  "Enables or disables an app or extension.
   
     |id| - This should be the id from an item of 'management.ExtensionInfo'.
     |enabled| - Whether this item should be enabled or disabled.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id enabled #_callback] (gen-call :function ::set-enabled &form id enabled)))

(defmacro uninstall
  "Uninstalls a currently installed app or extension.
   
     |id| - This should be the id from an item of 'management.ExtensionInfo'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id options #_callback] (gen-call :function ::uninstall &form id options))
  ([id] `(uninstall ~id :omit)))

(defmacro uninstall-self
  "Uninstalls the calling extension. Note: This function can be used without requesting the 'management' permission in the
   manifest.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::uninstall-self &form options))
  ([] `(uninstall-self :omit)))

(defmacro launch-app
  "Launches an application.
   
     |id| - The extension id of the application.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id #_callback] (gen-call :function ::launch-app &form id)))

(defmacro create-app-shortcut
  "Display options to create shortcuts for an app. On Mac, only packaged app shortcuts can be created.
   
     |id| - This should be the id from an app item of 'management.ExtensionInfo'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id #_callback] (gen-call :function ::create-app-shortcut &form id)))

(defmacro set-launch-type
  "Set the launch type of an app.
   
     |id| - This should be the id from an app item of 'management.ExtensionInfo'.
     |launchType| - The target launch type. Always check and make sure this launch type is in
                    'ExtensionInfo.availableLaunchTypes', because the available launch types vary on different platforms and
                    configurations.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id launch-type #_callback] (gen-call :function ::set-launch-type &form id launch-type)))

(defmacro generate-app-for-link
  "Generate an app for a URL. Returns the generated bookmark app.
   
     |url| - The URL of a web page. The scheme of the URL can only be 'http' or 'https'.
     |title| - The title of the generated app.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url title #_callback] (gen-call :function ::generate-app-for-link &form url title)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-installed-events
  "Fired when an app or extension has been installed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-installed &form channel args)))
(defmacro tap-on-uninstalled-events
  "Fired when an app or extension has been uninstalled.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-uninstalled &form channel args)))
(defmacro tap-on-enabled-events
  "Fired when an app or extension has been enabled.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-enabled &form channel args)))
(defmacro tap-on-disabled-events
  "Fired when an app or extension has been disabled.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-disabled &form channel args)))

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
  {:namespace "chrome.management",
   :since "8",
   :functions
   [{:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-management.ExtensionInfos]"}]}}]}
    {:id ::get,
     :name "get",
     :since "9",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "management.ExtensionInfo"}]}}]}
    {:id ::get-self,
     :name "getSelf",
     :since "39",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "management.ExtensionInfo"}]}}]}
    {:id ::get-permission-warnings-by-id,
     :name "getPermissionWarningsById",
     :since "15",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "permission-warnings", :type "[array-of-strings]"}]}}]}
    {:id ::get-permission-warnings-by-manifest,
     :name "getPermissionWarningsByManifest",
     :since "15",
     :callback? true,
     :params
     [{:name "manifest-str", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "permission-warnings", :type "[array-of-strings]"}]}}]}
    {:id ::set-enabled,
     :name "setEnabled",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "enabled", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::uninstall,
     :name "uninstall",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::uninstall-self,
     :name "uninstallSelf",
     :since "26",
     :callback? true,
     :params [{:name "options", :optional? true, :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::launch-app,
     :name "launchApp",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::create-app-shortcut,
     :name "createAppShortcut",
     :since "37",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-launch-type,
     :name "setLaunchType",
     :since "37",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "launch-type", :type "management.LaunchType"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::generate-app-for-link,
     :name "generateAppForLink",
     :since "37",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "title", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "management.ExtensionInfo"}]}}]}],
   :events
   [{:id ::on-installed, :name "onInstalled", :params [{:name "info", :type "management.ExtensionInfo"}]}
    {:id ::on-uninstalled, :name "onUninstalled", :params [{:name "id", :type "string"}]}
    {:id ::on-enabled, :name "onEnabled", :params [{:name "info", :type "management.ExtensionInfo"}]}
    {:id ::on-disabled, :name "onDisabled", :params [{:name "info", :type "management.ExtensionInfo"}]}]})

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