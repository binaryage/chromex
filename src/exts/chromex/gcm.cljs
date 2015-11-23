(ns chromex.gcm (:require-macros [chromex.gcm :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-message-size* [config]
  (gen-wrap :property ::max-message-size config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn register* [config sender-ids]
  (gen-wrap :function ::register config sender-ids))

(defn unregister* [config]
  (gen-wrap :function ::unregister config))

(defn send* [config message]
  (gen-wrap :function ::send config message))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-message* [config channel]
  (gen-wrap :event ::on-message config channel))

(defn on-messages-deleted* [config channel]
  (gen-wrap :event ::on-messages-deleted config channel))

(defn on-send-error* [config channel]
  (gen-wrap :event ::on-send-error config channel))

