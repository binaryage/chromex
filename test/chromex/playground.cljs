(ns chromex.playground
  (:require-macros [chromex.playground :refer [gen-wrap]])
  (:require [chromex-lib.core]
            [chromex.test.marshalling]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn some-prop* [config]
  (gen-wrap :property ::some-prop config))

(defn some-missing-prop* [config]
  (gen-wrap :property ::some-missing-prop config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-something* [config param1]
  (gen-wrap :function ::get-something config param1))

(defn do-something* [config param1]
  (gen-wrap :function ::do-something config param1))

(defn do-something-optional-args* [config opt-p1 opt-p2 opt-p3]
  (gen-wrap :function ::do-something-optional-args config opt-p1 opt-p2 opt-p3))

(defn do-something-missing* [config]
  (gen-wrap :function ::do-something-missing config))

(defn get-storage-area* [config]
  (gen-wrap :function ::get-storage-area config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-something* [config chan & args]
  (gen-wrap :event ::on-something config chan args))

(defn on-something-deprecated* [config chan & args]
  (gen-wrap :event ::on-something-deprecated config chan args))

(defn on-something-else* [config chan & args]
  (gen-wrap :event ::on-something-else config chan args))

(defn on-something-missing* [config chan & args]
  (gen-wrap :event ::on-something-missing config chan args))
