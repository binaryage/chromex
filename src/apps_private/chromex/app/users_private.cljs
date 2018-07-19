(ns chromex.app.users-private (:require-macros [chromex.app.users-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-whitelisted-users* [config]
  (gen-wrap :function ::get-whitelisted-users config))

(defn add-whitelisted-user* [config email]
  (gen-wrap :function ::add-whitelisted-user config email))

(defn remove-whitelisted-user* [config email]
  (gen-wrap :function ::remove-whitelisted-user config email))

(defn is-whitelist-managed* [config]
  (gen-wrap :function ::is-whitelist-managed config))

(defn get-current-user* [config]
  (gen-wrap :function ::get-current-user config))

(defn get-login-status* [config]
  (gen-wrap :function ::get-login-status config))

