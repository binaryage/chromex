(ns chromex.app.media-galleries (:require-macros [chromex.app.media-galleries :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-media-file-systems* [config details]
  (gen-wrap :function ::get-media-file-systems config details))

(defn add-user-selected-folder* [config]
  (gen-wrap :function ::add-user-selected-folder config))

(defn get-media-file-system-metadata* [config media-file-system]
  (gen-wrap :function ::get-media-file-system-metadata config media-file-system))

(defn get-metadata* [config media-file options]
  (gen-wrap :function ::get-metadata config media-file options))

(defn add-gallery-watch* [config gallery-id]
  (gen-wrap :function ::add-gallery-watch config gallery-id))

(defn remove-gallery-watch* [config gallery-id]
  (gen-wrap :function ::remove-gallery-watch config gallery-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-gallery-changed* [config channel & args]
  (gen-wrap :event ::on-gallery-changed config channel args))

