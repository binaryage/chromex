(ns chromex.app-view-guest-internal (:require-macros [chromex.app-view-guest-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn attach-frame* [config]
  (gen-wrap :function ::attach-frame config))

(defn deny-request* [config]
  (gen-wrap :function ::deny-request config))

