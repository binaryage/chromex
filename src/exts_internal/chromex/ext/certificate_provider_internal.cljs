(ns chromex.ext.certificate-provider-internal (:require-macros [chromex.ext.certificate-provider-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn report-signature* [config request-id signature]
  (gen-wrap :function ::report-signature config request-id signature))

(defn report-certificates* [config request-id certificates]
  (gen-wrap :function ::report-certificates config request-id certificates))

