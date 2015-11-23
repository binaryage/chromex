(ns chromex.display-source (:require-macros [chromex.display-source :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-available-sinks* [config]
  (gen-wrap :function ::get-available-sinks config))

(defn request-authentication* [config sink-id]
  (gen-wrap :function ::request-authentication config sink-id))

(defn start-session* [config session-info]
  (gen-wrap :function ::start-session config session-info))

(defn terminate-session* [config sink-id]
  (gen-wrap :function ::terminate-session config sink-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-sinks-updated* [config channel]
  (gen-wrap :event ::on-sinks-updated config channel))

(defn on-session-started* [config channel]
  (gen-wrap :event ::on-session-started config channel))

(defn on-session-terminated* [config channel]
  (gen-wrap :event ::on-session-terminated config channel))

(defn on-session-error-occured* [config channel]
  (gen-wrap :event ::on-session-error-occured config channel))

