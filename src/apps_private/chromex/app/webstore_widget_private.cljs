(ns chromex.app.webstore-widget-private (:require-macros [chromex.app.webstore-widget-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

(defn install-webstore-item* [config item-id silent-installation]
  (gen-wrap :function ::install-webstore-item config item-id silent-installation))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-show-widget* [config channel & args]
  (gen-wrap :event ::on-show-widget config channel args))

