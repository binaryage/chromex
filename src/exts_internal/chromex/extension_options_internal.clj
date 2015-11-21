(ns chromex.extension-options-internal
  "Internal API for the &lt;extensiontoptions&gt; tag
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/extensionOptionsInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-close-events [channel]
  (gen-call :event ::on-close (meta &form) channel))

(defmacro tap-on-load-events [channel]
  (gen-call :event ::on-load (meta &form) channel))

(defmacro tap-on-preferred-size-changed-events [channel]
  (gen-call :event ::on-preferred-size-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extensionOptionsInternal",
   :since "47",
   :events
   [{:id ::on-close, :name "onClose"}
    {:id ::on-load, :name "onLoad"}
    {:id ::on-preferred-size-changed,
     :name "onPreferredSizeChanged",
     :params [{:name "options", :type "object"}]}]})

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