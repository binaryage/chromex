(ns chromex.app.clipboard
  "The chrome.clipboard API is provided to allow users to
   access data of the clipboard. This API is currently only implemented for
   ChromeOS.

     * available since Chrome 55
     * https://developer.chrome.com/apps/clipboard"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clipboard-data-changed-events
  "Fired when clipboard data changes. Requires clipboard and clipboardRead permissions for adding listener to
   chrome.clipboard.onClipboardDataChanged event. After this event fires, the clipboard data is available by calling
   document.execCommand('paste').

   Events will be put on the |channel| with signature [::on-clipboard-data-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/clipboard#event-onClipboardDataChanged."
  ([channel & args] (apply gen-call :event ::on-clipboard-data-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.clipboard namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.clipboard",
   :since "55",
   :events [{:id ::on-clipboard-data-changed, :name "onClipboardDataChanged"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))