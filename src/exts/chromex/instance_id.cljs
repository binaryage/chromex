(ns chromex.instance-id (:require-macros [chromex.instance-id :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-id* [config]
  (gen-wrap :function ::get-id config))

(defn get-creation-time* [config]
  (gen-wrap :function ::get-creation-time config))

(defn get-token* [config get-token-params]
  (gen-wrap :function ::get-token config get-token-params))

(defn delete-token* [config delete-token-params]
  (gen-wrap :function ::delete-token config delete-token-params))

(defn delete-id* [config]
  (gen-wrap :function ::delete-id config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-token-refresh* [config channel & args]
  (gen-wrap :event ::on-token-refresh config channel args))

