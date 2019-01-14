(ns chromex.app.idle (:require-macros [chromex.app.idle :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn query-state* [config detection-interval-in-seconds]
  (gen-wrap :function ::query-state config detection-interval-in-seconds))

(defn set-detection-interval* [config interval-in-seconds]
  (gen-wrap :function ::set-detection-interval config interval-in-seconds))

(defn get-auto-lock-delay* [config]
  (gen-wrap :function ::get-auto-lock-delay config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-state-changed* [config channel & args]
  (gen-wrap :event ::on-state-changed config channel args))

