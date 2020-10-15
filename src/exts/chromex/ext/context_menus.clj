(ns chromex.ext.context-menus
  "Use the chrome.contextMenus API to add items to Google Chrome's context menu. You can choose what types of objects your
   context menu additions apply to, such as images, hyperlinks, and pages.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/contextMenus"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-action-menu-top-level-limit
  "The maximum number of top level extension items that can be added to an extension action context menu. Any items beyond
   this limit will be ignored.

   https://developer.chrome.com/extensions/contextMenus#property-ACTION_MENU_TOP_LEVEL_LIMIT."
  ([] (gen-call :property ::action-menu-top-level-limit &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a new context menu item. If an error occurs during creation, it may not be detected until the creation callback
   fires; details will be in 'runtime.lastError'.

     |create-properties| - https://developer.chrome.com/extensions/contextMenus#property-create-createProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/contextMenus#method-create."
  ([create-properties] (gen-call :function ::create &form create-properties)))

(defmacro update
  "Updates a previously created context menu item.

     |id| - The ID of the item to update.
     |update-properties| - The properties to update. Accepts the same values as the 'contextMenus.create' function.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/contextMenus#method-update."
  ([id update-properties] (gen-call :function ::update &form id update-properties)))

(defmacro remove
  "Removes a context menu item.

     |menu-item-id| - The ID of the context menu item to remove.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/contextMenus#method-remove."
  ([menu-item-id] (gen-call :function ::remove &form menu-item-id)))

(defmacro remove-all
  "Removes all context menu items added by this extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/contextMenus#method-removeAll."
  ([] (gen-call :function ::remove-all &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clicked-events
  "Fired when a context menu item is clicked.

   Events will be put on the |channel| with signature [::on-clicked [info tab]] where:

     |info| - Information about the item clicked and the context where the click happened.
     |tab| - The details of the tab where the click took place. If the click did not take place in a tab, this parameter will
             be missing.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/contextMenus#event-onClicked."
  ([channel & args] (apply gen-call :event ::on-clicked &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.context-menus namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.contextMenus",
   :since "38",
   :properties [{:id ::action-menu-top-level-limit, :name "ACTION_MENU_TOP_LEVEL_LIMIT", :return-type "unknown-type"}],
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :return-type "integer-or-string",
     :params [{:name "create-properties", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "id", :type "integer-or-string"}
      {:name "update-properties", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params [{:name "menu-item-id", :type "integer-or-string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-all,
     :name "removeAll",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-clicked,
     :name "onClicked",
     :params [{:name "info", :type "object"} {:name "tab", :optional? true, :type "tabs.Tab"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))