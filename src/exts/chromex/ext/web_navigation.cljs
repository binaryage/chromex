(ns chromex.ext.web-navigation (:require-macros [chromex.ext.web-navigation :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-frame* [config details]
  (gen-wrap :function ::get-frame config details))

(defn get-all-frames* [config details]
  (gen-wrap :function ::get-all-frames config details))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-before-navigate* [config channel & args]
  (gen-wrap :event ::on-before-navigate config channel args))
(defn on-committed* [config channel & args]
  (gen-wrap :event ::on-committed config channel args))
(defn on-dom-content-loaded* [config channel & args]
  (gen-wrap :event ::on-dom-content-loaded config channel args))
(defn on-completed* [config channel & args]
  (gen-wrap :event ::on-completed config channel args))
(defn on-error-occurred* [config channel & args]
  (gen-wrap :event ::on-error-occurred config channel args))
(defn on-created-navigation-target* [config channel & args]
  (gen-wrap :event ::on-created-navigation-target config channel args))
(defn on-reference-fragment-updated* [config channel & args]
  (gen-wrap :event ::on-reference-fragment-updated config channel args))
(defn on-tab-replaced* [config channel & args]
  (gen-wrap :event ::on-tab-replaced config channel args))
(defn on-history-state-updated* [config channel & args]
  (gen-wrap :event ::on-history-state-updated config channel args))

