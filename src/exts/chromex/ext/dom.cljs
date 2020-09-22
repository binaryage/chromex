(ns chromex.ext.dom (:require-macros [chromex.ext.dom :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-or-closed-shadow-root* [config element]
  (gen-wrap :function ::open-or-closed-shadow-root config element))

