(ns chromex.app.command-line-private (:require-macros [chromex.app.command-line-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn has-switch* [config name]
  (gen-wrap :function ::has-switch config name))

