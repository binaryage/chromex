(ns chromex.ext.system.storage (:require-macros [chromex.ext.system.storage :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

(defn eject-device* [config id]
  (gen-wrap :function ::eject-device config id))

(defn get-available-capacity* [config id]
  (gen-wrap :function ::get-available-capacity config id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-attached* [config channel & args]
  (gen-wrap :event ::on-attached config channel args))
(defn on-detached* [config channel & args]
  (gen-wrap :event ::on-detached config channel args))

