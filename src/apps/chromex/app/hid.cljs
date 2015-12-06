(ns chromex.app.hid (:require-macros [chromex.app.hid :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-devices* [config options]
  (gen-wrap :function ::get-devices config options))

(defn get-user-selected-devices* [config options]
  (gen-wrap :function ::get-user-selected-devices config options))

(defn connect* [config device-id]
  (gen-wrap :function ::connect config device-id))

(defn disconnect* [config connection-id]
  (gen-wrap :function ::disconnect config connection-id))

(defn receive* [config connection-id]
  (gen-wrap :function ::receive config connection-id))

(defn send* [config connection-id report-id data]
  (gen-wrap :function ::send config connection-id report-id data))

(defn receive-feature-report* [config connection-id report-id]
  (gen-wrap :function ::receive-feature-report config connection-id report-id))

(defn send-feature-report* [config connection-id report-id data]
  (gen-wrap :function ::send-feature-report config connection-id report-id data))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-device-added* [config channel & args]
  (gen-wrap :event ::on-device-added config channel args))
(defn on-device-removed* [config channel & args]
  (gen-wrap :event ::on-device-removed config channel args))

