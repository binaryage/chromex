(ns chromex.ext.bookmark-manager-private (:require-macros [chromex.ext.bookmark-manager-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn copy* [config id-list]
  (gen-wrap :function ::copy config id-list))

(defn cut* [config id-list]
  (gen-wrap :function ::cut config id-list))

(defn paste* [config parent-id selected-id-list]
  (gen-wrap :function ::paste config parent-id selected-id-list))

(defn can-paste* [config parent-id]
  (gen-wrap :function ::can-paste config parent-id))

(defn sort-children* [config parent-id]
  (gen-wrap :function ::sort-children config parent-id))

(defn start-drag* [config id-list drag-node-index is-from-touch x y]
  (gen-wrap :function ::start-drag config id-list drag-node-index is-from-touch x y))

(defn drop* [config parent-id index]
  (gen-wrap :function ::drop config parent-id index))

(defn get-subtree* [config id folders-only]
  (gen-wrap :function ::get-subtree config id folders-only))

(defn remove-trees* [config id-list]
  (gen-wrap :function ::remove-trees config id-list))

(defn undo* [config]
  (gen-wrap :function ::undo config))

(defn redo* [config]
  (gen-wrap :function ::redo config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-drag-enter* [config channel & args]
  (gen-wrap :event ::on-drag-enter config channel args))

(defn on-drag-leave* [config channel & args]
  (gen-wrap :event ::on-drag-leave config channel args))

(defn on-drop* [config channel & args]
  (gen-wrap :event ::on-drop config channel args))

