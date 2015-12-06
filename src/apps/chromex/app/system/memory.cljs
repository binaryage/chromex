(ns chromex.app.system.memory (:require-macros [chromex.app.system.memory :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

