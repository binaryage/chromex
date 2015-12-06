(ns chromex.app.printer-provider-internal (:require-macros [chromex.app.printer-provider-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn report-printers* [config request-id printers]
  (gen-wrap :function ::report-printers config request-id printers))

(defn report-usb-printer-info* [config request-id printer-info]
  (gen-wrap :function ::report-usb-printer-info config request-id printer-info))

(defn report-printer-capability* [config request-id capability]
  (gen-wrap :function ::report-printer-capability config request-id capability))

(defn report-print-result* [config request-id error]
  (gen-wrap :function ::report-print-result config request-id error))

(defn get-print-data* [config request-id]
  (gen-wrap :function ::get-print-data config request-id))

