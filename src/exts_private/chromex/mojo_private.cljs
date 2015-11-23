(ns chromex.mojo-private (:require-macros [chromex.mojo-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn define* [config module-name dependencies]
  (gen-wrap :function ::define config module-name dependencies))

(defn require-async* [config name]
  (gen-wrap :function ::require-async config name))

