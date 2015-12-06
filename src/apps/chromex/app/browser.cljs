(ns chromex.app.browser (:require-macros [chromex.app.browser :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-tab* [config options]
  (gen-wrap :function ::open-tab config options))

