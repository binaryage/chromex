(ns chromex.dashboard-private (:require-macros [chromex.dashboard-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn show-permission-prompt-for-delegated-install* [config details]
  (gen-wrap :function ::show-permission-prompt-for-delegated-install config details))

(defn show-permission-prompt-for-delegated-bundle-install* [config details contents]
  (gen-wrap :function ::show-permission-prompt-for-delegated-bundle-install config details contents))

