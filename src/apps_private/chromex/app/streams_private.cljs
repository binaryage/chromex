(ns chromex.app.streams-private (:require-macros [chromex.app.streams-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn abort* [config stream-url]
  (gen-wrap :function ::abort config stream-url))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-execute-mime-type-handler* [config channel & args]
  (gen-wrap :event ::on-execute-mime-type-handler config channel args))

