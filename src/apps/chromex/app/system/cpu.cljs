(ns chromex.app.system.cpu (:require-macros [chromex.app.system.cpu :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-info* [config]
  (gen-wrap :function ::get-info config))

