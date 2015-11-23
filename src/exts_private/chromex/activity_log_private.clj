(ns chromex.activity-log-private
  "  * available since Chrome 31
     * https://developer.chrome.com/extensions/activityLogPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-extension-activities
  "Retrieves activity from the ActivityLog that matches the specified filter.
   
     |filter| - Fill out the fields that you want to search for in the database."
  ([filter #_callback] (gen-call :function ::get-extension-activities (meta &form) filter)))

(defmacro delete-activities
  "Deletes activities in the ActivityLog database specified in the array of activity IDs.
   
     |activityIds| - Erases only the activities which IDs are listed in the array."
  ([activity-ids] (gen-call :function ::delete-activities (meta &form) activity-ids)))

(defmacro delete-database
  "Deletes the entire ActivityLog database."
  ([] (gen-call :function ::delete-database (meta &form))))

(defmacro delete-urls
  "Delete URLs in the ActivityLog database.
   
     |urls| - Erases only the URLs listed; if empty, erases all URLs."
  ([urls] (gen-call :function ::delete-urls (meta &form) urls))
  ([] `(delete-urls :omit)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-extension-activity-events
  "Fired when a given extension performs another activity."
  ([channel] (gen-call :event ::on-extension-activity (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.activityLogPrivate",
   :since "31",
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
    {:id ::delete-database, :name "deleteDatabase"}
    {:id ::delete-urls,
     :name "deleteUrls",
     :params [{:name "urls", :optional? true, :type "[array-of-strings]"}]}],
   :events
   [{:id ::on-extension-activity,
     :name "onExtensionActivity",
     :params [{:name "activity", :type "activityLogPrivate.ExtensionActivity"}]}]})

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