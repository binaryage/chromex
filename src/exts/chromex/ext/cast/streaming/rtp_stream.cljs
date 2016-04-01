(ns chromex.ext.cast.streaming.rtp-stream (:require-macros [chromex.ext.cast.streaming.rtp-stream :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn destroy* [config stream-id]
  (gen-wrap :function ::destroy config stream-id))

(defn get-supported-params* [config stream-id]
  (gen-wrap :function ::get-supported-params config stream-id))

(defn start* [config stream-id params]
  (gen-wrap :function ::start config stream-id params))

(defn stop* [config stream-id]
  (gen-wrap :function ::stop config stream-id))

(defn toggle-logging* [config stream-id enable]
  (gen-wrap :function ::toggle-logging config stream-id enable))

(defn get-raw-events* [config stream-id extra-data]
  (gen-wrap :function ::get-raw-events config stream-id extra-data))

(defn get-stats* [config stream-id]
  (gen-wrap :function ::get-stats config stream-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-started* [config channel & args]
  (gen-wrap :event ::on-started config channel args))

(defn on-stopped* [config channel & args]
  (gen-wrap :event ::on-stopped config channel args))

(defn on-error* [config channel & args]
  (gen-wrap :event ::on-error config channel args))

