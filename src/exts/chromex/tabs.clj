(ns chromex.tabs
  "Use the chrome.tabs API to interact with the browser's tab system. You can use this API to create, modify, and
   rearrange tabs in the browser.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/tabs"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-tab-id-none
  "An ID which represents the absence of a browser tab."
  ([] (gen-call :property ::tab-id-none (meta &form))))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves details about the specified tab."
  ([tab-id #_callback] (gen-call :function ::get (meta &form) tab-id)))

(defmacro get-current
  "Gets the tab that this script call is being made from. May be undefined if called from a non-tab context (for
   example: a background page or popup view)."
  ([#_callback] (gen-call :function ::get-current (meta &form))))

(defmacro connect
  "Connects to the content script(s) in the specified tab. The 'runtime.onConnect' event is fired in each content
   script running in the specified tab for the current extension. For more details, see Content Script Messaging."
  ([tab-id connect-info] (gen-call :function ::connect (meta &form) tab-id connect-info))
  ([tab-id] `(connect ~tab-id :omit)))

(defmacro send-request
  "Sends a single request to the content script(s) in the specified tab, with an optional callback to run when a
   response is sent back.  The 'extension.onRequest' event is fired in each content script running in the specified
   tab for the current extension."
  ([tab-id request #_response-callback] (gen-call :function ::send-request (meta &form) tab-id request)))

(defmacro send-message
  "Sends a single message to the content script(s) in the specified tab, with an optional callback to run when a
   response is sent back.  The 'runtime.onMessage' event is fired in each content script running in the specified tab
   for the current extension."
  ([tab-id message options #_response-callback] (gen-call :function ::send-message (meta &form) tab-id message options))
  ([tab-id message] `(send-message ~tab-id ~message :omit)))

(defmacro get-selected
  "Gets the tab that is selected in the specified window.
   
     |windowId| - Defaults to the current window."
  ([window-id #_callback] (gen-call :function ::get-selected (meta &form) window-id))
  ([] `(get-selected :omit)))

(defmacro get-all-in-window
  "Gets details about all tabs in the specified window.
   
     |windowId| - Defaults to the current window."
  ([window-id #_callback] (gen-call :function ::get-all-in-window (meta &form) window-id))
  ([] `(get-all-in-window :omit)))

(defmacro create
  "Creates a new tab."
  ([create-properties #_callback] (gen-call :function ::create (meta &form) create-properties)))

(defmacro duplicate
  "Duplicates a tab.
   
     |tabId| - The ID of the tab which is to be duplicated."
  ([tab-id #_callback] (gen-call :function ::duplicate (meta &form) tab-id)))

(defmacro query
  "Gets all tabs that have the specified properties, or all tabs if no properties are specified."
  ([query-info #_callback] (gen-call :function ::query (meta &form) query-info)))

(defmacro highlight
  "Highlights the given tabs."
  ([highlight-info #_callback] (gen-call :function ::highlight (meta &form) highlight-info)))

(defmacro update
  "Modifies the properties of a tab. Properties that are not specified in updateProperties are not modified.
   
     |tabId| - Defaults to the selected tab of the current window."
  ([tab-id update-properties #_callback] (gen-call :function ::update (meta &form) tab-id update-properties)))

(defmacro move
  "Moves one or more tabs to a new position within its window, or to a new window. Note that tabs can only be moved to
   and from normal (window.type === 'normal') windows.
   
     |tabIds| - The tab or list of tabs to move."
  ([tab-ids move-properties #_callback] (gen-call :function ::move (meta &form) tab-ids move-properties)))

(defmacro reload
  "Reload a tab.
   
     |tabId| - The ID of the tab to reload; defaults to the selected tab of the current window."
  ([tab-id reload-properties #_callback] (gen-call :function ::reload (meta &form) tab-id reload-properties))
  ([tab-id] `(reload ~tab-id :omit))
  ([] `(reload :omit :omit)))

(defmacro remove
  "Closes one or more tabs.
   
     |tabIds| - The tab or list of tabs to close."
  ([tab-ids #_callback] (gen-call :function ::remove (meta &form) tab-ids)))

(defmacro detect-language
  "Detects the primary language of the content in a tab.
   
     |tabId| - Defaults to the active tab of the current window."
  ([tab-id #_callback] (gen-call :function ::detect-language (meta &form) tab-id))
  ([] `(detect-language :omit)))

(defmacro capture-visible-tab
  "Captures the visible area of the currently active tab in the specified window. You must have &lt;all_urls&gt;
   permission to use this method.
   
     |windowId| - The target window. Defaults to the current window.
     |options| - Details about the format and quality of an image."
  ([window-id options #_callback] (gen-call :function ::capture-visible-tab (meta &form) window-id options))
  ([window-id] `(capture-visible-tab ~window-id :omit))
  ([] `(capture-visible-tab :omit :omit)))

(defmacro execute-script
  "Injects JavaScript code into a page. For details, see the programmatic injection section of the content scripts
   doc.
   
     |tabId| - The ID of the tab in which to run the script; defaults to the active tab of the current window.
     |details| - Details of the script or CSS to inject. Either the code or the file property must be set, but both
                 may not be set at the same time.
     |callback| - Called after all the JavaScript has been executed."
  ([tab-id details #_callback] (gen-call :function ::execute-script (meta &form) tab-id details)))

(defmacro insert-css
  "Injects CSS into a page. For details, see the programmatic injection section of the content scripts doc.
   
     |tabId| - The ID of the tab in which to insert the CSS; defaults to the active tab of the current window.
     |details| - Details of the script or CSS to inject. Either the code or the file property must be set, but both
                 may not be set at the same time.
     |callback| - Called when all the CSS has been inserted."
  ([tab-id details #_callback] (gen-call :function ::insert-css (meta &form) tab-id details)))

(defmacro set-zoom
  "Zooms a specified tab.
   
     |tabId| - The ID of the tab to zoom; defaults to the active tab of the current window.
     |zoomFactor| - The new zoom factor. Use a value of 0 here to set the tab to its current default zoom factor.
                    Values greater than zero specify a (possibly non-default) zoom factor for the tab.
     |callback| - Called after the zoom factor has been changed."
  ([tab-id zoom-factor #_callback] (gen-call :function ::set-zoom (meta &form) tab-id zoom-factor)))

(defmacro get-zoom
  "Gets the current zoom factor of a specified tab.
   
     |tabId| - The ID of the tab to get the current zoom factor from; defaults to the active tab of the current
               window.
     |callback| - Called with the tab's current zoom factor after it has been fetched."
  ([tab-id #_callback] (gen-call :function ::get-zoom (meta &form) tab-id))
  ([] `(get-zoom :omit)))

(defmacro set-zoom-settings
  "Sets the zoom settings for a specified tab, which define how zoom changes are handled. These settings are reset to
   defaults upon navigating the tab.
   
     |tabId| - The ID of the tab to change the zoom settings for; defaults to the active tab of the current window.
     |zoomSettings| - Defines how zoom changes are handled and at what scope.
     |callback| - Called after the zoom settings have been changed."
  ([tab-id zoom-settings #_callback] (gen-call :function ::set-zoom-settings (meta &form) tab-id zoom-settings)))

(defmacro get-zoom-settings
  "Gets the current zoom settings of a specified tab.
   
     |tabId| - The ID of the tab to get the current zoom settings from; defaults to the active tab of the current
               window.
     |callback| - Called with the tab's current zoom settings."
  ([tab-id #_callback] (gen-call :function ::get-zoom-settings (meta &form) tab-id))
  ([] `(get-zoom-settings :omit)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-created-events
  "Fired when a tab is created. Note that the tab's URL may not be set at the time this event fired, but you can
   listen to onUpdated events to be notified when a URL is set."
  ([channel] (gen-call :event ::on-created (meta &form) channel)))

(defmacro tap-on-updated-events
  "Fired when a tab is updated."
  ([channel] (gen-call :event ::on-updated (meta &form) channel)))

(defmacro tap-on-moved-events
  "Fired when a tab is moved within a window. Only one move event is fired, representing the tab the user directly
   moved. Move events are not fired for the other tabs that must move in response. This event is not fired when a tab
   is moved between windows. For that, see 'tabs.onDetached'."
  ([channel] (gen-call :event ::on-moved (meta &form) channel)))

(defmacro tap-on-selection-changed-events
  "Fires when the selected tab in a window changes."
  ([channel] (gen-call :event ::on-selection-changed (meta &form) channel)))

(defmacro tap-on-active-changed-events
  "Fires when the selected tab in a window changes. Note that the tab's URL may not be set at the time this event
   fired, but you can listen to 'tabs.onUpdated' events to be notified when a URL is set."
  ([channel] (gen-call :event ::on-active-changed (meta &form) channel)))

(defmacro tap-on-activated-events
  "Fires when the active tab in a window changes. Note that the tab's URL may not be set at the time this event fired,
   but you can listen to onUpdated events to be notified when a URL is set."
  ([channel] (gen-call :event ::on-activated (meta &form) channel)))

(defmacro tap-on-highlight-changed-events
  "Fired when the highlighted or selected tabs in a window changes."
  ([channel] (gen-call :event ::on-highlight-changed (meta &form) channel)))

(defmacro tap-on-highlighted-events
  "Fired when the highlighted or selected tabs in a window changes."
  ([channel] (gen-call :event ::on-highlighted (meta &form) channel)))

(defmacro tap-on-detached-events
  "Fired when a tab is detached from a window, for example because it is being moved between windows."
  ([channel] (gen-call :event ::on-detached (meta &form) channel)))

(defmacro tap-on-attached-events
  "Fired when a tab is attached to a window, for example because it was moved between windows."
  ([channel] (gen-call :event ::on-attached (meta &form) channel)))

(defmacro tap-on-removed-events
  "Fired when a tab is closed."
  ([channel] (gen-call :event ::on-removed (meta &form) channel)))

(defmacro tap-on-replaced-events
  "Fired when a tab is replaced with another tab due to prerendering or instant."
  ([channel] (gen-call :event ::on-replaced (meta &form) channel)))

(defmacro tap-on-zoom-change-events
  "Fired when a tab is zoomed."
  ([channel] (gen-call :event ::on-zoom-change (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tabs",
   :since "5",
   :properties [{:id ::tab-id-none, :name "TAB_ID_NONE", :since "46", :return-type "unknown-type"}],
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "tab", :type "tabs.Tab"}]}}]}
    {:id ::get-current,
     :name "getCurrent",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::connect,
     :name "connect",
     :return-type "runtime.Port",
     :params [{:name "tab-id", :type "integer"} {:name "connect-info", :optional? true, :type "object"}]}
    {:id ::send-request,
     :name "sendRequest",
     :since "33",
     :deprecated "Please use 'runtime.sendMessage'.",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "request", :type "any"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::send-message,
     :name "sendMessage",
     :since "20",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "message", :type "any"}
      {:name "options", :optional? true, :type "object"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-selected,
     :name "getSelected",
     :since "33",
     :deprecated "Please use 'tabs.query' {active: true}.",
     :callback? true,
     :params
     [{:name "window-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "tab", :type "tabs.Tab"}]}}]}
    {:id ::get-all-in-window,
     :name "getAllInWindow",
     :since "33",
     :deprecated "Please use 'tabs.query' {windowId: windowId}.",
     :callback? true,
     :params
     [{:name "window-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "tabs", :type "[array-of-tabs.Tabs]"}]}}]}
    {:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "create-properties", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tab", :type "tabs.Tab"}]}}]}
    {:id ::duplicate,
     :name "duplicate",
     :since "23",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::query,
     :name "query",
     :since "16",
     :callback? true,
     :params
     [{:name "query-info", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-tabs.Tabs]"}]}}]}
    {:id ::highlight,
     :name "highlight",
     :since "16",
     :callback? true,
     :params
     [{:name "highlight-info", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "window", :type "windows.Window"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "update-properties", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::move,
     :name "move",
     :callback? true,
     :params
     [{:name "tab-ids", :type "integer-or-[array-of-integers]"}
      {:name "move-properties", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tabs", :type "tabs.Tab-or-[array-of-tabs.Tabs]"}]}}]}
    {:id ::reload,
     :name "reload",
     :since "16",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "reload-properties", :optional? true, :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params
     [{:name "tab-ids", :type "integer-or-[array-of-integers]"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::detect-language,
     :name "detectLanguage",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "language", :type "string"}]}}]}
    {:id ::capture-visible-tab,
     :name "captureVisibleTab",
     :callback? true,
     :params
     [{:name "window-id", :optional? true, :type "integer"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "data-url", :type "string"}]}}]}
    {:id ::execute-script,
     :name "executeScript",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :optional? true, :type "[array-of-anys]"}]}}]}
    {:id ::insert-css,
     :name "insertCSS",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "details", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-zoom,
     :name "setZoom",
     :since "42",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "zoom-factor", :type "double"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom,
     :name "getZoom",
     :since "42",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "zoom-factor", :type "double"}]}}]}
    {:id ::set-zoom-settings,
     :name "setZoomSettings",
     :since "42",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "zoom-settings", :type "tabs.ZoomSettings"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom-settings,
     :name "getZoomSettings",
     :since "42",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "zoom-settings", :type "tabs.ZoomSettings"}]}}]}],
   :events
   [{:id ::on-created, :name "onCreated", :params [{:name "tab", :type "tabs.Tab"}]}
    {:id ::on-updated,
     :name "onUpdated",
     :params
     [{:name "tab-id", :type "integer"} {:name "change-info", :type "object"} {:name "tab", :type "tabs.Tab"}]}
    {:id ::on-moved,
     :name "onMoved",
     :params [{:name "tab-id", :type "integer"} {:name "move-info", :type "object"}]}
    {:id ::on-selection-changed,
     :name "onSelectionChanged",
     :since "33",
     :deprecated "Please use 'tabs.onActivated'.",
     :params [{:name "tab-id", :type "integer"} {:name "select-info", :type "object"}]}
    {:id ::on-active-changed,
     :name "onActiveChanged",
     :since "33",
     :deprecated "Please use 'tabs.onActivated'.",
     :params [{:name "tab-id", :type "integer"} {:name "select-info", :type "object"}]}
    {:id ::on-activated, :name "onActivated", :since "18", :params [{:name "active-info", :type "object"}]}
    {:id ::on-highlight-changed,
     :name "onHighlightChanged",
     :since "33",
     :deprecated "Please use 'tabs.onHighlighted'.",
     :params [{:name "select-info", :type "object"}]}
    {:id ::on-highlighted,
     :name "onHighlighted",
     :since "18",
     :params [{:name "highlight-info", :type "object"}]}
    {:id ::on-detached,
     :name "onDetached",
     :params [{:name "tab-id", :type "integer"} {:name "detach-info", :type "object"}]}
    {:id ::on-attached,
     :name "onAttached",
     :params [{:name "tab-id", :type "integer"} {:name "attach-info", :type "object"}]}
    {:id ::on-removed,
     :name "onRemoved",
     :params [{:name "tab-id", :type "integer"} {:name "remove-info", :type "object"}]}
    {:id ::on-replaced,
     :name "onReplaced",
     :since "26",
     :params [{:name "added-tab-id", :type "integer"} {:name "removed-tab-id", :type "integer"}]}
    {:id ::on-zoom-change,
     :name "onZoomChange",
     :since "38",
     :params [{:name "zoom-change-info", :type "object"}]}]})

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