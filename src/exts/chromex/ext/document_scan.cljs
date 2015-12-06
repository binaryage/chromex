(ns chromex.ext.document-scan (:require-macros [chromex.ext.document-scan :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn scan* [config options]
  (gen-wrap :function ::scan config options))

