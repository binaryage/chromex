(ns chromex.devtools.network (:require-macros [chromex.devtools.network :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-har* [config]
  (gen-wrap :function ::get-har config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-request-finished* [config channel]
  (gen-wrap :event ::on-request-finished config channel))

(defn on-navigated* [config channel]
  (gen-wrap :event ::on-navigated config channel))

