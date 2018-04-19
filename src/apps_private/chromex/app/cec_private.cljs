(ns chromex.app.cec-private (:require-macros [chromex.app.cec-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-stand-by* [config]
  (gen-wrap :function ::send-stand-by config))

(defn send-wake-up* [config]
  (gen-wrap :function ::send-wake-up config))

