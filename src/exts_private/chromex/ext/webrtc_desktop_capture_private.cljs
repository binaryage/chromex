(ns chromex.ext.webrtc-desktop-capture-private (:require-macros [chromex.ext.webrtc-desktop-capture-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn choose-desktop-media* [config sources request]
  (gen-wrap :function ::choose-desktop-media config sources request))

(defn cancel-choose-desktop-media* [config desktop-media-request-id]
  (gen-wrap :function ::cancel-choose-desktop-media config desktop-media-request-id))

