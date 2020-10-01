(ns chromex.app.dom (:require-macros [chromex.app.dom :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-or-closed-shadow-root* [config element]
  (gen-wrap :function ::open-or-closed-shadow-root config element))

