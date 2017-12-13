(ns chromex.ext.metrics-private (:require-macros [chromex.ext.metrics-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-is-crash-reporting-enabled* [config]
  (gen-wrap :function ::get-is-crash-reporting-enabled config))

(defn get-field-trial* [config name]
  (gen-wrap :function ::get-field-trial config name))

(defn get-variation-params* [config name]
  (gen-wrap :function ::get-variation-params config name))

(defn record-user-action* [config name]
  (gen-wrap :function ::record-user-action config name))

(defn record-percentage* [config metric-name value]
  (gen-wrap :function ::record-percentage config metric-name value))

(defn record-count* [config metric-name value]
  (gen-wrap :function ::record-count config metric-name value))

(defn record-small-count* [config metric-name value]
  (gen-wrap :function ::record-small-count config metric-name value))

(defn record-medium-count* [config metric-name value]
  (gen-wrap :function ::record-medium-count config metric-name value))

(defn record-time* [config metric-name value]
  (gen-wrap :function ::record-time config metric-name value))

(defn record-medium-time* [config metric-name value]
  (gen-wrap :function ::record-medium-time config metric-name value))

(defn record-long-time* [config metric-name value]
  (gen-wrap :function ::record-long-time config metric-name value))

(defn record-sparse-hashable* [config metric-name value]
  (gen-wrap :function ::record-sparse-hashable config metric-name value))

(defn record-sparse-value* [config metric-name value]
  (gen-wrap :function ::record-sparse-value config metric-name value))

(defn record-value* [config metric value]
  (gen-wrap :function ::record-value config metric value))

(defn record-boolean* [config metric-name value]
  (gen-wrap :function ::record-boolean config metric-name value))

(defn record-enumeration-value* [config metric-name value enum-size]
  (gen-wrap :function ::record-enumeration-value config metric-name value enum-size))

