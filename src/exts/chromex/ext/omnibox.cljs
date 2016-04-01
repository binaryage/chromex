(ns chromex.ext.omnibox (:require-macros [chromex.ext.omnibox :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-default-suggestion* [config suggestion]
  (gen-wrap :function ::set-default-suggestion config suggestion))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-input-started* [config channel & args]
  (gen-wrap :event ::on-input-started config channel args))

(defn on-input-changed* [config channel & args]
  (gen-wrap :event ::on-input-changed config channel args))

(defn on-input-entered* [config channel & args]
  (gen-wrap :event ::on-input-entered config channel args))

(defn on-input-cancelled* [config channel & args]
  (gen-wrap :event ::on-input-cancelled config channel args))

