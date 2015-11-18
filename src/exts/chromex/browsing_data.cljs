(ns chromex.browsing-data (:require-macros [chromex.browsing-data :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn settings* [config]
  (gen-wrap :function ::settings config))

(defn remove* [config options data-to-remove]
  (gen-wrap :function ::remove config options data-to-remove))

(defn remove-appcache* [config options]
  (gen-wrap :function ::remove-appcache config options))

(defn remove-cache* [config options]
  (gen-wrap :function ::remove-cache config options))

(defn remove-cookies* [config options]
  (gen-wrap :function ::remove-cookies config options))

(defn remove-downloads* [config options]
  (gen-wrap :function ::remove-downloads config options))

(defn remove-file-systems* [config options]
  (gen-wrap :function ::remove-file-systems config options))

(defn remove-form-data* [config options]
  (gen-wrap :function ::remove-form-data config options))

(defn remove-history* [config options]
  (gen-wrap :function ::remove-history config options))

(defn remove-indexed-db* [config options]
  (gen-wrap :function ::remove-indexed-db config options))

(defn remove-local-storage* [config options]
  (gen-wrap :function ::remove-local-storage config options))

(defn remove-plugin-data* [config options]
  (gen-wrap :function ::remove-plugin-data config options))

(defn remove-passwords* [config options]
  (gen-wrap :function ::remove-passwords config options))

(defn remove-web-sql* [config options]
  (gen-wrap :function ::remove-web-sql config options))

