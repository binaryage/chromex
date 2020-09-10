(ns chromex.ext.enterprise.networking-attributes (:require-macros [chromex.ext.enterprise.networking-attributes :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-network-details* [config]
  (gen-wrap :function ::get-network-details config))

