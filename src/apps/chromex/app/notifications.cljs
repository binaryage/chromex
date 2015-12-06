(ns chromex.app.notifications (:require-macros [chromex.app.notifications :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config notification-id options]
  (gen-wrap :function ::create config notification-id options))

(defn update* [config notification-id options]
  (gen-wrap :function ::update config notification-id options))

(defn clear* [config notification-id]
  (gen-wrap :function ::clear config notification-id))

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn get-permission-level* [config]
  (gen-wrap :function ::get-permission-level config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-closed* [config channel & args]
  (gen-wrap :event ::on-closed config channel args))
(defn on-clicked* [config channel & args]
  (gen-wrap :event ::on-clicked config channel args))
(defn on-button-clicked* [config channel & args]
  (gen-wrap :event ::on-button-clicked config channel args))
(defn on-permission-level-changed* [config channel & args]
  (gen-wrap :event ::on-permission-level-changed config channel args))
(defn on-show-settings* [config channel & args]
  (gen-wrap :event ::on-show-settings config channel args))

