(ns chromex.app.socket (:require-macros [chromex.app.socket :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config type options]
  (gen-wrap :function ::create config type options))

(defn destroy* [config socket-id]
  (gen-wrap :function ::destroy config socket-id))

(defn connect* [config socket-id hostname port]
  (gen-wrap :function ::connect config socket-id hostname port))

(defn bind* [config socket-id address port]
  (gen-wrap :function ::bind config socket-id address port))

(defn disconnect* [config socket-id]
  (gen-wrap :function ::disconnect config socket-id))

(defn read* [config socket-id buffer-size]
  (gen-wrap :function ::read config socket-id buffer-size))

(defn write* [config socket-id data]
  (gen-wrap :function ::write config socket-id data))

(defn recv-from* [config socket-id buffer-size]
  (gen-wrap :function ::recv-from config socket-id buffer-size))

(defn send-to* [config socket-id data address port]
  (gen-wrap :function ::send-to config socket-id data address port))

(defn listen* [config socket-id address port backlog]
  (gen-wrap :function ::listen config socket-id address port backlog))

(defn accept* [config socket-id]
  (gen-wrap :function ::accept config socket-id))

(defn set-keep-alive* [config socket-id enable delay]
  (gen-wrap :function ::set-keep-alive config socket-id enable delay))

(defn set-no-delay* [config socket-id no-delay]
  (gen-wrap :function ::set-no-delay config socket-id no-delay))

(defn get-info* [config socket-id]
  (gen-wrap :function ::get-info config socket-id))

(defn get-network-list* [config]
  (gen-wrap :function ::get-network-list config))

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

(defn secure* [config socket-id options]
  (gen-wrap :function ::secure config socket-id options))

