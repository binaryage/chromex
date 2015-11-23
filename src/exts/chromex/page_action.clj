(ns chromex.page-action
  "Use the chrome.pageAction API to put icons inside the address bar. Page actions represent actions that can be taken on the
   current page, but that aren't applicable to all pages.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/pageAction"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro show
  "Shows the page action. The page action is shown whenever the tab is selected.
   
     |tabId| - The id of the tab for which you want to modify the page action."
  ([tab-id] (gen-call :function ::show &form tab-id)))

(defmacro hide
  "Hides the page action.
   
     |tabId| - The id of the tab for which you want to modify the page action."
  ([tab-id] (gen-call :function ::hide &form tab-id)))

(defmacro set-title
  "Sets the title of the page action. This is displayed in a tooltip over the page action."
  ([details] (gen-call :function ::set-title &form details)))

(defmacro get-title
  "Gets the title of the page action.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([details #_callback] (gen-call :function ::get-title &form details)))

(defmacro set-icon
  "Sets the icon for the page action. The icon can be specified either as the path to an image file or as the pixel data from
   a canvas element, or as dictionary of either one of those. Either the path or the imageData property must be specified.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([details #_callback] (gen-call :function ::set-icon &form details)))

(defmacro set-popup
  "Sets the html document to be opened as a popup when the user clicks on the page action's icon."
  ([details] (gen-call :function ::set-popup &form details)))

(defmacro get-popup
  "Gets the html document set as the popup for this page action.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([details #_callback] (gen-call :function ::get-popup &form details)))

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-clicked-events
  "Fired when a page action icon is clicked.  This event will not fire if the page action has a popup."
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
  {:namespace "chrome.pageAction",
   :since "5",
   :functions
   [{:id ::show, :name "show", :params [{:name "tab-id", :type "integer"}]}
    {:id ::hide, :name "hide", :params [{:name "tab-id", :type "integer"}]}
    {:id ::set-title, :name "setTitle", :params [{:name "details", :type "object"}]}
    {:id ::get-title,
     :name "getTitle",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-icon,
     :name "setIcon",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-popup, :name "setPopup", :params [{:name "details", :type "object"}]}
    {:id ::get-popup,
     :name "getPopup",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}],
   :events [{:id ::on-clicked, :name "onClicked", :params [{:name "tab", :type "tabs.Tab"}]}]})

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