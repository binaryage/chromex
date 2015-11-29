(ns chromex.inline-install-private (:require-macros [chromex.inline-install-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn install* [config id]
  (gen-wrap :function ::install config id))

