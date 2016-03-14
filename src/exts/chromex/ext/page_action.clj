(ns chromex.ext.page-action
  "Use the chrome.pageAction API to put icons in the main Google Chrome toolbar, to the right of the address bar. Page
   actions represent actions that can be taken on the current page, but that aren't applicable to all pages. Page actions
   appear grayed out when inactive.

     * available since Chrome 5
     * https://developer.chrome.com/extensions/pageAction"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro show
  "Shows the page action. The page action is shown whenever the tab is selected.

     |tab-id| - The id of the tab for which you want to modify the page action.

   https://developer.chrome.com/extensions/pageAction#method-show."
  ([tab-id] (gen-call :function ::show &form tab-id)))

(defmacro hide
  "Hides the page action. Hidden page actions still appear in the Chrome toolbar, but are grayed out.

     |tab-id| - The id of the tab for which you want to modify the page action.

   https://developer.chrome.com/extensions/pageAction#method-hide."
  ([tab-id] (gen-call :function ::hide &form tab-id)))

(defmacro set-title
  "Sets the title of the page action. This is displayed in a tooltip over the page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setTitle-details.

   https://developer.chrome.com/extensions/pageAction#method-setTitle."
  ([details] (gen-call :function ::set-title &form details)))

(defmacro get-title
  "Gets the title of the page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-getTitle-details.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/pageAction#property-callback-result.

   https://developer.chrome.com/extensions/pageAction#method-getTitle."
  ([details] (gen-call :function ::get-title &form details)))

(defmacro set-icon
  "Sets the icon for the page action. The icon can be specified either as the path to an image file or as the pixel data from
   a canvas element, or as dictionary of either one of those. Either the path or the imageData property must be specified.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setIcon-details.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   https://developer.chrome.com/extensions/pageAction#method-setIcon."
  ([details] (gen-call :function ::set-icon &form details)))

(defmacro set-popup
  "Sets the html document to be opened as a popup when the user clicks on the page action's icon.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setPopup-details.

   https://developer.chrome.com/extensions/pageAction#method-setPopup."
  ([details] (gen-call :function ::set-popup &form details)))

(defmacro get-popup
  "Gets the html document set as the popup for this page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-getPopup-details.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/pageAction#property-callback-result.

   https://developer.chrome.com/extensions/pageAction#method-getPopup."
  ([details] (gen-call :function ::get-popup &form details)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clicked-events
  "Fired when a page action icon is clicked.  This event will not fire if the page action has a popup.

   Events will be put on the |channel| with signature [::on-clicked [tab]] where:

     |tab| - https://developer.chrome.com/extensions/pageAction#property-onClicked-tab.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/pageAction#event-onClicked."
  ([channel & args] (apply gen-call :event ::on-clicked &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
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