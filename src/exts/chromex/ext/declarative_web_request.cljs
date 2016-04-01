(ns chromex.ext.declarative-web-request (:require-macros [chromex.ext.declarative-web-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-request* [config channel & args]
  (gen-wrap :event ::on-request config channel args))

(defn on-message* [config channel & args]
  (gen-wrap :event ::on-message config channel args))

