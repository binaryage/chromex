(ns chromex.tts
  "Use the chrome.tts API to play synthesized text-to-speech (TTS). See also the related ttsEngine API, which allows
   an extension to implement a speech engine.
   
     * available since Chrome 14
     * https://developer.chrome.com/extensions/tts"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro speak
  "Speaks text using a text-to-speech engine.
   
     |utterance| - The text to speak, either plain text or a complete, well-formed SSML document. Speech engines
                   that do not support SSML will strip away the tags and speak the text. The maximum length of the
                   text is 32,768 characters.
     |options| - The speech options.
     |callback| - Called right away, before speech finishes. Check chrome.runtime.lastError to make sure there were
                  no errors. Use options.onEvent to get more detailed feedback."
  [utterance options #_callback]
  (gen-call :function ::speak (meta &form) utterance options))

(defmacro stop
  "Stops any current speech and flushes the queue of any pending utterances. In addition, if speech was paused, it
   will now be un-paused for the next call to speak."
  []
  (gen-call :function ::stop (meta &form)))

(defmacro pause
  "Pauses speech synthesis, potentially in the middle of an utterance. A call to resume or stop will un-pause speech."
  []
  (gen-call :function ::pause (meta &form)))

(defmacro resume
  "If speech was paused, resumes speaking where it left off."
  []
  (gen-call :function ::resume (meta &form)))

(defmacro is-speaking
  "Checks whether the engine is currently speaking. On Mac OS X, the result is true whenever the system speech engine
   is speaking, even if the speech wasn't initiated by Chrome."
  [#_callback]
  (gen-call :function ::is-speaking (meta &form)))

(defmacro get-voices
  "Gets an array of all available voices."
  [#_callback]
  (gen-call :function ::get-voices (meta &form)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tts",
   :since "14",
   :functions
   [{:id ::speak,
     :name "speak",
     :callback? true,
     :params
     [{:name "utterance", :type "string"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::stop, :name "stop"}
    {:id ::pause, :name "pause", :since "29"}
    {:id ::resume, :name "resume", :since "29"}
    {:id ::is-speaking,
     :name "isSpeaking",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "speaking", :type "boolean"}]}}]}
    {:id ::get-voices,
     :name "getVoices",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "voices", :type "[array-of-tts.TtsVoices]"}]}}]}]})

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