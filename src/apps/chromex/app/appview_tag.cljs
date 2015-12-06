(ns chromex.app.appview-tag (:require-macros [chromex.app.appview-tag :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn connect* [config app data]
  (gen-wrap :function ::connect config app data))

