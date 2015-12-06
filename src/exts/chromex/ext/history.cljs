(ns chromex.ext.history (:require-macros [chromex.ext.history :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn search* [config query]
  (gen-wrap :function ::search config query))

(defn get-visits* [config details]
  (gen-wrap :function ::get-visits config details))

(defn add-url* [config details]
  (gen-wrap :function ::add-url config details))

(defn delete-url* [config details]
  (gen-wrap :function ::delete-url config details))

(defn delete-range* [config range]
  (gen-wrap :function ::delete-range config range))

(defn delete-all* [config]
  (gen-wrap :function ::delete-all config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-visited* [config channel & args]
  (gen-wrap :event ::on-visited config channel args))
(defn on-visit-removed* [config channel & args]
  (gen-wrap :event ::on-visit-removed config channel args))

