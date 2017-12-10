(ns chromex.app.webstore
  "Use the chrome.webstore API to initiate app and extension installations 'inline' from your site.

     * available since Chrome 27
     * https://developer.chrome.com/apps/webstore"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro install
  "  |url| - If you have more than one &lt;link&gt; tag on your page with the chrome-webstore-item relation, you can choose
             which item you'd like to install by passing in its URL here. If it is omitted, then the first (or only) link
             will be used. An exception will be thrown if the passed in URL does not exist on the page.
     |success-callback| - This function is invoked when inline installation successfully completes (after the dialog is
                          shown and the user agrees to add the item to Chrome). You may wish to use this to hide the user
                          interface element that prompted the user to install the app or extension.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [error error-code] where:

     |error| - The failure detail. You may wish to inspect or log this for debugging purposes, but you should not rely on
               specific strings being passed back.
     |error-code| - The error code from the stable set of possible errors.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/webstore#method-install."
  ([url success-callback] (gen-call :function ::install &form url success-callback))
  ([url] `(install ~url :omit))
  ([] `(install :omit :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-install-stage-changed-events
  "Fired when an inline installation enters a new InstallStage. In order to receive notifications about this event, listeners
   must be registered before the inline installation begins.

   Events will be put on the |channel| with signature [::on-install-stage-changed [stage]] where:

     |stage| - The InstallStage that just began.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/webstore#event-onInstallStageChanged."
  ([channel & args] (apply gen-call :event ::on-install-stage-changed &form channel args)))

(defmacro tap-on-download-progress-events
  "Fired periodically with the download progress of an inline install. In order to receive notifications about this event,
   listeners must be registered before the inline installation begins.

   Events will be put on the |channel| with signature [::on-download-progress [percent-downloaded]] where:

     |percent-downloaded| - The progress of the download, between 0 and 1. 0 indicates no progress; 1.0 indicates complete.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/webstore#event-onDownloadProgress."
  ([channel & args] (apply gen-call :event ::on-download-progress &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.webstore namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webstore",
   :since "27",
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
        [{:name "error", :type "string"} {:name "error-code", :optional? "true", :type "webstore.ErrorCode"}]}}]}],
   :events
   [{:id ::on-install-stage-changed,
     :name "onInstallStageChanged",
     :since "35",
     :params [{:name "stage", :type "webstore.InstallStage"}]}
    {:id ::on-download-progress,
     :name "onDownloadProgress",
     :since "35",
     :params [{:name "percent-downloaded", :type "double"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))