(ns chromex.ext.page-action (:require-macros [chromex.ext.page-action :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn show* [config tab-id]
  (gen-wrap :function ::show config tab-id))

(defn hide* [config tab-id]
  (gen-wrap :function ::hide config tab-id))

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

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clicked* [config channel & args]
  (gen-wrap :event ::on-clicked config channel args))

