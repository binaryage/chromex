(ns chromex.ext.search-engines-private (:require-macros [chromex.ext.search-engines-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-search-engines* [config]
  (gen-wrap :function ::get-search-engines config))

(defn set-selected-search-engine* [config guid]
  (gen-wrap :function ::set-selected-search-engine config guid))

(defn add-other-search-engine* [config name keyword url]
  (gen-wrap :function ::add-other-search-engine config name keyword url))

(defn update-search-engine* [config guid name keyword url]
  (gen-wrap :function ::update-search-engine config guid name keyword url))

(defn remove-search-engine* [config guid]
  (gen-wrap :function ::remove-search-engine config guid))

(defn get-hotword-state* [config]
  (gen-wrap :function ::get-hotword-state config))

(defn opt-into-hotwording* [config retrain]
  (gen-wrap :function ::opt-into-hotwording config retrain))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-search-engines-changed* [config channel & args]
  (gen-wrap :event ::on-search-engines-changed config channel args))

