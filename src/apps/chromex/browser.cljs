(ns chromex.browser (:require-macros [chromex.browser :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-tab* [config options]
  (gen-wrap :function ::open-tab config options))

