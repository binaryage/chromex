(ns chromex.power (:require-macros [chromex.power :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn request-keep-awake* [config level]
  (gen-wrap :function ::request-keep-awake config level))

(defn release-keep-awake* [config]
  (gen-wrap :function ::release-keep-awake config))

