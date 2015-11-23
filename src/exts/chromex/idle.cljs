(ns chromex.idle (:require-macros [chromex.idle :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn query-state* [config detection-interval-in-seconds]
  (gen-wrap :function ::query-state config detection-interval-in-seconds))

(defn set-detection-interval* [config interval-in-seconds]
  (gen-wrap :function ::set-detection-interval config interval-in-seconds))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-state-changed* [config channel]
  (gen-wrap :event ::on-state-changed config channel))

