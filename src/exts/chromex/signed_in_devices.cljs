(ns chromex.signed-in-devices (:require-macros [chromex.signed-in-devices :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config is-local]
  (gen-wrap :function ::get config is-local))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-device-info-change* [config channel]
  (gen-wrap :event ::on-device-info-change config channel))

