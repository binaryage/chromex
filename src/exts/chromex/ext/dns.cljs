(ns chromex.ext.dns (:require-macros [chromex.ext.dns :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn resolve* [config hostname]
  (gen-wrap :function ::resolve config hostname))

