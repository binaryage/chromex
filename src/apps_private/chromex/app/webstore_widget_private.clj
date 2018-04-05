(ns chromex.app.webstore-widget-private
  "webstoreWidgetPrivate API.
   This is a private API used by the Chrome Webstore widget app on Chrome OS.

     * available since Chrome 44"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-strings
  "Gets localized strings and initialization data.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-strings &form)))

(defmacro install-webstore-item
  "Requests to install a webstore item. |item_id| The id of the item to install. |silentInstallation| False to show
   installation prompt. True not to show.     Can be set to true only for a subset of installation requests.

     |item-id| - ?
     |silent-installation| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([item-id silent-installation] (gen-call :function ::install-webstore-item &form item-id silent-installation)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-show-widget-events
  "Event dispatched when a Chrome Webstore widget is requested to be shown.

   Events will be put on the |channel| with signature [::on-show-widget [options]] where:

     |options| - Options describing the set of apps that should be shown in the     widget.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-show-widget &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.webstore-widget-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webstoreWidgetPrivate",
   :since "44",
   :functions
   [{:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::install-webstore-item,
     :name "installWebstoreItem",
     :callback? true,
     :params
     [{:name "item-id", :type "string"}
      {:name "silent-installation", :type "boolean"}
      {:name "callback", :type :callback}]}],
   :events [{:id ::on-show-widget, :name "onShowWidget", :params [{:name "options", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))