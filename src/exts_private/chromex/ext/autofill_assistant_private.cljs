(ns chromex.ext.autofill-assistant-private (:require-macros [chromex.ext.autofill-assistant-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create* [config]
  (gen-wrap :function ::create config))

(defn start* [config parameters]
  (gen-wrap :function ::start config parameters))

(defn perform-action* [config index]
  (gen-wrap :function ::perform-action config index))

(defn provide-user-data* [config]
  (gen-wrap :function ::provide-user-data config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-status-message-changed* [config channel & args]
  (gen-wrap :event ::on-status-message-changed config channel args))

(defn on-actions-changed* [config channel & args]
  (gen-wrap :event ::on-actions-changed config channel args))

