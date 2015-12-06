(ns chromex.ext.system-indicator (:require-macros [chromex.ext.system-indicator :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-icon* [config details]
  (gen-wrap :function ::set-icon config details))

(defn enable* [config]
  (gen-wrap :function ::enable config))

(defn disable* [config]
  (gen-wrap :function ::disable config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clicked* [config channel & args]
  (gen-wrap :event ::on-clicked config channel args))

