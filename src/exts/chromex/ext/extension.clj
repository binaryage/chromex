(ns chromex.ext.extension
  "The chrome.extension API has utilities that can be used by any extension page. It includes support for exchanging messages
   between an extension and its content scripts or between extensions, as described in detail in Message Passing.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/extension"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-last-error
  "Set for the lifetime of a callback if an ansychronous extension api has resulted in an error. If no error has occured
   lastError will be undefined.

   https://developer.chrome.com/extensions/extension#property-lastError."
  ([] (gen-call :property ::last-error &form)))

(defmacro get-in-incognito-context
  "True for content scripts running inside incognito tabs, and for extension pages running inside an incognito process. The
   latter only applies to extensions with 'split' incognito_behavior.

   https://developer.chrome.com/extensions/extension#property-inIncognitoContext."
  ([] (gen-call :property ::in-incognito-context &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro send-request
  "Sends a single request to other listeners within the extension. Similar to 'runtime.connect', but only sends a single
   request with an optional response. The 'extension.onRequest' event is fired in each page of the extension.

     |extension-id| - The extension ID of the extension you want to connect to. If omitted, default is your own extension.
     |request| - https://developer.chrome.com/extensions/extension#property-sendRequest-request.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The JSON response object sent by the handler of the request. If an error occurs while connecting to the
                  extension, the callback will be called with no arguments and 'runtime.lastError' will be set to the error
                  message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/extension#method-sendRequest."
  ([extension-id request] (gen-call :function ::send-request &form extension-id request)))

(defmacro get-url
  "Converts a relative path within an extension install directory to a fully-qualified URL.

     |path| - A path to a resource within an extension expressed relative to its install directory.

   https://developer.chrome.com/extensions/extension#method-getURL."
  ([path] (gen-call :function ::get-url &form path)))

(defmacro get-views
  "Returns an array of the JavaScript 'window' objects for each of the pages running inside the current extension.

     |fetch-properties| - https://developer.chrome.com/extensions/extension#property-getViews-fetchProperties.

   https://developer.chrome.com/extensions/extension#method-getViews."
  ([fetch-properties] (gen-call :function ::get-views &form fetch-properties))
  ([] `(get-views :omit)))

(defmacro get-background-page
  "Returns the JavaScript 'window' object for the background page running inside the current extension. Returns null if the
   extension has no background page.

   https://developer.chrome.com/extensions/extension#method-getBackgroundPage."
  ([] (gen-call :function ::get-background-page &form)))

(defmacro get-extension-tabs
  "Returns an array of the JavaScript 'window' objects for each of the tabs running inside the current extension. If windowId
   is specified, returns only the 'window' objects of tabs attached to the specified window.

     |window-id| - https://developer.chrome.com/extensions/extension#property-getExtensionTabs-windowId.

   https://developer.chrome.com/extensions/extension#method-getExtensionTabs."
  ([window-id] (gen-call :function ::get-extension-tabs &form window-id))
  ([] `(get-extension-tabs :omit)))

(defmacro is-allowed-incognito-access
  "Retrieves the state of the extension's access to Incognito-mode (as determined by the user-controlled 'Allowed in
   Incognito' checkbox.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-allowed-access] where:

     |is-allowed-access| - True if the extension has access to Incognito mode, false otherwise.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/extension#method-isAllowedIncognitoAccess."
  ([] (gen-call :function ::is-allowed-incognito-access &form)))

(defmacro is-allowed-file-scheme-access
  "Retrieves the state of the extension's access to the 'file://' scheme (as determined by the user-controlled 'Allow access
   to File URLs' checkbox.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [is-allowed-access] where:

     |is-allowed-access| - True if the extension can access the 'file://' scheme, false otherwise.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/extension#method-isAllowedFileSchemeAccess."
  ([] (gen-call :function ::is-allowed-file-scheme-access &form)))

(defmacro set-update-url-data
  "Sets the value of the ap CGI parameter used in the extension's update URL.  This value is ignored for extensions that are
   hosted in the Chrome Extension Gallery.

     |data| - https://developer.chrome.com/extensions/extension#property-setUpdateUrlData-data.

   https://developer.chrome.com/extensions/extension#method-setUpdateUrlData."
  ([data] (gen-call :function ::set-update-url-data &form data)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-request-events
  "Fired when a request is sent from either an extension process or a content script.

   Events will be put on the |channel| with signature [::on-request [request sender send-response]] where:

     |request| - The request sent by the calling script.
     |sender| - https://developer.chrome.com/extensions/extension#property-onRequest-sender.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/extension#event-onRequest."
  ([channel & args] (apply gen-call :event ::on-request &form channel args)))

(defmacro tap-on-request-external-events
  "Fired when a request is sent from another extension.

   Events will be put on the |channel| with signature [::on-request-external [request sender send-response]] where:

     |request| - The request sent by the calling script.
     |sender| - https://developer.chrome.com/extensions/extension#property-onRequestExternal-sender.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/extension#event-onRequestExternal."
  ([channel & args] (apply gen-call :event ::on-request-external &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.extension namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extension",
   :since "38",
   :properties
   [{:id ::last-error,
     :name "lastError",
     :since "58",
     :deprecated "Please use 'runtime.lastError'.",
     :return-type "object"}
    {:id ::in-incognito-context, :name "inIncognitoContext", :return-type "boolean"}],
   :functions
   [{:id ::send-request,
     :name "sendRequest",
     :since "38",
     :deprecated "Please use 'runtime.sendMessage'.",
     :callback? true,
     :params
     [{:name "extension-id", :optional? true, :type "string"}
      {:name "request", :type "any"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-url,
     :name "getURL",
     :since "58",
     :deprecated "Please use 'runtime.getURL'.",
     :return-type "string",
     :params [{:name "path", :type "string"}]}
    {:id ::get-views,
     :name "getViews",
     :return-type "[array-of-Windows]",
     :params [{:name "fetch-properties", :optional? true, :type "object"}]}
    {:id ::get-background-page, :name "getBackgroundPage", :return-type "Window"}
    {:id ::get-extension-tabs,
     :name "getExtensionTabs",
     :since "38",
     :deprecated "Please use 'extension.getViews' {type: \"tab\"}.",
     :return-type "[array-of-Windows]",
     :params [{:name "window-id", :optional? true, :type "integer"}]}
    {:id ::is-allowed-incognito-access,
     :name "isAllowedIncognitoAccess",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-allowed-access", :type "boolean"}]}}]}
    {:id ::is-allowed-file-scheme-access,
     :name "isAllowedFileSchemeAccess",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-allowed-access", :type "boolean"}]}}]}
    {:id ::set-update-url-data, :name "setUpdateUrlData", :params [{:name "data", :type "string"}]}],
   :events
   [{:id ::on-request,
     :name "onRequest",
     :since "38",
     :deprecated "Please use 'runtime.onMessage'.",
     :params
     [{:name "request", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-request-external,
     :name "onRequestExternal",
     :since "38",
     :deprecated "Please use 'runtime.onMessageExternal'.",
     :params
     [{:name "request", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))