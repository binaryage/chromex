(ns chromex.ext.settings-private (:require-macros [chromex.ext.settings-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-pref* [config name value page-id]
  (gen-wrap :function ::set-pref config name value page-id))

(defn get-all-prefs* [config]
  (gen-wrap :function ::get-all-prefs config))

(defn get-pref* [config name]
  (gen-wrap :function ::get-pref config name))

(defn get-default-zoom* [config]
  (gen-wrap :function ::get-default-zoom config))

(defn set-default-zoom* [config zoom]
  (gen-wrap :function ::set-default-zoom config zoom))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-prefs-changed* [config channel & args]
  (gen-wrap :event ::on-prefs-changed config channel args))

