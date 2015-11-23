(ns chromex.extension
  "The chrome.extension API has utilities that can be used by any extension page. It includes support for exchanging
   messages between an extension and its content scripts or between extensions, as described in detail in Message
   Passing.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/extension"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-last-error
  "Set for the lifetime of a callback if an ansychronous extension api has resulted in an error. If no error has
   occured lastError will be undefined."
  ([] (gen-call :property ::last-error (meta &form))))

(defmacro get-in-incognito-context
  "True for content scripts running inside incognito tabs, and for extension pages running inside an incognito
   process. The latter only applies to extensions with 'split' incognito_behavior."
  ([] (gen-call :property ::in-incognito-context (meta &form))))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro send-request
  "Sends a single request to other listeners within the extension. Similar to 'runtime.connect', but only sends a
   single request with an optional response. The 'extension.onRequest' event is fired in each page of the extension.
   
     |extensionId| - The extension ID of the extension you want to connect to. If omitted, default is your own
                     extension."
  ([extension-id request #_response-callback] (gen-call :function ::send-request (meta &form) extension-id request)))

(defmacro get-url
  "Converts a relative path within an extension install directory to a fully-qualified URL.
   
     |path| - A path to a resource within an extension expressed relative to its install directory."
  ([path] (gen-call :function ::get-url (meta &form) path)))

(defmacro get-views
  "Returns an array of the JavaScript 'window' objects for each of the pages running inside the current extension."
  ([fetch-properties] (gen-call :function ::get-views (meta &form) fetch-properties))
  ([] `(get-views :omit)))

(defmacro get-background-page
  "Returns the JavaScript 'window' object for the background page running inside the current extension. Returns null
   if the extension has no background page."
  ([] (gen-call :function ::get-background-page (meta &form))))

(defmacro get-extension-tabs
  "Returns an array of the JavaScript 'window' objects for each of the tabs running inside the current extension. If
   windowId is specified, returns only the 'window' objects of tabs attached to the specified window."
  ([window-id] (gen-call :function ::get-extension-tabs (meta &form) window-id))
  ([] `(get-extension-tabs :omit)))

(defmacro is-allowed-incognito-access
  "Retrieves the state of the extension's access to Incognito-mode (as determined by the user-controlled 'Allowed in
   Incognito' checkbox."
  ([#_callback] (gen-call :function ::is-allowed-incognito-access (meta &form))))

(defmacro is-allowed-file-scheme-access
  "Retrieves the state of the extension's access to the 'file://' scheme (as determined by the user-controlled 'Allow
   access to File URLs' checkbox."
  ([#_callback] (gen-call :function ::is-allowed-file-scheme-access (meta &form))))

(defmacro set-update-url-data
  "Sets the value of the ap CGI parameter used in the extension's update URL.  This value is ignored for extensions
   that are hosted in the Chrome Extension Gallery."
  ([data] (gen-call :function ::set-update-url-data (meta &form) data)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-request-events
  "Fired when a request is sent from either an extension process or a content script."
  ([channel] (gen-call :event ::on-request (meta &form) channel)))

(defmacro tap-on-request-external-events
  "Fired when a request is sent from another extension."
  ([channel] (gen-call :event ::on-request-external (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extension",
   :since "5",
   :properties
   [{:id ::last-error, :name "lastError", :return-type "object"}
    {:id ::in-incognito-context, :name "inIncognitoContext", :since "7", :return-type "boolean"}],
   :functions
   [{:id ::send-request,
     :name "sendRequest",
     :since "33",
     :deprecated "Please use 'runtime.sendMessage'.",
     :callback? true,
     :params
     [{:name "extension-id", :optional? true, :type "string"}
      {:name "request", :type "any"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-url, :name "getURL", :return-type "string", :params [{:name "path", :type "string"}]}
    {:id ::get-views,
     :name "getViews",
     :return-type "[array-of-Windows]",
     :params [{:name "fetch-properties", :optional? true, :type "object"}]}
    {:id ::get-background-page, :name "getBackgroundPage", :return-type "Window"}
    {:id ::get-extension-tabs,
     :name "getExtensionTabs",
     :since "33",
     :deprecated "Please use 'extension.getViews' {type: \"tab\"}.",
     :return-type "[array-of-Windows]",
     :params [{:name "window-id", :optional? true, :type "integer"}]}
    {:id ::is-allowed-incognito-access,
     :name "isAllowedIncognitoAccess",
     :since "12",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-allowed-access", :type "boolean"}]}}]}
    {:id ::is-allowed-file-scheme-access,
     :name "isAllowedFileSchemeAccess",
     :since "12",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-allowed-access", :type "boolean"}]}}]}
    {:id ::set-update-url-data,
     :name "setUpdateUrlData",
     :since "9",
     :params [{:name "data", :type "string"}]}],
   :events
   [{:id ::on-request,
     :name "onRequest",
     :since "33",
     :deprecated "Please use 'runtime.onMessage'.",
     :params
     [{:name "request", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}
    {:id ::on-request-external,
     :name "onRequestExternal",
     :since "33",
     :deprecated "Please use 'runtime.onMessageExternal'.",
     :params
     [{:name "request", :optional? true, :type "any"}
      {:name "sender", :type "runtime.MessageSender"}
      {:name "send-response", :type :callback}]}]})

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