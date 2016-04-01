(ns chromex.ext.copresence-private (:require-macros [chromex.ext.copresence-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-initialized* [config success]
  (gen-wrap :function ::send-initialized config success))

(defn send-found* [config client-id tokens]
  (gen-wrap :function ::send-found config client-id tokens))

(defn send-samples* [config client-id token samples]
  (gen-wrap :function ::send-samples config client-id token samples))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-config-audio* [config channel & args]
  (gen-wrap :event ::on-config-audio config channel args))

(defn on-encode-token-request* [config channel & args]
  (gen-wrap :event ::on-encode-token-request config channel args))

(defn on-decode-samples-request* [config channel & args]
  (gen-wrap :event ::on-decode-samples-request config channel args))

