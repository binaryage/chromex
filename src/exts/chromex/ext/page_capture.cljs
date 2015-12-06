(ns chromex.ext.page-capture (:require-macros [chromex.ext.page-capture :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn save-as-mhtml* [config details]
  (gen-wrap :function ::save-as-mhtml config details))

