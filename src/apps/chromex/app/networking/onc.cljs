(ns chromex.app.networking.onc (:require-macros [chromex.app.networking.onc :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-properties* [config network-guid]
  (gen-wrap :function ::get-properties config network-guid))

(defn get-managed-properties* [config network-guid]
  (gen-wrap :function ::get-managed-properties config network-guid))

(defn get-state* [config network-guid]
  (gen-wrap :function ::get-state config network-guid))

(defn set-properties* [config network-guid properties]
  (gen-wrap :function ::set-properties config network-guid properties))

(defn create-network* [config shared properties]
  (gen-wrap :function ::create-network config shared properties))

(defn forget-network* [config network-guid]
  (gen-wrap :function ::forget-network config network-guid))

(defn get-networks* [config filter]
  (gen-wrap :function ::get-networks config filter))

(defn get-device-states* [config]
  (gen-wrap :function ::get-device-states config))

(defn enable-network-type* [config network-type]
  (gen-wrap :function ::enable-network-type config network-type))

(defn disable-network-type* [config network-type]
  (gen-wrap :function ::disable-network-type config network-type))

(defn request-network-scan* [config network-type]
  (gen-wrap :function ::request-network-scan config network-type))

(defn start-connect* [config network-guid]
  (gen-wrap :function ::start-connect config network-guid))

(defn start-disconnect* [config network-guid]
  (gen-wrap :function ::start-disconnect config network-guid))

(defn get-captive-portal-status* [config network-guid]
  (gen-wrap :function ::get-captive-portal-status config network-guid))

(defn get-global-policy* [config]
  (gen-wrap :function ::get-global-policy config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-networks-changed* [config channel & args]
  (gen-wrap :event ::on-networks-changed config channel args))

(defn on-network-list-changed* [config channel & args]
  (gen-wrap :event ::on-network-list-changed config channel args))

(defn on-device-state-list-changed* [config channel & args]
  (gen-wrap :event ::on-device-state-list-changed config channel args))

(defn on-portal-detection-completed* [config channel & args]
  (gen-wrap :event ::on-portal-detection-completed config channel args))

