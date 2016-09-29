(ns chromex.app.bluetooth-low-energy (:require-macros [chromex.app.bluetooth-low-energy :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn connect* [config device-address properties]
  (gen-wrap :function ::connect config device-address properties))

(defn disconnect* [config device-address]
  (gen-wrap :function ::disconnect config device-address))

(defn get-service* [config service-id]
  (gen-wrap :function ::get-service config service-id))

(defn create-service* [config service]
  (gen-wrap :function ::create-service config service))

(defn get-services* [config device-address]
  (gen-wrap :function ::get-services config device-address))

(defn get-characteristic* [config characteristic-id]
  (gen-wrap :function ::get-characteristic config characteristic-id))

(defn create-characteristic* [config characteristic service-id]
  (gen-wrap :function ::create-characteristic config characteristic service-id))

(defn get-characteristics* [config service-id]
  (gen-wrap :function ::get-characteristics config service-id))

(defn get-included-services* [config service-id]
  (gen-wrap :function ::get-included-services config service-id))

(defn get-descriptor* [config descriptor-id]
  (gen-wrap :function ::get-descriptor config descriptor-id))

(defn create-descriptor* [config descriptor characteristic-id]
  (gen-wrap :function ::create-descriptor config descriptor characteristic-id))

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

(defn notify-characteristic-value-changed* [config characteristic-id notification]
  (gen-wrap :function ::notify-characteristic-value-changed config characteristic-id notification))

(defn read-descriptor-value* [config descriptor-id]
  (gen-wrap :function ::read-descriptor-value config descriptor-id))

(defn write-descriptor-value* [config descriptor-id value]
  (gen-wrap :function ::write-descriptor-value config descriptor-id value))

(defn register-service* [config service-id]
  (gen-wrap :function ::register-service config service-id))

(defn unregister-service* [config service-id]
  (gen-wrap :function ::unregister-service config service-id))

(defn remove-service* [config service-id]
  (gen-wrap :function ::remove-service config service-id))

(defn register-advertisement* [config advertisement]
  (gen-wrap :function ::register-advertisement config advertisement))

(defn unregister-advertisement* [config advertisement-id]
  (gen-wrap :function ::unregister-advertisement config advertisement-id))

(defn set-advertising-interval* [config min-interval max-interval]
  (gen-wrap :function ::set-advertising-interval config min-interval max-interval))

(defn send-request-response* [config response]
  (gen-wrap :function ::send-request-response config response))

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

(defn on-characteristic-read-request* [config channel & args]
  (gen-wrap :event ::on-characteristic-read-request config channel args))

(defn on-characteristic-write-request* [config channel & args]
  (gen-wrap :event ::on-characteristic-write-request config channel args))

(defn on-descriptor-read-request* [config channel & args]
  (gen-wrap :event ::on-descriptor-read-request config channel args))

(defn on-descriptor-write-request* [config channel & args]
  (gen-wrap :event ::on-descriptor-write-request config channel args))

