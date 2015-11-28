(ns chromex.experimental.devtools.console (:require-macros [chromex.experimental.devtools.console :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-message* [config severity text]
  (gen-wrap :function ::add-message config severity text))

(defn get-messages* [config]
  (gen-wrap :function ::get-messages config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-message-added* [config channel & args]
  (gen-wrap :event ::on-message-added config channel args))

