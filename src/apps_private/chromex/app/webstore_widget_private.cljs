(ns chromex.app.webstore-widget-private (:require-macros [chromex.app.webstore-widget-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn install-webstore-item* [config item-id silent-installation]
  (gen-wrap :function ::install-webstore-item config item-id silent-installation))

