(ns chromex.app.context-menus-internal
  "Use the chrome.contextMenus API to add items to Google Chrome's context menu. You can choose what types of objects your
   context menu additions apply to, such as images, hyperlinks, and pages.

     * available since Chrome 35"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clicked-events
  "Fired when a context menu item is clicked.

   Events will be put on the |channel| with signature [::on-clicked [info tab]] where:

     |info| - Information sent when a context menu item is clicked.
     |tab| - The details of the tab where the click took place. If the click did not take place in a tab, this parameter will
             be missing.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-clicked &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.context-menus-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.contextMenusInternal",
   :since "35",
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