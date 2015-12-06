(ns chromex.ext.system-private (:require-macros [chromex.ext.system-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-incognito-mode-availability* [config]
  (gen-wrap :function ::get-incognito-mode-availability config))

(defn get-update-status* [config]
  (gen-wrap :function ::get-update-status config))

(defn get-api-key* [config]
  (gen-wrap :function ::get-api-key config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-volume-changed* [config channel & args]
  (gen-wrap :event ::on-volume-changed config channel args))
(defn on-brightness-changed* [config channel & args]
  (gen-wrap :event ::on-brightness-changed config channel args))
(defn on-screen-unlocked* [config channel & args]
  (gen-wrap :event ::on-screen-unlocked config channel args))
(defn on-woke-up* [config channel & args]
  (gen-wrap :event ::on-woke-up config channel args))

