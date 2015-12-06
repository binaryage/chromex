(ns chromex.app.diagnostics (:require-macros [chromex.app.diagnostics :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-packet* [config options]
  (gen-wrap :function ::send-packet config options))

