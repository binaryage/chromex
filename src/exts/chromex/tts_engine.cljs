(ns chromex.tts-engine (:require-macros [chromex.tts-engine :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-speak* [config channel]
  (gen-wrap :event ::on-speak config channel))

(defn on-stop* [config channel]
  (gen-wrap :event ::on-stop config channel))

(defn on-pause* [config channel]
  (gen-wrap :event ::on-pause config channel))

(defn on-resume* [config channel]
  (gen-wrap :event ::on-resume config channel))

