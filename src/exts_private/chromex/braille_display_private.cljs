(ns chromex.braille-display-private (:require-macros [chromex.braille-display-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-display-state* [config]
  (gen-wrap :function ::get-display-state config))

(defn write-dots* [config cells]
  (gen-wrap :function ::write-dots config cells))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-display-state-changed* [config channel]
  (gen-wrap :event ::on-display-state-changed config channel))

(defn on-key-event* [config channel]
  (gen-wrap :event ::on-key-event config channel))

