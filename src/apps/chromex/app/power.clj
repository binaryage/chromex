(ns chromex.app.power
  "Use the chrome.power API to override the system's power
   management features.

     * available since Chrome 38
     * https://developer.chrome.com/apps/power"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro request-keep-awake
  "Requests that power management be temporarily disabled. |level| describes the degree to which power management should be
   disabled. If a request previously made by the same app is still active, it will be replaced by the new request.

     |level| - https://developer.chrome.com/apps/power#property-requestKeepAwake-level.

   https://developer.chrome.com/apps/power#method-requestKeepAwake."
  ([level] (gen-call :function ::request-keep-awake &form level)))

(defmacro release-keep-awake
  "Releases a request previously made via requestKeepAwake().

   https://developer.chrome.com/apps/power#method-releaseKeepAwake."
  ([] (gen-call :function ::release-keep-awake &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.power namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.power",
   :since "38",
   :functions
   [{:id ::request-keep-awake, :name "requestKeepAwake", :params [{:name "level", :type "power.Level"}]}
    {:id ::release-keep-awake, :name "releaseKeepAwake"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))