(ns chromex.sessions (:require-macros [chromex.sessions :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-session-results* [config]
  (gen-wrap :property ::max-session-results config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-recently-closed* [config filter]
  (gen-wrap :function ::get-recently-closed config filter))

(defn get-devices* [config filter]
  (gen-wrap :function ::get-devices config filter))

(defn restore* [config session-id]
  (gen-wrap :function ::restore config session-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-changed* [config channel]
  (gen-wrap :event ::on-changed config channel))

