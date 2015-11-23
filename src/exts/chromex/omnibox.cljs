(ns chromex.omnibox (:require-macros [chromex.omnibox :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-default-suggestion* [config suggestion]
  (gen-wrap :function ::set-default-suggestion config suggestion))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-input-started* [config channel]
  (gen-wrap :event ::on-input-started config channel))

(defn on-input-changed* [config channel]
  (gen-wrap :event ::on-input-changed config channel))

(defn on-input-entered* [config channel]
  (gen-wrap :event ::on-input-entered config channel))

(defn on-input-cancelled* [config channel]
  (gen-wrap :event ::on-input-cancelled config channel))

