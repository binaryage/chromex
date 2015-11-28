(ns chromex.terminal-private (:require-macros [chromex.terminal-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-terminal-process* [config process-name]
  (gen-wrap :function ::open-terminal-process config process-name))

(defn close-terminal-process* [config pid]
  (gen-wrap :function ::close-terminal-process config pid))

(defn send-input* [config pid input]
  (gen-wrap :function ::send-input config pid input))

(defn on-terminal-resize* [config pid width height]
  (gen-wrap :function ::on-terminal-resize config pid width height))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-process-output* [config channel & args]
  (gen-wrap :event ::on-process-output config channel args))

