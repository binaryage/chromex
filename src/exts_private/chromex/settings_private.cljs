(ns chromex.settings-private (:require-macros [chromex.settings-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn set-pref* [config name value page-id]
  (gen-wrap :function ::set-pref config name value page-id))

(defn get-all-prefs* [config]
  (gen-wrap :function ::get-all-prefs config))

(defn get-pref* [config name]
  (gen-wrap :function ::get-pref config name))

(defn get-default-zoom-percent* [config]
  (gen-wrap :function ::get-default-zoom-percent config))

(defn set-default-zoom-percent* [config percent]
  (gen-wrap :function ::set-default-zoom-percent config percent))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-prefs-changed* [config channel]
  (gen-wrap :event ::on-prefs-changed config channel))

