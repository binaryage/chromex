(ns chromex.app.preferences-private (:require-macros [chromex.app.preferences-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn easy-unlock-proximity-required* [config]
  (gen-wrap :property ::easy-unlock-proximity-required config))

