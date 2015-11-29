(ns chromex.dns (:require-macros [chromex.dns :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn resolve* [config hostname]
  (gen-wrap :function ::resolve config hostname))

