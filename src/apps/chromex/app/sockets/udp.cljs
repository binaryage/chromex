(ns chromex.app.sockets.udp (:require-macros [chromex.app.sockets.udp :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config properties]
  (gen-wrap :function ::create config properties))

(defn update* [config socket-id properties]
  (gen-wrap :function ::update config socket-id properties))

(defn set-paused* [config socket-id paused]
  (gen-wrap :function ::set-paused config socket-id paused))

(defn bind* [config socket-id address port]
  (gen-wrap :function ::bind config socket-id address port))

(defn send* [config socket-id data address port]
  (gen-wrap :function ::send config socket-id data address port))

(defn close* [config socket-id]
  (gen-wrap :function ::close config socket-id))

(defn get-info* [config socket-id]
  (gen-wrap :function ::get-info config socket-id))

(defn get-sockets* [config]
  (gen-wrap :function ::get-sockets config))

(defn join-group* [config socket-id address]
  (gen-wrap :function ::join-group config socket-id address))

(defn leave-group* [config socket-id address]
  (gen-wrap :function ::leave-group config socket-id address))

(defn set-multicast-time-to-live* [config socket-id ttl]
  (gen-wrap :function ::set-multicast-time-to-live config socket-id ttl))

(defn set-multicast-loopback-mode* [config socket-id enabled]
  (gen-wrap :function ::set-multicast-loopback-mode config socket-id enabled))

(defn get-joined-groups* [config socket-id]
  (gen-wrap :function ::get-joined-groups config socket-id))

(defn set-broadcast* [config socket-id enabled]
  (gen-wrap :function ::set-broadcast config socket-id enabled))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-receive* [config channel & args]
  (gen-wrap :event ::on-receive config channel args))

(defn on-receive-error* [config channel & args]
  (gen-wrap :event ::on-receive-error config channel args))

