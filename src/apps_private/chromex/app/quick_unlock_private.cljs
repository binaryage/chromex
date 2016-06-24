(ns chromex.app.quick-unlock-private (:require-macros [chromex.app.quick-unlock-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-available-modes* [config]
  (gen-wrap :function ::get-available-modes config))

(defn get-active-modes* [config]
  (gen-wrap :function ::get-active-modes config))

(defn set-modes* [config account-password modes credentials]
  (gen-wrap :function ::set-modes config account-password modes credentials))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-active-modes-changed* [config channel & args]
  (gen-wrap :event ::on-active-modes-changed config channel args))

