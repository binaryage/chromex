(ns chromex.feedback-private
  "Use the chrome.feedbackPrivate API to provide Chrome [OS]
   feedback to the Google Feedback servers.
   
     * available since Chrome 29
     * https://developer.chrome.com/extensions/feedbackPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-user-email
  "Returns the email of the currently active or logged in user."
  ([#_callback] (gen-call :function ::get-user-email (meta &form))))

(defmacro get-system-information
  "Returns the system information dictionary."
  ([#_callback] (gen-call :function ::get-system-information (meta &form))))

(defmacro send-feedback
  "Sends a feedback report."
  ([feedback #_callback] (gen-call :function ::send-feedback (meta &form) feedback)))

(defmacro get-strings
  "Gets localized translated strings for feedback. It returns the strings as a dictionary mapping from string
   identifier to the translated string to use in the feedback app UI."
  ([#_callback] (gen-call :function ::get-strings (meta &form))))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-feedback-requested-events
  "Fired when the a user requests the launch of the feedback UI. We're using an event for this versus using the
   override API since we want to be invoked, but not showing a UI, so the feedback extension can take a screenshot of
   the user's desktop."
  ([channel] (gen-call :event ::on-feedback-requested (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

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
       :callback
       {:params [{:name "system-information", :type "[array-of-feedbackPrivate.SystemInformations]"}]}}]}
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
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}],
   :events
   [{:id ::on-feedback-requested,
     :name "onFeedbackRequested",
     :params [{:name "feedback", :type "feedbackPrivate.FeedbackInfo"}]}]})

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