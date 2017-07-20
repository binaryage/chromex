(ns chromex.app.lock-screen.data (:require-macros [chromex.app.lock-screen.data :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config]
  (gen-wrap :function ::create config))

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn get-content* [config id]
  (gen-wrap :function ::get-content config id))

(defn set-content* [config id data]
  (gen-wrap :function ::set-content config id data))

(defn delete* [config id]
  (gen-wrap :function ::delete config id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-data-items-available* [config channel & args]
  (gen-wrap :event ::on-data-items-available config channel args))

