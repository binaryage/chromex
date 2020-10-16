(ns chromex.app.clipboard
  "The chrome.clipboard API is provided to allow users to
   access data of the clipboard. This is a temporary solution for
   chromeos platform apps until open-web alternative is available. It will be
   deprecated once open-web solution is available, which could be in 2017 Q4.

     * available since Chrome 88
     * https://developer.chrome.com/apps/clipboard"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-image-data
  "Sets image data to clipboard.

     |image-data| - The encoded image data.
     |type| - The type of image being passed.
     |additional-items| - Additional data items for describing image data. The callback is called with
                          chrome.runtime.lastError set to error code if there is an error. Requires clipboard and
                          clipboardWrite permissions.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/clipboard#method-setImageData."
  ([image-data type additional-items] (gen-call :function ::set-image-data &form image-data type additional-items))
  ([image-data type] `(set-image-data ~image-data ~type :omit)))

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
   :since "88",
   :functions
   [{:id ::set-image-data,
     :name "setImageData",
     :since "88",
     :callback? true,
     :params
     [{:name "image-data", :since "future", :type "ArrayBuffer"}
      {:name "type", :since "future", :type "unknown-type"}
      {:name "additional-items", :optional? true, :since "future", :type "[array-of-objects]"}
      {:name "callback", :optional? true, :type :callback}]}],
   :events [{:id ::on-clipboard-data-changed, :name "onClipboardDataChanged", :since "88"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))