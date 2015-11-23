(ns chromex.identity (:require-macros [chromex.identity :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-accounts* [config]
  (gen-wrap :function ::get-accounts config))

(defn get-auth-token* [config details]
  (gen-wrap :function ::get-auth-token config details))

(defn get-profile-user-info* [config]
  (gen-wrap :function ::get-profile-user-info config))

(defn remove-cached-auth-token* [config details]
  (gen-wrap :function ::remove-cached-auth-token config details))

(defn launch-web-auth-flow* [config details]
  (gen-wrap :function ::launch-web-auth-flow config details))

(defn get-redirect-url* [config path]
  (gen-wrap :function ::get-redirect-url config path))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-sign-in-changed* [config channel]
  (gen-wrap :event ::on-sign-in-changed config channel))

