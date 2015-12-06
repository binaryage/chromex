(ns chromex.ext.devtools.network (:require-macros [chromex.ext.devtools.network :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-har* [config]
  (gen-wrap :function ::get-har config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-request-finished* [config channel & args]
  (gen-wrap :event ::on-request-finished config channel args))
(defn on-navigated* [config channel & args]
  (gen-wrap :event ::on-navigated config channel args))

