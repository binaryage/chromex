(ns chromex.runtime
  "Use the chrome.runtime API to retrieve the background page, return details about the manifest, and listen for and
   respond to events in the app or extension lifecycle. You can also use this API to convert the relative path of
   URLs to fully-qualified URLs.
   
     * available since Chrome 22
     * https://developer.chrome.com/extensions/runtime"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-last-error
  "This will be defined during an API method callback if there was an error"
  []
  (gen-call :property ::last-error (meta &form)))

(defmacro get-id
  "The ID of the extension/app."
  []
  (gen-call :property ::id (meta &form)))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-background-page
  "Retrieves the JavaScript 'window' object for the background page running inside the current extension/app. If the
   background page is an event page, the system will ensure it is loaded before calling the callback. If there is no
   background page, an error is set."
  [#_callback]
  (gen-call :function ::get-background-page (meta &form)))

(defmacro open-options-page
  "Open your Extension's options page, if possible.The precise behavior may depend on your manifest's options_ui or
   options_page key, or what Chrome happens to support at the time. For example, the page may be opened in a new tab,
   within chrome://extensions, within an App, or it may just focus an open options page. It will never cause the
   caller page to reload.If your Extension does not declare an options page, or Chrome failed to create one for some
   other reason, the callback will set 'lastError'."
  [#_callback]
  (gen-call :function ::open-options-page (meta &form)))

(defmacro get-manifest
  "Returns details about the app or extension from the manifest. The object returned is a serialization of the full
   manifest file."
  []
  (gen-call :function ::get-manifest (meta &form)))

(defmacro get-url
  "Converts a relative path within an app/extension install directory to a fully-qualified URL.
   
     |path| - A path to a resource within an app/extension expressed relative to its install directory."
  [path]
  (gen-call :function ::get-url (meta &form) path))

(defmacro set-uninstall-url
  "Sets the URL to be visited upon uninstallation. This may be used to clean up server-side data, do analytics, and
   implement surveys. Maximum 255 characters.
   
     |url| - URL to be opened after the extension is uninstalled. This URL must have an http: or https: scheme. Set
             an empty string to not open a new tab upon uninstallation.
     |callback| - Called when the uninstall URL is set. If the given URL is invalid, 'runtime.lastError' will be set."
  [url #_callback]
  (gen-call :function ::set-uninstall-url (meta &form) url))

(defmacro reload
  "Reloads the app or extension. This method is not supported in kiosk mode. For kiosk mode, use
   chrome.runtime.restart() method."
  []
  (gen-call :function ::reload (meta &form)))

(defmacro request-update-check
  "Requests an update check for this app/extension."
  [#_callback]
  (gen-call :function ::request-update-check (meta &form)))

(defmacro restart
  "Restart the ChromeOS device when the app runs in kiosk mode. Otherwise, it's no-op."
  []
  (gen-call :function ::restart (meta &form)))

(defmacro connect
  "Attempts to connect to connect listeners within an extension/app (such as the background page), or other
   extensions/apps. This is useful for content scripts connecting to their extension processes, inter-app/extension
   communication, and web messaging. Note that this does not connect to any listeners in a content script. Extensions
   may connect to content scripts embedded in tabs via tabs.connect.
   
     |extensionId| - The ID of the extension or app to connect to. If omitted, a connection will be attempted with
                     your own extension. Required if sending messages from a web page for web messaging."
  [extension-id connect-info]
  (gen-call :function ::connect (meta &form) extension-id connect-info))

(defmacro connect-native
  "Connects to a native application in the host machine.
   
     |application| - The name of the registered application to connect to."
  [application]
  (gen-call :function ::connect-native (meta &form) application))

(defmacro send-message
  "Sends a single message to event listeners within your extension/app or a different extension/app. Similar to
   'runtime.connect' but only sends a single message, with an optional response. If sending to your extension, the
   'runtime.onMessage' event will be fired in each page, or 'runtime.onMessageExternal', if a different extension.
   Note that extensions cannot send messages to content scripts using this method. To send messages to content
   scripts, use tabs.sendMessage.
   
     |extensionId| - The ID of the extension/app to send the message to. If omitted, the message will be sent to
                     your own extension/app. Required if sending messages from a web page for web messaging."
  [extension-id message options #_response-callback]
  (gen-call :function ::send-message (meta &form) extension-id message options))

(defmacro send-native-message
  "Send a single message to a native application.
   
     |application| - The name of the native messaging host.
     |message| - The message that will be passed to the native messaging host."
  [application message #_response-callback]
  (gen-call :function ::send-native-message (meta &form) application message))

(defmacro get-platform-info
  "Returns information about the current platform.
   
     |callback| - Called with results"
  [#_callback]
  (gen-call :function ::get-platform-info (meta &form)))

(defmacro get-package-directory-entry
  "Returns a DirectoryEntry for the package directory."
  [#_callback]
  (gen-call :function ::get-package-directory-entry (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-startup
  "Fired when a profile that has this extension installed first starts up. This event is not fired when an incognito
   profile is started, even if this extension is operating in 'split' incognito mode."
  [channel]
  (gen-call :event ::on-startup (meta &form) channel))

(defmacro tap-on-installed
  "Fired when the extension is first installed, when the extension is updated to a new version, and when Chrome is
   updated to a new version."
  [channel]
  (gen-call :event ::on-installed (meta &form) channel))

(defmacro tap-on-suspend
  "Sent to the event page just before it is unloaded. This gives the extension opportunity to do some clean up. Note
   that since the page is unloading, any asynchronous operations started while handling this event are not guaranteed
   to complete. If more activity for the event page occurs before it gets unloaded the onSuspendCanceled event will be
   sent and the page won't be unloaded."
  [channel]
  (gen-call :event ::on-suspend (meta &form) channel))

(defmacro tap-on-suspend-canceled
  "Sent after onSuspend to indicate that the app won't be unloaded after all."
  [channel]
  (gen-call :event ::on-suspend-canceled (meta &form) channel))

(defmacro tap-on-update-available
  "Fired when an update is available, but isn't installed immediately because the app is currently running. If you do
   nothing, the update will be installed the next time the background page gets unloaded, if you want it to be
   installed sooner you can explicitly call chrome.runtime.reload(). If your extension is using a persistent
   background page, the background page of course never gets unloaded, so unless you call chrome.runtime.reload()
   manually in response to this event the update will not get installed until the next time chrome itself restarts. If
   no handlers are listening for this event, and your extension has a persistent background page, it behaves as if
   chrome.runtime.reload() is called in response to this event."
  [channel]
  (gen-call :event ::on-update-available (meta &form) channel))

(defmacro tap-on-browser-update-available
  "Fired when a Chrome update is available, but isn't installed immediately because a browser restart is required."
  [channel]
  (gen-call :event ::on-browser-update-available (meta &form) channel))

(defmacro tap-on-connect
  "Fired when a connection is made from either an extension process or a content script."
  [channel]
  (gen-call :event ::on-connect (meta &form) channel))

(defmacro tap-on-connect-external
  "Fired when a connection is made from another extension."
  [channel]
  (gen-call :event ::on-connect-external (meta &form) channel))

(defmacro tap-on-message
  "Fired when a message is sent from either an extension process or a content script."
  [channel]
  (gen-call :event ::on-message (meta &form) channel))

(defmacro tap-on-message-external
  "Fired when a message is sent from another extension/app. Cannot be used in a content script."
  [channel]
  (gen-call :event ::on-message-external (meta &form) channel))

(defmacro tap-on-restart-required
  "Fired when an app or the device that it runs on needs to be restarted. The app should close all its windows at its
   earliest convenient time to let the restart to happen. If the app does nothing, a restart will be enforced after a
   24-hour grace period has passed. Currently, this event is only fired for Chrome OS kiosk apps."
  [channel]
  (gen-call :event ::on-restart-required (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.runtime",
   :since "22",
   :properties
   [{:id ::last-error, :name "lastError", :return-type "object"} {:id ::id, :name "id", :return-type "string"}],
   :functions
   [{:id ::get-background-page,
     :name "getBackgroundPage",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "background-page", :type "Window"}]}}]}
    {:id ::open-options-page,
     :name "openOptionsPage",
     :since "42",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::get-manifest, :name "getManifest", :return-type "object"}
    {:id ::get-url, :name "getURL", :return-type "string", :params [{:name "path", :type "string"}]}
    {:id ::set-uninstall-url,
     :name "setUninstallURL",
     :since "41",
     :callback? true,
     :params [{:name "url", :type "string"} {:name "callback", :type :callback}]}
    {:id ::reload, :name "reload", :since "25"}
    {:id ::request-update-check,
     :name "requestUpdateCheck",
     :since "25",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "status", :type "runtime.RequestUpdateCheckStatus"} {:name "details", :type "object"}]}}]}
    {:id ::restart, :name "restart", :since "32"}
    {:id ::connect,
     :name "connect",
     :since "26",
     :return-type "runtime.Port",
     :params [{:name "extension-id", :type "string"} {:name "connect-info", :type "object"}]}
    {:id ::connect-native,
     :name "connectNative",
     :since "28",
     :return-type "runtime.Port",
     :params [{:name "application", :type "string"}]}
    {:id ::send-message,
     :name "sendMessage",
     :since "26",
     :callback? true,
     :params
     [{:name "extension-id", :type "string"}
      {:name "message", :type "any"}
      {:name "options", :type "object"}
      {:name "response-callback", :type :callback, :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::send-native-message,
     :name "sendNativeMessage",
     :since "28",
     :callback? true,
     :params
     [{:name "application", :type "string"}
      {:name "message", :type "object"}
      {:name "response-callback", :type :callback, :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-platform-info,
     :name "getPlatformInfo",
     :since "29",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "platform-info", :type "runtime.PlatformInfo"}]}}]}
    {:id ::get-package-directory-entry,
     :name "getPackageDirectoryEntry",
     :since "29",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "directory-entry", :type "DirectoryEntry"}]}}]}],
   :events
   [{:id ::on-startup, :name "onStartup", :since "23"}
    {:id ::on-installed, :name "onInstalled", :params [{:name "details", :type "object"}]}
    {:id ::on-suspend, :name "onSuspend"}
    {:id ::on-suspend-canceled, :name "onSuspendCanceled"}
    {:id ::on-update-available,
     :name "onUpdateAvailable",
     :since "25",
     :params [{:name "details", :type "object"}]}
    {:id ::on-browser-update-available,
     :name "onBrowserUpdateAvailable",
     :since "33",
     :deprecated "Please use 'runtime.onRestartRequired'."}
    {:id ::on-connect, :name "onConnect", :since "26", :params [{:name "port", :type "runtime.Port"}]}
    {:id ::on-connect-external,
     :name "onConnectExternal",
     :since "26",
     :params [{:name "port", :type "runtime.Port"}]}
    {:id ::on-message,
     :name "onMessage",
     :since "26",
     :params
     [{:name "message", :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-message-external,
     :name "onMessageExternal",
     :since "26",
     :params
     [{:name "message", :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-restart-required,
     :name "onRestartRequired",
     :since "29",
     :params [{:name "reason", :type "runtime.OnRestartRequiredReason"}]}]})

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