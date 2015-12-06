(ns chromex.app.audio (:require-macros [chromex.app.audio :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

(defn set-active-devices* [config ids]
  (gen-wrap :function ::set-active-devices config ids))

(defn set-properties* [config id properties]
  (gen-wrap :function ::set-properties config id properties))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-device-changed* [config channel & args]
  (gen-wrap :event ::on-device-changed config channel args))
(defn on-level-changed* [config channel & args]
  (gen-wrap :event ::on-level-changed config channel args))
(defn on-mute-changed* [config channel & args]
  (gen-wrap :event ::on-mute-changed config channel args))
(defn on-devices-changed* [config channel & args]
  (gen-wrap :event ::on-devices-changed config channel args))

