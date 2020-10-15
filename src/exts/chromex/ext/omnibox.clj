(ns chromex.ext.omnibox
  "The omnibox API allows you to register a keyword with Google Chrome's address bar, which is also known as the omnibox.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/omnibox"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-default-suggestion
  "Sets the description and styling for the default suggestion. The default suggestion is the text that is displayed in the
   first suggestion row underneath the URL bar.

     |suggestion| - A partial SuggestResult object, without the 'content' parameter.

   https://developer.chrome.com/extensions/omnibox#method-setDefaultSuggestion."
  ([suggestion] (gen-call :function ::set-default-suggestion &form suggestion)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-input-started-events
  "User has started a keyword input session by typing the extension's keyword. This is guaranteed to be sent exactly once per
   input session, and before any onInputChanged events.

   Events will be put on the |channel| with signature [::on-input-started []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/omnibox#event-onInputStarted."
  ([channel & args] (apply gen-call :event ::on-input-started &form channel args)))

(defmacro tap-on-input-changed-events
  "User has changed what is typed into the omnibox.

   Events will be put on the |channel| with signature [::on-input-changed [text suggest]] where:

     |text| - https://developer.chrome.com/extensions/omnibox#property-onInputChanged-text.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/omnibox#event-onInputChanged."
  ([channel & args] (apply gen-call :event ::on-input-changed &form channel args)))

(defmacro tap-on-input-entered-events
  "User has accepted what is typed into the omnibox.

   Events will be put on the |channel| with signature [::on-input-entered [text disposition]] where:

     |text| - https://developer.chrome.com/extensions/omnibox#property-onInputEntered-text.
     |disposition| - https://developer.chrome.com/extensions/omnibox#property-onInputEntered-disposition.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/omnibox#event-onInputEntered."
  ([channel & args] (apply gen-call :event ::on-input-entered &form channel args)))

(defmacro tap-on-input-cancelled-events
  "User has ended the keyword input session without accepting the input.

   Events will be put on the |channel| with signature [::on-input-cancelled []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/omnibox#event-onInputCancelled."
  ([channel & args] (apply gen-call :event ::on-input-cancelled &form channel args)))

(defmacro tap-on-delete-suggestion-events
  "User has deleted a suggested result.

   Events will be put on the |channel| with signature [::on-delete-suggestion [text]] where:

     |text| - Text of the deleted suggestion.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/omnibox#event-onDeleteSuggestion."
  ([channel & args] (apply gen-call :event ::on-delete-suggestion &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.omnibox namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.omnibox",
   :since "38",
   :functions
   [{:id ::set-default-suggestion, :name "setDefaultSuggestion", :params [{:name "suggestion", :type "object"}]}],
   :events
   [{:id ::on-input-started, :name "onInputStarted"}
    {:id ::on-input-changed,
     :name "onInputChanged",
     :params [{:name "text", :type "string"} {:name "suggest", :type :callback}]}
    {:id ::on-input-entered,
     :name "onInputEntered",
     :params [{:name "text", :type "string"} {:name "disposition", :type "omnibox.OnInputEnteredDisposition"}]}
    {:id ::on-input-cancelled, :name "onInputCancelled"}
    {:id ::on-delete-suggestion, :name "onDeleteSuggestion", :since "63", :params [{:name "text", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))