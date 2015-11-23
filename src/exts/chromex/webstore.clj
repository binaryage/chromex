(ns chromex.webstore
  "Use the chrome.webstore API to initiate app and extension installations 'inline' from your site.
   
     * available since Chrome 15
     * https://developer.chrome.com/extensions/webstore"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro install
  "  |url| - If you have more than one &lt;link&gt; tag on your page with the chrome-webstore-item relation, you can
             choose which item you'd like to install by passing in its URL here. If it is omitted, then the first
             (or only) link will be used. An exception will be thrown if the passed in URL does not exist on the
             page.
     |successCallback| - This function is invoked when inline installation successfully completes (after the dialog
                         is shown and the user agrees to add the item to Chrome). You may wish to use this to hide
                         the user interface element that prompted the user to install the app or extension.
     |failureCallback| - This function is invoked when inline installation does not successfully complete. Possible
                         reasons for this include the user canceling the dialog, the linked item not being found in
                         the store, or the install being initiated from a non-verified site."
  ([url success-callback #_failure-callback] (gen-call :function ::install (meta &form) url success-callback))
  ([url] `(install ~url :omit))
  ([] `(install :omit :omit)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-install-stage-changed-events
  "Fired when an inline installation enters a new InstallStage. In order to receive notifications about this event,
   listeners must be registered before the inline installation begins."
  ([channel] (gen-call :event ::on-install-stage-changed (meta &form) channel)))

(defmacro tap-on-download-progress-events
  "Fired periodically with the download progress of an inline install. In order to receive notifications about this
   event, listeners must be registered before the inline installation begins."
  ([channel] (gen-call :event ::on-download-progress (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webstore",
   :since "15",
   :functions
   [{:id ::install,
     :name "install",
     :callback? true,
     :params
     [{:name "url", :optional? true, :type "string"}
      {:name "success-callback", :optional? true, :type "function"}
      {:name "failure-callback",
       :optional? true,
       :type :callback,
       :callback
       {:params
        [{:name "error", :type "string"}
         {:name "error-code", :optional? "true", :type "webstore.ErrorCode"}]}}]}],
   :events
   [{:id ::on-install-stage-changed,
     :name "onInstallStageChanged",
     :since "35",
     :params [{:name "stage", :type "webstore.InstallStage"}]}
    {:id ::on-download-progress,
     :name "onDownloadProgress",
     :since "35",
     :params [{:name "percent-downloaded", :type "double"}]}]})

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