(ns chromex.system.storage (:require-macros [chromex.system.storage :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

(defn eject-device* [config id]
  (gen-wrap :function ::eject-device config id))

(defn get-available-capacity* [config id]
  (gen-wrap :function ::get-available-capacity config id))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-attached* [config channel]
  (gen-wrap :event ::on-attached config channel))

(defn on-detached* [config channel]
  (gen-wrap :event ::on-detached config channel))

