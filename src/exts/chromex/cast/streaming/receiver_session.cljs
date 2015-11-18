(ns chromex.cast.streaming.receiver-session (:require-macros [chromex.cast.streaming.receiver-session :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn create-and-bind* [config audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url transport-options]
  (gen-wrap :function ::create-and-bind config audio-params video-params local-endpoint max-width max-height max-frame-rate media-stream-url transport-options))

