(ns chromex.app.tabs
  "Use the chrome.tabs API to interact with the browser's tab system. You can use this API to create, modify, and rearrange
   tabs in the browser.

     * available since Chrome 38
     * https://developer.chrome.com/apps/tabs"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-tab-id-none
  "An ID that represents the absence of a browser tab.

   https://developer.chrome.com/apps/tabs#property-TAB_ID_NONE."
  ([] (gen-call :property ::tab-id-none &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves details about the specified tab.

     |tab-id| - https://developer.chrome.com/apps/tabs#property-get-tabId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - https://developer.chrome.com/apps/tabs#property-callback-tab.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-get."
  ([tab-id] (gen-call :function ::get &form tab-id)))

(defmacro get-current
  "Gets the tab that this script call is being made from. May be undefined if called from a non-tab context (for example, a
   background page or popup view).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - https://developer.chrome.com/apps/tabs#property-callback-tab.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-getCurrent."
  ([] (gen-call :function ::get-current &form)))

(defmacro connect
  "Connects to the content script(s) in the specified tab. The 'runtime.onConnect' event is fired in each content script
   running in the specified tab for the current extension. For more details, see Content Script Messaging.

     |tab-id| - https://developer.chrome.com/apps/tabs#property-connect-tabId.
     |connect-info| - https://developer.chrome.com/apps/tabs#property-connect-connectInfo.

   https://developer.chrome.com/apps/tabs#method-connect."
  ([tab-id connect-info] (gen-call :function ::connect &form tab-id connect-info))
  ([tab-id] `(connect ~tab-id :omit)))

(defmacro send-request
  "Sends a single request to the content script(s) in the specified tab, with an optional callback to run when a response is
   sent back.  The 'extension.onRequest' event is fired in each content script running in the specified tab for the current
   extension.

     |tab-id| - https://developer.chrome.com/apps/tabs#property-sendRequest-tabId.
     |request| - https://developer.chrome.com/apps/tabs#property-sendRequest-request.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The JSON response object sent by the handler of the request. If an error occurs while connecting to the
                  specified tab, the callback is called with no arguments and 'runtime.lastError' is set to the error message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-sendRequest."
  ([tab-id request] (gen-call :function ::send-request &form tab-id request)))

(defmacro send-message
  "Sends a single message to the content script(s) in the specified tab, with an optional callback to run when a response is
   sent back.  The 'runtime.onMessage' event is fired in each content script running in the specified tab for the current
   extension.

     |tab-id| - https://developer.chrome.com/apps/tabs#property-sendMessage-tabId.
     |message| - The message to send. This message should be a JSON-ifiable object.
     |options| - https://developer.chrome.com/apps/tabs#property-sendMessage-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - The JSON response object sent by the handler of the message. If an error occurs while connecting to the
                  specified tab, the callback is called with no arguments and 'runtime.lastError' is set to the error message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-sendMessage."
  ([tab-id message options] (gen-call :function ::send-message &form tab-id message options))
  ([tab-id message] `(send-message ~tab-id ~message :omit)))

(defmacro get-selected
  "Gets the tab that is selected in the specified window.

     |window-id| - Defaults to the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - https://developer.chrome.com/apps/tabs#property-callback-tab.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-getSelected."
  ([window-id] (gen-call :function ::get-selected &form window-id))
  ([] `(get-selected :omit)))

(defmacro get-all-in-window
  "Gets details about all tabs in the specified window.

     |window-id| - Defaults to the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tabs] where:

     |tabs| - https://developer.chrome.com/apps/tabs#property-callback-tabs.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-getAllInWindow."
  ([window-id] (gen-call :function ::get-all-in-window &form window-id))
  ([] `(get-all-in-window :omit)))

(defmacro create
  "Creates a new tab.

     |create-properties| - https://developer.chrome.com/apps/tabs#property-create-createProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - The created tab.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-create."
  ([create-properties] (gen-call :function ::create &form create-properties)))

(defmacro duplicate
  "Duplicates a tab.

     |tab-id| - The ID of the tab to duplicate.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - Details about the duplicated tab. The 'tabs.Tab' object does not contain url, pendingUrl, title, and favIconUrl
             if the 'tabs' permission has not been requested.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-duplicate."
  ([tab-id] (gen-call :function ::duplicate &form tab-id)))

(defmacro query
  "Gets all tabs that have the specified properties, or all tabs if no properties are specified.

     |query-info| - https://developer.chrome.com/apps/tabs#property-query-queryInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/tabs#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-query."
  ([query-info] (gen-call :function ::query &form query-info)))

(defmacro highlight
  "Highlights the given tabs and focuses on the first of group. Will appear to do nothing if the specified tab is currently
   active.

     |highlight-info| - https://developer.chrome.com/apps/tabs#property-highlight-highlightInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - Contains details about the window whose tabs were highlighted.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-highlight."
  ([highlight-info] (gen-call :function ::highlight &form highlight-info)))

(defmacro update
  "Modifies the properties of a tab. Properties that are not specified in updateProperties are not modified.

     |tab-id| - Defaults to the selected tab of the current window.
     |update-properties| - https://developer.chrome.com/apps/tabs#property-update-updateProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - Details about the updated tab. The 'tabs.Tab' object does not contain url, pendingUrl, title, and favIconUrl if
             the 'tabs' permission has not been requested.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-update."
  ([tab-id update-properties] (gen-call :function ::update &form tab-id update-properties)))

(defmacro move
  "Moves one or more tabs to a new position within its window, or to a new window. Note that tabs can only be moved to and
   from normal (window.type === 'normal') windows.

     |tab-ids| - The tab ID or list of tab IDs to move.
     |move-properties| - https://developer.chrome.com/apps/tabs#property-move-moveProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tabs] where:

     |tabs| - Details about the moved tabs.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-move."
  ([tab-ids move-properties] (gen-call :function ::move &form tab-ids move-properties)))

(defmacro reload
  "Reload a tab.

     |tab-id| - The ID of the tab to reload; defaults to the selected tab of the current window.
     |reload-properties| - https://developer.chrome.com/apps/tabs#property-reload-reloadProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-reload."
  ([tab-id reload-properties] (gen-call :function ::reload &form tab-id reload-properties))
  ([tab-id] `(reload ~tab-id :omit))
  ([] `(reload :omit :omit)))

(defmacro remove
  "Closes one or more tabs.

     |tab-ids| - The tab ID or list of tab IDs to close.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-remove."
  ([tab-ids] (gen-call :function ::remove &form tab-ids)))

(defmacro group
  "Adds one or more tabs to a specified group, or if no group is specified, adds the given tabs to a newly created group.

     |options| - https://developer.chrome.com/apps/tabs#property-group-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [group-id] where:

     |group-id| - The ID of the group that the tabs were added to.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-group."
  ([options] (gen-call :function ::group &form options)))

(defmacro ungroup
  "Removes one or more tabs from their respective groups. If any groups become empty, they are deleted.

     |tab-ids| - The tab ID or list of tab IDs to remove from their respective groups.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-ungroup."
  ([tab-ids] (gen-call :function ::ungroup &form tab-ids)))

(defmacro detect-language
  "Detects the primary language of the content in a tab.

     |tab-id| - Defaults to the active tab of the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [language] where:

     |language| - An ISO language code such as en or fr. For a complete list of languages supported by this method, see
                  kLanguageInfoTable. The second to fourth columns are checked and the first non-NULL value is returned,
                  except for Simplified Chinese for which zh-CN is returned. For an unknown/undefined language, und is
                  returned.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-detectLanguage."
  ([tab-id] (gen-call :function ::detect-language &form tab-id))
  ([] `(detect-language :omit)))

(defmacro capture-visible-tab
  "Captures the visible area of the currently active tab in the specified window. In order to call this method, the extension
   must have either the &lt;all_urls&gt; permission or the activeTab permission. In addition to sites that extensions can
   normally access, this method allows extensions to capture sensitive sites that are otherwise restricted, including
   chrome:-scheme pages, other extensions' pages, and data: URLs. These sensitive sites can only be captured with the
   activeTab permission. File URLs may be captured only if the extension has been granted file access.

     |window-id| - The target window. Defaults to the current window.
     |options| - Details about the format and quality of an image.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data-url] where:

     |data-url| - A data URL that encodes an image of the visible area of the captured tab. May be assigned to the 'src'
                  property of an HTML img element for display.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-captureVisibleTab."
  ([window-id options] (gen-call :function ::capture-visible-tab &form window-id options))
  ([window-id] `(capture-visible-tab ~window-id :omit))
  ([] `(capture-visible-tab :omit :omit)))

(defmacro execute-script
  "Injects JavaScript code into a page. For details, see the programmatic injection section of the content scripts doc.

     |tab-id| - The ID of the tab in which to run the script; defaults to the active tab of the current window.
     |details| - Details of the script to run. Either the code or the file property must be set, but both may not be set at
                 the same time.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The result of the script in every injected frame.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-executeScript."
  ([tab-id details] (gen-call :function ::execute-script &form tab-id details)))

(defmacro insert-css
  "Injects CSS into a page. For details, see the programmatic injection section of the content scripts doc.

     |tab-id| - The ID of the tab in which to insert the CSS; defaults to the active tab of the current window.
     |details| - Details of the CSS text to insert. Either the code or the file property must be set, but both may not be
                 set at the same time.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-insertCSS."
  ([tab-id details] (gen-call :function ::insert-css &form tab-id details)))

(defmacro remove-css
  "Removes from a page CSS that was previously injected by a call to 'tabs.insertCSS'.

     |tab-id| - The ID of the tab from which to remove the CSS; defaults to the active tab of the current window.
     |details| - Details of the CSS text to remove. Either the code or the file property must be set, but both may not be
                 set at the same time.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-removeCSS."
  ([tab-id details] (gen-call :function ::remove-css &form tab-id details)))

(defmacro set-zoom
  "Zooms a specified tab.

     |tab-id| - The ID of the tab to zoom; defaults to the active tab of the current window.
     |zoom-factor| - The new zoom factor. A value of 0 sets the tab to its current default zoom factor. Values greater than
                     0 specify a (possibly non-default) zoom factor for the tab.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-setZoom."
  ([tab-id zoom-factor] (gen-call :function ::set-zoom &form tab-id zoom-factor)))

(defmacro get-zoom
  "Gets the current zoom factor of a specified tab.

     |tab-id| - The ID of the tab to get the current zoom factor from; defaults to the active tab of the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [zoom-factor] where:

     |zoom-factor| - The tab's current zoom factor.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-getZoom."
  ([tab-id] (gen-call :function ::get-zoom &form tab-id))
  ([] `(get-zoom :omit)))

(defmacro set-zoom-settings
  "Sets the zoom settings for a specified tab, which define how zoom changes are handled. These settings are reset to defaults
   upon navigating the tab.

     |tab-id| - The ID of the tab to change the zoom settings for; defaults to the active tab of the current window.
     |zoom-settings| - Defines how zoom changes are handled and at what scope.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-setZoomSettings."
  ([tab-id zoom-settings] (gen-call :function ::set-zoom-settings &form tab-id zoom-settings)))

(defmacro get-zoom-settings
  "Gets the current zoom settings of a specified tab.

     |tab-id| - The ID of the tab to get the current zoom settings from; defaults to the active tab of the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [zoom-settings] where:

     |zoom-settings| - The tab's current zoom settings.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-getZoomSettings."
  ([tab-id] (gen-call :function ::get-zoom-settings &form tab-id))
  ([] `(get-zoom-settings :omit)))

(defmacro discard
  "Discards a tab from memory. Discarded tabs are still visible on the tab strip and are reloaded when activated.

     |tab-id| - The ID of the tab to be discarded. If specified, the tab is discarded unless it is active or already
                discarded. If omitted, the browser discards the least important tab. This can fail if no discardable tabs
                exist.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tab] where:

     |tab| - The discarded tab, if it was successfully discarded; undefined otherwise.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-discard."
  ([tab-id] (gen-call :function ::discard &form tab-id))
  ([] `(discard :omit)))

(defmacro go-forward
  "Go foward to the next page, if one is available.

     |tab-id| - The ID of the tab to navigate forward; defaults to the selected tab of the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-goForward."
  ([tab-id] (gen-call :function ::go-forward &form tab-id))
  ([] `(go-forward :omit)))

(defmacro go-back
  "Go back to the previous page, if one is available.

     |tab-id| - The ID of the tab to navigate back; defaults to the selected tab of the current window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tabs#method-goBack."
  ([tab-id] (gen-call :function ::go-back &form tab-id))
  ([] `(go-back :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "Fired when a tab is created. Note that the tab's URL may not be set at the time this event is fired, but you can listen to
   onUpdated events so as to be notified when a URL is set.

   Events will be put on the |channel| with signature [::on-created [tab]] where:

     |tab| - Details of the tab that was created.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-updated-events
  "Fired when a tab is updated.

   Events will be put on the |channel| with signature [::on-updated [tab-id change-info tab]] where:

     |tab-id| - https://developer.chrome.com/apps/tabs#property-onUpdated-tabId.
     |change-info| - Lists the changes to the state of the tab that was updated.
     |tab| - Gives the state of the tab that was updated.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onUpdated."
  ([channel & args] (apply gen-call :event ::on-updated &form channel args)))

(defmacro tap-on-moved-events
  "Fired when a tab is moved within a window. Only one move event is fired, representing the tab the user directly moved. Move
   events are not fired for the other tabs that must move in response to the manually-moved tab. This event is not fired when
   a tab is moved between windows; for details, see 'tabs.onDetached'.

   Events will be put on the |channel| with signature [::on-moved [tab-id move-info]] where:

     |tab-id| - https://developer.chrome.com/apps/tabs#property-onMoved-tabId.
     |move-info| - https://developer.chrome.com/apps/tabs#property-onMoved-moveInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onMoved."
  ([channel & args] (apply gen-call :event ::on-moved &form channel args)))

(defmacro tap-on-selection-changed-events
  "Fires when the selected tab in a window changes.

   Events will be put on the |channel| with signature [::on-selection-changed [tab-id select-info]] where:

     |tab-id| - The ID of the tab that has become active.
     |select-info| - https://developer.chrome.com/apps/tabs#property-onSelectionChanged-selectInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onSelectionChanged."
  ([channel & args] (apply gen-call :event ::on-selection-changed &form channel args)))

(defmacro tap-on-active-changed-events
  "Fires when the selected tab in a window changes. Note that the tab's URL may not be set at the time this event fired, but
   you can listen to 'tabs.onUpdated' events so as to be notified when a URL is set.

   Events will be put on the |channel| with signature [::on-active-changed [tab-id select-info]] where:

     |tab-id| - The ID of the tab that has become active.
     |select-info| - https://developer.chrome.com/apps/tabs#property-onActiveChanged-selectInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onActiveChanged."
  ([channel & args] (apply gen-call :event ::on-active-changed &form channel args)))

(defmacro tap-on-activated-events
  "Fires when the active tab in a window changes. Note that the tab's URL may not be set at the time this event fired, but you
   can listen to onUpdated events so as to be notified when a URL is set.

   Events will be put on the |channel| with signature [::on-activated [active-info]] where:

     |active-info| - https://developer.chrome.com/apps/tabs#property-onActivated-activeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onActivated."
  ([channel & args] (apply gen-call :event ::on-activated &form channel args)))

(defmacro tap-on-highlight-changed-events
  "Fired when the highlighted or selected tabs in a window changes.

   Events will be put on the |channel| with signature [::on-highlight-changed [select-info]] where:

     |select-info| - https://developer.chrome.com/apps/tabs#property-onHighlightChanged-selectInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onHighlightChanged."
  ([channel & args] (apply gen-call :event ::on-highlight-changed &form channel args)))

(defmacro tap-on-highlighted-events
  "Fired when the highlighted or selected tabs in a window changes.

   Events will be put on the |channel| with signature [::on-highlighted [highlight-info]] where:

     |highlight-info| - https://developer.chrome.com/apps/tabs#property-onHighlighted-highlightInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onHighlighted."
  ([channel & args] (apply gen-call :event ::on-highlighted &form channel args)))

(defmacro tap-on-detached-events
  "Fired when a tab is detached from a window; for example, because it was moved between windows.

   Events will be put on the |channel| with signature [::on-detached [tab-id detach-info]] where:

     |tab-id| - https://developer.chrome.com/apps/tabs#property-onDetached-tabId.
     |detach-info| - https://developer.chrome.com/apps/tabs#property-onDetached-detachInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onDetached."
  ([channel & args] (apply gen-call :event ::on-detached &form channel args)))

(defmacro tap-on-attached-events
  "Fired when a tab is attached to a window; for example, because it was moved between windows.

   Events will be put on the |channel| with signature [::on-attached [tab-id attach-info]] where:

     |tab-id| - https://developer.chrome.com/apps/tabs#property-onAttached-tabId.
     |attach-info| - https://developer.chrome.com/apps/tabs#property-onAttached-attachInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onAttached."
  ([channel & args] (apply gen-call :event ::on-attached &form channel args)))

(defmacro tap-on-removed-events
  "Fired when a tab is closed.

   Events will be put on the |channel| with signature [::on-removed [tab-id remove-info]] where:

     |tab-id| - https://developer.chrome.com/apps/tabs#property-onRemoved-tabId.
     |remove-info| - https://developer.chrome.com/apps/tabs#property-onRemoved-removeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onRemoved."
  ([channel & args] (apply gen-call :event ::on-removed &form channel args)))

(defmacro tap-on-replaced-events
  "Fired when a tab is replaced with another tab due to prerendering or instant.

   Events will be put on the |channel| with signature [::on-replaced [added-tab-id removed-tab-id]] where:

     |added-tab-id| - https://developer.chrome.com/apps/tabs#property-onReplaced-addedTabId.
     |removed-tab-id| - https://developer.chrome.com/apps/tabs#property-onReplaced-removedTabId.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onReplaced."
  ([channel & args] (apply gen-call :event ::on-replaced &form channel args)))

(defmacro tap-on-zoom-change-events
  "Fired when a tab is zoomed.

   Events will be put on the |channel| with signature [::on-zoom-change [zoom-change-info]] where:

     |zoom-change-info| - https://developer.chrome.com/apps/tabs#property-onZoomChange-ZoomChangeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/tabs#event-onZoomChange."
  ([channel & args] (apply gen-call :event ::on-zoom-change &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.tabs namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tabs",
   :since "38",
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
     [{:name "callback", :type :callback, :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::connect,
     :name "connect",
     :return-type "runtime.Port",
     :params [{:name "tab-id", :type "integer"} {:name "connect-info", :optional? true, :type "object"}]}
    {:id ::send-request,
     :name "sendRequest",
     :since "38",
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
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "message", :type "any"}
      {:name "options", :optional? true, :since "41", :type "object"}
      {:name "response-callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "response", :type "any"}]}}]}
    {:id ::get-selected,
     :name "getSelected",
     :since "38",
     :deprecated "Please use 'tabs.query' {active: true}.",
     :callback? true,
     :params
     [{:name "window-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "tab", :type "tabs.Tab"}]}}]}
    {:id ::get-all-in-window,
     :name "getAllInWindow",
     :since "38",
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
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "tab", :type "tabs.Tab"}]}}]}
    {:id ::duplicate,
     :name "duplicate",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::query,
     :name "query",
     :callback? true,
     :params
     [{:name "query-info", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-tabs.Tabs]"}]}}]}
    {:id ::highlight,
     :name "highlight",
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
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "reload-properties", :optional? true, :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params
     [{:name "tab-ids", :type "integer-or-[array-of-integers]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::group,
     :name "group",
     :since "master",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "group-id", :type "integer"}]}}]}
    {:id ::ungroup,
     :name "ungroup",
     :since "master",
     :callback? true,
     :params
     [{:name "tab-ids", :type "integer-or-[array-of-integers]"} {:name "callback", :optional? true, :type :callback}]}
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
    {:id ::remove-css,
     :name "removeCSS",
     :since "future",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "details", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-zoom,
     :name "setZoom",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "zoom-factor", :type "double"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom,
     :name "getZoom",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "zoom-factor", :type "double"}]}}]}
    {:id ::set-zoom-settings,
     :name "setZoomSettings",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "zoom-settings", :type "tabs.ZoomSettings"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-zoom-settings,
     :name "getZoomSettings",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "zoom-settings", :type "tabs.ZoomSettings"}]}}]}
    {:id ::discard,
     :name "discard",
     :since "54",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "tab", :optional? true, :type "tabs.Tab"}]}}]}
    {:id ::go-forward,
     :name "goForward",
     :since "72",
     :callback? true,
     :params [{:name "tab-id", :optional? true, :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::go-back,
     :name "goBack",
     :since "72",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-created, :name "onCreated", :params [{:name "tab", :type "tabs.Tab"}]}
    {:id ::on-updated,
     :name "onUpdated",
     :params [{:name "tab-id", :type "integer"} {:name "change-info", :type "object"} {:name "tab", :type "tabs.Tab"}]}
    {:id ::on-moved, :name "onMoved", :params [{:name "tab-id", :type "integer"} {:name "move-info", :type "object"}]}
    {:id ::on-selection-changed,
     :name "onSelectionChanged",
     :since "38",
     :deprecated "Please use 'tabs.onActivated'.",
     :params [{:name "tab-id", :type "integer"} {:name "select-info", :type "object"}]}
    {:id ::on-active-changed,
     :name "onActiveChanged",
     :since "38",
     :deprecated "Please use 'tabs.onActivated'.",
     :params [{:name "tab-id", :type "integer"} {:name "select-info", :type "object"}]}
    {:id ::on-activated, :name "onActivated", :params [{:name "active-info", :type "object"}]}
    {:id ::on-highlight-changed,
     :name "onHighlightChanged",
     :since "38",
     :deprecated "Please use 'tabs.onHighlighted'.",
     :params [{:name "select-info", :type "object"}]}
    {:id ::on-highlighted, :name "onHighlighted", :params [{:name "highlight-info", :type "object"}]}
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
     :params [{:name "added-tab-id", :type "integer"} {:name "removed-tab-id", :type "integer"}]}
    {:id ::on-zoom-change, :name "onZoomChange", :params [{:name "zoom-change-info", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))