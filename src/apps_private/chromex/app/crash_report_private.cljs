(ns chromex.app.crash-report-private (:require-macros [chromex.app.crash-report-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn report-error* [config info]
  (gen-wrap :function ::report-error config info))

