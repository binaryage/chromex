(ns chromex.ext.font-settings (:require-macros [chromex.ext.font-settings :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn clear-font* [config details]
  (gen-wrap :function ::clear-font config details))

(defn get-font* [config details]
  (gen-wrap :function ::get-font config details))

(defn set-font* [config details]
  (gen-wrap :function ::set-font config details))

(defn get-font-list* [config]
  (gen-wrap :function ::get-font-list config))

(defn clear-default-font-size* [config details]
  (gen-wrap :function ::clear-default-font-size config details))

(defn get-default-font-size* [config details]
  (gen-wrap :function ::get-default-font-size config details))

(defn set-default-font-size* [config details]
  (gen-wrap :function ::set-default-font-size config details))

(defn clear-default-fixed-font-size* [config details]
  (gen-wrap :function ::clear-default-fixed-font-size config details))

(defn get-default-fixed-font-size* [config details]
  (gen-wrap :function ::get-default-fixed-font-size config details))

(defn set-default-fixed-font-size* [config details]
  (gen-wrap :function ::set-default-fixed-font-size config details))

(defn clear-minimum-font-size* [config details]
  (gen-wrap :function ::clear-minimum-font-size config details))

(defn get-minimum-font-size* [config details]
  (gen-wrap :function ::get-minimum-font-size config details))

(defn set-minimum-font-size* [config details]
  (gen-wrap :function ::set-minimum-font-size config details))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-font-changed* [config channel & args]
  (gen-wrap :event ::on-font-changed config channel args))

(defn on-default-font-size-changed* [config channel & args]
  (gen-wrap :event ::on-default-font-size-changed config channel args))

(defn on-default-fixed-font-size-changed* [config channel & args]
  (gen-wrap :event ::on-default-fixed-font-size-changed config channel args))

(defn on-minimum-font-size-changed* [config channel & args]
  (gen-wrap :event ::on-minimum-font-size-changed config channel args))

