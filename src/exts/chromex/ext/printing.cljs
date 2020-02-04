(ns chromex.ext.printing (:require-macros [chromex.ext.printing :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-submit-job-calls-per-minute* [config]
  (gen-wrap :property ::max-submit-job-calls-per-minute config))

(defn max-get-printer-info-calls-per-minute* [config]
  (gen-wrap :property ::max-get-printer-info-calls-per-minute config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn submit-job* [config request]
  (gen-wrap :function ::submit-job config request))

(defn cancel-job* [config job-id]
  (gen-wrap :function ::cancel-job config job-id))

(defn get-printers* [config]
  (gen-wrap :function ::get-printers config))

(defn get-printer-info* [config printer-id]
  (gen-wrap :function ::get-printer-info config printer-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-job-status-changed* [config channel & args]
  (gen-wrap :event ::on-job-status-changed config channel args))

