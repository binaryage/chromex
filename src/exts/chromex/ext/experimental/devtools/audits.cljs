(ns chromex.ext.experimental.devtools.audits (:require-macros [chromex.ext.experimental.devtools.audits :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-category* [config display-name result-count]
  (gen-wrap :function ::add-category config display-name result-count))

