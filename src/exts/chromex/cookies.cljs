(ns chromex.cookies (:require-macros [chromex.cookies :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get* [config details]
  (gen-wrap :function ::get config details))

(defn get-all* [config details]
  (gen-wrap :function ::get-all config details))

(defn set* [config details]
  (gen-wrap :function ::set config details))

(defn remove* [config details]
  (gen-wrap :function ::remove config details))

(defn get-all-cookie-stores* [config]
  (gen-wrap :function ::get-all-cookie-stores config))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-changed* [config channel]
  (gen-wrap :event ::on-changed config channel))

