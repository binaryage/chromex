(ns chromex.app.clipboard (:require-macros [chromex.app.clipboard :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-image-data* [config image-data type additional-items]
  (gen-wrap :function ::set-image-data config image-data type additional-items))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clipboard-data-changed* [config channel & args]
  (gen-wrap :event ::on-clipboard-data-changed config channel args))

