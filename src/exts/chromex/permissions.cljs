(ns chromex.permissions (:require-macros [chromex.permissions :refer [gen-wrap]])
    (:require [chromex-lib.core]))

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

(defn on-added* [config channel]
  (gen-wrap :event ::on-added config channel))

(defn on-removed* [config channel]
  (gen-wrap :event ::on-removed config channel))

