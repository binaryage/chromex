(ns chromex.copresence-private (:require-macros [chromex.copresence-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-initialized* [config success]
  (gen-wrap :function ::send-initialized config success))

(defn send-found* [config client-id tokens]
  (gen-wrap :function ::send-found config client-id tokens))

(defn send-samples* [config client-id token samples]
  (gen-wrap :function ::send-samples config client-id token samples))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-config-audio* [config channel]
  (gen-wrap :event ::on-config-audio config channel))

(defn on-encode-token-request* [config channel]
  (gen-wrap :event ::on-encode-token-request config channel))

(defn on-decode-samples-request* [config channel]
  (gen-wrap :event ::on-decode-samples-request config channel))

