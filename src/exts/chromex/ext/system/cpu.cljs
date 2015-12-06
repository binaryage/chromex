(ns chromex.ext.system.cpu (:require-macros [chromex.ext.system.cpu :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

