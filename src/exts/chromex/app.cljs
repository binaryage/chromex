(ns chromex.app (:require-macros [chromex.app :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-is-installed* [config]
  (gen-wrap :function ::get-is-installed config))

(defn install-state* [config]
  (gen-wrap :function ::install-state config))

(defn running-state* [config]
  (gen-wrap :function ::running-state config))

(defn get-details* [config]
  (gen-wrap :function ::get-details config))

