(ns chromex.ext.cloud-print-private (:require-macros [chromex.ext.cloud-print-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn setup-connector* [config user-email robot-email credentials user-settings]
  (gen-wrap :function ::setup-connector config user-email robot-email credentials user-settings))

(defn get-host-name* [config]
  (gen-wrap :function ::get-host-name config))

(defn get-printers* [config]
  (gen-wrap :function ::get-printers config))

(defn get-client-id* [config]
  (gen-wrap :function ::get-client-id config))

