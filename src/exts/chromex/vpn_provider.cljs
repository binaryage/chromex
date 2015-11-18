(ns chromex.vpn-provider (:require-macros [chromex.vpn-provider :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn create-config* [config name]
  (gen-wrap :function ::create-config config name))

(defn destroy-config* [config id]
  (gen-wrap :function ::destroy-config config id))

(defn set-parameters* [config parameters]
  (gen-wrap :function ::set-parameters config parameters))

(defn send-packet* [config data]
  (gen-wrap :function ::send-packet config data))

(defn notify-connection-state-changed* [config state]
  (gen-wrap :function ::notify-connection-state-changed config state))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-platform-message* [config channel]
  (gen-wrap :event ::on-platform-message config channel))

(defn on-packet-received* [config channel]
  (gen-wrap :event ::on-packet-received config channel))

(defn on-config-removed* [config channel]
  (gen-wrap :event ::on-config-removed config channel))

(defn on-config-created* [config channel]
  (gen-wrap :event ::on-config-created config channel))

(defn on-ui-event* [config channel]
  (gen-wrap :event ::on-ui-event config channel))

