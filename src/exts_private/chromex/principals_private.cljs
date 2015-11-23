(ns chromex.principals-private (:require-macros [chromex.principals-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn sign-out* [config]
  (gen-wrap :function ::sign-out config))

(defn show-avatar-bubble* [config]
  (gen-wrap :function ::show-avatar-bubble config))

