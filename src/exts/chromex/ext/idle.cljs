(ns chromex.ext.idle (:require-macros [chromex.ext.idle :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn query-state* [config detection-interval-in-seconds]
  (gen-wrap :function ::query-state config detection-interval-in-seconds))

(defn set-detection-interval* [config interval-in-seconds]
  (gen-wrap :function ::set-detection-interval config interval-in-seconds))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-state-changed* [config channel & args]
  (gen-wrap :event ::on-state-changed config channel args))

