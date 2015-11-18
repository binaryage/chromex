(ns chromex.feedback-private (:require-macros [chromex.feedback-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-user-email* [config]
  (gen-wrap :function ::get-user-email config))

(defn get-system-information* [config]
  (gen-wrap :function ::get-system-information config))

(defn send-feedback* [config feedback]
  (gen-wrap :function ::send-feedback config feedback))

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-feedback-requested* [config channel]
  (gen-wrap :event ::on-feedback-requested config channel))

