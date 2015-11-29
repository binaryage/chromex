(ns chromex.bluetooth-socket (:require-macros [chromex.bluetooth-socket :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config properties]
  (gen-wrap :function ::create config properties))

(defn update* [config socket-id properties]
  (gen-wrap :function ::update config socket-id properties))

(defn set-paused* [config socket-id paused]
  (gen-wrap :function ::set-paused config socket-id paused))

(defn listen-using-rfcomm* [config socket-id uuid options]
  (gen-wrap :function ::listen-using-rfcomm config socket-id uuid options))

(defn listen-using-l2cap* [config socket-id uuid options]
  (gen-wrap :function ::listen-using-l2cap config socket-id uuid options))

(defn connect* [config socket-id address uuid]
  (gen-wrap :function ::connect config socket-id address uuid))

(defn disconnect* [config socket-id]
  (gen-wrap :function ::disconnect config socket-id))

(defn close* [config socket-id]
  (gen-wrap :function ::close config socket-id))

(defn send* [config socket-id data]
  (gen-wrap :function ::send config socket-id data))

(defn get-info* [config socket-id]
  (gen-wrap :function ::get-info config socket-id))

(defn get-sockets* [config]
  (gen-wrap :function ::get-sockets config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-accept* [config channel & args]
  (gen-wrap :event ::on-accept config channel args))
(defn on-accept-error* [config channel & args]
  (gen-wrap :event ::on-accept-error config channel args))
(defn on-receive* [config channel & args]
  (gen-wrap :event ::on-receive config channel args))
(defn on-receive-error* [config channel & args]
  (gen-wrap :event ::on-receive-error config channel args))

