(ns chromex.omnibox
  "The omnibox API allows you to register a keyword with Google Chrome's address bar, which is also known as the
   omnibox.
   
     * available since Chrome 9
     * https://developer.chrome.com/extensions/omnibox"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-default-suggestion
  "Sets the description and styling for the default suggestion. The default suggestion is the text that is displayed
   in the first suggestion row underneath the URL bar.
   
     |suggestion| - A partial SuggestResult object, without the 'content' parameter."
  [suggestion]
  (gen-call :function ::set-default-suggestion (meta &form) suggestion))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-input-started
  "User has started a keyword input session by typing the extension's keyword. This is guaranteed to be sent exactly
   once per input session, and before any onInputChanged events."
  [channel]
  (gen-call :event ::on-input-started (meta &form) channel))

(defmacro tap-on-input-changed
  "User has changed what is typed into the omnibox."
  [channel]
  (gen-call :event ::on-input-changed (meta &form) channel))

(defmacro tap-on-input-entered
  "User has accepted what is typed into the omnibox."
  [channel]
  (gen-call :event ::on-input-entered (meta &form) channel))

(defmacro tap-on-input-cancelled
  "User has ended the keyword input session without accepting the input."
  [channel]
  (gen-call :event ::on-input-cancelled (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.omnibox",
   :since "9",
   :functions
   [{:id ::set-default-suggestion,
     :name "setDefaultSuggestion",
     :params [{:name "suggestion", :type "object"}]}],
   :events
   [{:id ::on-input-started, :name "onInputStarted"}
    {:id ::on-input-changed,
     :name "onInputChanged",
     :params [{:name "text", :type "string"} {:name "suggest", :type :callback}]}
    {:id ::on-input-entered,
     :name "onInputEntered",
     :params [{:name "text", :type "string"} {:name "disposition", :type "omnibox.OnInputEnteredDisposition"}]}
    {:id ::on-input-cancelled, :name "onInputCancelled"}]})

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