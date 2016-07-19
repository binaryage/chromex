(ns chromex.app.clipboard (:require-macros [chromex.app.clipboard :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clipboard-data-changed* [config channel & args]
  (gen-wrap :event ::on-clipboard-data-changed config channel args))

