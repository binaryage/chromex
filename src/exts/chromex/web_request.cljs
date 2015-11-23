(ns chromex.web-request (:require-macros [chromex.web-request :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-handler-behavior-changed-calls-per10-minutes* [config]
  (gen-wrap :property ::max-handler-behavior-changed-calls-per10-minutes config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn handler-behavior-changed* [config]
  (gen-wrap :function ::handler-behavior-changed config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-before-request* [config channel]
  (gen-wrap :event ::on-before-request config channel))

(defn on-before-send-headers* [config channel]
  (gen-wrap :event ::on-before-send-headers config channel))

(defn on-send-headers* [config channel]
  (gen-wrap :event ::on-send-headers config channel))

(defn on-headers-received* [config channel]
  (gen-wrap :event ::on-headers-received config channel))

(defn on-auth-required* [config channel]
  (gen-wrap :event ::on-auth-required config channel))

(defn on-response-started* [config channel]
  (gen-wrap :event ::on-response-started config channel))

(defn on-before-redirect* [config channel]
  (gen-wrap :event ::on-before-redirect config channel))

(defn on-completed* [config channel]
  (gen-wrap :event ::on-completed config channel))

(defn on-error-occurred* [config channel]
  (gen-wrap :event ::on-error-occurred config channel))

