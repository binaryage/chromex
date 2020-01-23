(ns chromex.ext.printing (:require-macros [chromex.ext.printing :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn submit-job* [config request]
  (gen-wrap :function ::submit-job config request))

(defn get-printers* [config]
  (gen-wrap :function ::get-printers config))

(defn get-printer-info* [config printer-id]
  (gen-wrap :function ::get-printer-info config printer-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-job-status-changed* [config channel & args]
  (gen-wrap :event ::on-job-status-changed config channel args))

