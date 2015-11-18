(ns chromex.bookmarks (:require-macros [chromex.bookmarks :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -----------------------------------------------------------------------------------------------------

(defn max-write-operations-per-hour* [config]
  (gen-wrap :property ::max-write-operations-per-hour config))

(defn max-sustained-write-operations-per-minute* [config]
  (gen-wrap :property ::max-sustained-write-operations-per-minute config))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get* [config id-or-id-list]
  (gen-wrap :function ::get config id-or-id-list))

(defn get-children* [config id]
  (gen-wrap :function ::get-children config id))

(defn get-recent* [config number-of-items]
  (gen-wrap :function ::get-recent config number-of-items))

(defn get-tree* [config]
  (gen-wrap :function ::get-tree config))

(defn get-sub-tree* [config id]
  (gen-wrap :function ::get-sub-tree config id))

(defn search* [config query]
  (gen-wrap :function ::search config query))

(defn create* [config bookmark]
  (gen-wrap :function ::create config bookmark))

(defn move* [config id destination]
  (gen-wrap :function ::move config id destination))

(defn update* [config id changes]
  (gen-wrap :function ::update config id changes))

(defn remove* [config id]
  (gen-wrap :function ::remove config id))

(defn remove-tree* [config id]
  (gen-wrap :function ::remove-tree config id))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-created* [config channel]
  (gen-wrap :event ::on-created config channel))

(defn on-removed* [config channel]
  (gen-wrap :event ::on-removed config channel))

(defn on-changed* [config channel]
  (gen-wrap :event ::on-changed config channel))

(defn on-moved* [config channel]
  (gen-wrap :event ::on-moved config channel))

(defn on-children-reordered* [config channel]
  (gen-wrap :event ::on-children-reordered config channel))

(defn on-import-began* [config channel]
  (gen-wrap :event ::on-import-began config channel))

(defn on-import-ended* [config channel]
  (gen-wrap :event ::on-import-ended config channel))

