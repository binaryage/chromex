(ns chromex.app.system.power-source (:require-macros [chromex.app.system.power-source :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-power-source-info* [config]
  (gen-wrap :function ::get-power-source-info config))

(defn request-status-update* [config]
  (gen-wrap :function ::request-status-update config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-power-changed* [config channel & args]
  (gen-wrap :event ::on-power-changed config channel args))

