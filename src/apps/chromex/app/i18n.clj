(ns chromex.app.i18n
  "Use the chrome.i18n infrastructure to implement internationalization across your whole app or extension.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/i18n"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-accept-languages
  "Gets the accept-languages of the browser. This is different from the locale used by the browser; to get the locale, use
   'i18n.getUILanguage'.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [languages] where:
   
     |languages| - Array of LanguageCode
   
   See https://developer.chrome.com/extensions/i18n#method-getAcceptLanguages."
  ([#_callback] (gen-call :function ::get-accept-languages &form)))

(defmacro get-message
  "Gets the localized string for the specified message. If the message is missing, this method returns an empty string ('').
   If the format of the getMessage() call is wrong &mdash; for example, messageName is not a string or the substitutions array
   has more than 9 elements &mdash; this method returns undefined.
   
     |messageName| - The name of the message, as specified in the messages.json file.
     |substitutions| - Up to 9 substitution strings, if the message requires any.
   
   See https://developer.chrome.com/extensions/i18n#method-getMessage."
  ([message-name substitutions] (gen-call :function ::get-message &form message-name substitutions))
  ([message-name] `(get-message ~message-name :omit)))

(defmacro get-ui-language
  "Gets the browser UI language of the browser. This is different from 'i18n.getAcceptLanguages' which returns the preferred
   user languages.
   
   See https://developer.chrome.com/extensions/i18n#method-getUILanguage."
  ([] (gen-call :function ::get-ui-language &form)))

(defmacro detect-language
  "Detects the language of the provided text using CLD.
   
     |text| - User input string to be translated.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - LanguageDetectionResult object that holds detected langugae reliability and array of DetectedLanguage
   
   See https://developer.chrome.com/extensions/i18n#method-detectLanguage."
  ([text #_callback] (gen-call :function ::detect-language &form text)))

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
  {:namespace "chrome.i18n",
   :since "5",
   :functions
   [{:id ::get-accept-languages,
     :name "getAcceptLanguages",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "languages", :type "[array-of-i18n.LanguageCodes]"}]}}]}
    {:id ::get-message,
     :name "getMessage",
     :return-type "string",
     :params [{:name "message-name", :type "string"} {:name "substitutions", :optional? true, :type "any"}]}
    {:id ::get-ui-language, :name "getUILanguage", :since "35", :return-type "string"}
    {:id ::detect-language,
     :name "detectLanguage",
     :since "47",
     :callback? true,
     :params
     [{:name "text", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

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