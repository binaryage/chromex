(ns chromex.app.management
  "The chrome.management API provides ways to manage the list of extensions/apps that are installed and running. It is
   particularly useful for extensions that override the built-in New Tab page.

     * available since Chrome 38
     * https://developer.chrome.com/apps/management"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-all
  "Returns a list of information about installed extensions and apps.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/management#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro get
  "Returns information about the installed extension, app, or theme that has the given ID.

     |id| - The ID from an item of 'management.ExtensionInfo'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/management#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-get."
  ([id] (gen-call :function ::get &form id)))

(defmacro get-self
  "Returns information about the calling extension, app, or theme. Note: This function can be used without requesting the
   'management' permission in the manifest.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/management#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-getSelf."
  ([] (gen-call :function ::get-self &form)))

(defmacro get-permission-warnings-by-id
  "Returns a list of permission warnings for the given extension id.

     |id| - The ID of an already installed extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [permission-warnings] where:

     |permission-warnings| - https://developer.chrome.com/apps/management#property-callback-permissionWarnings.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-getPermissionWarningsById."
  ([id] (gen-call :function ::get-permission-warnings-by-id &form id)))

(defmacro get-permission-warnings-by-manifest
  "Returns a list of permission warnings for the given extension manifest string. Note: This function can be used without
   requesting the 'management' permission in the manifest.

     |manifest-str| - Extension manifest JSON string.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [permission-warnings] where:

     |permission-warnings| - https://developer.chrome.com/apps/management#property-callback-permissionWarnings.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-getPermissionWarningsByManifest."
  ([manifest-str] (gen-call :function ::get-permission-warnings-by-manifest &form manifest-str)))

(defmacro set-enabled
  "Enables or disables an app or extension. In most cases this function must be called in the context of a user gesture (e.g.
   an onclick handler for a button), and may present the user with a native confirmation UI as a way of preventing abuse.

     |id| - This should be the id from an item of 'management.ExtensionInfo'.
     |enabled| - Whether this item should be enabled or disabled.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-setEnabled."
  ([id enabled] (gen-call :function ::set-enabled &form id enabled)))

(defmacro uninstall
  "Uninstalls a currently installed app or extension.

     |id| - This should be the id from an item of 'management.ExtensionInfo'.
     |options| - https://developer.chrome.com/apps/management#property-uninstall-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-uninstall."
  ([id options] (gen-call :function ::uninstall &form id options))
  ([id] `(uninstall ~id :omit)))

(defmacro uninstall-self
  "Uninstalls the calling extension. Note: This function can be used without requesting the 'management' permission in the
   manifest.

     |options| - https://developer.chrome.com/apps/management#property-uninstallSelf-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-uninstallSelf."
  ([options] (gen-call :function ::uninstall-self &form options))
  ([] `(uninstall-self :omit)))

(defmacro launch-app
  "Launches an application.

     |id| - The extension id of the application.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-launchApp."
  ([id] (gen-call :function ::launch-app &form id)))

(defmacro create-app-shortcut
  "Display options to create shortcuts for an app. On Mac, only packaged app shortcuts can be created.

     |id| - This should be the id from an app item of 'management.ExtensionInfo'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-createAppShortcut."
  ([id] (gen-call :function ::create-app-shortcut &form id)))

(defmacro set-launch-type
  "Set the launch type of an app.

     |id| - This should be the id from an app item of 'management.ExtensionInfo'.
     |launch-type| - The target launch type. Always check and make sure this launch type is in
                     'ExtensionInfo.availableLaunchTypes', because the available launch types vary on different platforms
                     and configurations.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-setLaunchType."
  ([id launch-type] (gen-call :function ::set-launch-type &form id launch-type)))

(defmacro generate-app-for-link
  "Generate an app for a URL. Returns the generated bookmark app.

     |url| - The URL of a web page. The scheme of the URL can only be 'http' or 'https'.
     |title| - The title of the generated app.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/management#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-generateAppForLink."
  ([url title] (gen-call :function ::generate-app-for-link &form url title)))

(defmacro can-install-replacement-android-app
  "Checks if the replacement android app can be installed. Errors generated by this API are reported by setting
   'runtime.lastError' and executing the function's regular callback.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/management#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-canInstallReplacementAndroidApp."
  ([] (gen-call :function ::can-install-replacement-android-app &form)))

(defmacro install-replacement-android-app
  "Prompts the user to install the replacement Android app from the manifest. Errors generated by this API are reported by
   setting 'runtime.lastError' and executing the function's regular callback.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-installReplacementAndroidApp."
  ([] (gen-call :function ::install-replacement-android-app &form)))

(defmacro install-replacement-web-app
  "Launches the replacement_web_app specified in the manifest. Prompts the user to install if not already installed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/management#method-installReplacementWebApp."
  ([] (gen-call :function ::install-replacement-web-app &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-installed-events
  "Fired when an app or extension has been installed.

   Events will be put on the |channel| with signature [::on-installed [info]] where:

     |info| - https://developer.chrome.com/apps/management#property-onInstalled-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/management#event-onInstalled."
  ([channel & args] (apply gen-call :event ::on-installed &form channel args)))

(defmacro tap-on-uninstalled-events
  "Fired when an app or extension has been uninstalled.

   Events will be put on the |channel| with signature [::on-uninstalled [id]] where:

     |id| - The id of the extension, app, or theme that was uninstalled.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/management#event-onUninstalled."
  ([channel & args] (apply gen-call :event ::on-uninstalled &form channel args)))

(defmacro tap-on-enabled-events
  "Fired when an app or extension has been enabled.

   Events will be put on the |channel| with signature [::on-enabled [info]] where:

     |info| - https://developer.chrome.com/apps/management#property-onEnabled-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/management#event-onEnabled."
  ([channel & args] (apply gen-call :event ::on-enabled &form channel args)))

(defmacro tap-on-disabled-events
  "Fired when an app or extension has been disabled.

   Events will be put on the |channel| with signature [::on-disabled [info]] where:

     |info| - https://developer.chrome.com/apps/management#property-onDisabled-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/management#event-onDisabled."
  ([channel & args] (apply gen-call :event ::on-disabled &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.management namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.management",
   :since "38",
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
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "permission-warnings", :type "[array-of-strings]"}]}}]}
    {:id ::get-permission-warnings-by-manifest,
     :name "getPermissionWarningsByManifest",
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
      {:name "options", :optional? true, :type "management.UninstallOptions"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::uninstall-self,
     :name "uninstallSelf",
     :callback? true,
     :params
     [{:name "options", :optional? true, :type "management.UninstallOptions"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::launch-app,
     :name "launchApp",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::create-app-shortcut,
     :name "createAppShortcut",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-launch-type,
     :name "setLaunchType",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "launch-type", :type "management.LaunchType"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::generate-app-for-link,
     :name "generateAppForLink",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "title", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "management.ExtensionInfo"}]}}]}
    {:id ::can-install-replacement-android-app,
     :name "canInstallReplacementAndroidApp",
     :since "78",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::install-replacement-android-app,
     :name "installReplacementAndroidApp",
     :since "78",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::install-replacement-web-app,
     :name "installReplacementWebApp",
     :since "75",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-installed, :name "onInstalled", :params [{:name "info", :type "management.ExtensionInfo"}]}
    {:id ::on-uninstalled, :name "onUninstalled", :params [{:name "id", :type "string"}]}
    {:id ::on-enabled, :name "onEnabled", :params [{:name "info", :type "management.ExtensionInfo"}]}
    {:id ::on-disabled, :name "onDisabled", :params [{:name "info", :type "management.ExtensionInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))