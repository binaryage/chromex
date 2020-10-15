(ns chromex.ext.webstore-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro install
  "Installs the extension corresponding to the given id

     |expected-id| - The id of the extension to install.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([expected-id] (gen-call :function ::install &form expected-id)))

(defmacro begin-install-with-manifest3
  "Initiates the install process for the given extension.

     |details| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - A string result code, which will be empty upon success. The possible values in the case of errors include
                'unknown_error', 'user_cancelled', 'manifest_error', 'icon_error', 'invalid_id', 'permission_denied',
                'invalid_icon_url' and 'already_installed'.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([details] (gen-call :function ::begin-install-with-manifest3 &form details)))

(defmacro complete-install
  "  |expected-id| - The id of the extension to be installed. This should match a previous call to beginInstallWithManifest3.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([expected-id] (gen-call :function ::complete-install &form expected-id)))

(defmacro enable-app-launcher
  "This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::enable-app-launcher &form)))

(defmacro get-browser-login
  "Returns the logged-in sync user login if there is one, or the empty string otherwise.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-browser-login &form)))

(defmacro get-store-login
  "Returns the previous value set by setStoreLogin, or the empty string if there is none.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [login] where:

     |login| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-store-login &form)))

(defmacro set-store-login
  "Sets a preference value with the store login.

     |login| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([login] (gen-call :function ::set-store-login &form login)))

(defmacro get-web-gl-status
  "Invokes a callback that returns whether WebGL is blacklisted or not.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [webgl-status] where:

     |webgl-status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-web-gl-status &form)))

(defmacro get-is-launcher-enabled
  "Returns whether the apps launcher is enabled or not.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-enabled] where:

     |is-enabled| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-is-launcher-enabled &form)))

(defmacro is-in-incognito-mode
  "Returns whether the browser is in incognito mode or not.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-incognito] where:

     |is-incognito| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-in-incognito-mode &form)))

(defmacro get-ephemeral-apps-enabled
  "Returns whether the ephemeral apps feature is enabled.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-enabled] where:

     |is-enabled| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-ephemeral-apps-enabled &form)))

(defmacro launch-ephemeral-app
  "Installs an app ephemerally in Chrome (if not already fully installed) and launches the app. A user gesture is required.

     |id| - The extension id of the app to launch.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Whether an attempt to launch an app succeeded, or the reason for failure.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::launch-ephemeral-app &form id)))

(defmacro is-pending-custodian-approval
  "Checks if an extension installed on a Supervised User profile is pending custodian approval.

     |id| - The extension id of the extension to be checked.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-pending-approval] where:

     |is-pending-approval| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::is-pending-custodian-approval &form id)))

(defmacro get-referrer-chain
  "Returns a base-64 encoded referrer chain leading to the webstore page. Should only be used for extension anti-abuse
   purposes.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [referrer-chain] where:

     |referrer-chain| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-referrer-chain &form)))

(defmacro get-extension-status
  "Returns the install status of the extension.

     |id| - The id of the extension
     |manifest| - The manifest of the extension

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id manifest] (gen-call :function ::get-extension-status &form id manifest))
  ([id] `(get-extension-status ~id :omit)))

(defmacro request-extension
  "Ask Chrome to send the extension request to the Admin Console.

     |id| - The id of the extension to be requested. The webstore should call this after a call to getExtensionStatus

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::request-extension &form id)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.webstore-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webstorePrivate",
   :since "38",
   :functions
   [{:id ::install,
     :name "install",
     :callback? true,
     :params [{:name "expected-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::begin-install-with-manifest3,
     :name "beginInstallWithManifest3",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "webstorePrivate.Result"}]}}]}
    {:id ::complete-install,
     :name "completeInstall",
     :callback? true,
     :params [{:name "expected-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::enable-app-launcher,
     :name "enableAppLauncher",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-browser-login,
     :name "getBrowserLogin",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::get-store-login,
     :name "getStoreLogin",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "login", :type "string"}]}}]}
    {:id ::set-store-login,
     :name "setStoreLogin",
     :callback? true,
     :params [{:name "login", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-web-gl-status,
     :name "getWebGLStatus",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "webgl-status", :type "webstorePrivate.WebGlStatus"}]}}]}
    {:id ::get-is-launcher-enabled,
     :name "getIsLauncherEnabled",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::is-in-incognito-mode,
     :name "isInIncognitoMode",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-incognito", :type "boolean"}]}}]}
    {:id ::get-ephemeral-apps-enabled,
     :name "getEphemeralAppsEnabled",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::launch-ephemeral-app,
     :name "launchEphemeralApp",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "webstorePrivate.Result"}]}}]}
    {:id ::is-pending-custodian-approval,
     :name "isPendingCustodianApproval",
     :since "54",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "is-pending-approval", :type "boolean"}]}}]}
    {:id ::get-referrer-chain,
     :name "getReferrerChain",
     :since "68",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "referrer-chain", :type "string"}]}}]}
    {:id ::get-extension-status,
     :name "getExtensionStatus",
     :since "80",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "manifest", :optional? true, :since "85", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "webstorePrivate.ExtensionInstallStatus"}]}}]}
    {:id ::request-extension,
     :name "requestExtension",
     :since "80",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "status", :type "webstorePrivate.ExtensionInstallStatus"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))