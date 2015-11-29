(ns chromex.music-manager-private (:require-macros [chromex.music-manager-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-device-id* [config]
  (gen-wrap :function ::get-device-id config))

