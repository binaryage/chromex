(ns chromex.app.file-browser-handler-internal (:require-macros [chromex.app.file-browser-handler-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-file* [config selection-params]
  (gen-wrap :function ::select-file config selection-params))

