(ns chromex.ext.command-line-private (:require-macros [chromex.ext.command-line-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn has-switch* [config name]
  (gen-wrap :function ::has-switch config name))

