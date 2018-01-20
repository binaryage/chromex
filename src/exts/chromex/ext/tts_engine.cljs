(ns chromex.ext.tts-engine (:require-macros [chromex.ext.tts-engine :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn update-voices* [config voices]
  (gen-wrap :function ::update-voices config voices))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-speak* [config channel & args]
  (gen-wrap :event ::on-speak config channel args))

(defn on-stop* [config channel & args]
  (gen-wrap :event ::on-stop config channel args))

(defn on-pause* [config channel & args]
  (gen-wrap :event ::on-pause config channel args))

(defn on-resume* [config channel & args]
  (gen-wrap :event ::on-resume config channel args))

