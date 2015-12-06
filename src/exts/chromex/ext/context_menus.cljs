(ns chromex.ext.context-menus (:require-macros [chromex.ext.context-menus :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn action-menu-top-level-limit* [config]
  (gen-wrap :property ::action-menu-top-level-limit config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config create-properties]
  (gen-wrap :function ::create config create-properties))

(defn update* [config id update-properties]
  (gen-wrap :function ::update config id update-properties))

(defn remove* [config menu-item-id]
  (gen-wrap :function ::remove config menu-item-id))

(defn remove-all* [config]
  (gen-wrap :function ::remove-all config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clicked* [config channel & args]
  (gen-wrap :event ::on-clicked config channel args))

