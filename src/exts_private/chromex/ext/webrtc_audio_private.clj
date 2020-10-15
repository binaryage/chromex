(ns chromex.ext.webrtc-audio-private
  "The chrome.webrtcAudioPrivate API allows enumeration
   of audio output (sink) devices.

   Note that device IDs as used in this API are opaque (i.e. they are
   not the hardware identifier of the device) and while they are
   unique and persistent across sessions, they are valid only to the
   extension calling this API (i.e. they cannot be shared between
   extensions).

   See http://goo.gl/8rOmgk for further documentation of this API.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-sinks
  "Retrieves a list of available audio sink devices.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [sink-info] where:

     |sink-info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-sinks &form)))

(defmacro get-associated-sink
  "Given a security origin and an input device ID valid for that security origin, retrieve an audio sink ID valid for the
   extension, or the empty string if there is no associated audio sink.The associated sink ID can be used as a sink ID for
   setActiveSink. It is valid irrespective of which process you are setting the active sink for.

     |security-origin| - ?
     |source-id-in-origin| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [sink-id] where:

     |sink-id| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([security-origin source-id-in-origin] (gen-call :function ::get-associated-sink &form security-origin source-id-in-origin)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-sinks-changed-events
  "Fired when audio sink devices are added or removed.

   Events will be put on the |channel| with signature [::on-sinks-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-sinks-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.webrtc-audio-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webrtcAudioPrivate",
   :since "38",
   :functions
   [{:id ::get-sinks,
     :name "getSinks",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "sink-info", :type "[array-of-objects]"}]}}]}
    {:id ::get-associated-sink,
     :name "getAssociatedSink",
     :callback? true,
     :params
     [{:name "security-origin", :type "string"}
      {:name "source-id-in-origin", :type "string"}
      {:name "cb", :type :callback, :callback {:params [{:name "sink-id", :type "string"}]}}]}],
   :events [{:id ::on-sinks-changed, :name "onSinksChanged"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))