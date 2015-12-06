(ns chromex.ext.cast.streaming.session (:require-macros [chromex.ext.cast.streaming.session :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config audio-track video-track]
  (gen-wrap :function ::create config audio-track video-track))

