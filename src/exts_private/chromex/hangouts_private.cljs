(ns chromex.hangouts-private (:require-macros [chromex.hangouts-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-hangout-requested* [config channel]
  (gen-wrap :event ::on-hangout-requested config channel))

