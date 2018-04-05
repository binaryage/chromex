(ns chromex.app.streams-private
  "Streams Private API.

     * available since Chrome 27"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro abort
  "Abort the URL request on the given stream.

     |stream-url| - The URL of the stream to abort.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([stream-url] (gen-call :function ::abort &form stream-url)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-execute-mime-type-handler-events
  "Fired when a resource is fetched which matches a mime type handled by this extension. The resource request is cancelled,
   and the extension is expected to handle the request. The event is restricted to a small number of white-listed extensions.

   Events will be put on the |channel| with signature [::on-execute-mime-type-handler [stream-info]] where:

     |stream-info| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-execute-mime-type-handler &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.streams-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.streamsPrivate",
   :since "27",
   :functions
   [{:id ::abort,
     :name "abort",
     :since "37",
     :callback? true,
     :params [{:name "stream-url", :type "string"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-execute-mime-type-handler,
     :name "onExecuteMimeTypeHandler",
     :params [{:name "stream-info", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))