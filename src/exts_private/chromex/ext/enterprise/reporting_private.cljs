(ns chromex.ext.enterprise.reporting-private (:require-macros [chromex.ext.enterprise.reporting-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-device-id* [config]
  (gen-wrap :function ::get-device-id config))

(defn get-persistent-secret* [config reset-secret]
  (gen-wrap :function ::get-persistent-secret config reset-secret))

(defn get-device-data* [config id]
  (gen-wrap :function ::get-device-data config id))

(defn set-device-data* [config id data]
  (gen-wrap :function ::set-device-data config id data))

(defn get-device-info* [config]
  (gen-wrap :function ::get-device-info config))

