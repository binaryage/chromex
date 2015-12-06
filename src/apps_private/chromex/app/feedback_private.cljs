(ns chromex.app.feedback-private (:require-macros [chromex.app.feedback-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-user-email* [config]
  (gen-wrap :function ::get-user-email config))

(defn get-system-information* [config]
  (gen-wrap :function ::get-system-information config))

(defn send-feedback* [config feedback]
  (gen-wrap :function ::send-feedback config feedback))

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-feedback-requested* [config channel & args]
  (gen-wrap :event ::on-feedback-requested config channel args))

