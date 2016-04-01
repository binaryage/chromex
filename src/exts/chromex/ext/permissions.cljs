(ns chromex.ext.permissions (:require-macros [chromex.ext.permissions :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn contains* [config permissions]
  (gen-wrap :function ::contains config permissions))

(defn request* [config permissions]
  (gen-wrap :function ::request config permissions))

(defn remove* [config permissions]
  (gen-wrap :function ::remove config permissions))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-added* [config channel & args]
  (gen-wrap :event ::on-added config channel args))

(defn on-removed* [config channel & args]
  (gen-wrap :event ::on-removed config channel args))

