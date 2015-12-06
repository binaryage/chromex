(ns chromex.app.file-browser-handler (:require-macros [chromex.app.file-browser-handler :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-file* [config selection-params]
  (gen-wrap :function ::select-file config selection-params))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-execute* [config channel & args]
  (gen-wrap :event ::on-execute config channel args))

