(ns chromex.app.feedback-private
  "Use the chrome.feedbackPrivate API to provide Chrome [OS]
   feedback to the Google Feedback servers.

     * available since Chrome 29"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-user-email
  "Returns the email of the currently active or logged in user.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [email] where:

     |email| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-user-email &form)))

(defmacro get-system-information
  "Returns the system information dictionary.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [system-information] where:

     |system-information| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-system-information &form)))

(defmacro send-feedback
  "Sends a feedback report.

     |feedback| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([feedback] (gen-call :function ::send-feedback &form feedback)))

(defmacro get-strings
  "Gets localized translated strings for feedback. It returns the strings as a dictionary mapping from string identifier to
   the translated string to use in the feedback app UI.

     |flow| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([flow] (gen-call :function ::get-strings &form flow)))

(defmacro log-srt-prompt-result
  "Logs whether the user accepted a prompt to try the Software Removal Tool.

     |result| - ?"
  ([result] (gen-call :function ::log-srt-prompt-result &form result)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-feedback-requested-events
  "Fired when the a user requests the launch of the feedback UI. We're using an event for this versus using the override API
   since we want to be invoked, but not showing a UI, so the feedback extension can take a screenshot of the user's desktop.

   Events will be put on the |channel| with signature [::on-feedback-requested [feedback]] where:

     |feedback| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-feedback-requested &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.feedback-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.feedbackPrivate",
   :since "29",
   :functions
   [{:id ::get-user-email,
     :name "getUserEmail",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "email", :type "string"}]}}]}
    {:id ::get-system-information,
     :name "getSystemInformation",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "system-information", :type "[array-of-feedbackPrivate.SystemInformations]"}]}}]}
    {:id ::send-feedback,
     :name "sendFeedback",
     :callback? true,
     :params
     [{:name "feedback", :type "feedbackPrivate.FeedbackInfo"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "unknown-type"}]}}]}
    {:id ::get-strings,
     :name "getStrings",
     :since "31",
     :callback? true,
     :params
     [{:name "flow", :type "feedbackPrivate.FeedbackFlow"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::log-srt-prompt-result,
     :name "logSrtPromptResult",
     :since "52",
     :params [{:name "result", :type "unknown-type"}]}],
   :events
   [{:id ::on-feedback-requested,
     :name "onFeedbackRequested",
     :params [{:name "feedback", :type "feedbackPrivate.FeedbackInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))