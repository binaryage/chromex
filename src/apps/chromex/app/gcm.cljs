(ns chromex.app.gcm (:require-macros [chromex.app.gcm :refer [gen-wrap]])
    (:require [chromex.core]))

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

(defn on-message* [config channel & args]
  (gen-wrap :event ::on-message config channel args))
(defn on-messages-deleted* [config channel & args]
  (gen-wrap :event ::on-messages-deleted config channel args))
(defn on-send-error* [config channel & args]
  (gen-wrap :event ::on-send-error config channel args))

