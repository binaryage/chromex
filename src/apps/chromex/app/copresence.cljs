(ns chromex.app.copresence (:require-macros [chromex.app.copresence :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn execute* [config operations]
  (gen-wrap :function ::execute config operations))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-messages-received* [config channel & args]
  (gen-wrap :event ::on-messages-received config channel args))
(defn on-status-updated* [config channel & args]
  (gen-wrap :event ::on-status-updated config channel args))

