(ns chromex.ext.feedback-private
  "Use the chrome.feedbackPrivate API to provide Chrome [OS]
   feedback to the Google Feedback servers.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-user-email
  "Returns the email of the currently active or logged in user.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [email] where:

     |email| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-user-email &form)))

(defmacro get-system-information
  "Returns the system information dictionary.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [system-information] where:

     |system-information| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-system-information &form)))

(defmacro send-feedback
  "Sends a feedback report.

     |feedback| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status type] where:

     |status| - ?
     |type| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([feedback] (gen-call :function ::send-feedback &form feedback)))

(defmacro get-strings
  "Gets localized translated strings for feedback. It returns the strings as a dictionary mapping from string identifier to
   the translated string to use in the feedback app UI.

     |flow| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([flow] (gen-call :function ::get-strings &form flow)))

(defmacro read-log-source
  "Reads from a log source indicated by source. If incremental is false:    Returns the entire contents of the log file.
   Returns readerId value of 0 to callback.  If incremental is true, and no readerId is provided:    Returns the entire
   contents of the log file.   Starts tracking the file read handle, which is returned as a       nonzero readerId value in
   the callback.          If can't create a new file handle, returns readerId       value of 0 in the callback.         If
   incremental is true, and a valid non-zero readerId is provided:    Returns new lines written to the file since the last
   time this       function was called for the same file and readerId.          Returns the same readerId value to the
   callback.

     |params| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([params] (gen-call :function ::read-log-source &form params)))

(defmacro login-feedback-complete
  "Invoked when the extension is complete during sending feedback from the login page. This is then used to know we can unload
   the feedback extension from the login profile."
  ([] (gen-call :function ::login-feedback-complete &form)))

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
  "Taps all valid non-deprecated events in chromex.ext.feedback-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.feedbackPrivate",
   :since "38",
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
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "unknown-type"} {:name "type", :type "unknown-type"}]}}]}
    {:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params
     [{:name "flow", :since "60", :type "feedbackPrivate.FeedbackFlow"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::read-log-source,
     :name "readLogSource",
     :since "61",
     :callback? true,
     :params
     [{:name "params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::login-feedback-complete, :name "loginFeedbackComplete", :since "75"}],
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