(ns chromex.ext.cast.streaming.udp-transport (:require-macros [chromex.ext.cast.streaming.udp-transport :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn destroy* [config transport-id]
  (gen-wrap :function ::destroy config transport-id))

(defn set-destination* [config transport-id destination]
  (gen-wrap :function ::set-destination config transport-id destination))

(defn set-options* [config transport-id options]
  (gen-wrap :function ::set-options config transport-id options))

