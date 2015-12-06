(ns chromex.app.media-galleries (:require-macros [chromex.app.media-galleries :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-media-file-systems* [config details]
  (gen-wrap :function ::get-media-file-systems config details))

(defn add-user-selected-folder* [config]
  (gen-wrap :function ::add-user-selected-folder config))

(defn drop-permission-for-media-file-system* [config gallery-id]
  (gen-wrap :function ::drop-permission-for-media-file-system config gallery-id))

(defn start-media-scan* [config]
  (gen-wrap :function ::start-media-scan config))

(defn cancel-media-scan* [config]
  (gen-wrap :function ::cancel-media-scan config))

(defn add-scan-results* [config]
  (gen-wrap :function ::add-scan-results config))

(defn get-media-file-system-metadata* [config media-file-system]
  (gen-wrap :function ::get-media-file-system-metadata config media-file-system))

(defn get-all-media-file-system-metadata* [config]
  (gen-wrap :function ::get-all-media-file-system-metadata config))

(defn get-metadata* [config media-file options]
  (gen-wrap :function ::get-metadata config media-file options))

(defn add-gallery-watch* [config gallery-id]
  (gen-wrap :function ::add-gallery-watch config gallery-id))

(defn remove-gallery-watch* [config gallery-id]
  (gen-wrap :function ::remove-gallery-watch config gallery-id))

(defn get-all-gallery-watch* [config]
  (gen-wrap :function ::get-all-gallery-watch config))

(defn remove-all-gallery-watch* [config]
  (gen-wrap :function ::remove-all-gallery-watch config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-gallery-changed* [config channel & args]
  (gen-wrap :event ::on-gallery-changed config channel args))
(defn on-scan-progress* [config channel & args]
  (gen-wrap :event ::on-scan-progress config channel args))

