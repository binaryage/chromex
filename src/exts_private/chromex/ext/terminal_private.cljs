(ns chromex.ext.terminal-private (:require-macros [chromex.ext.terminal-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-terminal-process* [config process-name args]
  (gen-wrap :function ::open-terminal-process config process-name args))

(defn open-vmshell-process* [config args]
  (gen-wrap :function ::open-vmshell-process config args))

(defn close-terminal-process* [config id]
  (gen-wrap :function ::close-terminal-process config id))

(defn send-input* [config id input]
  (gen-wrap :function ::send-input config id input))

(defn on-terminal-resize* [config id width height]
  (gen-wrap :function ::on-terminal-resize config id width height))

(defn ack-output* [config id]
  (gen-wrap :function ::ack-output config id))

(defn open-window* [config]
  (gen-wrap :function ::open-window config))

(defn open-options-page* [config]
  (gen-wrap :function ::open-options-page config))

(defn get-settings* [config]
  (gen-wrap :function ::get-settings config))

(defn set-settings* [config settings]
  (gen-wrap :function ::set-settings config settings))

(defn get-a11y-status* [config]
  (gen-wrap :function ::get-a11y-status config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-process-output* [config channel & args]
  (gen-wrap :event ::on-process-output config channel args))

(defn on-settings-changed* [config channel & args]
  (gen-wrap :event ::on-settings-changed config channel args))

(defn on-a11y-status-changed* [config channel & args]
  (gen-wrap :event ::on-a11y-status-changed config channel args))

