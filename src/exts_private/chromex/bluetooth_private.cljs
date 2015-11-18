(ns chromex.bluetooth-private (:require-macros [chromex.bluetooth-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

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

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-pairing* [config channel]
  (gen-wrap :event ::on-pairing config channel))

