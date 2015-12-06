(ns chromex.ext.signed-in-devices (:require-macros [chromex.ext.signed-in-devices :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config is-local]
  (gen-wrap :function ::get config is-local))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-device-info-change* [config channel & args]
  (gen-wrap :event ::on-device-info-change config channel args))

