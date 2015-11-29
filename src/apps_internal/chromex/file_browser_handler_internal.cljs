(ns chromex.file-browser-handler-internal (:require-macros [chromex.file-browser-handler-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-file* [config selection-params]
  (gen-wrap :function ::select-file config selection-params))

