(ns chromex.location
  "Use the chrome.location API to retrieve the geographic location
   of the host machine. This API is a version of the HTML Geolocation API
   that is compatible with event pages.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/location"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro watch-location
  "Starts a location watching request. If there is another location watching request with the same name (or no name if
   none is specified), it will be cancelled and replaced by this request.
   
     |name| - Optional name to identify this request. Defaults to the empty string.
     |requestInfo| - Optional parameters for this request."
  [name request-info]
  (gen-call :function ::watch-location (meta &form) name request-info))

(defmacro clear-watch
  "Ends a location watching request.
   
     |name| - Optional name to identify the request to remove. Defaults to the empty string."
  [name]
  (gen-call :function ::clear-watch (meta &form) name))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-location-update
  "Fired when a location change is detected."
  [channel]
  (gen-call :event ::on-location-update (meta &form) channel))

(defmacro tap-on-location-error
  "Fired when detecting location in not possible."
  [channel]
  (gen-call :event ::on-location-error (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.location",
   :since "48",
   :functions
   [{:id ::watch-location,
     :name "watchLocation",
     :params [{:name "name", :type "string"} {:name "request-info", :type "object"}]}
    {:id ::clear-watch, :name "clearWatch", :params [{:name "name", :type "string"}]}],
   :events
   [{:id ::on-location-update, :name "onLocationUpdate", :params [{:name "location", :type "object"}]}
    {:id ::on-location-error, :name "onLocationError", :params [{:name "error", :type "string"}]}]})

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