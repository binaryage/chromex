(ns chromex.webstore (:require-macros [chromex.webstore :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn install* [config url success-callback]
  (gen-wrap :function ::install config url success-callback))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-install-stage-changed* [config channel]
  (gen-wrap :event ::on-install-stage-changed config channel))

(defn on-download-progress* [config channel]
  (gen-wrap :event ::on-download-progress config channel))

