(ns chromex.app.virtual-keyboard-private (:require-macros [chromex.app.virtual-keyboard-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn insert-text* [config text]
  (gen-wrap :function ::insert-text config text))

(defn send-key-event* [config key-event]
  (gen-wrap :function ::send-key-event config key-event))

(defn hide-keyboard* [config]
  (gen-wrap :function ::hide-keyboard config))

(defn set-hotrod-keyboard* [config enable]
  (gen-wrap :function ::set-hotrod-keyboard config enable))

(defn lock-keyboard* [config lock]
  (gen-wrap :function ::lock-keyboard config lock))

(defn keyboard-loaded* [config]
  (gen-wrap :function ::keyboard-loaded config))

(defn get-keyboard-config* [config]
  (gen-wrap :function ::get-keyboard-config config))

(defn open-settings* [config]
  (gen-wrap :function ::open-settings config))

(defn set-container-behavior* [config options]
  (gen-wrap :function ::set-container-behavior config options))

(defn set-draggable-area* [config bounds]
  (gen-wrap :function ::set-draggable-area config bounds))

(defn set-keyboard-state* [config state]
  (gen-wrap :function ::set-keyboard-state config state))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-bounds-changed config channel args))

(defn on-keyboard-closed* [config channel & args]
  (gen-wrap :event ::on-keyboard-closed config channel args))

(defn on-keyboard-config-changed* [config channel & args]
  (gen-wrap :event ::on-keyboard-config-changed config channel args))

