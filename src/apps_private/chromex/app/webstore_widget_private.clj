(ns chromex.app.webstore-widget-private
  "webstoreWidgetPrivate API.
   This is a private API used by the Chrome Webstore widget app on Chrome OS.
   
     * available since Chrome 44
     * https://developer.chrome.com/extensions/webstoreWidgetPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-strings
  "Gets localized strings and initialization data.
   
     |callback| - |result| Object containing the string assets.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-strings &form)))

(defmacro install-webstore-item
  "Requests to install a webstore item. |item_id| The id of the item to install. |silentInstallation| False to show
   installation prompt. True not to show.     Can be set to true only for a subset of installation requests.
   
     |callback| - Callback that does not take arguments.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([item-id silent-installation #_callback] (gen-call :function ::install-webstore-item &form item-id silent-installation)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-show-widget-events
  "Event dispatched when a Chrome Webstore widget is requested to be shown.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-show-widget &form channel args)))

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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))