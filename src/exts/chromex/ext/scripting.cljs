(ns chromex.ext.scripting (:require-macros [chromex.ext.scripting :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn execute-script* [config injection]
  (gen-wrap :function ::execute-script config injection))

