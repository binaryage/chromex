(ns chromex.app.easy-unlock-private (:require-macros [chromex.app.easy-unlock-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

(defn generate-ec-p256-key-pair* [config]
  (gen-wrap :function ::generate-ec-p256-key-pair config))

(defn perform-ecdh-key-agreement* [config private-key public-key]
  (gen-wrap :function ::perform-ecdh-key-agreement config private-key public-key))

(defn create-secure-message* [config payload key options]
  (gen-wrap :function ::create-secure-message config payload key options))

(defn unwrap-secure-message* [config secure-message key options]
  (gen-wrap :function ::unwrap-secure-message config secure-message key options))

(defn set-permit-access* [config permit-access]
  (gen-wrap :function ::set-permit-access config permit-access))

(defn get-permit-access* [config]
  (gen-wrap :function ::get-permit-access config))

(defn clear-permit-access* [config]
  (gen-wrap :function ::clear-permit-access config))

(defn set-remote-devices* [config devices]
  (gen-wrap :function ::set-remote-devices config devices))

(defn get-remote-devices* [config]
  (gen-wrap :function ::get-remote-devices config))

(defn get-user-info* [config]
  (gen-wrap :function ::get-user-info config))

(defn show-error-bubble* [config message link-range link-target anchor-rect]
  (gen-wrap :function ::show-error-bubble config message link-range link-target anchor-rect))

(defn hide-error-bubble* [config]
  (gen-wrap :function ::hide-error-bubble config))

(defn find-setup-connection* [config setup-service-uuid time-out]
  (gen-wrap :function ::find-setup-connection config setup-service-uuid time-out))

(defn setup-connection-disconnect* [config connection-id]
  (gen-wrap :function ::setup-connection-disconnect config connection-id))

(defn setup-connection-send* [config connection-id data]
  (gen-wrap :function ::setup-connection-send config connection-id data))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-connection-status-changed* [config channel & args]
  (gen-wrap :event ::on-connection-status-changed config channel args))

(defn on-data-received* [config channel & args]
  (gen-wrap :event ::on-data-received config channel args))

(defn on-send-completed* [config channel & args]
  (gen-wrap :event ::on-send-completed config channel args))

