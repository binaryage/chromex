(ns chromex.app.proxy (:require-macros [chromex.app.proxy :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn settings* [config]
  (gen-wrap :property ::settings config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-proxy-error* [config channel & args]
  (gen-wrap :event ::on-proxy-error config channel args))

