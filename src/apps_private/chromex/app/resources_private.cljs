(ns chromex.app.resources-private (:require-macros [chromex.app.resources-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-strings* [config component]
  (gen-wrap :function ::get-strings config component))

