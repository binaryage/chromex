(ns chromex.top-sites (:require-macros [chromex.top-sites :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get* [config]
  (gen-wrap :function ::get config))

