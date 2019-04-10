(ns chromex.app.feedback-private (:require-macros [chromex.app.feedback-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-user-email* [config]
  (gen-wrap :function ::get-user-email config))

(defn get-system-information* [config]
  (gen-wrap :function ::get-system-information config))

(defn send-feedback* [config feedback]
  (gen-wrap :function ::send-feedback config feedback))

(defn get-strings* [config flow]
  (gen-wrap :function ::get-strings config flow))

(defn read-log-source* [config params]
  (gen-wrap :function ::read-log-source config params))

(defn login-feedback-complete* [config]
  (gen-wrap :function ::login-feedback-complete config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-feedback-requested* [config channel & args]
  (gen-wrap :event ::on-feedback-requested config channel args))

