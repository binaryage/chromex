(ns chromex.ext.printing (:require-macros [chromex.ext.printing :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-job-status-changed* [config channel & args]
  (gen-wrap :event ::on-job-status-changed config channel args))

