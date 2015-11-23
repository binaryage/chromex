(ns chromex.notification-provider (:require-macros [chromex.notification-provider :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn notify-on-cleared* [config notifier-id notification-id]
  (gen-wrap :function ::notify-on-cleared config notifier-id notification-id))

(defn notify-on-clicked* [config notifier-id notification-id]
  (gen-wrap :function ::notify-on-clicked config notifier-id notification-id))

(defn notify-on-button-clicked* [config notifier-id notification-id button-index]
  (gen-wrap :function ::notify-on-button-clicked config notifier-id notification-id button-index))

(defn notify-on-permission-level-changed* [config notifier-id notifier-type level]
  (gen-wrap :function ::notify-on-permission-level-changed config notifier-id notifier-type level))

(defn notify-on-show-settings* [config notifier-id notifier-type]
  (gen-wrap :function ::notify-on-show-settings config notifier-id notifier-type))

(defn get-notifier* [config]
  (gen-wrap :function ::get-notifier config))

(defn get-all-notifiers* [config]
  (gen-wrap :function ::get-all-notifiers config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-created* [config channel]
  (gen-wrap :event ::on-created config channel))

(defn on-updated* [config channel]
  (gen-wrap :event ::on-updated config channel))

(defn on-cleared* [config channel]
  (gen-wrap :event ::on-cleared config channel))

