(ns chromex.file-browser-handler (:require-macros [chromex.file-browser-handler :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn select-file* [config selection-params]
  (gen-wrap :function ::select-file config selection-params))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-execute* [config channel]
  (gen-wrap :event ::on-execute config channel))

