(ns chromex.app.file-browser-handler
  "Use the chrome.fileBrowserHandler API to extend the Chrome OS file browser. For example, you can use this API to enable
   users to upload files to your website.

     * available since Chrome 38
     * https://developer.chrome.com/apps/fileBrowserHandler"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro select-file
  "Prompts user to select file path under which file should be saved. When the file is selected, file access permission
   required to use the file (read, write and create) are granted to the caller. The file will not actually get created during
   the function call, so function caller must ensure its existence before using it. The function has to be invoked with a user
   gesture.

     |selection-params| - Parameters that will be used while selecting the file.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Result of the method.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/fileBrowserHandler#method-selectFile."
  ([selection-params] (gen-call :function ::select-file &form selection-params)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-execute-events
  "Fired when file system action is executed from ChromeOS file browser.

   Events will be put on the |channel| with signature [::on-execute [id details]] where:

     |id| - File browser action id as specified in the listener component's manifest.
     |details| - File handler execute event details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/fileBrowserHandler#event-onExecute."
  ([channel & args] (apply gen-call :event ::on-execute &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.file-browser-handler namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileBrowserHandler",
   :since "38",
   :functions
   [{:id ::select-file,
     :name "selectFile",
     :callback? true,
     :params
     [{:name "selection-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}],
   :events
   [{:id ::on-execute,
     :name "onExecute",
     :params
     [{:name "id", :type "string"} {:name "details", :type "fileBrowserHandler.FileHandlerExecuteEventDetails"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))