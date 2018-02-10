(ns chromex.app.terminal-private (:require-macros [chromex.app.terminal-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-terminal-process* [config process-name args]
  (gen-wrap :function ::open-terminal-process config process-name args))

(defn close-terminal-process* [config pid]
  (gen-wrap :function ::close-terminal-process config pid))

(defn send-input* [config pid input]
  (gen-wrap :function ::send-input config pid input))

(defn on-terminal-resize* [config pid width height]
  (gen-wrap :function ::on-terminal-resize config pid width height))

(defn ack-output* [config tab-id pid]
  (gen-wrap :function ::ack-output config tab-id pid))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-process-output* [config channel & args]
  (gen-wrap :event ::on-process-output config channel args))

