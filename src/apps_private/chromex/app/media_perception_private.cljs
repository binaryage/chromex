(ns chromex.app.media-perception-private (:require-macros [chromex.app.media-perception-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-state* [config]
  (gen-wrap :function ::get-state config))

(defn set-state* [config state]
  (gen-wrap :function ::set-state config state))

(defn get-diagnostics* [config]
  (gen-wrap :function ::get-diagnostics config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-media-perception* [config channel & args]
  (gen-wrap :event ::on-media-perception config channel args))

