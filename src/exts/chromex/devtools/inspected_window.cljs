(ns chromex.devtools.inspected-window (:require-macros [chromex.devtools.inspected-window :refer [gen-wrap]])
    (:require [chromex-lib.core]))

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

(defn on-resource-added* [config channel]
  (gen-wrap :event ::on-resource-added config channel))

(defn on-resource-content-committed* [config channel]
  (gen-wrap :event ::on-resource-content-committed config channel))

