(ns chromex.app.dashboard-private (:require-macros [chromex.app.dashboard-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn show-permission-prompt-for-delegated-install* [config details]
  (gen-wrap :function ::show-permission-prompt-for-delegated-install config details))

