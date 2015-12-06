(ns chromex.app.mdns (:require-macros [chromex.app.mdns :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-service-instances-per-event* [config]
  (gen-wrap :property ::max-service-instances-per-event config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn force-discovery* [config]
  (gen-wrap :function ::force-discovery config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-service-list* [config channel & args]
  (gen-wrap :event ::on-service-list config channel args))

