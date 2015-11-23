(ns chromex.web-navigation (:require-macros [chromex.web-navigation :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-frame* [config details]
  (gen-wrap :function ::get-frame config details))

(defn get-all-frames* [config details]
  (gen-wrap :function ::get-all-frames config details))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-before-navigate*
  ([config channel] (gen-wrap :event ::on-before-navigate config channel))
  ([config channel filters] (gen-wrap :event ::on-before-navigate config channel filters)))

(defn on-committed*
  ([config channel] (gen-wrap :event ::on-committed config channel))
  ([config channel filters] (gen-wrap :event ::on-committed config channel filters)))

(defn on-dom-content-loaded*
  ([config channel] (gen-wrap :event ::on-dom-content-loaded config channel))
  ([config channel filters] (gen-wrap :event ::on-dom-content-loaded config channel filters)))

(defn on-completed*
  ([config channel] (gen-wrap :event ::on-completed config channel))
  ([config channel filters] (gen-wrap :event ::on-completed config channel filters)))

(defn on-error-occurred*
  ([config channel] (gen-wrap :event ::on-error-occurred config channel))
  ([config channel filters] (gen-wrap :event ::on-error-occurred config channel filters)))

(defn on-created-navigation-target*
  ([config channel] (gen-wrap :event ::on-created-navigation-target config channel))
  ([config channel filters] (gen-wrap :event ::on-created-navigation-target config channel filters)))

(defn on-reference-fragment-updated*
  ([config channel] (gen-wrap :event ::on-reference-fragment-updated config channel))
  ([config channel filters] (gen-wrap :event ::on-reference-fragment-updated config channel filters)))

(defn on-tab-replaced* [config channel]
  (gen-wrap :event ::on-tab-replaced config channel))

(defn on-history-state-updated*
  ([config channel] (gen-wrap :event ::on-history-state-updated config channel))
  ([config channel filters] (gen-wrap :event ::on-history-state-updated config channel filters)))

