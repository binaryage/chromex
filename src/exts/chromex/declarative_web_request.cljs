(ns chromex.declarative-web-request (:require-macros [chromex.declarative-web-request :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-request* [config channel]
  (gen-wrap :event ::on-request config channel))

(defn on-message* [config channel]
  (gen-wrap :event ::on-message config channel))

