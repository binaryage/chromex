(ns chromex.app.bluetooth-private (:require-macros [chromex.app.bluetooth-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-adapter-state* [config adapter-state]
  (gen-wrap :function ::set-adapter-state config adapter-state))

(defn set-pairing-response* [config options]
  (gen-wrap :function ::set-pairing-response config options))

(defn disconnect-all* [config device-address]
  (gen-wrap :function ::disconnect-all config device-address))

(defn forget-device* [config device-address]
  (gen-wrap :function ::forget-device config device-address))

(defn set-discovery-filter* [config discovery-filter]
  (gen-wrap :function ::set-discovery-filter config discovery-filter))

(defn connect* [config device-address]
  (gen-wrap :function ::connect config device-address))

(defn pair* [config device-address]
  (gen-wrap :function ::pair config device-address))

(defn record-pairing* [config success transport pairing-duration-ms]
  (gen-wrap :function ::record-pairing config success transport pairing-duration-ms))

(defn record-reconnection* [config success]
  (gen-wrap :function ::record-reconnection config success))

(defn record-device-selection* [config selection-duration-ms was-paired transport]
  (gen-wrap :function ::record-device-selection config selection-duration-ms was-paired transport))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-pairing* [config channel & args]
  (gen-wrap :event ::on-pairing config channel args))

(defn on-device-address-changed* [config channel & args]
  (gen-wrap :event ::on-device-address-changed config channel args))

