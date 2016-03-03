(ns chromex.ext.processes (:require-macros [chromex.ext.processes :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-process-id-for-tab* [config tab-id]
  (gen-wrap :function ::get-process-id-for-tab config tab-id))

(defn terminate* [config process-id]
  (gen-wrap :function ::terminate config process-id))

(defn get-process-info* [config process-ids include-memory]
  (gen-wrap :function ::get-process-info config process-ids include-memory))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-updated* [config channel & args]
  (gen-wrap :event ::on-updated config channel args))
(defn on-updated-with-memory* [config channel & args]
  (gen-wrap :event ::on-updated-with-memory config channel args))
(defn on-created* [config channel & args]
  (gen-wrap :event ::on-created config channel args))
(defn on-unresponsive* [config channel & args]
  (gen-wrap :event ::on-unresponsive config channel args))
(defn on-exited* [config channel & args]
  (gen-wrap :event ::on-exited config channel args))

