(ns chromex.ext.login-state (:require-macros [chromex.ext.login-state :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-profile-type* [config]
  (gen-wrap :function ::get-profile-type config))

(defn get-session-state* [config]
  (gen-wrap :function ::get-session-state config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-session-state-changed* [config channel & args]
  (gen-wrap :event ::on-session-state-changed config channel args))

