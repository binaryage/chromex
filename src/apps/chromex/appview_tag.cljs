(ns chromex.appview-tag (:require-macros [chromex.appview-tag :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn connect* [config app data]
  (gen-wrap :function ::connect config app data))

