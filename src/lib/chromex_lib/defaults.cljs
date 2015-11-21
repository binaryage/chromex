(ns chromex-lib.defaults
  (:require [cljs.core.async :refer [put! chan]]))

; -- callback support -----------------------------------------------------------------------------------------------
;
; async methods using core.async channels

(defn default-callback-fn-factory [_config chan]
  (fn [& args]
    (put! chan args)))

(defn default-callback-channel-factory [_config]
  (chan))

(defn default-event-fn-factory [_config event-id chan]
  (fn [& args]
    (put! chan [event-id (vec args)])))

; -- logging support ------------------------------------------------------------------------------------------------

(defn console-log [& args]
  (.apply (.-log js/console) js/console (apply array args)))

(defn default-logger [& args]
  (apply console-log "[chromex]" args))

; -- default config -------------------------------------------------------------------------------------------------

(def default-config
  {:callback-channel-factory default-callback-channel-factory
   :callback-fn-factory      default-callback-fn-factory
   :event-fn-factory         default-event-fn-factory
   :logger                   default-logger
   :verbose-logging          false})