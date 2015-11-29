(ns chromex.users-private (:require-macros [chromex.users-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

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

