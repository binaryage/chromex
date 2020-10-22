(ns chromex.ext.windows (:require-macros [chromex.ext.windows :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn window-id-none* [config]
  (gen-wrap :property ::window-id-none config))

(defn window-id-current* [config]
  (gen-wrap :property ::window-id-current config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config window-id query-options]
  (gen-wrap :function ::get config window-id query-options))

(defn get-current* [config query-options]
  (gen-wrap :function ::get-current config query-options))

(defn get-last-focused* [config query-options]
  (gen-wrap :function ::get-last-focused config query-options))

(defn get-all* [config query-options]
  (gen-wrap :function ::get-all config query-options))

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

(defn on-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-bounds-changed config channel args))

