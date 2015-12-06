(ns chromex.app.bluetooth-low-energy (:require-macros [chromex.app.bluetooth-low-energy :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn connect* [config device-address properties]
  (gen-wrap :function ::connect config device-address properties))

(defn disconnect* [config device-address]
  (gen-wrap :function ::disconnect config device-address))

(defn get-service* [config service-id]
  (gen-wrap :function ::get-service config service-id))

(defn get-services* [config device-address]
  (gen-wrap :function ::get-services config device-address))

(defn get-characteristic* [config characteristic-id]
  (gen-wrap :function ::get-characteristic config characteristic-id))

(defn get-characteristics* [config service-id]
  (gen-wrap :function ::get-characteristics config service-id))

(defn get-included-services* [config service-id]
  (gen-wrap :function ::get-included-services config service-id))

(defn get-descriptor* [config descriptor-id]
  (gen-wrap :function ::get-descriptor config descriptor-id))

(defn get-descriptors* [config characteristic-id]
  (gen-wrap :function ::get-descriptors config characteristic-id))

(defn read-characteristic-value* [config characteristic-id]
  (gen-wrap :function ::read-characteristic-value config characteristic-id))

(defn write-characteristic-value* [config characteristic-id value]
  (gen-wrap :function ::write-characteristic-value config characteristic-id value))

(defn start-characteristic-notifications* [config characteristic-id properties]
  (gen-wrap :function ::start-characteristic-notifications config characteristic-id properties))

(defn stop-characteristic-notifications* [config characteristic-id]
  (gen-wrap :function ::stop-characteristic-notifications config characteristic-id))

(defn read-descriptor-value* [config descriptor-id]
  (gen-wrap :function ::read-descriptor-value config descriptor-id))

(defn write-descriptor-value* [config descriptor-id value]
  (gen-wrap :function ::write-descriptor-value config descriptor-id value))

(defn register-advertisement* [config advertisement]
  (gen-wrap :function ::register-advertisement config advertisement))

(defn unregister-advertisement* [config advertisement-id]
  (gen-wrap :function ::unregister-advertisement config advertisement-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-service-added* [config channel & args]
  (gen-wrap :event ::on-service-added config channel args))
(defn on-service-changed* [config channel & args]
  (gen-wrap :event ::on-service-changed config channel args))
(defn on-service-removed* [config channel & args]
  (gen-wrap :event ::on-service-removed config channel args))
(defn on-characteristic-value-changed* [config channel & args]
  (gen-wrap :event ::on-characteristic-value-changed config channel args))
(defn on-descriptor-value-changed* [config channel & args]
  (gen-wrap :event ::on-descriptor-value-changed config channel args))

