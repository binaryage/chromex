(ns chromex.context-menus
  "Use the chrome.contextMenus API to add items to Google Chrome's context menu. You can choose what types of objects your
   context menu additions apply to, such as images, hyperlinks, and pages.
   
     * available since Chrome 6
     * https://developer.chrome.com/extensions/contextMenus"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-action-menu-top-level-limit
  "The maximum number of top level extension items that can be added to an extension action context menu. Any items beyond
   this limit will be ignored."
  ([] (gen-call :property ::action-menu-top-level-limit &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a new context menu item. Note that if an error occurs during creation, you may not find out until the creation
   callback fires (the details will be in chrome.runtime.lastError).
   
     |callback| - Called when the item has been created in the browser. If there were any problems creating the item,
                  details will be available in chrome.runtime.lastError.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([create-properties #_callback] (gen-call :function ::create &form create-properties)))

(defmacro update
  "Updates a previously created context menu item.
   
     |id| - The ID of the item to update.
     |updateProperties| - The properties to update. Accepts the same values as the create function.
     |callback| - Called when the context menu has been updated.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id update-properties #_callback] (gen-call :function ::update &form id update-properties)))

(defmacro remove
  "Removes a context menu item.
   
     |menuItemId| - The ID of the context menu item to remove.
     |callback| - Called when the context menu has been removed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([menu-item-id #_callback] (gen-call :function ::remove &form menu-item-id)))

(defmacro remove-all
  "Removes all context menu items added by this extension.
   
     |callback| - Called when removal is complete.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::remove-all &form)))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-clicked-events
  "Fired when a context menu item is clicked."
  ([channel] (gen-call :event ::on-clicked &form channel)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.contextMenus",
   :since "6",
   :properties
   [{:id ::action-menu-top-level-limit, :name "ACTION_MENU_TOP_LEVEL_LIMIT", :since "38", :return-type "unknown-type"}],
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
     :since "21",
     :params [{:name "info", :type "object"} {:name "tab", :optional? true, :type "tabs.Tab"}]}]})

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