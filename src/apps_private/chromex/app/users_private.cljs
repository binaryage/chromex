(ns chromex.app.users-private (:require-macros [chromex.app.users-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-whitelisted-users* [config]
  (gen-wrap :function ::get-whitelisted-users config))

(defn add-whitelisted-user* [config email]
  (gen-wrap :function ::add-whitelisted-user config email))

(defn remove-whitelisted-user* [config email]
  (gen-wrap :function ::remove-whitelisted-user config email))

(defn is-current-user-owner* [config]
  (gen-wrap :function ::is-current-user-owner config))

(defn is-whitelist-managed* [config]
  (gen-wrap :function ::is-whitelist-managed config))

