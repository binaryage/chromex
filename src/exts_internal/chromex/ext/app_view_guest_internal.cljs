(ns chromex.ext.app-view-guest-internal (:require-macros [chromex.ext.app-view-guest-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn attach-frame* [config]
  (gen-wrap :function ::attach-frame config))

(defn deny-request* [config]
  (gen-wrap :function ::deny-request config))

