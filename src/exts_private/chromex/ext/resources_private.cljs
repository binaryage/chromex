(ns chromex.ext.resources-private (:require-macros [chromex.ext.resources-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-strings* [config component]
  (gen-wrap :function ::get-strings config component))

