(ns chromex.location (:require-macros [chromex.location :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn watch-location* [config name request-info]
  (gen-wrap :function ::watch-location config name request-info))

(defn clear-watch* [config name]
  (gen-wrap :function ::clear-watch config name))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-location-update* [config channel]
  (gen-wrap :event ::on-location-update config channel))

(defn on-location-error* [config channel]
  (gen-wrap :event ::on-location-error config channel))

