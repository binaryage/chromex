(ns chromex.streams-private (:require-macros [chromex.streams-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn abort* [config stream-url]
  (gen-wrap :function ::abort config stream-url))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-execute-mime-type-handler* [config channel]
  (gen-wrap :event ::on-execute-mime-type-handler config channel))

