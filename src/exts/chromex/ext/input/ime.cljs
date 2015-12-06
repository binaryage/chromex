(ns chromex.ext.input.ime (:require-macros [chromex.ext.input.ime :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

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

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-activate* [config channel & args]
  (gen-wrap :event ::on-activate config channel args))
(defn on-deactivated* [config channel & args]
  (gen-wrap :event ::on-deactivated config channel args))
(defn on-focus* [config channel & args]
  (gen-wrap :event ::on-focus config channel args))
(defn on-blur* [config channel & args]
  (gen-wrap :event ::on-blur config channel args))
(defn on-input-context-update* [config channel & args]
  (gen-wrap :event ::on-input-context-update config channel args))
(defn on-key-event* [config channel & args]
  (gen-wrap :event ::on-key-event config channel args))
(defn on-candidate-clicked* [config channel & args]
  (gen-wrap :event ::on-candidate-clicked config channel args))
(defn on-menu-item-activated* [config channel & args]
  (gen-wrap :event ::on-menu-item-activated config channel args))
(defn on-surrounding-text-changed* [config channel & args]
  (gen-wrap :event ::on-surrounding-text-changed config channel args))
(defn on-reset* [config channel & args]
  (gen-wrap :event ::on-reset config channel args))

