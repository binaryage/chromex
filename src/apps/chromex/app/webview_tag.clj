(ns chromex.app.webview-tag
  "Use the webview tag to actively load live content from the web over the network and embed it in your Chrome App. Your app
   can control the appearance of the webview and interact with the web content, initiate navigations in an embedded web page,
   react to error events that happen within it, and more (see Usage).

     * available since Chrome 38
     * https://developer.chrome.com/apps/tags/webview"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-content-window
  "Object reference which can be used to post messages into the guest page.

   https://developer.chrome.com/apps/tags/webview#property-contentWindow."
  ([] (gen-call :property ::content-window &form)))

(defmacro get-request
  "Interface which provides access to webRequest events on the guest page.

   https://developer.chrome.com/apps/tags/webview#property-request."
  ([] (gen-call :property ::request &form)))

(defmacro get-context-menus
  "Similar to chrome's ContextMenus API, but applies to webview instead of browser. Use the webview.contextMenus API to add
   items to webview's context menu. You can choose what types of objects your context menu additions apply to, such as images,
   hyperlinks, and pages.

   https://developer.chrome.com/apps/tags/webview#property-contextMenus."
  ([] (gen-call :property ::context-menus &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-audio-state
  "Queries audio state.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [audible] where:

     |audible| - https://developer.chrome.com/apps/tags/webview#property-callback-audible.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-getAudioState."
  ([] (gen-call :function ::get-audio-state &form)))

(defmacro set-audio-muted
  "Sets audio mute state of the webview.

     |mute| - Mute audio value

   https://developer.chrome.com/apps/tags/webview#method-setAudioMuted."
  ([mute] (gen-call :function ::set-audio-muted &form mute)))

(defmacro is-audio-muted
  "Queries whether audio is muted.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [muted] where:

     |muted| - https://developer.chrome.com/apps/tags/webview#property-callback-muted.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-isAudioMuted."
  ([] (gen-call :function ::is-audio-muted &form)))

(defmacro capture-visible-region
  "Captures the visible region of the webview.

     |options| - Details about the format and quality of an image.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data-url] where:

     |data-url| - A data URL which encodes an image of the visible area of the captured tab. May be assigned to the 'src'
                  property of an HTML Image element for display.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-captureVisibleRegion."
  ([options] (gen-call :function ::capture-visible-region &form options))
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

     |content-script-list| - Details of the content scripts to add.

   https://developer.chrome.com/apps/tags/webview#method-addContentScripts."
  ([content-script-list] (gen-call :function ::add-content-scripts &form content-script-list)))

(defmacro back
  "Navigates backward one history entry if possible. Equivalent to go(-1).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - Indicates whether the navigation was successful.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-back."
  ([] (gen-call :function ::back &form)))

(defmacro can-go-back
  "Indicates whether or not it is possible to navigate backward through history. The state of this function is cached, and
   updated before each loadcommit, so the best place to call it is on loadcommit.

   https://developer.chrome.com/apps/tags/webview#method-canGoBack."
  ([] (gen-call :function ::can-go-back &form)))

(defmacro can-go-forward
  "Indicates whether or not it is possible to navigate forward through history. The state of this function is cached, and
   updated before each loadcommit, so the best place to call it is on loadcommit.

   https://developer.chrome.com/apps/tags/webview#method-canGoForward."
  ([] (gen-call :function ::can-go-forward &form)))

(defmacro clear-data
  "Clears browsing data for the webview partition.

     |options| - Options determining which data to clear.
     |types| - The types of data to be cleared.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-clearData."
  ([options types] (gen-call :function ::clear-data &form options types)))

(defmacro execute-script
  "Injects JavaScript code into the guest page.The following sample code uses script injection to set the guest page's
   background color to red:webview.executeScript({ code: 'document.body.style.backgroundColor = 'red'' });

     |details| - Details of the script to run.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The result of the script in every injected frame.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-executeScript."
  ([details] (gen-call :function ::execute-script &form details)))

(defmacro find
  "Initiates a find-in-page request.

     |search-text| - The string to find in the page.
     |options| - Options for the find request.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - Contains all of the results of the find request. results can be omitted if it is not utilized in the callback
                 function body; for example, if the callback is only used to discern when the find request has completed.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-find."
  ([search-text options] (gen-call :function ::find &form search-text options))
  ([search-text] `(find ~search-text :omit)))

(defmacro forward
  "Navigates forward one history entry if possible. Equivalent to go(1).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - Indicates whether the navigation was successful.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-forward."
  ([] (gen-call :function ::forward &form)))

(defmacro get-process-id
  "Returns Chrome's internal process ID for the guest web page's current process, allowing embedders to know how many guests
   would be affected by terminating the process. Two guests will share a process only if they belong to the same app and have
   the same storage partition ID. The call is synchronous and returns the embedder's cached notion of the current process ID.
   The process ID isn't the same as the operating system's process ID.

   https://developer.chrome.com/apps/tags/webview#method-getProcessId."
  ([] (gen-call :function ::get-process-id &form)))

(defmacro get-user-agent
  "Returns the user agent string used by the webview for guest page requests.

   https://developer.chrome.com/apps/tags/webview#method-getUserAgent."
  ([] (gen-call :function ::get-user-agent &form)))

(defmacro get-zoom
  "Gets the current zoom factor.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [zoom-factor] where:

     |zoom-factor| - The current zoom factor.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-getZoom."
  ([] (gen-call :function ::get-zoom &form)))

(defmacro get-zoom-mode
  "Gets the current zoom mode.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [zoom-mode] where:

     |zoom-mode| - The webview's current zoom mode.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-getZoomMode."
  ([] (gen-call :function ::get-zoom-mode &form)))

(defmacro go
  "Navigates to a history entry using a history index relative to the current navigation. If the requested navigation is
   impossible, this method has no effect.

     |relative-index| - Relative history index to which the webview should be navigated. For example, a value of 2 will
                        navigate forward 2 history entries if possible; a value of -3 will navigate backward 3 entries.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - Indicates whether the navigation was successful.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-go."
  ([relative-index] (gen-call :function ::go &form relative-index)))

(defmacro insert-css
  "Injects CSS into the guest page.

     |details| - Details of the CSS to insert.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-insertCSS."
  ([details] (gen-call :function ::insert-css &form details)))

(defmacro is-user-agent-overridden
  "Indicates whether or not the webview's user agent string has been overridden by 'webviewTag.setUserAgentOverride'.

   https://developer.chrome.com/apps/tags/webview#method-isUserAgentOverridden."
  ([] (gen-call :function ::is-user-agent-overridden &form)))

(defmacro print
  "Prints the contents of the webview. This is equivalent to calling scripted print function from the webview itself.

   https://developer.chrome.com/apps/tags/webview#method-print."
  ([] (gen-call :function ::print &form)))

(defmacro reload
  "Reloads the current top-level page.

   https://developer.chrome.com/apps/tags/webview#method-reload."
  ([] (gen-call :function ::reload &form)))

(defmacro remove-content-scripts
  "Removes content scripts from a webview.The following example removes 'myRule' which was added
   before.webview.removeContentScripts(['myRule']);You can remove all the rules by calling:webview.removeContentScripts();

     |script-name-list| - A list of names of content scripts that will be removed. If the list is empty, all the content
                          scripts added to the webview will be removed.

   https://developer.chrome.com/apps/tags/webview#method-removeContentScripts."
  ([script-name-list] (gen-call :function ::remove-content-scripts &form script-name-list))
  ([] `(remove-content-scripts :omit)))

(defmacro set-user-agent-override
  "Override the user agent string used by the webview for guest page requests.

     |user-agent| - The user agent string to use.

   https://developer.chrome.com/apps/tags/webview#method-setUserAgentOverride."
  ([user-agent] (gen-call :function ::set-user-agent-override &form user-agent)))

(defmacro set-zoom
  "Changes the zoom factor of the page. The scope and persistence of this change are determined by the webview's current zoom
   mode (see 'webviewTag.ZoomMode').

     |zoom-factor| - The new zoom factor.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-setZoom."
  ([zoom-factor] (gen-call :function ::set-zoom &form zoom-factor)))

(defmacro set-zoom-mode
  "Sets the zoom mode of the webview.

     |zoom-mode| - Defines how zooming is handled in the webview.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-setZoomMode."
  ([zoom-mode] (gen-call :function ::set-zoom-mode &form zoom-mode)))

(defmacro stop
  "Stops loading the current webview navigation if in progress.

   https://developer.chrome.com/apps/tags/webview#method-stop."
  ([] (gen-call :function ::stop &form)))

(defmacro stop-finding
  "Ends the current find session (clearing all highlighting) and cancels all find requests in progress.

     |action| - Determines what to do with the active match after the find session has ended. clear will clear the
                highlighting over the active match; keep will keep the active match highlighted; activate will keep the
                active match highlighted and simulate a user click on that match. The default action is keep.

   https://developer.chrome.com/apps/tags/webview#method-stopFinding."
  ([action] (gen-call :function ::stop-finding &form action))
  ([] `(stop-finding :omit)))

(defmacro load-data-with-base-url
  "Loads a data URL with a specified base URL used for relative links. Optionally, a virtual URL can be provided to be shown
   to the user instead of the data URL.

     |data-url| - The data URL to load.
     |base-url| - The base URL that will be used for relative links.
     |virtual-url| - The URL that will be displayed to the user (in the address bar).

   https://developer.chrome.com/apps/tags/webview#method-loadDataWithBaseUrl."
  ([data-url base-url virtual-url] (gen-call :function ::load-data-with-base-url &form data-url base-url virtual-url))
  ([data-url base-url] `(load-data-with-base-url ~data-url ~base-url :omit)))

(defmacro set-spatial-navigation-enabled
  "Sets spatial navigation state of the webview.

     |enabled| - Spatial navigation state value.

   https://developer.chrome.com/apps/tags/webview#method-setSpatialNavigationEnabled."
  ([enabled] (gen-call :function ::set-spatial-navigation-enabled &form enabled)))

(defmacro is-spatial-navigation-enabled
  "Queries whether spatial navigation is enabled for the webview.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [enabled] where:

     |enabled| - https://developer.chrome.com/apps/tags/webview#property-callback-enabled.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tags/webview#method-isSpatialNavigationEnabled."
  ([] (gen-call :function ::is-spatial-navigation-enabled &form)))

(defmacro terminate
  "Forcibly kills the guest web page's renderer process. This may affect multiple webview tags in the current app if they
   share the same process, but it will not affect webview tags in other apps.

   https://developer.chrome.com/apps/tags/webview#method-terminate."
  ([] (gen-call :function ::terminate &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.webview-tag namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "<webview>",
   :since "38",
   :properties
   [{:id ::content-window, :name "contentWindow", :return-type "webviewTag.ContentWindow"}
    {:id ::request, :name "request", :return-type "webviewTag.WebRequestEventInterface"}
    {:id ::context-menus, :name "contextMenus", :since "44", :return-type "webviewTag.ContextMenus"}],
   :functions
   [{:id ::get-audio-state,
     :name "getAudioState",
     :since "62",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "audible", :type "boolean"}]}}]}
    {:id ::set-audio-muted, :name "setAudioMuted", :since "62", :params [{:name "mute", :type "boolean"}]}
    {:id ::is-audio-muted,
     :name "isAudioMuted",
     :since "62",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "muted", :type "boolean"}]}}]}
    {:id ::capture-visible-region,
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
    {:id ::get-user-agent, :name "getUserAgent", :return-type "string"}
    {:id ::get-zoom,
     :name "getZoom",
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
    {:id ::is-user-agent-overridden, :name "isUserAgentOverridden"}
    {:id ::print, :name "print"}
    {:id ::reload, :name "reload"}
    {:id ::remove-content-scripts,
     :name "removeContentScripts",
     :since "44",
     :params [{:name "script-name-list", :optional? true, :type "[array-of-strings]"}]}
    {:id ::set-user-agent-override, :name "setUserAgentOverride", :params [{:name "user-agent", :type "string"}]}
    {:id ::set-zoom,
     :name "setZoom",
     :callback? true,
     :params [{:name "zoom-factor", :type "double"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-zoom-mode,
     :name "setZoomMode",
     :since "43",
     :callback? true,
     :params [{:name "zoom-mode", :type "webviewTag.ZoomMode"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::stop, :name "stop"}
    {:id ::stop-finding, :name "stopFinding", :params [{:name "action", :optional? true, :type "unknown-type"}]}
    {:id ::load-data-with-base-url,
     :name "loadDataWithBaseUrl",
     :since "40",
     :params
     [{:name "data-url", :type "string"}
      {:name "base-url", :type "string"}
      {:name "virtual-url", :optional? true, :type "string"}]}
    {:id ::set-spatial-navigation-enabled,
     :name "setSpatialNavigationEnabled",
     :since "71",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::is-spatial-navigation-enabled,
     :name "isSpatialNavigationEnabled",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "enabled", :type "boolean"}]}}]}
    {:id ::terminate, :name "terminate"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))