(ns chromex.system-indicator
  "Manages an app's system indicator icon, an image displayed in the system's
   menubar, system tray, or other visible area provided by the OS.
   This is modelled after the other extension action APIs, such as
   chrome.browserAction and chrome.pageAction.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/systemIndicator"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-icon
  "Set the image to be used as an indicator icon, using a set of ImageData objects. These objects should have multiple
   resolutions so that an appropriate size can be selected for the given icon size and DPI scaling settings. Only
   square ImageData objects are accepted."
  [details #_callback]
  (gen-call :function ::set-icon (meta &form) details))

(defmacro enable
  "Show the icon in the status tray."
  []
  (gen-call :function ::enable (meta &form)))

(defmacro disable
  "Hide the icon from the status tray."
  []
  (gen-call :function ::disable (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-clicked
  "Fired only when a click on the icon does not result in a menu being shown."
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
  {:namespace "chrome.systemIndicator",
   :since "48",
   :functions
   [{:id ::set-icon,
     :name "setIcon",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::enable, :name "enable"}
    {:id ::disable, :name "disable"}],
   :events [{:id ::on-clicked, :name "onClicked"}]})

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