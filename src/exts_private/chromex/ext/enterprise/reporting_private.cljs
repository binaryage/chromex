(ns chromex.ext.enterprise.reporting-private (:require-macros [chromex.ext.enterprise.reporting-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn upload-chrome-desktop-report* [config report]
  (gen-wrap :function ::upload-chrome-desktop-report config report))

(defn get-device-id* [config]
  (gen-wrap :function ::get-device-id config))

