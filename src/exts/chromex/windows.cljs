(ns chromex.windows (:require-macros [chromex.windows :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn window-id-none* [config]
  (gen-wrap :property ::window-id-none config))

(defn window-id-current* [config]
  (gen-wrap :property ::window-id-current config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config window-id get-info]
  (gen-wrap :function ::get config window-id get-info))

(defn get-current* [config get-info]
  (gen-wrap :function ::get-current config get-info))

(defn get-last-focused* [config get-info]
  (gen-wrap :function ::get-last-focused config get-info))

(defn get-all* [config get-info]
  (gen-wrap :function ::get-all config get-info))

(defn create* [config create-data]
  (gen-wrap :function ::create config create-data))

(defn update* [config window-id update-info]
  (gen-wrap :function ::update config window-id update-info))

(defn remove* [config window-id]
  (gen-wrap :function ::remove config window-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-created* [config channel & args]
  (gen-wrap :event ::on-created config channel args))
(defn on-removed* [config channel & args]
  (gen-wrap :event ::on-removed config channel args))
(defn on-focus-changed* [config channel & args]
  (gen-wrap :event ::on-focus-changed config channel args))

