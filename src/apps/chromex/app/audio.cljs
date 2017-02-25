(ns chromex.app.audio (:require-macros [chromex.app.audio :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-devices* [config filter]
  (gen-wrap :function ::get-devices config filter))

(defn set-active-devices* [config ids]
  (gen-wrap :function ::set-active-devices config ids))

(defn set-properties* [config id properties]
  (gen-wrap :function ::set-properties config id properties))

(defn get-mute* [config stream-type]
  (gen-wrap :function ::get-mute config stream-type))

(defn set-mute* [config stream-type is-muted]
  (gen-wrap :function ::set-mute config stream-type is-muted))

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-level-changed* [config channel & args]
  (gen-wrap :event ::on-level-changed config channel args))

(defn on-mute-changed* [config channel & args]
  (gen-wrap :event ::on-mute-changed config channel args))

(defn on-device-list-changed* [config channel & args]
  (gen-wrap :event ::on-device-list-changed config channel args))

(defn on-device-changed* [config channel & args]
  (gen-wrap :event ::on-device-changed config channel args))

