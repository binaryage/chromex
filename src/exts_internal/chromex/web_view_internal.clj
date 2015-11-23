(ns chromex.web-view-internal
  "  * available since Chrome 48
     * https://developer.chrome.com/extensions/webViewInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro execute-script
  "Injects JavaScript code into a  page.
   
     |instanceId| - The instance ID of the guest  process.
     |src| - The src of the guest  tag.
     |details| - Details of the script or CSS to inject. Either the code or the file property must be set, but both
                 may not be set at the same time.
     |callback| - Called after all the JavaScript has been executed."
  [instance-id src details #_callback]
  (gen-call :function ::execute-script (meta &form) instance-id src details))

(defmacro insert-css
  "Injects CSS into a  page. For details, see the programmatic injection section of the content scripts doc.
   
     |instanceId| - The instance ID of the guest  process.
     |src| - The src of the guest  tag.
     |details| - Details of the script or CSS to inject. Either the code or the file property must be set, but both
                 may not be set at the same time.
     |callback| - Called when all the CSS has been inserted."
  [instance-id src details #_callback]
  (gen-call :function ::insert-css (meta &form) instance-id src details))

(defmacro add-content-scripts
  "Adds content scripts into a  page. For details, see the programmatic injection section of the content scripts doc.
   
     |instanceId| - The instance ID of the guest  process.
     |contentScriptList| - Details of the content scripts to add."
  [instance-id content-script-list]
  (gen-call :function ::add-content-scripts (meta &form) instance-id content-script-list))

(defmacro remove-content-scripts
  "Removes specified content scripts from a  page. For details, see the programmatic injection section of the content
   scripts doc.
   
     |instanceId| - The instance ID of the guest  process.
     |scriptNameList| - A list of names of content scripts that will be removed. If the list is empty, all the
                        content scripts added to the  page will be removed."
  [instance-id script-name-list]
  (gen-call :function ::remove-content-scripts (meta &form) instance-id script-name-list))

(defmacro set-zoom
  "  |instanceId| - The instance ID of the guest  process.
     |zoomFactor| - The new zoom factor.
     |callback| - Called after the zoom message has been sent to the guest process."
  [instance-id zoom-factor #_callback]
  (gen-call :function ::set-zoom (meta &form) instance-id zoom-factor))

(defmacro get-zoom
  "  |instanceId| - The instance ID of the guest  process.
     |callback| - Called after the current zoom factor is retrieved."
  [instance-id #_callback]
  (gen-call :function ::get-zoom (meta &form) instance-id))

(defmacro set-zoom-mode
  "Sets the zoom mode of the webview.
   
     |instanceId| - The instance ID of the guest  process.
     |ZoomMode| - Defines the how zooming is handled in the webview.
     |callback| - Called after the zoom mode has been changed."
  [instance-id zoom-mode #_callback]
  (gen-call :function ::set-zoom-mode (meta &form) instance-id zoom-mode))

(defmacro get-zoom-mode
  "Gets the current zoom mode.
   
     |instanceId| - The instance ID of the guest  process.
     |callback| - Called with the webview's current zoom mode."
  [instance-id #_callback]
  (gen-call :function ::get-zoom-mode (meta &form) instance-id))

(defmacro find
  "Initiates a find-in-page request.
   
     |instanceId| - The instance ID of the guest  process.
     |searchText| - The string to find in the page.
     |callback| - Called after all find results have been returned for this find request."
  [instance-id search-text options #_callback]
  (gen-call :function ::find (meta &form) instance-id search-text options))

(defmacro stop-finding
  "Ends the current find session (clearing all highlighting) and cancels all find requests in progress.
   
     |instanceId| - The instance ID of the guest  process.
     |action| - Determines what to do with the active match after the find session has ended. 'clear' will clear the
                highlighting over the active match; 'keep' will keep the active match highlighted; 'activate' will
                keep the active match highlighted and simulate a user click on that match."
  [instance-id action]
  (gen-call :function ::stop-finding (meta &form) instance-id action))

(defmacro load-data-with-base-url
  "Loads a data URL with a specified base URL used for relative links. Optionally, a virtual URL can be provided to be
   shown to the user instead of the data URL.
   
     |instanceId| - The instance ID of the guest  process.
     |dataUrl| - The data URL to load.
     |baseUrl| - The base URL that will be used for relative links.
     |virtualUrl| - The URL that will be displayed to the user.
     |callback| - Called internally for the purpose of reporting errors to console.error()."
  [instance-id data-url base-url virtual-url #_callback]
  (gen-call :function ::load-data-with-base-url (meta &form) instance-id data-url base-url virtual-url))

(defmacro go [instance-id relative-index #_callback]
  (gen-call :function ::go (meta &form) instance-id relative-index))

(defmacro override-user-agent [instance-id user-agent-override]
  (gen-call :function ::override-user-agent (meta &form) instance-id user-agent-override))

(defmacro reload [instance-id]
  (gen-call :function ::reload (meta &form) instance-id))

(defmacro set-allow-transparency [instance-id allow]
  (gen-call :function ::set-allow-transparency (meta &form) instance-id allow))

(defmacro set-allow-scaling [instance-id allow]
  (gen-call :function ::set-allow-scaling (meta &form) instance-id allow))

(defmacro set-name [instance-id frame-name]
  (gen-call :function ::set-name (meta &form) instance-id frame-name))

(defmacro set-permission [instance-id request-id action user-input #_callback]
  (gen-call :function ::set-permission (meta &form) instance-id request-id action user-input))

(defmacro navigate [instance-id src]
  (gen-call :function ::navigate (meta &form) instance-id src))

(defmacro stop [instance-id]
  (gen-call :function ::stop (meta &form) instance-id))

(defmacro terminate [instance-id]
  (gen-call :function ::terminate (meta &form) instance-id))

(defmacro clear-data
  "Clears various types of browsing data stored in a storage partition of a .
   
     |instanceId| - The instance ID of the guest  process.
     |options| - Options that determine exactly what data will be removed.
     |dataToRemove| - A set of data types. Missing data types are interpreted as false.
     |callback| - Called when deletion has completed."
  [instance-id options data-to-remove #_callback]
  (gen-call :function ::clear-data (meta &form) instance-id options data-to-remove))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webViewInternal",
   :since "48",
   :functions
   [{:id ::execute-script,
     :name "executeScript",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "src", :type "string"}
      {:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :optional? true, :type "[array-of-anys]"}]}}]}
    {:id ::insert-css,
     :name "insertCSS",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "src", :type "string"}
      {:name "details", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::add-content-scripts,
     :name "addContentScripts",
     :params
     [{:name "instance-id", :type "integer"} {:name "content-script-list", :type "[array-of-objects]"}]}
    {:id ::remove-content-scripts,
     :name "removeContentScripts",
     :params
     [{:name "instance-id", :type "integer"}
      {:name "script-name-list", :optional? true, :type "[array-of-strings]"}]}
    {:id ::set-zoom,
     :name "setZoom",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "zoom-factor", :type "double"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom,
     :name "getZoom",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "zoom-factor", :type "double"}]}}]}
    {:id ::set-zoom-mode,
     :name "setZoomMode",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "zoom-mode", :type "unknown-type"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom-mode,
     :name "getZoomMode",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "zoom-mode", :type "unknown-type"}]}}]}
    {:id ::find,
     :name "find",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "search-text", :type "string"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "results", :optional? true, :type "object"}]}}]}
    {:id ::stop-finding,
     :name "stopFinding",
     :params [{:name "instance-id", :type "integer"} {:name "action", :optional? true, :type "unknown-type"}]}
    {:id ::load-data-with-base-url,
     :name "loadDataWithBaseUrl",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "data-url", :type "string"}
      {:name "base-url", :type "string"}
      {:name "virtual-url", :optional? true, :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::go,
     :name "go",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "relative-index", :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::override-user-agent,
     :name "overrideUserAgent",
     :params [{:name "instance-id", :type "integer"} {:name "user-agent-override", :type "string"}]}
    {:id ::reload, :name "reload", :params [{:name "instance-id", :type "integer"}]}
    {:id ::set-allow-transparency,
     :name "setAllowTransparency",
     :params [{:name "instance-id", :type "integer"} {:name "allow", :type "boolean"}]}
    {:id ::set-allow-scaling,
     :name "setAllowScaling",
     :params [{:name "instance-id", :type "integer"} {:name "allow", :type "boolean"}]}
    {:id ::set-name,
     :name "setName",
     :params [{:name "instance-id", :type "integer"} {:name "frame-name", :type "string"}]}
    {:id ::set-permission,
     :name "setPermission",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "request-id", :type "integer"}
      {:name "action", :type "unknown-type"}
      {:name "user-input", :optional? true, :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "allowed", :type "boolean"}]}}]}
    {:id ::navigate,
     :name "navigate",
     :params [{:name "instance-id", :type "integer"} {:name "src", :type "string"}]}
    {:id ::stop, :name "stop", :params [{:name "instance-id", :type "integer"}]}
    {:id ::terminate, :name "terminate", :params [{:name "instance-id", :type "integer"}]}
    {:id ::clear-data,
     :name "clearData",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "options", :type "object"}
      {:name "data-to-remove", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}]})

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