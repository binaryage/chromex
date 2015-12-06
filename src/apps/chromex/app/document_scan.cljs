(ns chromex.app.document-scan (:require-macros [chromex.app.document-scan :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn scan* [config options]
  (gen-wrap :function ::scan config options))

