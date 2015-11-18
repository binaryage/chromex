(ns chromex.networking.config (:require-macros [chromex.networking.config :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn set-network-filter* [config networks]
  (gen-wrap :function ::set-network-filter config networks))

(defn finish-authentication* [config guid result]
  (gen-wrap :function ::finish-authentication config guid result))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-captive-portal-detected* [config channel]
  (gen-wrap :event ::on-captive-portal-detected config channel))

