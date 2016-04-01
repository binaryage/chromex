(ns chromex.app.usb (:require-macros [chromex.app.usb :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-devices* [config options]
  (gen-wrap :function ::get-devices config options))

(defn get-user-selected-devices* [config options]
  (gen-wrap :function ::get-user-selected-devices config options))

(defn get-configurations* [config device]
  (gen-wrap :function ::get-configurations config device))

(defn request-access* [config device interface-id]
  (gen-wrap :function ::request-access config device interface-id))

(defn open-device* [config device]
  (gen-wrap :function ::open-device config device))

(defn find-devices* [config options]
  (gen-wrap :function ::find-devices config options))

(defn close-device* [config handle]
  (gen-wrap :function ::close-device config handle))

(defn set-configuration* [config handle configuration-value]
  (gen-wrap :function ::set-configuration config handle configuration-value))

(defn get-configuration* [config handle]
  (gen-wrap :function ::get-configuration config handle))

(defn list-interfaces* [config handle]
  (gen-wrap :function ::list-interfaces config handle))

(defn claim-interface* [config handle interface-number]
  (gen-wrap :function ::claim-interface config handle interface-number))

(defn release-interface* [config handle interface-number]
  (gen-wrap :function ::release-interface config handle interface-number))

(defn set-interface-alternate-setting* [config handle interface-number alternate-setting]
  (gen-wrap :function ::set-interface-alternate-setting config handle interface-number alternate-setting))

(defn control-transfer* [config handle transfer-info]
  (gen-wrap :function ::control-transfer config handle transfer-info))

(defn bulk-transfer* [config handle transfer-info]
  (gen-wrap :function ::bulk-transfer config handle transfer-info))

(defn interrupt-transfer* [config handle transfer-info]
  (gen-wrap :function ::interrupt-transfer config handle transfer-info))

(defn isochronous-transfer* [config handle transfer-info]
  (gen-wrap :function ::isochronous-transfer config handle transfer-info))

(defn reset-device* [config handle]
  (gen-wrap :function ::reset-device config handle))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-device-added* [config channel & args]
  (gen-wrap :event ::on-device-added config channel args))

(defn on-device-removed* [config channel & args]
  (gen-wrap :event ::on-device-removed config channel args))

