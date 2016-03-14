(ns chromex.app.system.display
  "Use the system.display API to query display metadata.

     * available since Chrome 30
     * https://developer.chrome.com/apps/system.display"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Get the information of all attached display devices.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [display-info] where:

     |display-info| - https://developer.chrome.com/apps/system.display#property-callback-displayInfo.

   https://developer.chrome.com/apps/system.display#method-getInfo."
  ([] (gen-call :function ::get-info &form)))

(defmacro set-display-properties
  "Updates the properties for the display specified by |id|, according to the information provided in |info|. On failure,
   'runtime.lastError' will be set.

     |id| - The display's unique identifier.
     |info| - The information about display properties that should be changed.     A property will be changed only if a new
              value for it is specified in     |info|.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   https://developer.chrome.com/apps/system.display#method-setDisplayProperties."
  ([id info] (gen-call :function ::set-display-properties &form id info)))

(defmacro enable-unified-desktop
  "Enables/disables the unified desktop feature. Note that this simply enables the feature, but will not change the actual
   desktop mode. (That is, if the desktop is in mirror mode, it will stay in mirror mode)

     |enabled| - https://developer.chrome.com/apps/system.display#property-enableUnifiedDesktop-enabled.

   https://developer.chrome.com/apps/system.display#method-enableUnifiedDesktop."
  ([enabled] (gen-call :function ::enable-unified-desktop &form enabled)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-display-changed-events
  "Fired when anything changes to the display configuration.

   Events will be put on the |channel| with signature [::on-display-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/system.display#event-onDisplayChanged."
  ([channel & args] (apply gen-call :event ::on-display-changed &form channel args)))

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
  {:namespace "chrome.system.display",
   :since "30",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "display-info", :type "[array-of-objects]"}]}}]}
    {:id ::set-display-properties,
     :name "setDisplayProperties",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "info", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::enable-unified-desktop,
     :name "enableUnifiedDesktop",
     :since "46",
     :params [{:name "enabled", :type "boolean"}]}],
   :events [{:id ::on-display-changed, :name "onDisplayChanged"}]})

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