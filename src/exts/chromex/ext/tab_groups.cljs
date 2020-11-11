(ns chromex.ext.tab-groups (:require-macros [chromex.ext.tab-groups :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn tab-group-id-none* [config]
  (gen-wrap :property ::tab-group-id-none config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config group-id]
  (gen-wrap :function ::get config group-id))

(defn query* [config query-info]
  (gen-wrap :function ::query config query-info))

(defn update* [config group-id update-properties]
  (gen-wrap :function ::update config group-id update-properties))

(defn move* [config group-id move-properties]
  (gen-wrap :function ::move config group-id move-properties))

