(ns chromex.app.quick-unlock-private (:require-macros [chromex.app.quick-unlock-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-auth-token* [config account-password]
  (gen-wrap :function ::get-auth-token config account-password))

(defn set-lock-screen-enabled* [config token enabled]
  (gen-wrap :function ::set-lock-screen-enabled config token enabled))

(defn get-available-modes* [config]
  (gen-wrap :function ::get-available-modes config))

(defn get-active-modes* [config]
  (gen-wrap :function ::get-active-modes config))

(defn check-credential* [config mode credential]
  (gen-wrap :function ::check-credential config mode credential))

(defn get-credential-requirements* [config mode]
  (gen-wrap :function ::get-credential-requirements config mode))

(defn set-modes* [config token modes credentials]
  (gen-wrap :function ::set-modes config token modes credentials))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-active-modes-changed* [config channel & args]
  (gen-wrap :event ::on-active-modes-changed config channel args))

