(ns chromex.app.terminal-private (:require-macros [chromex.app.terminal-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-terminal-process* [config process-name args]
  (gen-wrap :function ::open-terminal-process config process-name args))

(defn close-terminal-process* [config id]
  (gen-wrap :function ::close-terminal-process config id))

(defn send-input* [config id input]
  (gen-wrap :function ::send-input config id input))

(defn on-terminal-resize* [config id width height]
  (gen-wrap :function ::on-terminal-resize config id width height))

(defn ack-output* [config tab-id id]
  (gen-wrap :function ::ack-output config tab-id id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-process-output* [config channel & args]
  (gen-wrap :event ::on-process-output config channel args))

