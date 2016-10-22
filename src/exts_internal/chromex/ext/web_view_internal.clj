(ns chromex.ext.web-view-internal
  "  * available since Chrome 56"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro execute-script
  "Injects JavaScript code into a webview page.

     |instance-id| - The instance ID of the guest webview process.
     |src| - The src of the guest webview tag.
     |details| - Details of the script to run.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - The result of the script in every injected frame.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id src details] (gen-call :function ::execute-script &form instance-id src details)))

(defmacro insert-css
  "Injects CSS into a webview page. For details, see the programmatic injection section of the content scripts doc.

     |instance-id| - The instance ID of the guest webview process.
     |src| - The src of the guest webview tag.
     |details| - Details of the CSS text to insert.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id src details] (gen-call :function ::insert-css &form instance-id src details)))

(defmacro add-content-scripts
  "Adds content scripts into a webview page. For details, see the programmatic injection section of the content scripts doc.

     |instance-id| - The instance ID of the guest webview process.
     |content-script-list| - Details of the content scripts to add."
  ([instance-id content-script-list] (gen-call :function ::add-content-scripts &form instance-id content-script-list)))

(defmacro remove-content-scripts
  "Removes specified content scripts from a webview page. For details, see the programmatic injection section of the content
   scripts doc.

     |instance-id| - The instance ID of the guest webview process.
     |script-name-list| - A list of names of content scripts that will be removed. If the list is empty, all the content
                          scripts added to the webview page will be removed."
  ([instance-id script-name-list] (gen-call :function ::remove-content-scripts &form instance-id script-name-list))
  ([instance-id] `(remove-content-scripts ~instance-id :omit)))

(defmacro set-zoom
  "  |instance-id| - The instance ID of the guest webview process.
     |zoom-factor| - The new zoom factor.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id zoom-factor] (gen-call :function ::set-zoom &form instance-id zoom-factor)))

(defmacro get-zoom
  "  |instance-id| - The instance ID of the guest webview process.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [zoom-factor] where:

     |zoom-factor| - The current zoom factor.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id] (gen-call :function ::get-zoom &form instance-id)))

(defmacro set-zoom-mode
  "Sets the zoom mode of the webview.

     |instance-id| - The instance ID of the guest webview process.
     |zoom-mode| - Defines how zooming is handled in the webview.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id zoom-mode] (gen-call :function ::set-zoom-mode &form instance-id zoom-mode)))

(defmacro get-zoom-mode
  "Gets the current zoom mode.

     |instance-id| - The instance ID of the guest webview process.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [zoom-mode] where:

     |zoom-mode| - The webview's current zoom mode.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id] (gen-call :function ::get-zoom-mode &form instance-id)))

(defmacro find
  "Initiates a find-in-page request.

     |instance-id| - The instance ID of the guest webview process.
     |search-text| - The string to find in the page.
     |options| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [results] where:

     |results| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id search-text options] (gen-call :function ::find &form instance-id search-text options))
  ([instance-id search-text] `(find ~instance-id ~search-text :omit)))

(defmacro stop-finding
  "Ends the current find session (clearing all highlighting) and cancels all find requests in progress.

     |instance-id| - The instance ID of the guest webview process.
     |action| - Determines what to do with the active match after the find session has ended."
  ([instance-id action] (gen-call :function ::stop-finding &form instance-id action))
  ([instance-id] `(stop-finding ~instance-id :omit)))

(defmacro load-data-with-base-url
  "Loads a data URL with a specified base URL used for relative links. Optionally, a virtual URL can be provided to be shown
   to the user instead of the data URL.

     |instance-id| - The instance ID of the guest webview process.
     |data-url| - The data URL to load.
     |base-url| - The base URL that will be used for relative links.
     |virtual-url| - The URL that will be displayed to the user.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id data-url base-url virtual-url] (gen-call :function ::load-data-with-base-url &form instance-id data-url base-url virtual-url))
  ([instance-id data-url base-url] `(load-data-with-base-url ~instance-id ~data-url ~base-url :omit)))

(defmacro go
  "  |instance-id| - ?
     |relative-index| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - Indicates whether the navigation was successful.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id relative-index] (gen-call :function ::go &form instance-id relative-index)))

(defmacro override-user-agent
  "  |instance-id| - ?
     |user-agent-override| - ?"
  ([instance-id user-agent-override] (gen-call :function ::override-user-agent &form instance-id user-agent-override)))

(defmacro reload
  "  |instance-id| - ?"
  ([instance-id] (gen-call :function ::reload &form instance-id)))

(defmacro set-allow-transparency
  "  |instance-id| - ?
     |allow| - ?"
  ([instance-id allow] (gen-call :function ::set-allow-transparency &form instance-id allow)))

(defmacro set-allow-scaling
  "  |instance-id| - ?
     |allow| - ?"
  ([instance-id allow] (gen-call :function ::set-allow-scaling &form instance-id allow)))

(defmacro set-name
  "  |instance-id| - ?
     |frame-name| - ?"
  ([instance-id frame-name] (gen-call :function ::set-name &form instance-id frame-name)))

(defmacro set-permission
  "  |instance-id| - ?
     |request-id| - ?
     |action| - ?
     |user-input| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [allowed] where:

     |allowed| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id request-id action user-input] (gen-call :function ::set-permission &form instance-id request-id action user-input))
  ([instance-id request-id action] `(set-permission ~instance-id ~request-id ~action :omit)))

(defmacro navigate
  "  |instance-id| - ?
     |src| - ?"
  ([instance-id src] (gen-call :function ::navigate &form instance-id src)))

(defmacro stop
  "  |instance-id| - ?"
  ([instance-id] (gen-call :function ::stop &form instance-id)))

(defmacro terminate
  "  |instance-id| - ?"
  ([instance-id] (gen-call :function ::terminate &form instance-id)))

(defmacro capture-visible-region
  "foo

     |instance-id| - The instance ID of the guest webview process.
     |options| - Details about the format and quality of an image.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [data-url] where:

     |data-url| - A data URL which encodes an image of the visible area of the captured tab. May be assigned to the 'src'
                  property of an HTML Image element for display.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id options] (gen-call :function ::capture-visible-region &form instance-id options))
  ([instance-id] `(capture-visible-region ~instance-id :omit)))

(defmacro clear-data
  "Clears various types of browsing data stored in a storage partition of a webview.

     |instance-id| - The instance ID of the guest webview process.
     |options| - Options that determine exactly what data will be removed.
     |data-to-remove| - The set of data types to remove.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id options data-to-remove] (gen-call :function ::clear-data &form instance-id options data-to-remove)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.web-view-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webViewInternal",
   :since "56",
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
     :params [{:name "instance-id", :type "integer"} {:name "content-script-list", :type "[array-of-objects]"}]}
    {:id ::remove-content-scripts,
     :name "removeContentScripts",
     :params
     [{:name "instance-id", :type "integer"} {:name "script-name-list", :optional? true, :type "[array-of-strings]"}]}
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
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
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
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "allowed", :type "boolean"}]}}]}
    {:id ::navigate, :name "navigate", :params [{:name "instance-id", :type "integer"} {:name "src", :type "string"}]}
    {:id ::stop, :name "stop", :params [{:name "instance-id", :type "integer"}]}
    {:id ::terminate, :name "terminate", :params [{:name "instance-id", :type "integer"}]}
    {:id ::capture-visible-region,
     :name "captureVisibleRegion",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "data-url", :type "string"}]}}]}
    {:id ::clear-data,
     :name "clearData",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "options", :type "object"}
      {:name "data-to-remove", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))