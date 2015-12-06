(ns chromex.ext.alarms (:require-macros [chromex.ext.alarms :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config name alarm-info]
  (gen-wrap :function ::create config name alarm-info))

(defn get* [config name]
  (gen-wrap :function ::get config name))

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn clear* [config name]
  (gen-wrap :function ::clear config name))

(defn clear-all* [config]
  (gen-wrap :function ::clear-all config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-alarm* [config channel & args]
  (gen-wrap :event ::on-alarm config channel args))

