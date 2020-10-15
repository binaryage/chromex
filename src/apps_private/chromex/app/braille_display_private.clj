(ns chromex.app.braille-display-private
  "Braille display access private API.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-display-state
  "Gets the current display state.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-display-state &form)))

(defmacro write-dots
  "Write the given dot patterns to the display.  The buffer contains one byte for each braille cell on the display, starting
   from the leftmost cell. Each byte contains a bit pattern indicating which dots should be raised in the corresponding cell
   with the low-order bit representing dot 1 and so on until bit 7 which corresponds to dot 8.  If the number of bytes in the
   buffer is not equal to the display size, the buffer will either be clipped or padded with blank cells on the right. The
   buffer is a 2D array compressed into 1D. The |columns| and |rows| parameters give the original 2D dimensions of the buffer.
   To access an element cells[r][c], simply access cells[r * columns + c].

     |cells| - ?
     |columns| - ?
     |rows| - ?"
  ([cells columns rows] (gen-call :function ::write-dots &form cells columns rows)))

(defmacro update-bluetooth-braille-display-address
  "Updates the single user-preferred braille device with the given bluetooth device address and starts or restarts the Brltty
   daemon.

     |address| - ?"
  ([address] (gen-call :function ::update-bluetooth-braille-display-address &form address)))

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
  "Taps all valid non-deprecated events in chromex.app.braille-display-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.brailleDisplayPrivate",
   :since "38",
   :functions
   [{:id ::get-display-state,
     :name "getDisplayState",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "brailleDisplayPrivate.DisplayState"}]}}]}
    {:id ::write-dots,
     :name "writeDots",
     :params
     [{:name "cells", :type "ArrayBuffer"}
      {:name "columns", :since "56", :type "integer"}
      {:name "rows", :since "56", :type "integer"}]}
    {:id ::update-bluetooth-braille-display-address,
     :name "updateBluetoothBrailleDisplayAddress",
     :since "72",
     :params [{:name "address", :type "string"}]}],
   :events
   [{:id ::on-display-state-changed,
     :name "onDisplayStateChanged",
     :params [{:name "state", :type "brailleDisplayPrivate.DisplayState"}]}
    {:id ::on-key-event, :name "onKeyEvent", :params [{:name "event", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))