(ns chromex.ext.tts-engine
  "Use the chrome.ttsEngine API to implement a text-to-speech(TTS) engine using an extension. If your extension registers
   using this API, it will receive events containing an utterance to be spoken and other parameters when any extension or
   Chrome App uses the tts API to generate speech. Your extension can then use any available web technology to synthesize and
   output the speech, and send events back to the calling function to report the status.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/ttsEngine"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro update-voices
  "Called by an engine to update its list of voices. This list overrides any voices declared in this extension's manifest.

     |voices| - Array of 'tts.TtsVoice' objects representing the available voices for speech synthesis.

   https://developer.chrome.com/extensions/ttsEngine#method-updateVoices."
  ([voices] (gen-call :function ::update-voices &form voices)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-speak-events
  "Called when the user makes a call to tts.speak() and one of the voices from this extension's manifest is the first to match
   the options object.

   Events will be put on the |channel| with signature [::on-speak [utterance options send-tts-event]] where:

     |utterance| - The text to speak, specified as either plain text or an SSML document. If your engine does not support
                   SSML, you should strip out all XML markup and synthesize only the underlying text content. The value of
                   this parameter is guaranteed to be no more than 32,768 characters. If this engine does not support speaking
                   that many characters at a time, the utterance should be split into smaller chunks and queued internally
                   without returning an error.
     |options| - Options specified to the tts.speak() method.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/ttsEngine#event-onSpeak."
  ([channel & args] (apply gen-call :event ::on-speak &form channel args)))

(defmacro tap-on-stop-events
  "Fired when a call is made to tts.stop and this extension may be in the middle of speaking. If an extension receives a call
   to onStop and speech is already stopped, it should do nothing (not raise an error). If speech is in the paused state, this
   should cancel the paused state.

   Events will be put on the |channel| with signature [::on-stop []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/ttsEngine#event-onStop."
  ([channel & args] (apply gen-call :event ::on-stop &form channel args)))

(defmacro tap-on-pause-events
  "Optional: if an engine supports the pause event, it should pause the current utterance being spoken, if any, until it
   receives a resume event or stop event. Note that a stop event should also clear the paused state.

   Events will be put on the |channel| with signature [::on-pause []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/ttsEngine#event-onPause."
  ([channel & args] (apply gen-call :event ::on-pause &form channel args)))

(defmacro tap-on-resume-events
  "Optional: if an engine supports the pause event, it should also support the resume event, to continue speaking the current
   utterance, if any. Note that a stop event should also clear the paused state.

   Events will be put on the |channel| with signature [::on-resume []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/ttsEngine#event-onResume."
  ([channel & args] (apply gen-call :event ::on-resume &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.tts-engine namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.ttsEngine",
   :since "38",
   :functions
   [{:id ::update-voices,
     :name "updateVoices",
     :since "66",
     :params [{:name "voices", :type "[array-of-tts.TtsVoices]"}]}],
   :events
   [{:id ::on-speak,
     :name "onSpeak",
     :params
     [{:name "utterance", :type "string"} {:name "options", :type "object"} {:name "send-tts-event", :type :callback}]}
    {:id ::on-stop, :name "onStop"}
    {:id ::on-pause, :name "onPause"}
    {:id ::on-resume, :name "onResume"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))