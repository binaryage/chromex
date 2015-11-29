(ns chromex.media-player-private (:require-macros [chromex.media-player-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-next-track* [config channel & args]
  (gen-wrap :event ::on-next-track config channel args))
(defn on-prev-track* [config channel & args]
  (gen-wrap :event ::on-prev-track config channel args))
(defn on-toggle-play-state* [config channel & args]
  (gen-wrap :event ::on-toggle-play-state config channel args))

