(ns chromex.ext.declarative-net-request (:require-macros [chromex.ext.declarative-net-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-number-of-allowed-pages* [config]
  (gen-wrap :property ::max-number-of-allowed-pages config))

(defn max-number-of-rules* [config]
  (gen-wrap :property ::max-number-of-rules config))

(defn max-number-of-dynamic-rules* [config]
  (gen-wrap :property ::max-number-of-dynamic-rules config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn update-dynamic-rules* [config rule-ids-to-remove rules-to-add]
  (gen-wrap :function ::update-dynamic-rules config rule-ids-to-remove rules-to-add))

(defn get-dynamic-rules* [config]
  (gen-wrap :function ::get-dynamic-rules config))

(defn add-allowed-pages* [config page-patterns]
  (gen-wrap :function ::add-allowed-pages config page-patterns))

(defn remove-allowed-pages* [config page-patterns]
  (gen-wrap :function ::remove-allowed-pages config page-patterns))

(defn get-allowed-pages* [config]
  (gen-wrap :function ::get-allowed-pages config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-rule-matched-debug* [config channel & args]
  (gen-wrap :event ::on-rule-matched-debug config channel args))

