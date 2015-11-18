(ns chromex.copresence (:require-macros [chromex.copresence :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn execute* [config operations]
  (gen-wrap :function ::execute config operations))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-messages-received* [config channel]
  (gen-wrap :event ::on-messages-received config channel))

(defn on-status-updated* [config channel]
  (gen-wrap :event ::on-status-updated config channel))

