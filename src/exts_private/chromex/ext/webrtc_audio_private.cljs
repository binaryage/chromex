(ns chromex.ext.webrtc-audio-private (:require-macros [chromex.ext.webrtc-audio-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-sinks* [config]
  (gen-wrap :function ::get-sinks config))

(defn get-associated-sink* [config security-origin source-id-in-origin]
  (gen-wrap :function ::get-associated-sink config security-origin source-id-in-origin))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-sinks-changed* [config channel & args]
  (gen-wrap :event ::on-sinks-changed config channel args))

