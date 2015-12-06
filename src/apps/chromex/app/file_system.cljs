(ns chromex.app.file-system (:require-macros [chromex.app.file-system :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-display-path* [config entry]
  (gen-wrap :function ::get-display-path config entry))

(defn get-writable-entry* [config entry]
  (gen-wrap :function ::get-writable-entry config entry))

(defn is-writable-entry* [config entry]
  (gen-wrap :function ::is-writable-entry config entry))

(defn choose-entry* [config options]
  (gen-wrap :function ::choose-entry config options))

(defn restore-entry* [config id]
  (gen-wrap :function ::restore-entry config id))

(defn is-restorable* [config id]
  (gen-wrap :function ::is-restorable config id))

(defn retain-entry* [config entry]
  (gen-wrap :function ::retain-entry config entry))

(defn request-file-system* [config options]
  (gen-wrap :function ::request-file-system config options))

(defn get-volume-list* [config]
  (gen-wrap :function ::get-volume-list config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-volume-list-changed* [config channel & args]
  (gen-wrap :event ::on-volume-list-changed config channel args))

