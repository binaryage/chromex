(ns chromex.enterprise.device-attributes (:require-macros [chromex.enterprise.device-attributes :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-directory-device-id* [config]
  (gen-wrap :function ::get-directory-device-id config))

