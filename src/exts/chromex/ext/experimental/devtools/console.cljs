(ns chromex.ext.experimental.devtools.console (:require-macros [chromex.ext.experimental.devtools.console :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-message* [config severity text]
  (gen-wrap :function ::add-message config severity text))

(defn get-messages* [config]
  (gen-wrap :function ::get-messages config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-message-added* [config channel & args]
  (gen-wrap :event ::on-message-added config channel args))

