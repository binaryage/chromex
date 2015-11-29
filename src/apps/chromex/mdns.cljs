(ns chromex.mdns (:require-macros [chromex.mdns :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-service-instances-per-event* [config]
  (gen-wrap :property ::max-service-instances-per-event config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn force-discovery* [config]
  (gen-wrap :function ::force-discovery config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-service-list* [config channel & args]
  (gen-wrap :event ::on-service-list config channel args))

