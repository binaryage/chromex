(ns chromex.ext.cryptotoken-private (:require-macros [chromex.ext.cryptotoken-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn can-origin-assert-app-id* [config security-origin app-id-url]
  (gen-wrap :function ::can-origin-assert-app-id config security-origin app-id-url))

(defn is-app-id-hash-in-enterprise-context* [config app-id-hash]
  (gen-wrap :function ::is-app-id-hash-in-enterprise-context config app-id-hash))

