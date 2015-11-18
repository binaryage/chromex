(ns chromex.desktop-capture (:require-macros [chromex.desktop-capture :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn choose-desktop-media* [config sources target-tab]
  (gen-wrap :function ::choose-desktop-media config sources target-tab))

(defn cancel-choose-desktop-media* [config desktop-media-request-id]
  (gen-wrap :function ::cancel-choose-desktop-media config desktop-media-request-id))

