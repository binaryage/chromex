(ns chromex.ext.storage (:require-macros [chromex.ext.storage :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn sync* [config]
  (gen-wrap :property ::sync config))

(defn local* [config]
  (gen-wrap :property ::local config))

(defn managed* [config]
  (gen-wrap :property ::managed config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-changed* [config channel & args]
  (gen-wrap :event ::on-changed config channel args))

