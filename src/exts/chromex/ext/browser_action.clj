(ns chromex.ext.browser-action
  "Use browser actions to put icons in the main Google Chrome toolbar, to the right of the address bar. In addition to its
   icon, a browser action can have a tooltip, a badge, and a popup.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/browserAction"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-title
  "Sets the title of the browser action. This title appears in the tooltip.

     |details| - https://developer.chrome.com/extensions/browserAction#property-setTitle-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-setTitle."
  ([details] (gen-call :function ::set-title &form details)))

(defmacro get-title
  "Gets the title of the browser action.

     |details| - https://developer.chrome.com/extensions/browserAction#property-getTitle-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/browserAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-getTitle."
  ([details] (gen-call :function ::get-title &form details)))

(defmacro set-icon
  "Sets the icon for the browser action. The icon can be specified as the path to an image file, as the pixel data from a
   canvas element, or as a dictionary of one of those. Either the path or the imageData property must be specified.

     |details| - https://developer.chrome.com/extensions/browserAction#property-setIcon-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-setIcon."
  ([details] (gen-call :function ::set-icon &form details)))

(defmacro set-popup
  "Sets the HTML document to be opened as a popup when the user clicks the browser action icon.

     |details| - https://developer.chrome.com/extensions/browserAction#property-setPopup-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-setPopup."
  ([details] (gen-call :function ::set-popup &form details)))

(defmacro get-popup
  "Gets the HTML document that is set as the popup for this browser action.

     |details| - https://developer.chrome.com/extensions/browserAction#property-getPopup-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/browserAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-getPopup."
  ([details] (gen-call :function ::get-popup &form details)))

(defmacro set-badge-text
  "Sets the badge text for the browser action. The badge is displayed on top of the icon.

     |details| - https://developer.chrome.com/extensions/browserAction#property-setBadgeText-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-setBadgeText."
  ([details] (gen-call :function ::set-badge-text &form details)))

(defmacro get-badge-text
  "Gets the badge text of the browser action. If no tab is specified, the non-tab-specific badge text is returned.

     |details| - https://developer.chrome.com/extensions/browserAction#property-getBadgeText-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/browserAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-getBadgeText."
  ([details] (gen-call :function ::get-badge-text &form details)))

(defmacro set-badge-background-color
  "Sets the background color for the badge.

     |details| - https://developer.chrome.com/extensions/browserAction#property-setBadgeBackgroundColor-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-setBadgeBackgroundColor."
  ([details] (gen-call :function ::set-badge-background-color &form details)))

(defmacro get-badge-background-color
  "Gets the background color of the browser action.

     |details| - https://developer.chrome.com/extensions/browserAction#property-getBadgeBackgroundColor-details.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/browserAction#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-getBadgeBackgroundColor."
  ([details] (gen-call :function ::get-badge-background-color &form details)))

(defmacro enable
  "Enables the browser action for a tab. Defaults to enabled.

     |tab-id| - The ID of the tab for which to modify the browser action.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-enable."
  ([tab-id] (gen-call :function ::enable &form tab-id))
  ([] `(enable :omit)))

(defmacro disable
  "Disables the browser action for a tab.

     |tab-id| - The ID of the tab for which to modify the browser action.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/browserAction#method-disable."
  ([tab-id] (gen-call :function ::disable &form tab-id))
  ([] `(disable :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clicked-events
  "Fired when a browser action icon is clicked. Does not fire if the browser action has a popup.

   Events will be put on the |channel| with signature [::on-clicked [tab]] where:

     |tab| - https://developer.chrome.com/extensions/browserAction#property-onClicked-tab.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/browserAction#event-onClicked."
  ([channel & args] (apply gen-call :event ::on-clicked &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.browser-action namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.browserAction",
   :since "38",
   :functions
   [{:id ::set-title,
     :name "setTitle",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-title,
     :name "getTitle",
     :callback? true,
     :params
     [{:name "details", :type "browserAction.TabDetails"}
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
     [{:name "details", :type "browserAction.TabDetails"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-badge-text,
     :name "setBadgeText",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-badge-text,
     :name "getBadgeText",
     :callback? true,
     :params
     [{:name "details", :type "browserAction.TabDetails"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::set-badge-background-color,
     :name "setBadgeBackgroundColor",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-badge-background-color,
     :name "getBadgeBackgroundColor",
     :callback? true,
     :params
     [{:name "details", :type "browserAction.TabDetails"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "browserAction.ColorArray"}]}}]}
    {:id ::enable,
     :name "enable",
     :callback? true,
     :params [{:name "tab-id", :optional? true, :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::disable,
     :name "disable",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"} {:name "callback", :optional? true, :type :callback}]}],
   :events [{:id ::on-clicked, :name "onClicked", :params [{:name "tab", :type "tabs.Tab"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))