(ns chromex.app.runtime
  "Use the chrome.runtime API to retrieve the background page, return details about the manifest, and listen for and respond
   to events in the app or extension lifecycle. You can also use this API to convert the relative path of URLs to
   fully-qualified URLs.

     * available since Chrome 38
     * https://developer.chrome.com/apps/runtime"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-last-error
  "This will be defined during an API method callback if there was an error

   https://developer.chrome.com/apps/runtime#property-lastError."
  ([] (gen-call :property ::last-error &form)))

(defmacro get-id
  "The ID of the extension/app.

   https://developer.chrome.com/apps/runtime#property-id."
  ([] (gen-call :property ::id &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-background-page
  "Retrieves the JavaScript 'window' object for the background page running inside the current extension/app. If the
   background page is an event page, the system will ensure it is loaded before calling the callback. If there is no
   background page, an error is set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [background-page] where:

     |background-page| - The JavaScript 'window' object for the background page.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-getBackgroundPage."
  ([] (gen-call :function ::get-background-page &form)))

(defmacro open-options-page
  "Open your Extension's options page, if possible.The precise behavior may depend on your manifest's options_ui or
   options_page key, or what Chrome happens to support at the time. For example, the page may be opened in a new tab, within
   chrome://extensions, within an App, or it may just focus an open options page. It will never cause the caller page to
   reload.If your Extension does not declare an options page, or Chrome failed to create one for some other reason, the
   callback will set 'lastError'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-openOptionsPage."
  ([] (gen-call :function ::open-options-page &form)))

(defmacro get-manifest
  "Returns details about the app or extension from the manifest. The object returned is a serialization of the full manifest
   file.

   https://developer.chrome.com/apps/runtime#method-getManifest."
  ([] (gen-call :function ::get-manifest &form)))

(defmacro get-url
  "Converts a relative path within an app/extension install directory to a fully-qualified URL.

     |path| - A path to a resource within an app/extension expressed relative to its install directory.

   https://developer.chrome.com/apps/runtime#method-getURL."
  ([path] (gen-call :function ::get-url &form path)))

(defmacro set-uninstall-url
  "Sets the URL to be visited upon uninstallation. This may be used to clean up server-side data, do analytics, and implement
   surveys. Maximum 255 characters.

     |url| - URL to be opened after the extension is uninstalled. This URL must have an http: or https: scheme. Set an empty
             string to not open a new tab upon uninstallation.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-setUninstallURL."
  ([url] (gen-call :function ::set-uninstall-url &form url)))

(defmacro reload
  "Reloads the app or extension. This method is not supported in kiosk mode. For kiosk mode, use chrome.runtime.restart()
   method.

   https://developer.chrome.com/apps/runtime#method-reload."
  ([] (gen-call :function ::reload &form)))

(defmacro request-update-check
  "Requests an immediate update check be done for this app/extension. Important: Most extensions/apps should not use this
   method, since chrome already does automatic checks every few hours, and you can listen for the 'runtime.onUpdateAvailable'
   event without needing to call requestUpdateCheck.This method is only appropriate to call in very limited circumstances,
   such as if your extension/app talks to a backend service, and the backend service has determined that the client
   extension/app version is very far out of date and you'd like to prompt a user to update. Most other uses of
   requestUpdateCheck, such as calling it unconditionally based on a repeating timer, probably only serve to waste client,
   network, and server resources.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status details] where:

     |status| - Result of the update check.
     |details| - If an update is available, this contains more information about the available update.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-requestUpdateCheck."
  ([] (gen-call :function ::request-update-check &form)))

(defmacro restart
  "Restart the ChromeOS device when the app runs in kiosk mode. Otherwise, it's no-op.

   https://developer.chrome.com/apps/runtime#method-restart."
  ([] (gen-call :function ::restart &form)))

(defmacro restart-after-delay
  "Restart the ChromeOS device when the app runs in kiosk mode after the given seconds. If called again before the time ends,
   the reboot will be delayed. If called with a value of -1, the reboot will be cancelled. It's a no-op in non-kiosk mode.
   It's only allowed to be called repeatedly by the first extension to invoke this API.

     |seconds| - Time to wait in seconds before rebooting the device, or -1 to cancel a scheduled reboot.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-restartAfterDelay."
  ([seconds] (gen-call :function ::restart-after-delay &form seconds)))

(defmacro connect
  "Attempts to connect to connect listeners within an extension/app (such as the background page), or other extensions/apps.
   This is useful for content scripts connecting to their extension processes, inter-app/extension communication, and web
   messaging. Note that this does not connect to any listeners in a content script. Extensions may connect to content scripts
   embedded in tabs via 'tabs.connect'.

     |extension-id| - The ID of the extension or app to connect to. If omitted, a connection will be attempted with your own
                      extension. Required if sending messages from a web page for web messaging.
     |connect-info| - https://developer.chrome.com/apps/runtime#property-connect-connectInfo.

   https://developer.chrome.com/apps/runtime#method-connect."
  ([extension-id connect-info] (gen-call :function ::connect &form extension-id connect-info))
  ([extension-id] `(connect ~extension-id :omit))
  ([] `(connect :omit :omit)))

(defmacro connect-native
  "Connects to a native application in the host machine. See Native Messaging for more information.

     |application| - The name of the registered application to connect to.

   https://developer.chrome.com/apps/runtime#method-connectNative."
  ([application] (gen-call :function ::connect-native &form application)))

(defmacro send-message
  "Sends a single message to event listeners within your extension/app or a different extension/app. Similar to
   'runtime.connect' but only sends a single message, with an optional response. If sending to your extension, the
   'runtime.onMessage' event will be fired in every frame of your extension (except for the sender's frame), or
   'runtime.onMessageExternal', if a different extension. Note that extensions cannot send messages to content scripts using
   this method. To send messages to content scripts, use 'tabs.sendMessage'.

     |extension-id| - The ID of the extension/app to send the message to. If omitted, the message will be sent to your own
                      extension/app. Required if sending messages from a web page for web messaging.
     |message| - The message to send. This message should be a JSON-ifiable object.
     |options| - https://developer.chrome.com/apps/runtime#property-sendMessage-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The JSON response object sent by the handler of the message. If an error occurs while connecting to the
                  extension, the callback will be called with no arguments and 'runtime.lastError' will be set to the error
                  message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-sendMessage."
  ([extension-id message options] (gen-call :function ::send-message &form extension-id message options))
  ([extension-id message] `(send-message ~extension-id ~message :omit)))

(defmacro send-native-message
  "Send a single message to a native application.

     |application| - The name of the native messaging host.
     |message| - The message that will be passed to the native messaging host.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The response message sent by the native messaging host. If an error occurs while connecting to the native
                  messaging host, the callback will be called with no arguments and 'runtime.lastError' will be set to the
                  error message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-sendNativeMessage."
  ([application message] (gen-call :function ::send-native-message &form application message)))

(defmacro get-platform-info
  "Returns information about the current platform.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [platform-info] where:

     |platform-info| - https://developer.chrome.com/apps/runtime#property-callback-platformInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-getPlatformInfo."
  ([] (gen-call :function ::get-platform-info &form)))

(defmacro get-package-directory-entry
  "Returns a DirectoryEntry for the package directory.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [directory-entry] where:

     |directory-entry| - https://developer.chrome.com/apps/runtime#property-callback-directoryEntry.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/runtime#method-getPackageDirectoryEntry."
  ([] (gen-call :function ::get-package-directory-entry &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-startup-events
  "Fired when a profile that has this extension installed first starts up. This event is not fired when an incognito profile
   is started, even if this extension is operating in 'split' incognito mode.

   Events will be put on the |channel| with signature [::on-startup []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onStartup."
  ([channel & args] (apply gen-call :event ::on-startup &form channel args)))

(defmacro tap-on-installed-events
  "Fired when the extension is first installed, when the extension is updated to a new version, and when Chrome is updated to
   a new version.

   Events will be put on the |channel| with signature [::on-installed [details]] where:

     |details| - https://developer.chrome.com/apps/runtime#property-onInstalled-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onInstalled."
  ([channel & args] (apply gen-call :event ::on-installed &form channel args)))

(defmacro tap-on-suspend-events
  "Sent to the event page just before it is unloaded. This gives the extension opportunity to do some clean up. Note that
   since the page is unloading, any asynchronous operations started while handling this event are not guaranteed to complete.
   If more activity for the event page occurs before it gets unloaded the onSuspendCanceled event will be sent and the page
   won't be unloaded.

   Events will be put on the |channel| with signature [::on-suspend []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onSuspend."
  ([channel & args] (apply gen-call :event ::on-suspend &form channel args)))

(defmacro tap-on-suspend-canceled-events
  "Sent after onSuspend to indicate that the app won't be unloaded after all.

   Events will be put on the |channel| with signature [::on-suspend-canceled []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onSuspendCanceled."
  ([channel & args] (apply gen-call :event ::on-suspend-canceled &form channel args)))

(defmacro tap-on-update-available-events
  "Fired when an update is available, but isn't installed immediately because the app is currently running. If you do nothing,
   the update will be installed the next time the background page gets unloaded, if you want it to be installed sooner you can
   explicitly call chrome.runtime.reload(). If your extension is using a persistent background page, the background page of
   course never gets unloaded, so unless you call chrome.runtime.reload() manually in response to this event the update will
   not get installed until the next time chrome itself restarts. If no handlers are listening for this event, and your
   extension has a persistent background page, it behaves as if chrome.runtime.reload() is called in response to this event.

   Events will be put on the |channel| with signature [::on-update-available [details]] where:

     |details| - The manifest details of the available update.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onUpdateAvailable."
  ([channel & args] (apply gen-call :event ::on-update-available &form channel args)))

(defmacro tap-on-browser-update-available-events
  "Fired when a Chrome update is available, but isn't installed immediately because a browser restart is required.

   Events will be put on the |channel| with signature [::on-browser-update-available []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onBrowserUpdateAvailable."
  ([channel & args] (apply gen-call :event ::on-browser-update-available &form channel args)))

(defmacro tap-on-connect-events
  "Fired when a connection is made from either an extension process or a content script (by 'runtime.connect').

   Events will be put on the |channel| with signature [::on-connect [port]] where:

     |port| - https://developer.chrome.com/apps/runtime#property-onConnect-port.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onConnect."
  ([channel & args] (apply gen-call :event ::on-connect &form channel args)))

(defmacro tap-on-connect-external-events
  "Fired when a connection is made from another extension (by 'runtime.connect').

   Events will be put on the |channel| with signature [::on-connect-external [port]] where:

     |port| - https://developer.chrome.com/apps/runtime#property-onConnectExternal-port.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onConnectExternal."
  ([channel & args] (apply gen-call :event ::on-connect-external &form channel args)))

(defmacro tap-on-connect-native-events
  "Fired when a connection is made from a native application. Currently only supported on Chrome OS.

   Events will be put on the |channel| with signature [::on-connect-native [port]] where:

     |port| - https://developer.chrome.com/apps/runtime#property-onConnectNative-port.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onConnectNative."
  ([channel & args] (apply gen-call :event ::on-connect-native &form channel args)))

(defmacro tap-on-message-events
  "Fired when a message is sent from either an extension process (by 'runtime.sendMessage') or a content script (by
   'tabs.sendMessage').

   Events will be put on the |channel| with signature [::on-message [message sender send-response]] where:

     |message| - The message sent by the calling script.
     |sender| - https://developer.chrome.com/apps/runtime#property-onMessage-sender.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onMessage."
  ([channel & args] (apply gen-call :event ::on-message &form channel args)))

(defmacro tap-on-message-external-events
  "Fired when a message is sent from another extension/app (by 'runtime.sendMessage'). Cannot be used in a content script.

   Events will be put on the |channel| with signature [::on-message-external [message sender send-response]] where:

     |message| - The message sent by the calling script.
     |sender| - https://developer.chrome.com/apps/runtime#property-onMessageExternal-sender.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onMessageExternal."
  ([channel & args] (apply gen-call :event ::on-message-external &form channel args)))

(defmacro tap-on-restart-required-events
  "Fired when an app or the device that it runs on needs to be restarted. The app should close all its windows at its earliest
   convenient time to let the restart to happen. If the app does nothing, a restart will be enforced after a 24-hour grace
   period has passed. Currently, this event is only fired for Chrome OS kiosk apps.

   Events will be put on the |channel| with signature [::on-restart-required [reason]] where:

     |reason| - The reason that the event is being dispatched.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/runtime#event-onRestartRequired."
  ([channel & args] (apply gen-call :event ::on-restart-required &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.runtime namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.runtime",
   :since "38",
   :properties
   [{:id ::last-error, :name "lastError", :return-type "object"} {:id ::id, :name "id", :return-type "string"}],
   :functions
   [{:id ::get-background-page,
     :name "getBackgroundPage",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "background-page", :optional? true, :type "Window"}]}}]}
    {:id ::open-options-page,
     :name "openOptionsPage",
     :since "42",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-manifest, :name "getManifest", :return-type "object"}
    {:id ::get-url, :name "getURL", :return-type "string", :params [{:name "path", :type "string"}]}
    {:id ::set-uninstall-url,
     :name "setUninstallURL",
     :since "41",
     :callback? true,
     :params [{:name "url", :since "38", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::reload, :name "reload"}
    {:id ::request-update-check,
     :name "requestUpdateCheck",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params
        [{:name "status", :type "runtime.RequestUpdateCheckStatus"}
         {:name "details", :optional? true, :type "object"}]}}]}
    {:id ::restart, :name "restart"}
    {:id ::restart-after-delay,
     :name "restartAfterDelay",
     :since "53",
     :callback? true,
     :params [{:name "seconds", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::connect,
     :name "connect",
     :return-type "runtime.Port",
     :params
     [{:name "extension-id", :optional? true, :type "string"} {:name "connect-info", :optional? true, :type "object"}]}
    {:id ::connect-native,
     :name "connectNative",
     :return-type "runtime.Port",
     :params [{:name "application", :type "string"}]}
    {:id ::send-message,
     :name "sendMessage",
     :callback? true,
     :params
     [{:name "extension-id", :optional? true, :type "string"}
      {:name "message", :type "any"}
      {:name "options", :optional? true, :type "object"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::send-native-message,
     :name "sendNativeMessage",
     :callback? true,
     :params
     [{:name "application", :type "string"}
      {:name "message", :type "object"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-platform-info,
     :name "getPlatformInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "platform-info", :type "runtime.PlatformInfo"}]}}]}
    {:id ::get-package-directory-entry,
     :name "getPackageDirectoryEntry",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "directory-entry", :type "DirectoryEntry"}]}}]}],
   :events
   [{:id ::on-startup, :name "onStartup"}
    {:id ::on-installed, :name "onInstalled", :params [{:name "details", :type "object"}]}
    {:id ::on-suspend, :name "onSuspend"}
    {:id ::on-suspend-canceled, :name "onSuspendCanceled"}
    {:id ::on-update-available, :name "onUpdateAvailable", :params [{:name "details", :type "object"}]}
    {:id ::on-browser-update-available,
     :name "onBrowserUpdateAvailable",
     :since "38",
     :deprecated "Please use 'runtime.onRestartRequired'."}
    {:id ::on-connect, :name "onConnect", :params [{:name "port", :type "runtime.Port"}]}
    {:id ::on-connect-external, :name "onConnectExternal", :params [{:name "port", :type "runtime.Port"}]}
    {:id ::on-connect-native,
     :name "onConnectNative",
     :since "76",
     :params [{:name "port", :since "74", :type "runtime.Port"}]}
    {:id ::on-message,
     :name "onMessage",
     :params
     [{:name "message", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-message-external,
     :name "onMessageExternal",
     :params
     [{:name "message", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-restart-required,
     :name "onRestartRequired",
     :params [{:name "reason", :type "runtime.OnRestartRequiredReason"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))