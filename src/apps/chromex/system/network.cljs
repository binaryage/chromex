(ns chromex.system.network (:require-macros [chromex.system.network :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-network-interfaces* [config]
  (gen-wrap :function ::get-network-interfaces config))

