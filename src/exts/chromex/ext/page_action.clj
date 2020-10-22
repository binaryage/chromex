(ns chromex.ext.page-action
  "Use the chrome.pageAction API to put icons in the main Google Chrome toolbar, to the right of the address bar. Page
   actions represent actions that can be taken on the current page, but that aren't applicable to all pages. Page actions
   appear grayed out when inactive.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/pageAction"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro show
  "Shows the page action. The page action is shown whenever the tab is selected.

     |tab-id| - The id of the tab for which you want to modify the page action.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-show."
  ([tab-id] (gen-call :function ::show &form tab-id)))

(defmacro hide
  "Hides the page action. Hidden page actions still appear in the Chrome toolbar, but are grayed out.

     |tab-id| - The id of the tab for which you want to modify the page action.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-hide."
  ([tab-id] (gen-call :function ::hide &form tab-id)))

(defmacro set-title
  "Sets the title of the page action. This is displayed in a tooltip over the page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setTitle-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-setTitle."
  ([details] (gen-call :function ::set-title &form details)))

(defmacro get-title
  "Gets the title of the page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-getTitle-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/pageAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-getTitle."
  ([details] (gen-call :function ::get-title &form details)))

(defmacro set-icon
  "Sets the icon for the page action. The icon can be specified either as the path to an image file or as the pixel data from
   a canvas element, or as dictionary of either one of those. Either the path or the imageData property must be specified.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setIcon-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-setIcon."
  ([details] (gen-call :function ::set-icon &form details)))

(defmacro set-popup
  "Sets the html document to be opened as a popup when the user clicks on the page action's icon.

     |details| - https://developer.chrome.com/extensions/pageAction#property-setPopup-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/pageAction#method-setPopup."
  ([details] (gen-call :function ::set-popup &form details)))

(defmacro get-popup
  "Gets the html document set as the popup for this page action.

     |details| - https://developer.chrome.com/extensions/pageAction#property-getPopup-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/pageAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

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
  "Taps all valid non-deprecated events in chromex.ext.page-action namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.pageAction",
   :since "38",
   :functions
   [{:id ::show,
     :name "show",
     :callback? true,
     :params [{:name "tab-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::hide,
     :name "hide",
     :callback? true,
     :params [{:name "tab-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-title,
     :name "setTitle",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-title,
     :name "getTitle",
     :callback? true,
     :params
     [{:name "details", :type "pageAction.TabDetails"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-icon,
     :name "setIcon",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-popup,
     :name "setPopup",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-popup,
     :name "getPopup",
     :callback? true,
     :params
     [{:name "details", :type "pageAction.TabDetails"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}],
   :events [{:id ::on-clicked, :name "onClicked", :params [{:name "tab", :type "tabs.Tab"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))