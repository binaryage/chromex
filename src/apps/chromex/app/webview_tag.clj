(ns chromex.app.webview-tag
  "Use the webview tag to actively load live content from the web over the network and embed it in your Chrome App. Your app
   can control the appearance of the webview and interact with the web content, initiate navigations in an embedded web page,
   react to error events that happen within it, and more (see Usage).
   
     * available since Chrome 25
     * https://developer.chrome.com/extensions/webviewTag"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-content-window
  "Object reference which can be used to post messages into the guest page."
  ([] (gen-call :property ::content-window &form)))

(defmacro get-request
  "Interface which provides access to webRequest events on the guest page."
  ([] (gen-call :property ::request &form)))

(defmacro get-context-menus
  "Similar to chrome's ContextMenus API, but applies to webview instead of browser. Use the webview.contextMenus API to add
   items to webview's context menu. You can choose what types of objects your context menu additions apply to, such as images,
   hyperlinks, and pages."
  ([] (gen-call :property ::context-menus &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro capture-visible-region
  "Captures the visible region of the webview.
   
     |options| - Details about the format and quality of an image.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::capture-visible-region &form options))
  ([] `(capture-visible-region :omit)))

(defmacro add-content-scripts
  "Adds content script injection rules to the webview. When the webview navigates to a page matching one or more rules, the
   associated scripts will be injected. You can programmatically add rules or update existing rules.The following example adds
   two rules to the webview: 'myRule' and 'anotherRule'.webview.addContentScripts([
  {
    name: 'myRule',
    matches:
   ['http://www.foo.com/*'],
    css: { files: ['mystyles.css'] },
    js: { files: ['jquery.js', 'myscript.js'] },
   run_at: 'document_start'
  },
  {
    name: 'anotherRule',
    matches: ['http://www.bar.com/*'],
    js: { code:
   'document.body.style.backgroundColor = 'red';' },
    run_at: 'document_end'
  }]);
 ...

// Navigates webview.
webview.src
   = 'http://www.foo.com';You can defer addContentScripts call until you needs to inject scripts.The following example shows
   how to overwrite an existing rule.webview.addContentScripts([{
    name: 'rule',
    matches: ['http://www.foo.com/*'],
   js: { files: ['scriptA.js'] },
    run_at: 'document_start'}]);

// Do something.
webview.src = 'http://www.foo.com/*';
   ...
// Overwrite 'rule' defined before.
webview.addContentScripts([{
    name: 'rule',
    matches:
   ['http://www.bar.com/*'],
    js: { files: ['scriptB.js'] },
    run_at: 'document_end'}]);If webview has been naviagted to
   the origin (e.g., foo.com) and calls webview.addContentScripts to add 'myRule', you need to wait for next navigation to
   make the scripts injected. If you want immediate injection, executeScript will do the right thing.Rules are preserved even
   if the guest process crashes or is killed or even if the webview is reparented.Refer to the content scripts documentation
   for more details.
   
     |contentScriptList| - Details of the content scripts to add."
  ([content-script-list] (gen-call :function ::add-content-scripts &form content-script-list)))

(defmacro back
  "Navigates backward one history entry if possible. Equivalent to go(-1).
   
     |callback| - Called after the navigation has either failed or completed successfully.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::back &form)))

(defmacro can-go-back
  "Indicates whether or not it is possible to navigate backward through history. The state of this function is cached, and
   updated before each loadcommit, so the best place to call it is on loadcommit."
  ([] (gen-call :function ::can-go-back &form)))

(defmacro can-go-forward
  "Indicates whether or not it is possible to navigate forward through history. The state of this function is cached, and
   updated before each loadcommit, so the best place to call it is on loadcommit."
  ([] (gen-call :function ::can-go-forward &form)))

(defmacro clear-data
  "Clears browsing data for the webview partition.
   
     |options| - Options determining which data to clear.
     |types| - The types of data to be cleared.
     |callback| - Called after the data has been successfully cleared.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options types #_callback] (gen-call :function ::clear-data &form options types)))

(defmacro execute-script
  "Injects JavaScript code into the guest page.The following sample code uses script injection to set the guest page's
   background color to red:webview.executeScript({ code: 'document.body.style.backgroundColor = 'red'' });
   
     |details| - Details of the script to run.
     |callback| - Called after all the JavaScript has been executed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([details #_callback] (gen-call :function ::execute-script &form details)))

(defmacro find
  "Initiates a find-in-page request.
   
     |searchText| - The string to find in the page.
     |options| - Options for the find request.
     |callback| - Called after all find results have been returned for this find request.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([search-text options #_callback] (gen-call :function ::find &form search-text options))
  ([search-text] `(find ~search-text :omit)))

(defmacro forward
  "Navigates forward one history entry if possible. Equivalent to go(1).
   
     |callback| - Called after the navigation has either failed or completed successfully.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::forward &form)))

(defmacro get-process-id
  "Returns Chrome's internal process ID for the guest web page's current process, allowing embedders to know how many guests
   would be affected by terminating the process. Two guests will share a process only if they belong to the same app and have
   the same storage partition ID. The call is synchronous and returns the embedder's cached notion of the current process ID.
   The process ID isn't the same as the operating system's process ID."
  ([] (gen-call :function ::get-process-id &form)))

(defmacro get-user-agent
  "Returns the user agent string used by the webview for guest page requests."
  ([] (gen-call :function ::get-user-agent &form)))

(defmacro get-zoom
  "Gets the current zoom factor.
   
     |callback| - Called after the current zoom factor is retrieved.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-zoom &form)))

(defmacro get-zoom-mode
  "Gets the current zoom mode.
   
     |callback| - Called with the webview's current zoom mode.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-zoom-mode &form)))

(defmacro go
  "Navigates to a history entry using a history index relative to the current navigation. If the requested navigation is
   impossible, this method has no effect.
   
     |relativeIndex| - Relative history index to which the webview should be navigated. For example, a value of 2 will
                       navigate forward 2 history entries if possible; a value of -3 will navigate backward 3 entries.
     |callback| - Called after the navigation has either failed or completed successfully.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([relative-index #_callback] (gen-call :function ::go &form relative-index)))

(defmacro insert-css
  "Injects CSS into the guest page.
   
     |details| - Details of the CSS to insert.
     |callback| - Called after the CSS has been inserted.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([details #_callback] (gen-call :function ::insert-css &form details)))

(defmacro is-user-agent-overridden
  "Indicates whether or not the webview's user agent string has been overridden by 'webviewTag.setUserAgentOverride'."
  ([] (gen-call :function ::is-user-agent-overridden &form)))

(defmacro print
  "Prints the contents of the webview. This is equivalent to calling scripted print function from the webview itself."
  ([] (gen-call :function ::print &form)))

(defmacro reload
  "Reloads the current top-level page."
  ([] (gen-call :function ::reload &form)))

(defmacro remove-content-scripts
  "Removes content scripts from a webview.The following example removes 'myRule' which was added
   before.webview.removeContentScripts(['myRule']);You can remove all the rules by calling:webview.removeContentScripts();
   
     |scriptNameList| - A list of names of content scripts that will be removed. If the list is empty, all the content
                        scripts added to the webview will be removed."
  ([script-name-list] (gen-call :function ::remove-content-scripts &form script-name-list))
  ([] `(remove-content-scripts :omit)))

(defmacro set-user-agent-override
  "Override the user agent string used by the webview for guest page requests.
   
     |userAgent| - The user agent string to use."
  ([user-agent] (gen-call :function ::set-user-agent-override &form user-agent)))

(defmacro set-zoom
  "Changes the zoom factor of the page. The scope and persistence of this change are determined by the webview's current zoom
   mode (see 'webviewTag.ZoomMode').
   
     |zoomFactor| - The new zoom factor.
     |callback| - Called after the page has been zoomed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([zoom-factor #_callback] (gen-call :function ::set-zoom &form zoom-factor)))

(defmacro set-zoom-mode
  "Sets the zoom mode of the webview.
   
     |ZoomMode| - Defines how zooming is handled in the webview.
     |callback| - Called after the zoom mode has been changed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([zoom-mode #_callback] (gen-call :function ::set-zoom-mode &form zoom-mode)))

(defmacro stop
  "Stops loading the current webview navigation if in progress."
  ([] (gen-call :function ::stop &form)))

(defmacro stop-finding
  "Ends the current find session (clearing all highlighting) and cancels all find requests in progress.
   
     |action| - Determines what to do with the active match after the find session has ended. clear will clear the
                highlighting over the active match; keep will keep the active match highlighted; activate will keep the
                active match highlighted and simulate a user click on that match. The default action is keep."
  ([action] (gen-call :function ::stop-finding &form action))
  ([] `(stop-finding :omit)))

(defmacro load-data-with-base-url
  "Loads a data URL with a specified base URL used for relative links. Optionally, a virtual URL can be provided to be shown
   to the user instead of the data URL.
   
     |dataUrl| - The data URL to load.
     |baseUrl| - The base URL that will be used for relative links.
     |virtualUrl| - The URL that will be displayed to the user (in the address bar)."
  ([data-url base-url virtual-url] (gen-call :function ::load-data-with-base-url &form data-url base-url virtual-url))
  ([data-url base-url] `(load-data-with-base-url ~data-url ~base-url :omit)))

(defmacro terminate
  "Forcibly kills the guest web page's renderer process. This may affect multiple webview tags in the current app if they
   share the same process, but it will not affect webview tags in other apps."
  ([] (gen-call :function ::terminate &form)))

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
  {:namespace "<webview>",
   :since "25",
   :properties
   [{:id ::content-window, :name "contentWindow", :return-type "webviewTag.ContentWindow"}
    {:id ::request, :name "request", :since "33", :return-type "webviewTag.WebRequestEventInterface"}
    {:id ::context-menus, :name "contextMenus", :since "44", :return-type "webviewTag.ContextMenus"}],
   :functions
   [{:id ::capture-visible-region,
     :name "captureVisibleRegion",
     :since "50",
     :callback? true,
     :params
     [{:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "data-url", :type "string"}]}}]}
    {:id ::add-content-scripts,
     :name "addContentScripts",
     :since "44",
     :params [{:name "content-script-list", :type "[array-of-webviewTag.ContentScriptDetailss]"}]}
    {:id ::back,
     :name "back",
     :callback? true,
     :params
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::can-go-back, :name "canGoBack", :return-type "boolean"}
    {:id ::can-go-forward, :name "canGoForward", :return-type "boolean"}
    {:id ::clear-data,
     :name "clearData",
     :since "33",
     :callback? true,
     :params
     [{:name "options", :type "webviewTag.ClearDataOptions"}
      {:name "types", :type "webviewTag.ClearDataTypeSet"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::execute-script,
     :name "executeScript",
     :callback? true,
     :params
     [{:name "details", :type "webviewTag.InjectDetails"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :optional? true, :type "[array-of-anys]"}]}}]}
    {:id ::find,
     :name "find",
     :since "35",
     :callback? true,
     :params
     [{:name "search-text", :type "string"}
      {:name "options", :optional? true, :type "webviewTag.FindOptions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "results", :optional? true, :type "webviewTag.FindCallbackResults"}]}}]}
    {:id ::forward,
     :name "forward",
     :callback? true,
     :params
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::get-process-id, :name "getProcessId", :return-type "integer"}
    {:id ::get-user-agent, :name "getUserAgent", :since "33", :return-type "string"}
    {:id ::get-zoom,
     :name "getZoom",
     :since "36",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "zoom-factor", :type "double"}]}}]}
    {:id ::get-zoom-mode,
     :name "getZoomMode",
     :since "43",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "zoom-mode", :type "webviewTag.ZoomMode"}]}}]}
    {:id ::go,
     :name "go",
     :callback? true,
     :params
     [{:name "relative-index", :type "integer"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::insert-css,
     :name "insertCSS",
     :callback? true,
     :params
     [{:name "details", :type "webviewTag.InjectDetails"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::is-user-agent-overridden, :name "isUserAgentOverridden", :since "33"}
    {:id ::print, :name "print", :since "38"}
    {:id ::reload, :name "reload"}
    {:id ::remove-content-scripts,
     :name "removeContentScripts",
     :since "44",
     :params [{:name "script-name-list", :optional? true, :type "[array-of-strings]"}]}
    {:id ::set-user-agent-override,
     :name "setUserAgentOverride",
     :since "33",
     :params [{:name "user-agent", :type "string"}]}
    {:id ::set-zoom,
     :name "setZoom",
     :since "36",
     :callback? true,
     :params [{:name "zoom-factor", :type "double"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-zoom-mode,
     :name "setZoomMode",
     :since "43",
     :callback? true,
     :params [{:name "zoom-mode", :type "webviewTag.ZoomMode"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::stop, :name "stop"}
    {:id ::stop-finding,
     :name "stopFinding",
     :since "35",
     :params [{:name "action", :optional? true, :type "unknown-type"}]}
    {:id ::load-data-with-base-url,
     :name "loadDataWithBaseUrl",
     :since "40",
     :params
     [{:name "data-url", :type "string"}
      {:name "base-url", :type "string"}
      {:name "virtual-url", :optional? true, :type "string"}]}
    {:id ::terminate, :name "terminate"}]})

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