(ns chromex.ext.printing-metrics (:require-macros [chromex.ext.printing-metrics :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-print-jobs* [config]
  (gen-wrap :function ::get-print-jobs config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-print-job-finished* [config channel & args]
  (gen-wrap :event ::on-print-job-finished config channel args))

