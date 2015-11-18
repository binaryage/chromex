(ns chromex.browser-action
  "Use browser actions to put icons in the main Google Chrome toolbar, to the right of the address bar. In addition
   to its icon, a browser action can also have a tooltip, a badge, and a popup.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/browserAction"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-title
  "Sets the title of the browser action. This shows up in the tooltip."
  [details]
  (gen-call :function ::set-title (meta &form) details))

(defmacro get-title
  "Gets the title of the browser action."
  [details #_callback]
  (gen-call :function ::get-title (meta &form) details))

(defmacro set-icon
  "Sets the icon for the browser action. The icon can be specified either as the path to an image file or as the pixel
   data from a canvas element, or as dictionary of either one of those. Either the path or the imageData property must
   be specified."
  [details #_callback]
  (gen-call :function ::set-icon (meta &form) details))

(defmacro set-popup
  "Sets the html document to be opened as a popup when the user clicks on the browser action's icon."
  [details]
  (gen-call :function ::set-popup (meta &form) details))

(defmacro get-popup
  "Gets the html document set as the popup for this browser action."
  [details #_callback]
  (gen-call :function ::get-popup (meta &form) details))

(defmacro set-badge-text
  "Sets the badge text for the browser action. The badge is displayed on top of the icon."
  [details]
  (gen-call :function ::set-badge-text (meta &form) details))

(defmacro get-badge-text
  "Gets the badge text of the browser action. If no tab is specified, the non-tab-specific badge text is returned."
  [details #_callback]
  (gen-call :function ::get-badge-text (meta &form) details))

(defmacro set-badge-background-color
  "Sets the background color for the badge."
  [details]
  (gen-call :function ::set-badge-background-color (meta &form) details))

(defmacro get-badge-background-color
  "Gets the background color of the browser action."
  [details #_callback]
  (gen-call :function ::get-badge-background-color (meta &form) details))

(defmacro enable
  "Enables the browser action for a tab. By default, browser actions are enabled.
   
     |tabId| - The id of the tab for which you want to modify the browser action."
  [tab-id]
  (gen-call :function ::enable (meta &form) tab-id))

(defmacro disable
  "Disables the browser action for a tab.
   
     |tabId| - The id of the tab for which you want to modify the browser action."
  [tab-id]
  (gen-call :function ::disable (meta &form) tab-id))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-clicked
  "Fired when a browser action icon is clicked.  This event will not fire if the browser action has a popup."
  [channel]
  (gen-call :event ::on-clicked (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.browserAction",
   :since "5",
   :functions
   [{:id ::set-title, :name "setTitle", :params [{:name "details", :type "object"}]}
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
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::set-popup, :name "setPopup", :params [{:name "details", :type "object"}]}
    {:id ::get-popup,
     :name "getPopup",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-badge-text, :name "setBadgeText", :params [{:name "details", :type "object"}]}
    {:id ::get-badge-text,
     :name "getBadgeText",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-badge-background-color,
     :name "setBadgeBackgroundColor",
     :params [{:name "details", :type "object"}]}
    {:id ::get-badge-background-color,
     :name "getBadgeBackgroundColor",
     :since "19",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "browserAction.ColorArray"}]}}]}
    {:id ::enable, :name "enable", :since "22", :params [{:name "tab-id", :type "integer"}]}
    {:id ::disable, :name "disable", :since "22", :params [{:name "tab-id", :type "integer"}]}],
   :events [{:id ::on-clicked, :name "onClicked", :params [{:name "tab", :type "tabs.Tab"}]}]})

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