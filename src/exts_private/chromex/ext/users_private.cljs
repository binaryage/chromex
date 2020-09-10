(ns chromex.ext.users-private (:require-macros [chromex.ext.users-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-users* [config]
  (gen-wrap :function ::get-users config))

(defn is-user-in-list* [config email]
  (gen-wrap :function ::is-user-in-list config email))

(defn add-user* [config email]
  (gen-wrap :function ::add-user config email))

(defn remove-user* [config email]
  (gen-wrap :function ::remove-user config email))

(defn is-user-list-managed* [config]
  (gen-wrap :function ::is-user-list-managed config))

(defn get-current-user* [config]
  (gen-wrap :function ::get-current-user config))

(defn get-login-status* [config]
  (gen-wrap :function ::get-login-status config))

