(ns chromex.app.braille-display-private
  "Braille display access private API.

     * available since Chrome 31"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-display-state
  "Gets the current display state.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([] (gen-call :function ::get-display-state &form)))

(defmacro write-dots
  "Write the given dot patterns to the display.  The buffer contains one byte for each braille cell on the display, starting
   from the leftmost cell. Each byte contains a bit pattern indicating which dots should be raised in the corresponding cell
   with the low-order bit representing dot 1 and so on until bit 7 which corresponds to dot 8.  If the number of bytes in the
   buffer is not equal to the display size, the buffer will either be clipped or padded with blank cells on the right.

     |cells| - ?"
  ([cells] (gen-call :function ::write-dots &form cells)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-display-state-changed-events
  "Fired when a braille display is connected or disconnected.

   Events will be put on the |channel| with signature [::on-display-state-changed [state]] where:

     |state| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-display-state-changed &form channel args)))

(defmacro tap-on-key-event-events
  "Fired when an input event is received from the display.

   Events will be put on the |channel| with signature [::on-key-event [event]] where:

     |event| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-key-event &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.brailleDisplayPrivate",
   :since "31",
   :functions
   [{:id ::get-display-state,
     :name "getDisplayState",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "brailleDisplayPrivate.DisplayState"}]}}]}
    {:id ::write-dots, :name "writeDots", :params [{:name "cells", :type "ArrayBuffer"}]}],
   :events
   [{:id ::on-display-state-changed,
     :name "onDisplayStateChanged",
     :params [{:name "state", :type "brailleDisplayPrivate.DisplayState"}]}
    {:id ::on-key-event, :name "onKeyEvent", :params [{:name "event", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))