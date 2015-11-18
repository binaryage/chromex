(ns chromex.printer-provider (:require-macros [chromex.printer-provider :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-get-printers-requested* [config channel]
  (gen-wrap :event ::on-get-printers-requested config channel))

(defn on-get-usb-printer-info-requested* [config channel]
  (gen-wrap :event ::on-get-usb-printer-info-requested config channel))

(defn on-get-capability-requested* [config channel]
  (gen-wrap :event ::on-get-capability-requested config channel))

(defn on-print-requested* [config channel]
  (gen-wrap :event ::on-print-requested config channel))

