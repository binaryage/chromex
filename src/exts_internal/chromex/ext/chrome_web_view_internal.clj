(ns chromex.ext.chrome-web-view-internal
  "  * available since Chrome 54"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro context-menus-create
  "  |create-properties| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([create-properties] (gen-call :function ::context-menus-create &form create-properties)))

(defmacro context-menus-update
  "Updates a previously created context menu item.

     |id| - The ID of the item to update.
     |update-properties| - The properties to update. Accepts the same values as the create function.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id update-properties] (gen-call :function ::context-menus-update &form id update-properties)))

(defmacro context-menus-remove
  "Removes a context menu item.

     |menu-item-id| - The ID of the context menu item to remove.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([menu-item-id] (gen-call :function ::context-menus-remove &form menu-item-id)))

(defmacro context-menus-remove-all
  "Removes all context menu items added by this webview.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::context-menus-remove-all &form)))

(defmacro show-context-menu
  "  |instance-id| - The instance ID of the guest &lt;webview&gt; process. This not exposed to developers through the API.
     |request-id| - The strictly increasing request counter that serves as ID for the context menu. This not exposed to
                    developers through the API.
     |items-to-show| - Items to be shown in the context menu. These are top level items as opposed to children items."
  ([instance-id request-id items-to-show] (gen-call :function ::show-context-menu &form instance-id request-id items-to-show))
  ([instance-id request-id] `(show-context-menu ~instance-id ~request-id :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.chrome-web-view-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.chromeWebViewInternal",
   :since "54",
   :functions
   [{:id ::context-menus-create,
     :name "contextMenusCreate",
     :callback? true,
     :return-type "integer-or-string",
     :params [{:name "create-properties", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::context-menus-update,
     :name "contextMenusUpdate",
     :callback? true,
     :params
     [{:name "id", :type "integer-or-string"}
      {:name "update-properties", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::context-menus-remove,
     :name "contextMenusRemove",
     :callback? true,
     :params [{:name "menu-item-id", :type "integer-or-string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::context-menus-remove-all,
     :name "contextMenusRemoveAll",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::show-context-menu,
     :name "showContextMenu",
     :params
     [{:name "instance-id", :type "integer"}
      {:name "request-id", :type "integer"}
      {:name "items-to-show", :optional? true, :type "[array-of-objects]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))