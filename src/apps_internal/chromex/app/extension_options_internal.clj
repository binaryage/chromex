(ns chromex.app.extension-options-internal
  "Internal API for the &lt;extensiontoptions&gt; tag
   
     * available since Chrome 50
     * https://developer.chrome.com/extensions/extensionOptionsInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-close-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/extensionOptionsInternal#event-onClose."
  ([channel & args] (apply gen-call :event ::on-close &form channel args)))

(defmacro tap-on-load-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/extensionOptionsInternal#event-onLoad."
  ([channel & args] (apply gen-call :event ::on-load &form channel args)))

(defmacro tap-on-preferred-size-changed-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/extensionOptionsInternal#event-onPreferredSizeChanged."
  ([channel & args] (apply gen-call :event ::on-preferred-size-changed &form channel args)))

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
  {:namespace "chrome.extensionOptionsInternal",
   :since "50",
   :events
   [{:id ::on-close, :name "onClose"}
    {:id ::on-load, :name "onLoad"}
    {:id ::on-preferred-size-changed, :name "onPreferredSizeChanged", :params [{:name "options", :type "object"}]}]})

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