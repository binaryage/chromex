(ns chromex.ext.proxy (:require-macros [chromex.ext.proxy :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn settings* [config]
  (gen-wrap :property ::settings config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-proxy-error* [config channel & args]
  (gen-wrap :event ::on-proxy-error config channel args))

