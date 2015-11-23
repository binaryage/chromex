(ns chromex.braille-display-private
  "Braille display access private API.
   
     * available since Chrome 31
     * https://developer.chrome.com/extensions/brailleDisplayPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-display-state
  "Gets the current display state.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-display-state &form)))

(defmacro write-dots
  "Write the given dot patterns to the display.  The buffer contains one byte for each braille cell on the display,
   starting from the leftmost cell. Each byte contains a bit pattern indicating which dots should be raised in the
   corresponding cell with the low-order bit representing dot 1 and so on until bit 7 which corresponds to dot 8.  If
   the number of bytes in the buffer is not equal to the display size, the buffer will either be clipped or padded
   with blank cells on the right."
  ([cells] (gen-call :function ::write-dots &form cells)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-display-state-changed-events
  "Fired when a braille display is connected or disconnected."
  ([channel] (gen-call :event ::on-display-state-changed &form channel)))

(defmacro tap-on-key-event-events
  "Fired when an input event is received from the display."
  ([channel] (gen-call :event ::on-key-event &form channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

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

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))