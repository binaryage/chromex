(ns chromex.ext.browser-action (:require-macros [chromex.ext.browser-action :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-title* [config details]
  (gen-wrap :function ::set-title config details))

(defn get-title* [config details]
  (gen-wrap :function ::get-title config details))

(defn set-icon* [config details]
  (gen-wrap :function ::set-icon config details))

(defn set-popup* [config details]
  (gen-wrap :function ::set-popup config details))

(defn get-popup* [config details]
  (gen-wrap :function ::get-popup config details))

(defn set-badge-text* [config details]
  (gen-wrap :function ::set-badge-text config details))

(defn get-badge-text* [config details]
  (gen-wrap :function ::get-badge-text config details))

(defn set-badge-background-color* [config details]
  (gen-wrap :function ::set-badge-background-color config details))

(defn get-badge-background-color* [config details]
  (gen-wrap :function ::get-badge-background-color config details))

(defn enable* [config tab-id]
  (gen-wrap :function ::enable config tab-id))

(defn disable* [config tab-id]
  (gen-wrap :function ::disable config tab-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clicked* [config channel & args]
  (gen-wrap :event ::on-clicked config channel args))

