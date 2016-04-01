(ns chromex.app.extension-options-internal (:require-macros [chromex.app.extension-options-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-close* [config channel & args]
  (gen-wrap :event ::on-close config channel args))

(defn on-load* [config channel & args]
  (gen-wrap :event ::on-load config channel args))

(defn on-preferred-size-changed* [config channel & args]
  (gen-wrap :event ::on-preferred-size-changed config channel args))

