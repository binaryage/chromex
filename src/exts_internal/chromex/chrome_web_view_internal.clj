(ns chromex.chrome-web-view-internal
  "  * available since Chrome 48
     * https://developer.chrome.com/extensions/chromeWebViewInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro context-menus-create
  "  |callback| - Called when the item has been created in the browser. If there were any problems creating the
                  item, details will be available in chrome.runtime.lastError."
  [create-properties #_callback]
  (gen-call :function ::context-menus-create (meta &form) create-properties))

(defmacro context-menus-update
  "Updates a previously created context menu item.
   
     |id| - The ID of the item to update.
     |updateProperties| - The properties to update. Accepts the same values as the create function.
     |callback| - Called when the context menu has been updated."
  [id update-properties #_callback]
  (gen-call :function ::context-menus-update (meta &form) id update-properties))

(defmacro context-menus-remove
  "Removes a context menu item.
   
     |menuItemId| - The ID of the context menu item to remove.
     |callback| - Called when the context menu has been removed."
  [menu-item-id #_callback]
  (gen-call :function ::context-menus-remove (meta &form) menu-item-id))

(defmacro context-menus-remove-all
  "Removes all context menu items added by this webview.
   
     |callback| - Called when removal is complete."
  [#_callback]
  (gen-call :function ::context-menus-remove-all (meta &form)))

(defmacro show-context-menu
  "  |instanceId| - The instance ID of the guest &lt;webview&gt; process. This not exposed to developers through the
                    API.
     |requestId| - The strictly increasing request counter that serves as ID for the context menu. This not exposed
                   to developers through the API.
     |itemsToShow| - Items to be shown in the context menu. These are top level items as opposed to children items."
  [instance-id request-id items-to-show]
  (gen-call :function ::show-context-menu (meta &form) instance-id request-id items-to-show))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.chromeWebViewInternal",
   :since "48",
   :functions
   [{:id ::context-menus-create,
     :name "contextMenusCreate",
     :callback? true,
     :return-type "integer-or-string",
     :params [{:name "create-properties", :type "object"} {:name "callback", :type :callback}]}
    {:id ::context-menus-update,
     :name "contextMenusUpdate",
     :callback? true,
     :params
     [{:name "id", :type "integer-or-string"}
      {:name "update-properties", :type "object"}
      {:name "callback", :type :callback}]}
    {:id ::context-menus-remove,
     :name "contextMenusRemove",
     :callback? true,
     :params [{:name "menu-item-id", :type "integer-or-string"} {:name "callback", :type :callback}]}
    {:id ::context-menus-remove-all,
     :name "contextMenusRemoveAll",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::show-context-menu,
     :name "showContextMenu",
     :params
     [{:name "instance-id", :type "integer"}
      {:name "request-id", :type "integer"}
      {:name "items-to-show", :type "[array-of-objects]"}]}]})

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