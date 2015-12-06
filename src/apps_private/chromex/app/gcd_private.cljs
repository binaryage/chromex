(ns chromex.app.gcd-private (:require-macros [chromex.app.gcd-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-device-info* [config service-name]
  (gen-wrap :function ::get-device-info config service-name))

(defn create-session* [config service-name]
  (gen-wrap :function ::create-session config service-name))

(defn start-pairing* [config session-id pairing-type]
  (gen-wrap :function ::start-pairing config session-id pairing-type))

(defn confirm-code* [config session-id code]
  (gen-wrap :function ::confirm-code config session-id code))

(defn send-message* [config session-id api input]
  (gen-wrap :function ::send-message config session-id api input))

(defn terminate-session* [config session-id]
  (gen-wrap :function ::terminate-session config session-id))

