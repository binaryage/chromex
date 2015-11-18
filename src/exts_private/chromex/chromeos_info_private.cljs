(ns chromex.chromeos-info-private (:require-macros [chromex.chromeos-info-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get* [config property-names]
  (gen-wrap :function ::get config property-names))

(defn set* [config property-name property-value]
  (gen-wrap :function ::set config property-name property-value))

