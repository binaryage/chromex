(ns chromex.context-menus-internal (:require-macros [chromex.context-menus-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clicked* [config channel]
  (gen-wrap :event ::on-clicked config channel))

