(ns chromex.webrtc-audio-private
  "The chrome.webrtcAudioPrivate API allows enumeration
   of audio output (sink) devices as well as getting and setting the
   active device for a given requesting process.
   
   Note that device IDs as used in this API are opaque (i.e. they are
   not the hardware identifier of the device) and while they are
   unique and persistent across sessions, they are valid only to the
   extension calling this API (i.e. they cannot be shared between
   extensions).
   
   See http://goo.gl/8rOmgk for further documentation of this API.
   
     * available since Chrome 32
     * https://developer.chrome.com/extensions/webrtcAudioPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-sinks
  "Retrieves a list of available audio sink devices."
  [#_callback]
  (gen-call :function ::get-sinks (meta &form)))

(defmacro get-active-sink
  "Retrieves the currently active audio sink for the given requesting process."
  [request #_callback]
  (gen-call :function ::get-active-sink (meta &form) request))

(defmacro set-active-sink
  "Sets the active audio sink device for the specified requesting process."
  [request sink-id #_callback]
  (gen-call :function ::set-active-sink (meta &form) request sink-id))

(defmacro get-associated-sink
  "Given a security origin and an input device ID valid for that security origin, retrieve an audio sink ID valid for
   the extension, or the empty string if there is no associated audio sink.The associated sink ID can be used as a
   sink ID for setActiveSink. It is valid irrespective of which process you are setting the active sink for."
  [security-origin source-id-in-origin #_cb]
  (gen-call :function ::get-associated-sink (meta &form) security-origin source-id-in-origin))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-sinks-changed
  "Fired when audio sink devices are added or removed."
  [channel]
  (gen-call :event ::on-sinks-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webrtcAudioPrivate",
   :since "32",
   :functions
   [{:id ::get-sinks,
     :name "getSinks",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "sink-info", :type "[array-of-objects]"}]}}]}
    {:id ::get-active-sink,
     :name "getActiveSink",
     :callback? true,
     :params
     [{:name "request", :type "webrtcAudioPrivate.RequestInfo"}
      {:name "callback", :type :callback, :callback {:params [{:name "sink-id", :type "string"}]}}]}
    {:id ::set-active-sink,
     :name "setActiveSink",
     :callback? true,
     :params
     [{:name "request", :type "webrtcAudioPrivate.RequestInfo"}
      {:name "sink-id", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::get-associated-sink,
     :name "getAssociatedSink",
     :callback? true,
     :params
     [{:name "security-origin", :type "string"}
      {:name "source-id-in-origin", :type "string"}
      {:name "cb", :type :callback, :callback {:params [{:name "sink-id", :type "string"}]}}]}],
   :events [{:id ::on-sinks-changed, :name "onSinksChanged"}]})

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