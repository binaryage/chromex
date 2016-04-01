(ns chromex.ext.devtools.inspected-window (:require-macros [chromex.ext.devtools.inspected-window :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn tab-id* [config]
  (gen-wrap :property ::tab-id config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn eval* [config expression options]
  (gen-wrap :function ::eval config expression options))

(defn reload* [config reload-options]
  (gen-wrap :function ::reload config reload-options))

(defn get-resources* [config]
  (gen-wrap :function ::get-resources config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-resource-added* [config channel & args]
  (gen-wrap :event ::on-resource-added config channel args))

(defn on-resource-content-committed* [config channel & args]
  (gen-wrap :event ::on-resource-content-committed config channel args))

