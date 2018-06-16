(ns chromex.ext.declarative-net-request (:require-macros [chromex.ext.declarative-net-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-number-of-allowed-pages* [config]
  (gen-wrap :property ::max-number-of-allowed-pages config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-allowed-pages* [config page-patterns]
  (gen-wrap :function ::add-allowed-pages config page-patterns))

(defn remove-allowed-pages* [config page-patterns]
  (gen-wrap :function ::remove-allowed-pages config page-patterns))

(defn get-allowed-pages* [config]
  (gen-wrap :function ::get-allowed-pages config))

