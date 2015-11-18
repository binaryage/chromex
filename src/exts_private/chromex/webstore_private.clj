(ns chromex.webstore-private
  "  * available since Chrome 7
     * https://developer.chrome.com/extensions/webstorePrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro install
  "Installs the extension corresponding to the given id
   
     |expected_id| - The id of the extension to install."
  [expected-id #_callback]
  (gen-call :function ::install (meta &form) expected-id))

(defmacro begin-install-with-manifest3
  "Initiates the install process for the given extension.
   
     |callback| - Called when the user has either accepted/rejected the dialog, or some error occurred (such as
                  invalid manifest or icon image data)."
  [details #_callback]
  (gen-call :function ::begin-install-with-manifest3 (meta &form) details))

(defmacro complete-install
  "  |expected_id| - The id of the extension to be installed. This should match a previous call to
                     beginInstallWithManifest3."
  [expected-id #_callback]
  (gen-call :function ::complete-install (meta &form) expected-id))

(defmacro install-bundle
  "Initiates the install process for the given bundle of extensions.
   
     |contents| - An array of extension details to be installed.
     |callback| - Called when the install process completes. Upon failures, chrome.runtime.lastError will be set to
                  'Invalid bundle', 'Invalid icon url', 'This item is already installed', or 'User cancelled
                  install'."
  [details contents #_callback]
  (gen-call :function ::install-bundle (meta &form) details contents))

(defmacro enable-app-launcher [#_callback]
  (gen-call :function ::enable-app-launcher (meta &form)))

(defmacro get-browser-login
  "Returns the logged-in sync user login if there is one, or the empty string otherwise."
  [#_callback]
  (gen-call :function ::get-browser-login (meta &form)))

(defmacro get-store-login
  "Returns the previous value set by setStoreLogin, or the empty string if there is none."
  [#_callback]
  (gen-call :function ::get-store-login (meta &form)))

(defmacro set-store-login
  "Sets a preference value with the store login."
  [login #_callback]
  (gen-call :function ::set-store-login (meta &form) login))

(defmacro get-web-gl-status
  "Invokes a callback that returns whether WebGL is blacklisted or not."
  [#_callback]
  (gen-call :function ::get-web-gl-status (meta &form)))

(defmacro get-is-launcher-enabled
  "Returns whether the apps launcher is enabled or not."
  [#_callback]
  (gen-call :function ::get-is-launcher-enabled (meta &form)))

(defmacro is-in-incognito-mode
  "Returns whether the browser is in incognito mode or not."
  [#_callback]
  (gen-call :function ::is-in-incognito-mode (meta &form)))

(defmacro get-ephemeral-apps-enabled
  "Returns whether the ephemeral apps feature is enabled."
  [#_callback]
  (gen-call :function ::get-ephemeral-apps-enabled (meta &form)))

(defmacro launch-ephemeral-app
  "Installs an app ephemerally in Chrome (if not already fully installed) and launches the app. A user gesture is
   required.
   
     |id| - The extension id of the app to launch."
  [id #_callback]
  (gen-call :function ::launch-ephemeral-app (meta &form) id))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webstorePrivate",
   :since "7",
   :functions
   [{:id ::install,
     :name "install",
     :callback? true,
     :params [{:name "expected-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::begin-install-with-manifest3,
     :name "beginInstallWithManifest3",
     :since "16",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "webstorePrivate.Result"}]}}]}
    {:id ::complete-install,
     :name "completeInstall",
     :since "8",
     :callback? true,
     :params [{:name "expected-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::install-bundle,
     :name "installBundle",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "contents", :type "[array-of-objects]"}
      {:name "callback", :type :callback}]}
    {:id ::enable-app-launcher,
     :name "enableAppLauncher",
     :since "28",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::get-browser-login,
     :name "getBrowserLogin",
     :since "8",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::get-store-login,
     :name "getStoreLogin",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "login", :type "string"}]}}]}
    {:id ::set-store-login,
     :name "setStoreLogin",
     :callback? true,
     :params [{:name "login", :type "string"} {:name "callback", :type :callback}]}
    {:id ::get-web-gl-status,
     :name "getWebGLStatus",
     :since "18",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "webgl-status", :type "webstorePrivate.WebGlStatus"}]}}]}
    {:id ::get-is-launcher-enabled,
     :name "getIsLauncherEnabled",
     :since "26",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::is-in-incognito-mode,
     :name "isInIncognitoMode",
     :since "30",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-incognito", :type "boolean"}]}}]}
    {:id ::get-ephemeral-apps-enabled,
     :name "getEphemeralAppsEnabled",
     :since "38",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::launch-ephemeral-app,
     :name "launchEphemeralApp",
     :since "38",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "webstorePrivate.Result"}]}}]}]})

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