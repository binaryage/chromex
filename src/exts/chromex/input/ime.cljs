(ns chromex.input.ime (:require-macros [chromex.input.ime :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn set-composition* [config parameters]
  (gen-wrap :function ::set-composition config parameters))

(defn clear-composition* [config parameters]
  (gen-wrap :function ::clear-composition config parameters))

(defn commit-text* [config parameters]
  (gen-wrap :function ::commit-text config parameters))

(defn send-key-events* [config parameters]
  (gen-wrap :function ::send-key-events config parameters))

(defn hide-input-view* [config]
  (gen-wrap :function ::hide-input-view config))

(defn set-candidate-window-properties* [config parameters]
  (gen-wrap :function ::set-candidate-window-properties config parameters))

(defn set-candidates* [config parameters]
  (gen-wrap :function ::set-candidates config parameters))

(defn set-cursor-position* [config parameters]
  (gen-wrap :function ::set-cursor-position config parameters))

(defn set-menu-items* [config parameters]
  (gen-wrap :function ::set-menu-items config parameters))

(defn update-menu-items* [config parameters]
  (gen-wrap :function ::update-menu-items config parameters))

(defn delete-surrounding-text* [config parameters]
  (gen-wrap :function ::delete-surrounding-text config parameters))

(defn key-event-handled* [config request-id response]
  (gen-wrap :function ::key-event-handled config request-id response))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-activate* [config channel]
  (gen-wrap :event ::on-activate config channel))

(defn on-deactivated* [config channel]
  (gen-wrap :event ::on-deactivated config channel))

(defn on-focus* [config channel]
  (gen-wrap :event ::on-focus config channel))

(defn on-blur* [config channel]
  (gen-wrap :event ::on-blur config channel))

(defn on-input-context-update* [config channel]
  (gen-wrap :event ::on-input-context-update config channel))

(defn on-key-event* [config channel]
  (gen-wrap :event ::on-key-event config channel))

(defn on-candidate-clicked* [config channel]
  (gen-wrap :event ::on-candidate-clicked config channel))

(defn on-menu-item-activated* [config channel]
  (gen-wrap :event ::on-menu-item-activated config channel))

(defn on-surrounding-text-changed* [config channel]
  (gen-wrap :event ::on-surrounding-text-changed config channel))

(defn on-reset* [config channel]
  (gen-wrap :event ::on-reset config channel))

