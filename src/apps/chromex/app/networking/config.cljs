(ns chromex.app.networking.config (:require-macros [chromex.app.networking.config :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-network-filter* [config networks]
  (gen-wrap :function ::set-network-filter config networks))

(defn finish-authentication* [config guid result]
  (gen-wrap :function ::finish-authentication config guid result))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-captive-portal-detected* [config channel & args]
  (gen-wrap :event ::on-captive-portal-detected config channel args))

