(ns chromex.experimental.devtools.audits (:require-macros [chromex.experimental.devtools.audits :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-category* [config display-name result-count]
  (gen-wrap :function ::add-category config display-name result-count))

