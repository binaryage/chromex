(ns chromex.ext.feedback-private (:require-macros [chromex.ext.feedback-private :refer [gen-wrap]])
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

(defn log-srt-prompt-result* [config result]
  (gen-wrap :function ::log-srt-prompt-result config result))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-feedback-requested* [config channel & args]
  (gen-wrap :event ::on-feedback-requested config channel args))

