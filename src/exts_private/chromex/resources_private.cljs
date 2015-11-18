(ns chromex.resources-private (:require-macros [chromex.resources-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-strings* [config component]
  (gen-wrap :function ::get-strings config component))

