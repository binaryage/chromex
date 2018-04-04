(ns chromex.ext.declarative-net-request (:require-macros [chromex.ext.declarative-net-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-number-of-whitelisted-pages* [config]
  (gen-wrap :property ::max-number-of-whitelisted-pages config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-whitelisted-pages* [config page-patterns]
  (gen-wrap :function ::add-whitelisted-pages config page-patterns))

(defn remove-whitelisted-pages* [config page-patterns]
  (gen-wrap :function ::remove-whitelisted-pages config page-patterns))

(defn get-whitelisted-pages* [config]
  (gen-wrap :function ::get-whitelisted-pages config))

