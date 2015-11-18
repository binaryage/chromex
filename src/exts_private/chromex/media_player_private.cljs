(ns chromex.media-player-private (:require-macros [chromex.media-player-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-next-track* [config channel]
  (gen-wrap :event ::on-next-track config channel))

(defn on-prev-track* [config channel]
  (gen-wrap :event ::on-prev-track config channel))

(defn on-toggle-play-state* [config channel]
  (gen-wrap :event ::on-toggle-play-state config channel))

