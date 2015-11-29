(ns chromex.serial (:require-macros [chromex.serial :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-devices* [config]
  (gen-wrap :function ::get-devices config))

(defn connect* [config path options]
  (gen-wrap :function ::connect config path options))

(defn update* [config connection-id options]
  (gen-wrap :function ::update config connection-id options))

(defn disconnect* [config connection-id]
  (gen-wrap :function ::disconnect config connection-id))

(defn set-paused* [config connection-id paused]
  (gen-wrap :function ::set-paused config connection-id paused))

(defn get-info* [config connection-id]
  (gen-wrap :function ::get-info config connection-id))

(defn get-connections* [config]
  (gen-wrap :function ::get-connections config))

(defn send* [config connection-id data]
  (gen-wrap :function ::send config connection-id data))

(defn flush* [config connection-id]
  (gen-wrap :function ::flush config connection-id))

(defn get-control-signals* [config connection-id]
  (gen-wrap :function ::get-control-signals config connection-id))

(defn set-control-signals* [config connection-id signals]
  (gen-wrap :function ::set-control-signals config connection-id signals))

(defn set-break* [config connection-id]
  (gen-wrap :function ::set-break config connection-id))

(defn clear-break* [config connection-id]
  (gen-wrap :function ::clear-break config connection-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-receive* [config channel & args]
  (gen-wrap :event ::on-receive config channel args))
(defn on-receive-error* [config channel & args]
  (gen-wrap :event ::on-receive-error config channel args))

