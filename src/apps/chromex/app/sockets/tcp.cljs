(ns chromex.app.sockets.tcp (:require-macros [chromex.app.sockets.tcp :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config properties]
  (gen-wrap :function ::create config properties))

(defn update* [config socket-id properties]
  (gen-wrap :function ::update config socket-id properties))

(defn set-paused* [config socket-id paused]
  (gen-wrap :function ::set-paused config socket-id paused))

(defn set-keep-alive* [config socket-id enable delay]
  (gen-wrap :function ::set-keep-alive config socket-id enable delay))

(defn set-no-delay* [config socket-id no-delay]
  (gen-wrap :function ::set-no-delay config socket-id no-delay))

(defn connect* [config socket-id peer-address peer-port]
  (gen-wrap :function ::connect config socket-id peer-address peer-port))

(defn disconnect* [config socket-id]
  (gen-wrap :function ::disconnect config socket-id))

(defn secure* [config socket-id options]
  (gen-wrap :function ::secure config socket-id options))

(defn send* [config socket-id data]
  (gen-wrap :function ::send config socket-id data))

(defn close* [config socket-id]
  (gen-wrap :function ::close config socket-id))

(defn get-info* [config socket-id]
  (gen-wrap :function ::get-info config socket-id))

(defn get-sockets* [config]
  (gen-wrap :function ::get-sockets config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-receive* [config channel & args]
  (gen-wrap :event ::on-receive config channel args))
(defn on-receive-error* [config channel & args]
  (gen-wrap :event ::on-receive-error config channel args))

