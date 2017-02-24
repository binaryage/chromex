(ns chromex.ext.cast.streaming.receiver-session (:require-macros [chromex.ext.cast.streaming.receiver-session :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create-and-bind* [config audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url error-callback transport-options]
  (gen-wrap :function ::create-and-bind config audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url error-callback transport-options))

