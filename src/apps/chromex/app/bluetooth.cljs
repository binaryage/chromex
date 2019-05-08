(ns chromex.app.bluetooth (:require-macros [chromex.app.bluetooth :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-adapter-state* [config]
  (gen-wrap :function ::get-adapter-state config))

(defn get-device* [config device-address]
  (gen-wrap :function ::get-device config device-address))

(defn get-devices* [config filter]
  (gen-wrap :function ::get-devices config filter))

(defn start-discovery* [config]
  (gen-wrap :function ::start-discovery config))

(defn stop-discovery* [config]
  (gen-wrap :function ::stop-discovery config))

(defn record-pairing* [config success transport]
  (gen-wrap :function ::record-pairing config success transport))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-adapter-state-changed* [config channel & args]
  (gen-wrap :event ::on-adapter-state-changed config channel args))

(defn on-device-added* [config channel & args]
  (gen-wrap :event ::on-device-added config channel args))

(defn on-device-changed* [config channel & args]
  (gen-wrap :event ::on-device-changed config channel args))

(defn on-device-removed* [config channel & args]
  (gen-wrap :event ::on-device-removed config channel args))

