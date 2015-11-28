(ns chromex.tab-capture (:require-macros [chromex.tab-capture :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn capture* [config options]
  (gen-wrap :function ::capture config options))

(defn get-captured-tabs* [config]
  (gen-wrap :function ::get-captured-tabs config))

(defn capture-offscreen-tab* [config start-url options]
  (gen-wrap :function ::capture-offscreen-tab config start-url options))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-status-changed* [config channel & args]
  (gen-wrap :event ::on-status-changed config channel args))

