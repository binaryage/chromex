(ns chromex.app.virtual-keyboard (:require-macros [chromex.app.virtual-keyboard :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn restrict-features* [config restrictions]
  (gen-wrap :function ::restrict-features config restrictions))

