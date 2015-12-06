(ns chromex.app.braille-display-private (:require-macros [chromex.app.braille-display-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-display-state* [config]
  (gen-wrap :function ::get-display-state config))

(defn write-dots* [config cells]
  (gen-wrap :function ::write-dots config cells))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-display-state-changed* [config channel & args]
  (gen-wrap :event ::on-display-state-changed config channel args))
(defn on-key-event* [config channel & args]
  (gen-wrap :event ::on-key-event config channel args))

