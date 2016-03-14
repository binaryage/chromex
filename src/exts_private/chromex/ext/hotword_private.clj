(ns chromex.ext.hotword-private
  "The chrome.hotwordPrivate API allows extensions to access and
   mutate the preference for enabling hotword search. It also provides
   information on whether the hotword search is available. This API provides an
   event interface to transmit to the extension a signal that the preference fo
   hotword search has change.

   For an FYI, visit http://goo.gl/AyHbkH

     * available since Chrome 34"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-enabled
  "Sets the current enabled state of hotword search. True: enable hotword search. False: disable hotword search.

     |state| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([state] (gen-call :function ::set-enabled &form state)))

(defmacro get-status
  "Retrieves the current state of hotword search. The result is put into a StatusDetails object.

     |get-optional-fields| - If true, fills in fields tagged as optional in StatusDetails with valid values. These fields
                             are not valid by default since their current implementations may cause blocking operations.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([get-optional-fields] (gen-call :function ::get-status &form get-optional-fields))
  ([] `(get-status :omit)))

(defmacro get-localized-strings
  "Retrieves a dictionary mapping names to localized resource strings.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([] (gen-call :function ::get-localized-strings &form)))

(defmacro set-audio-logging-enabled
  "Sets the current enabled state of audio logging in the extension. True: logging enabled. False: no logging.

     |state| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([state] (gen-call :function ::set-audio-logging-enabled &form state)))

(defmacro set-hotword-always-on-search-enabled
  "Sets the current enabled state of hotword-always-on-search pref. True: enable hotword always on search. False: disable
   hotword always on search.

     |state| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([state] (gen-call :function ::set-hotword-always-on-search-enabled &form state)))

(defmacro set-hotword-session-state
  "Sets the current state of the browser-requested hotword session.

     |started| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([started] (gen-call :function ::set-hotword-session-state &form started)))

(defmacro notify-hotword-recognition
  "Notifies that a hotword has been recognized in the browser-requested hotword session.

     |type| - ?
     |log| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([type log] (gen-call :function ::notify-hotword-recognition &form type log))
  ([type] `(notify-hotword-recognition ~type :omit)))

(defmacro get-launch-state
  "Retrieves the state that the Hotword Audio Verification app was launched in. The result is put into a LaunchState object.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([] (gen-call :function ::get-launch-state &form)))

(defmacro start-training
  "Starts the speaker model training.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([] (gen-call :function ::start-training &form)))

(defmacro finalize-speaker-model
  "Finalizess the speaker model.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([] (gen-call :function ::finalize-speaker-model &form)))

(defmacro notify-speaker-model-saved
  "Notifies that the speaker model has been saved.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([] (gen-call :function ::notify-speaker-model-saved &form)))

(defmacro stop-training
  "Stops the speaker model training.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([] (gen-call :function ::stop-training &form)))

(defmacro set-audio-history-enabled
  "Sets the audio history opt-in state.

     |enabled| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([enabled] (gen-call :function ::set-audio-history-enabled &form enabled)))

(defmacro get-audio-history-enabled
  "Gets the audio history opt-in state.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?"
  ([] (gen-call :function ::get-audio-history-enabled &form)))

(defmacro speaker-model-exists-result
  "Sends the result of whether a speaker model exists to the browser.

     |exists| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is []."
  ([exists] (gen-call :function ::speaker-model-exists-result &form exists)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-enabled-changed-events
  "Fired when the hotword detector enabled state should be changed. This can be from various sources, e.g. a pref change or
   training a speaker model.

   Events will be put on the |channel| with signature [::on-enabled-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-enabled-changed &form channel args)))

(defmacro tap-on-hotword-session-requested-events
  "Fired when the browser wants to start a hotword session.

   Events will be put on the |channel| with signature [::on-hotword-session-requested []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-hotword-session-requested &form channel args)))

(defmacro tap-on-hotword-session-stopped-events
  "Fired when the browser wants to stop the requested hotword session.

   Events will be put on the |channel| with signature [::on-hotword-session-stopped []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-hotword-session-stopped &form channel args)))

(defmacro tap-on-finalize-speaker-model-events
  "Fired when the speaker model should be finalized.

   Events will be put on the |channel| with signature [::on-finalize-speaker-model []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-finalize-speaker-model &form channel args)))

(defmacro tap-on-speaker-model-saved-events
  "Fired when the speaker model has been saved.

   Events will be put on the |channel| with signature [::on-speaker-model-saved []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-speaker-model-saved &form channel args)))

(defmacro tap-on-hotword-triggered-events
  "Fired when a hotword has triggered.

   Events will be put on the |channel| with signature [::on-hotword-triggered []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-hotword-triggered &form channel args)))

(defmacro tap-on-delete-speaker-model-events
  "Fired when the speaker model should be deleted.

   Events will be put on the |channel| with signature [::on-delete-speaker-model []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-delete-speaker-model &form channel args)))

(defmacro tap-on-speaker-model-exists-events
  "Fired when the browser wants to find out whether the speaker model exists.

   Events will be put on the |channel| with signature [::on-speaker-model-exists []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-speaker-model-exists &form channel args)))

(defmacro tap-on-microphone-state-changed-events
  "Fired when the microphone state changes.

   Events will be put on the |channel| with signature [::on-microphone-state-changed [enabled]] where:

     |enabled| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-microphone-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.hotwordPrivate",
   :since "34",
   :functions
   [{:id ::set-enabled,
     :name "setEnabled",
     :callback? true,
     :params [{:name "state", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-status,
     :name "getStatus",
     :callback? true,
     :params
     [{:name "get-optional-fields", :optional? true, :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-localized-strings,
     :name "getLocalizedStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::set-audio-logging-enabled,
     :name "setAudioLoggingEnabled",
     :callback? true,
     :params [{:name "state", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-hotword-always-on-search-enabled,
     :name "setHotwordAlwaysOnSearchEnabled",
     :callback? true,
     :params [{:name "state", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-hotword-session-state,
     :name "setHotwordSessionState",
     :callback? true,
     :params [{:name "started", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::notify-hotword-recognition,
     :name "notifyHotwordRecognition",
     :callback? true,
     :params
     [{:name "type", :type "unknown-type"}
      {:name "log", :optional? true, :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-launch-state,
     :name "getLaunchState",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::start-training,
     :name "startTraining",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::finalize-speaker-model,
     :name "finalizeSpeakerModel",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::notify-speaker-model-saved,
     :name "notifySpeakerModelSaved",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::stop-training,
     :name "stopTraining",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::set-audio-history-enabled,
     :name "setAudioHistoryEnabled",
     :callback? true,
     :params
     [{:name "enabled", :type "boolean"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "hotwordPrivate.AudioHistoryState"}]}}]}
    {:id ::get-audio-history-enabled,
     :name "getAudioHistoryEnabled",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "hotwordPrivate.AudioHistoryState"}]}}]}
    {:id ::speaker-model-exists-result,
     :name "speakerModelExistsResult",
     :since "43",
     :callback? true,
     :params [{:name "exists", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-enabled-changed, :name "onEnabledChanged"}
    {:id ::on-hotword-session-requested, :name "onHotwordSessionRequested"}
    {:id ::on-hotword-session-stopped, :name "onHotwordSessionStopped"}
    {:id ::on-finalize-speaker-model, :name "onFinalizeSpeakerModel"}
    {:id ::on-speaker-model-saved, :name "onSpeakerModelSaved"}
    {:id ::on-hotword-triggered, :name "onHotwordTriggered"}
    {:id ::on-delete-speaker-model, :name "onDeleteSpeakerModel", :since "42"}
    {:id ::on-speaker-model-exists, :name "onSpeakerModelExists", :since "43"}
    {:id ::on-microphone-state-changed,
     :name "onMicrophoneStateChanged",
     :since "44",
     :params [{:name "enabled", :type "boolean"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))