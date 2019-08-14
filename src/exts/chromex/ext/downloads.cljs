(ns chromex.ext.downloads (:require-macros [chromex.ext.downloads :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn download* [config options]
  (gen-wrap :function ::download config options))

(defn search* [config query]
  (gen-wrap :function ::search config query))

(defn pause* [config download-id]
  (gen-wrap :function ::pause config download-id))

(defn resume* [config download-id]
  (gen-wrap :function ::resume config download-id))

(defn cancel* [config download-id]
  (gen-wrap :function ::cancel config download-id))

(defn get-file-icon* [config download-id options]
  (gen-wrap :function ::get-file-icon config download-id options))

(defn open* [config download-id]
  (gen-wrap :function ::open config download-id))

(defn show* [config download-id]
  (gen-wrap :function ::show config download-id))

(defn show-default-folder* [config]
  (gen-wrap :function ::show-default-folder config))

(defn erase* [config query]
  (gen-wrap :function ::erase config query))

(defn remove-file* [config download-id]
  (gen-wrap :function ::remove-file config download-id))

(defn accept-danger* [config download-id]
  (gen-wrap :function ::accept-danger config download-id))

(defn set-shelf-enabled* [config enabled]
  (gen-wrap :function ::set-shelf-enabled config enabled))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-created* [config channel & args]
  (gen-wrap :event ::on-created config channel args))

(defn on-erased* [config channel & args]
  (gen-wrap :event ::on-erased config channel args))

(defn on-changed* [config channel & args]
  (gen-wrap :event ::on-changed config channel args))

(defn on-determining-filename* [config channel & args]
  (gen-wrap :event ::on-determining-filename config channel args))

