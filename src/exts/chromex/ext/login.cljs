(ns chromex.ext.login (:require-macros [chromex.ext.login :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn launch-managed-guest-session* [config password]
  (gen-wrap :function ::launch-managed-guest-session config password))

(defn exit-current-session* [config data-for-next-login-attempt]
  (gen-wrap :function ::exit-current-session config data-for-next-login-attempt))

(defn fetch-data-for-next-login-attempt* [config]
  (gen-wrap :function ::fetch-data-for-next-login-attempt config))

(defn lock-managed-guest-session* [config]
  (gen-wrap :function ::lock-managed-guest-session config))

(defn unlock-managed-guest-session* [config password]
  (gen-wrap :function ::unlock-managed-guest-session config password))

