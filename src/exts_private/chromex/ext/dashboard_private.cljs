(ns chromex.ext.dashboard-private (:require-macros [chromex.ext.dashboard-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn show-permission-prompt-for-delegated-install* [config details]
  (gen-wrap :function ::show-permission-prompt-for-delegated-install config details))

