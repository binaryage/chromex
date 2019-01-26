(ns chromex.app.activity-log-private (:require-macros [chromex.app.activity-log-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-extension-activities* [config filter]
  (gen-wrap :function ::get-extension-activities config filter))

(defn delete-activities* [config activity-ids]
  (gen-wrap :function ::delete-activities config activity-ids))

(defn delete-activities-by-extension* [config extension-id]
  (gen-wrap :function ::delete-activities-by-extension config extension-id))

(defn delete-database* [config]
  (gen-wrap :function ::delete-database config))

(defn delete-urls* [config urls]
  (gen-wrap :function ::delete-urls config urls))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-extension-activity* [config channel & args]
  (gen-wrap :event ::on-extension-activity config channel args))

