(ns chromex.ext.enterprise.hardware-platform (:require-macros [chromex.ext.enterprise.hardware-platform :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-hardware-platform-info* [config]
  (gen-wrap :function ::get-hardware-platform-info config))

