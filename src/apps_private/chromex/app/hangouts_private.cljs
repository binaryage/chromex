(ns chromex.app.hangouts-private (:require-macros [chromex.app.hangouts-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-hangout-requested* [config channel & args]
  (gen-wrap :event ::on-hangout-requested config channel args))

