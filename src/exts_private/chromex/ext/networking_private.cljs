(ns chromex.ext.networking-private (:require-macros [chromex.ext.networking-private :refer [gen-wrap]])
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

(defn get-visible-networks* [config network-type]
  (gen-wrap :function ::get-visible-networks config network-type))

(defn get-enabled-network-types* [config]
  (gen-wrap :function ::get-enabled-network-types config))

(defn get-device-states* [config]
  (gen-wrap :function ::get-device-states config))

(defn enable-network-type* [config network-type]
  (gen-wrap :function ::enable-network-type config network-type))

(defn disable-network-type* [config network-type]
  (gen-wrap :function ::disable-network-type config network-type))

(defn request-network-scan* [config]
  (gen-wrap :function ::request-network-scan config))

(defn start-connect* [config network-guid]
  (gen-wrap :function ::start-connect config network-guid))

(defn start-disconnect* [config network-guid]
  (gen-wrap :function ::start-disconnect config network-guid))

(defn start-activate* [config network-guid carrier]
  (gen-wrap :function ::start-activate config network-guid carrier))

(defn verify-destination* [config properties]
  (gen-wrap :function ::verify-destination config properties))

(defn verify-and-encrypt-credentials* [config properties network-guid]
  (gen-wrap :function ::verify-and-encrypt-credentials config properties network-guid))

(defn verify-and-encrypt-data* [config properties data]
  (gen-wrap :function ::verify-and-encrypt-data config properties data))

(defn set-wifi-tdls-enabled-state* [config ip-or-mac-address enabled]
  (gen-wrap :function ::set-wifi-tdls-enabled-state config ip-or-mac-address enabled))

(defn get-wifi-tdls-status* [config ip-or-mac-address]
  (gen-wrap :function ::get-wifi-tdls-status config ip-or-mac-address))

(defn get-captive-portal-status* [config network-guid]
  (gen-wrap :function ::get-captive-portal-status config network-guid))

(defn unlock-cellular-sim* [config network-guid pin puk]
  (gen-wrap :function ::unlock-cellular-sim config network-guid pin puk))

(defn set-cellular-sim-state* [config network-guid sim-state]
  (gen-wrap :function ::set-cellular-sim-state config network-guid sim-state))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-networks-changed* [config channel & args]
  (gen-wrap :event ::on-networks-changed config channel args))
(defn on-network-list-changed* [config channel & args]
  (gen-wrap :event ::on-network-list-changed config channel args))
(defn on-device-state-list-changed* [config channel & args]
  (gen-wrap :event ::on-device-state-list-changed config channel args))
(defn on-portal-detection-completed* [config channel & args]
  (gen-wrap :event ::on-portal-detection-completed config channel args))

