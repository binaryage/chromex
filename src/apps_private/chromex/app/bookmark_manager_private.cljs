(ns chromex.app.bookmark-manager-private (:require-macros [chromex.app.bookmark-manager-private :refer [gen-wrap]])
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

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

(defn start-drag* [config id-list drag-node-index is-from-touch]
  (gen-wrap :function ::start-drag config id-list drag-node-index is-from-touch))

(defn drop* [config parent-id index]
  (gen-wrap :function ::drop config parent-id index))

(defn get-subtree* [config id folders-only]
  (gen-wrap :function ::get-subtree config id folders-only))

(defn can-edit* [config]
  (gen-wrap :function ::can-edit config))

(defn remove-trees* [config id-list]
  (gen-wrap :function ::remove-trees config id-list))

(defn record-launch* [config]
  (gen-wrap :function ::record-launch config))

(defn create-with-meta-info* [config bookmark meta-info]
  (gen-wrap :function ::create-with-meta-info config bookmark meta-info))

(defn get-meta-info* [config id key]
  (gen-wrap :function ::get-meta-info config id key))

(defn set-meta-info* [config id key value]
  (gen-wrap :function ::set-meta-info config id key value))

(defn update-meta-info* [config id meta-info-changes]
  (gen-wrap :function ::update-meta-info config id meta-info-changes))

(defn undo* [config]
  (gen-wrap :function ::undo config))

(defn redo* [config]
  (gen-wrap :function ::redo config))

(defn get-undo-info* [config]
  (gen-wrap :function ::get-undo-info config))

(defn get-redo-info* [config]
  (gen-wrap :function ::get-redo-info config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-drag-enter* [config channel & args]
  (gen-wrap :event ::on-drag-enter config channel args))

(defn on-drag-leave* [config channel & args]
  (gen-wrap :event ::on-drag-leave config channel args))

(defn on-drop* [config channel & args]
  (gen-wrap :event ::on-drop config channel args))

(defn on-meta-info-changed* [config channel & args]
  (gen-wrap :event ::on-meta-info-changed config channel args))

