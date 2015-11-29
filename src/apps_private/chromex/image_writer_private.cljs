(ns chromex.image-writer-private (:require-macros [chromex.image-writer-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn write-from-url* [config storage-unit-id image-url options]
  (gen-wrap :function ::write-from-url config storage-unit-id image-url options))

(defn write-from-file* [config storage-unit-id file-entry]
  (gen-wrap :function ::write-from-file config storage-unit-id file-entry))

(defn cancel-write* [config]
  (gen-wrap :function ::cancel-write config))

(defn destroy-partitions* [config storage-unit-id]
  (gen-wrap :function ::destroy-partitions config storage-unit-id))

(defn list-removable-storage-devices* [config]
  (gen-wrap :function ::list-removable-storage-devices config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-write-progress* [config channel & args]
  (gen-wrap :event ::on-write-progress config channel args))
(defn on-write-complete* [config channel & args]
  (gen-wrap :event ::on-write-complete config channel args))
(defn on-write-error* [config channel & args]
  (gen-wrap :event ::on-write-error config channel args))
(defn on-device-inserted* [config channel & args]
  (gen-wrap :event ::on-device-inserted config channel args))
(defn on-device-removed* [config channel & args]
  (gen-wrap :event ::on-device-removed config channel args))

