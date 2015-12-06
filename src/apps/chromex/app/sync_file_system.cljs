(ns chromex.app.sync-file-system (:require-macros [chromex.app.sync-file-system :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn request-file-system* [config]
  (gen-wrap :function ::request-file-system config))

(defn set-conflict-resolution-policy* [config policy]
  (gen-wrap :function ::set-conflict-resolution-policy config policy))

(defn get-conflict-resolution-policy* [config]
  (gen-wrap :function ::get-conflict-resolution-policy config))

(defn get-usage-and-quota* [config file-system]
  (gen-wrap :function ::get-usage-and-quota config file-system))

(defn get-file-status* [config file-entry]
  (gen-wrap :function ::get-file-status config file-entry))

(defn get-file-statuses* [config file-entries]
  (gen-wrap :function ::get-file-statuses config file-entries))

(defn get-service-status* [config]
  (gen-wrap :function ::get-service-status config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-service-status-changed* [config channel & args]
  (gen-wrap :event ::on-service-status-changed config channel args))
(defn on-file-status-changed* [config channel & args]
  (gen-wrap :event ::on-file-status-changed config channel args))

