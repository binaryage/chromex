(ns chromex.app.sockets.tcp-server (:require-macros [chromex.app.sockets.tcp-server :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config properties]
  (gen-wrap :function ::create config properties))

(defn update* [config socket-id properties]
  (gen-wrap :function ::update config socket-id properties))

(defn set-paused* [config socket-id paused]
  (gen-wrap :function ::set-paused config socket-id paused))

(defn listen* [config socket-id address port backlog]
  (gen-wrap :function ::listen config socket-id address port backlog))

(defn disconnect* [config socket-id]
  (gen-wrap :function ::disconnect config socket-id))

(defn close* [config socket-id]
  (gen-wrap :function ::close config socket-id))

(defn get-info* [config socket-id]
  (gen-wrap :function ::get-info config socket-id))

(defn get-sockets* [config]
  (gen-wrap :function ::get-sockets config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-accept* [config channel & args]
  (gen-wrap :event ::on-accept config channel args))

(defn on-accept-error* [config channel & args]
  (gen-wrap :event ::on-accept-error config channel args))

