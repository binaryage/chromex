(ns chromex.ext.webstore (:require-macros [chromex.ext.webstore :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn install* [config url success-callback]
  (gen-wrap :function ::install config url success-callback))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-install-stage-changed* [config channel & args]
  (gen-wrap :event ::on-install-stage-changed config channel args))

(defn on-download-progress* [config channel & args]
  (gen-wrap :event ::on-download-progress config channel args))

