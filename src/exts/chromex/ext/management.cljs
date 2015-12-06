(ns chromex.ext.management (:require-macros [chromex.ext.management :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-all* [config]
  (gen-wrap :function ::get-all config))

(defn get* [config id]
  (gen-wrap :function ::get config id))

(defn get-self* [config]
  (gen-wrap :function ::get-self config))

(defn get-permission-warnings-by-id* [config id]
  (gen-wrap :function ::get-permission-warnings-by-id config id))

(defn get-permission-warnings-by-manifest* [config manifest-str]
  (gen-wrap :function ::get-permission-warnings-by-manifest config manifest-str))

(defn set-enabled* [config id enabled]
  (gen-wrap :function ::set-enabled config id enabled))

(defn uninstall* [config id options]
  (gen-wrap :function ::uninstall config id options))

(defn uninstall-self* [config options]
  (gen-wrap :function ::uninstall-self config options))

(defn launch-app* [config id]
  (gen-wrap :function ::launch-app config id))

(defn create-app-shortcut* [config id]
  (gen-wrap :function ::create-app-shortcut config id))

(defn set-launch-type* [config id launch-type]
  (gen-wrap :function ::set-launch-type config id launch-type))

(defn generate-app-for-link* [config url title]
  (gen-wrap :function ::generate-app-for-link config url title))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-installed* [config channel & args]
  (gen-wrap :event ::on-installed config channel args))
(defn on-uninstalled* [config channel & args]
  (gen-wrap :event ::on-uninstalled config channel args))
(defn on-enabled* [config channel & args]
  (gen-wrap :event ::on-enabled config channel args))
(defn on-disabled* [config channel & args]
  (gen-wrap :event ::on-disabled config channel args))

