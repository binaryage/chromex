(ns chromex.command-line-private (:require-macros [chromex.command-line-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn has-switch* [config name]
  (gen-wrap :function ::has-switch config name))

