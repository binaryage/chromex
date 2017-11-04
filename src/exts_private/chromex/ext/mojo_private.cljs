(ns chromex.ext.mojo-private (:require-macros [chromex.ext.mojo-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn require-async* [config name]
  (gen-wrap :function ::require-async config name))

