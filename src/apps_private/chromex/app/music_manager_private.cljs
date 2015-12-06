(ns chromex.app.music-manager-private (:require-macros [chromex.app.music-manager-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-device-id* [config]
  (gen-wrap :function ::get-device-id config))

