(ns chromex.app.tts
  "Use the chrome.tts API to play synthesized text-to-speech (TTS). See also the related ttsEngine API, which allows an
   extension to implement a speech engine.

     * available since Chrome 38
     * https://developer.chrome.com/apps/tts"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro speak
  "Speaks text using a text-to-speech engine.

     |utterance| - The text to speak, either plain text or a complete, well-formed SSML document. Speech engines that do not
                   support SSML will strip away the tags and speak the text. The maximum length of the text is 32,768
                   characters.
     |options| - The speech options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tts#method-speak."
  ([utterance options] (gen-call :function ::speak &form utterance options))
  ([utterance] `(speak ~utterance :omit)))

(defmacro stop
  "Stops any current speech and flushes the queue of any pending utterances. In addition, if speech was paused, it will now be
   un-paused for the next call to speak.

   https://developer.chrome.com/apps/tts#method-stop."
  ([] (gen-call :function ::stop &form)))

(defmacro pause
  "Pauses speech synthesis, potentially in the middle of an utterance. A call to resume or stop will un-pause speech.

   https://developer.chrome.com/apps/tts#method-pause."
  ([] (gen-call :function ::pause &form)))

(defmacro resume
  "If speech was paused, resumes speaking where it left off.

   https://developer.chrome.com/apps/tts#method-resume."
  ([] (gen-call :function ::resume &form)))

(defmacro is-speaking
  "Checks whether the engine is currently speaking. On Mac OS X, the result is true whenever the system speech engine is
   speaking, even if the speech wasn't initiated by Chrome.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [speaking] where:

     |speaking| - True if speaking, false otherwise.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tts#method-isSpeaking."
  ([] (gen-call :function ::is-speaking &form)))

(defmacro get-voices
  "Gets an array of all available voices.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [voices] where:

     |voices| - Array of 'tts.TtsVoice' objects representing the available voices for speech synthesis.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/tts#method-getVoices."
  ([] (gen-call :function ::get-voices &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.tts namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tts",
   :since "38",
   :functions
   [{:id ::speak,
     :name "speak",
     :callback? true,
     :params
     [{:name "utterance", :type "string"}
      {:name "options", :optional? true, :type "tts.TtsOptions"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::stop, :name "stop"}
    {:id ::pause, :name "pause"}
    {:id ::resume, :name "resume"}
    {:id ::is-speaking,
     :name "isSpeaking",
     :callback? true,
     :params
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "speaking", :type "boolean"}]}}]}
    {:id ::get-voices,
     :name "getVoices",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "voices", :type "[array-of-tts.TtsVoices]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))