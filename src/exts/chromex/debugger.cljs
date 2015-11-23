(ns chromex.debugger (:require-macros [chromex.debugger :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn attach* [config target required-version]
  (gen-wrap :function ::attach config target required-version))

(defn detach* [config target]
  (gen-wrap :function ::detach config target))

(defn send-command* [config target method command-params]
  (gen-wrap :function ::send-command config target method command-params))

(defn get-targets* [config]
  (gen-wrap :function ::get-targets config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-event* [config channel]
  (gen-wrap :event ::on-event config channel))

(defn on-detach* [config channel]
  (gen-wrap :event ::on-detach config channel))

