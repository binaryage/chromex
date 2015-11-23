(ns chromex.context-menus-internal
  "Use the chrome.contextMenus API to add items to Google Chrome's context menu. You can choose what types of objects
   your context menu additions apply to, such as images, hyperlinks, and pages.
   
     * available since Chrome 35
     * https://developer.chrome.com/extensions/contextMenusInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-clicked-events
  "Fired when a context menu item is clicked."
  ([channel] (gen-call :event ::on-clicked (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.contextMenusInternal",
   :since "35",
   :events
   [{:id ::on-clicked,
     :name "onClicked",
     :params [{:name "info", :type "object"} {:name "tab", :optional? true, :type "tabs.Tab"}]}]})

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