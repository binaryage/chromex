(ns chromex.extension-options-internal (:require-macros [chromex.extension-options-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-close* [config channel]
  (gen-wrap :event ::on-close config channel))

(defn on-load* [config channel]
  (gen-wrap :event ::on-load config channel))

(defn on-preferred-size-changed* [config channel]
  (gen-wrap :event ::on-preferred-size-changed config channel))

