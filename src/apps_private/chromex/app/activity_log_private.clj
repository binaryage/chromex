(ns chromex.app.activity-log-private
  "  * available since Chrome 29
     * https://developer.chrome.com/extensions/activityLogPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-extension-activities
  "Retrieves activity from the ActivityLog that matches the specified filter.
   
     |filter| - Fill out the fields that you want to search for in the database.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([filter #_callback] (gen-call :function ::get-extension-activities &form filter)))

(defmacro delete-activities
  "Deletes activities in the ActivityLog database specified in the array of activity IDs.
   
     |activityIds| - Erases only the activities which IDs are listed in the array."
  ([activity-ids] (gen-call :function ::delete-activities &form activity-ids)))

(defmacro delete-database
  "Deletes the entire ActivityLog database."
  ([] (gen-call :function ::delete-database &form)))

(defmacro delete-urls
  "Delete URLs in the ActivityLog database.
   
     |urls| - Erases only the URLs listed; if empty, erases all URLs."
  ([urls] (gen-call :function ::delete-urls &form urls))
  ([] `(delete-urls :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-extension-activity-events
  "Fired when a given extension performs another activity.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-extension-activity &form channel args)))

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
  {:namespace "chrome.activityLogPrivate",
   :since "29",
   :functions
   [{:id ::get-extension-activities,
     :name "getExtensionActivities",
     :callback? true,
     :params
     [{:name "filter", :type "activityLogPrivate.Filter"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "activityLogPrivate.ActivityResultSet"}]}}]}
    {:id ::delete-activities,
     :name "deleteActivities",
     :since "34",
     :params [{:name "activity-ids", :type "[array-of-strings]"}]}
    {:id ::delete-database, :name "deleteDatabase", :since "31"}
    {:id ::delete-urls,
     :name "deleteUrls",
     :since "31",
     :params [{:name "urls", :optional? true, :type "[array-of-strings]"}]}],
   :events
   [{:id ::on-extension-activity,
     :name "onExtensionActivity",
     :params [{:name "activity", :type "activityLogPrivate.ExtensionActivity"}]}]})

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