(ns chromex.system.display
  "Use the system.display API to query display metadata.
   
     * available since Chrome 30
     * https://developer.chrome.com/extensions/system.display"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Get the information of all attached display devices."
  [#_callback]
  (gen-call :function ::get-info (meta &form)))

(defmacro set-display-properties
  "Updates the properties for the display specified by |id|, according to the information provided in |info|. On
   failure, 'runtime.lastError' will be set.
   
     |id| - The display's unique identifier.
     |info| - The information about display properties that should be changed.     A property will be changed only
              if a new value for it is specified in     |info|.
     |callback| - Empty function called when the function finishes. To find out     whether the function succeeded,
                  'runtime.lastError' should be     queried."
  [id info #_callback]
  (gen-call :function ::set-display-properties (meta &form) id info))

(defmacro enable-unified-desktop
  "Enables/disables the unified desktop feature. Note that this simply enables the feature, but will not change the
   actual desktop mode. (That is, if the desktop is in mirror mode, it will stay in mirror mode)"
  [enabled]
  (gen-call :function ::enable-unified-desktop (meta &form) enabled))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-display-changed
  "Fired when anything changes to the display configuration."
  [channel]
  (gen-call :event ::on-display-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.display",
   :since "30",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "display-info", :type "[array-of-objects]"}]}}]}
    {:id ::set-display-properties,
     :name "setDisplayProperties",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "info", :type "object"} {:name "callback", :type :callback}]}
    {:id ::enable-unified-desktop,
     :name "enableUnifiedDesktop",
     :since "46",
     :params [{:name "enabled", :type "boolean"}]}],
   :events [{:id ::on-display-changed, :name "onDisplayChanged"}]})

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