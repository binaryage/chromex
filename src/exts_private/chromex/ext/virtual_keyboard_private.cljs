(ns chromex.ext.virtual-keyboard-private (:require-macros [chromex.ext.virtual-keyboard-private :refer [gen-wrap]])
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

(defn open-suggestion-settings* [config]
  (gen-wrap :function ::open-suggestion-settings config))

(defn set-container-behavior* [config options]
  (gen-wrap :function ::set-container-behavior config options))

(defn set-draggable-area* [config bounds]
  (gen-wrap :function ::set-draggable-area config bounds))

(defn set-keyboard-state* [config state]
  (gen-wrap :function ::set-keyboard-state config state))

(defn set-occluded-bounds* [config bounds-list]
  (gen-wrap :function ::set-occluded-bounds config bounds-list))

(defn set-hit-test-bounds* [config bounds-list]
  (gen-wrap :function ::set-hit-test-bounds config bounds-list))

(defn set-area-to-remain-on-screen* [config bounds]
  (gen-wrap :function ::set-area-to-remain-on-screen config bounds))

(defn set-window-bounds-in-screen* [config bounds]
  (gen-wrap :function ::set-window-bounds-in-screen config bounds))

(defn get-clipboard-history* [config options]
  (gen-wrap :function ::get-clipboard-history config options))

(defn paste-clipboard-item* [config item-id]
  (gen-wrap :function ::paste-clipboard-item config item-id))

(defn delete-clipboard-item* [config item-id]
  (gen-wrap :function ::delete-clipboard-item config item-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-bounds-changed config channel args))

(defn on-keyboard-closed* [config channel & args]
  (gen-wrap :event ::on-keyboard-closed config channel args))

(defn on-keyboard-config-changed* [config channel & args]
  (gen-wrap :event ::on-keyboard-config-changed config channel args))

(defn on-clipboard-history-changed* [config channel & args]
  (gen-wrap :event ::on-clipboard-history-changed config channel args))

(defn on-clipboard-item-updated* [config channel & args]
  (gen-wrap :event ::on-clipboard-item-updated config channel args))

