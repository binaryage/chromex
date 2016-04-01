(ns chromex.ext.web-request (:require-macros [chromex.ext.web-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-handler-behavior-changed-calls-per10-minutes* [config]
  (gen-wrap :property ::max-handler-behavior-changed-calls-per10-minutes config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn handler-behavior-changed* [config]
  (gen-wrap :function ::handler-behavior-changed config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-before-request* [config channel & args]
  (gen-wrap :event ::on-before-request config channel args))

(defn on-before-send-headers* [config channel & args]
  (gen-wrap :event ::on-before-send-headers config channel args))

(defn on-send-headers* [config channel & args]
  (gen-wrap :event ::on-send-headers config channel args))

(defn on-headers-received* [config channel & args]
  (gen-wrap :event ::on-headers-received config channel args))

(defn on-auth-required* [config channel & args]
  (gen-wrap :event ::on-auth-required config channel args))

(defn on-response-started* [config channel & args]
  (gen-wrap :event ::on-response-started config channel args))

(defn on-before-redirect* [config channel & args]
  (gen-wrap :event ::on-before-redirect config channel args))

(defn on-completed* [config channel & args]
  (gen-wrap :event ::on-completed config channel args))

(defn on-error-occurred* [config channel & args]
  (gen-wrap :event ::on-error-occurred config channel args))

