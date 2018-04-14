(ns chromex.app.easy-unlock-private (:require-macros [chromex.app.easy-unlock-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

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

