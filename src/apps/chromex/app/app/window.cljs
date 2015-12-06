(ns chromex.app.app.window (:require-macros [chromex.app.app.window :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config url options]
  (gen-wrap :function ::create config url options))

(defn current* [config]
  (gen-wrap :function ::current config))

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn get* [config id]
  (gen-wrap :function ::get config id))

(defn can-set-visible-on-all-workspaces* [config]
  (gen-wrap :function ::can-set-visible-on-all-workspaces config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-bounds-changed config channel args))
(defn on-closed* [config channel & args]
  (gen-wrap :event ::on-closed config channel args))
(defn on-fullscreened* [config channel & args]
  (gen-wrap :event ::on-fullscreened config channel args))
(defn on-maximized* [config channel & args]
  (gen-wrap :event ::on-maximized config channel args))
(defn on-minimized* [config channel & args]
  (gen-wrap :event ::on-minimized config channel args))
(defn on-restored* [config channel & args]
  (gen-wrap :event ::on-restored config channel args))

