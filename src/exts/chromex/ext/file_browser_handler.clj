(ns chromex.ext.file-browser-handler
  "Use the chrome.fileBrowserHandler API to extend the Chrome OS file browser. For example, you can use this API to enable
   users to upload files to your website.

     * available since Chrome 12
     * https://developer.chrome.com/extensions/fileBrowserHandler"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro select-file
  "Prompts user to select file path under which file should be saved. When the file is selected, file access permission
   required to use the file (read, write and create) are granted to the caller. The file will not actually get created during
   the function call, so function caller must ensure its existence before using it. The function has to be invoked with a user
   gesture.

     |selection-params| - Parameters that will be used while selecting the file.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - Result of the method.

   https://developer.chrome.com/extensions/fileBrowserHandler#method-selectFile."
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

   https://developer.chrome.com/extensions/fileBrowserHandler#event-onExecute."
  ([channel & args] (apply gen-call :event ::on-execute &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileBrowserHandler",
   :since "12",
   :functions
   [{:id ::select-file,
     :name "selectFile",
     :since "21",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))