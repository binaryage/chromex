(ns chromex.app.system.network (:require-macros [chromex.app.system.network :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-network-interfaces* [config]
  (gen-wrap :function ::get-network-interfaces config))

