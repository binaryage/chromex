(ns chromex.app.chromeos-info-private (:require-macros [chromex.app.chromeos-info-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config property-names]
  (gen-wrap :function ::get config property-names))

(defn set* [config property-name property-value]
  (gen-wrap :function ::set config property-name property-value))

(defn is-tablet-mode-enabled* [config]
  (gen-wrap :function ::is-tablet-mode-enabled config))

