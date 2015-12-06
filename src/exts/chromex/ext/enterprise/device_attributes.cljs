(ns chromex.ext.enterprise.device-attributes (:require-macros [chromex.ext.enterprise.device-attributes :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-directory-device-id* [config]
  (gen-wrap :function ::get-directory-device-id config))

