(ns chromex.ext.cec-private
  "Private API for HDMI CEC functionality.

     * available since Chrome 68"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro send-stand-by
  "Attempt to put all HDMI CEC compatible devices in stand-by.This is not guaranteed to have any effect on the connected
   displays. Displays that do not support HDMI CEC will not be affected."
  ([] (gen-call :function ::send-stand-by &form)))

(defmacro send-wake-up
  "Attempt to announce this device as the active input source towards all HDMI CEC enabled displays connected, waking them
   from standby if necessary."
  ([] (gen-call :function ::send-wake-up &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cec-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cecPrivate",
   :since "68",
   :functions [{:id ::send-stand-by, :name "sendStandBy"} {:id ::send-wake-up, :name "sendWakeUp"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))