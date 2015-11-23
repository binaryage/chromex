(ns chromex.tts-engine
  "Use the chrome.ttsEngine API to implement a text-to-speech(TTS) engine using an extension. If your extension
   registers using this API, it will receive events containing an utterance to be spoken and other parameters when
   any extension or Chrome App uses the tts API to generate speech. Your extension can then use any available web
   technology to synthesize and output the speech, and send events back to the calling function to report the status.
   
     * available since Chrome 14
     * https://developer.chrome.com/extensions/ttsEngine"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-speak-events
  "Called when the user makes a call to tts.speak() and one of the voices from this extension's manifest is the first
   to match the options object."
  ([channel] (gen-call :event ::on-speak (meta &form) channel)))

(defmacro tap-on-stop-events
  "Fired when a call is made to tts.stop and this extension may be in the middle of speaking. If an extension receives
   a call to onStop and speech is already stopped, it should do nothing (not raise an error). If speech is in the
   paused state, this should cancel the paused state."
  ([channel] (gen-call :event ::on-stop (meta &form) channel)))

(defmacro tap-on-pause-events
  "Optional: if an engine supports the pause event, it should pause the current utterance being spoken, if any, until
   it receives a resume event or stop event. Note that a stop event should also clear the paused state."
  ([channel] (gen-call :event ::on-pause (meta &form) channel)))

(defmacro tap-on-resume-events
  "Optional: if an engine supports the pause event, it should also support the resume event, to continue speaking the
   current utterance, if any. Note that a stop event should also clear the paused state."
  ([channel] (gen-call :event ::on-resume (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.ttsEngine",
   :since "14",
   :events
   [{:id ::on-speak,
     :name "onSpeak",
     :params
     [{:name "utterance", :type "string"}
      {:name "options", :type "object"}
      {:name "send-tts-event", :type :callback}]}
    {:id ::on-stop, :name "onStop"}
    {:id ::on-pause, :name "onPause", :since "29"}
    {:id ::on-resume, :name "onResume", :since "29"}]})

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