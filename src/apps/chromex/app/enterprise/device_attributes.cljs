(ns chromex.app.enterprise.device-attributes (:require-macros [chromex.app.enterprise.device-attributes :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-directory-device-id* [config]
  (gen-wrap :function ::get-directory-device-id config))

(defn get-device-serial-number* [config]
  (gen-wrap :function ::get-device-serial-number config))

(defn get-device-asset-id* [config]
  (gen-wrap :function ::get-device-asset-id config))

(defn get-device-annotated-location* [config]
  (gen-wrap :function ::get-device-annotated-location config))

(defn get-device-hostname* [config]
  (gen-wrap :function ::get-device-hostname config))

