(ns chromex.ext.search (:require-macros [chromex.ext.search :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn query* [config query-info]
  (gen-wrap :function ::query config query-info))

