(ns chromex.ext.language-settings-private
  "Use the chrome.languageSettingsPrivate API to get or change
   language and input method settings.

     * available since Chrome 62"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-language-list
  "Gets languages available for translate, spell checking, input and locale.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [languages] where:

     |languages| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-language-list &form)))

(defmacro enable-language
  "Enables a language, adding it to the Accept-Language list (used to decide which languages to translate, generate the
   Accept-Language header, etc.).

     |language-code| - ?"
  ([language-code] (gen-call :function ::enable-language &form language-code)))

(defmacro disable-language
  "Disables a language, removing it from the Accept-Language list.

     |language-code| - ?"
  ([language-code] (gen-call :function ::disable-language &form language-code)))

(defmacro set-enable-translation-for-language
  "Enables or disables translation for a given language.

     |language-code| - ?
     |enable| - ?"
  ([language-code enable] (gen-call :function ::set-enable-translation-for-language &form language-code enable)))

(defmacro get-spellcheck-dictionary-statuses
  "Gets the current status of the chosen spell check dictionaries.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-spellcheck-dictionary-statuses &form)))

(defmacro get-spellcheck-words
  "Gets the custom spell check words, in sorted order.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [words] where:

     |words| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-spellcheck-words &form)))

(defmacro add-spellcheck-word
  "Adds a word to the custom dictionary.

     |word| - ?"
  ([word] (gen-call :function ::add-spellcheck-word &form word)))

(defmacro remove-spellcheck-word
  "Removes a word from the custom dictionary.

     |word| - ?"
  ([word] (gen-call :function ::remove-spellcheck-word &form word)))

(defmacro get-translate-target-language
  "Gets the translate target language (in most cases, the display locale).

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [language-code] where:

     |language-code| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-translate-target-language &form)))

(defmacro get-input-method-lists
  "Gets all supported input methods, including third-party IMEs. Chrome OS only.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [lists] where:

     |lists| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-input-method-lists &form)))

(defmacro add-input-method
  "Adds the input method to the current user's list of enabled input methods, enabling the input method for the current user.
   Chrome OS only.

     |input-method-id| - ?"
  ([input-method-id] (gen-call :function ::add-input-method &form input-method-id)))

(defmacro remove-input-method
  "Removes the input method from the current user's list of enabled input methods, disabling the input method for the current
   user. Chrome OS only.

     |input-method-id| - ?"
  ([input-method-id] (gen-call :function ::remove-input-method &form input-method-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-spellcheck-dictionaries-changed-events
  "Called when the pref for the dictionaries used for spell checking changes or the status of one of the spell check
   dictionaries changes.

   Events will be put on the |channel| with signature [::on-spellcheck-dictionaries-changed [statuses]] where:

     |statuses| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-spellcheck-dictionaries-changed &form channel args)))

(defmacro tap-on-custom-dictionary-changed-events
  "Called when words are added to and/or removed from the custom spell check dictionary.

   Events will be put on the |channel| with signature [::on-custom-dictionary-changed [words-added words-removed]] where:

     |words-added| - ?
     |words-removed| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-custom-dictionary-changed &form channel args)))

(defmacro tap-on-input-method-added-events
  "Called when an input method is added.

   Events will be put on the |channel| with signature [::on-input-method-added [input-method-id]] where:

     |input-method-id| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-input-method-added &form channel args)))

(defmacro tap-on-input-method-removed-events
  "Called when an input method is removed.

   Events will be put on the |channel| with signature [::on-input-method-removed [input-method-id]] where:

     |input-method-id| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-input-method-removed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.language-settings-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.languageSettingsPrivate",
   :since "62",
   :functions
   [{:id ::get-language-list,
     :name "getLanguageList",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "languages", :type "[array-of-objects]"}]}}]}
    {:id ::enable-language, :name "enableLanguage", :params [{:name "language-code", :type "string"}]}
    {:id ::disable-language, :name "disableLanguage", :params [{:name "language-code", :type "string"}]}
    {:id ::set-enable-translation-for-language,
     :name "setEnableTranslationForLanguage",
     :since "master",
     :params [{:name "language-code", :type "string"} {:name "enable", :type "boolean"}]}
    {:id ::get-spellcheck-dictionary-statuses,
     :name "getSpellcheckDictionaryStatuses",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback
       {:params [{:name "status", :type "[array-of-languageSettingsPrivate.SpellcheckDictionaryStatuss]"}]}}]}
    {:id ::get-spellcheck-words,
     :name "getSpellcheckWords",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "words", :type "[array-of-strings]"}]}}]}
    {:id ::add-spellcheck-word, :name "addSpellcheckWord", :params [{:name "word", :type "string"}]}
    {:id ::remove-spellcheck-word, :name "removeSpellcheckWord", :params [{:name "word", :type "string"}]}
    {:id ::get-translate-target-language,
     :name "getTranslateTargetLanguage",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "language-code", :type "string"}]}}]}
    {:id ::get-input-method-lists,
     :name "getInputMethodLists",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "lists", :type "object"}]}}]}
    {:id ::add-input-method, :name "addInputMethod", :params [{:name "input-method-id", :type "string"}]}
    {:id ::remove-input-method, :name "removeInputMethod", :params [{:name "input-method-id", :type "string"}]}],
   :events
   [{:id ::on-spellcheck-dictionaries-changed,
     :name "onSpellcheckDictionariesChanged",
     :params [{:name "statuses", :type "[array-of-languageSettingsPrivate.SpellcheckDictionaryStatuss]"}]}
    {:id ::on-custom-dictionary-changed,
     :name "onCustomDictionaryChanged",
     :params [{:name "words-added", :type "[array-of-strings]"} {:name "words-removed", :type "[array-of-strings]"}]}
    {:id ::on-input-method-added, :name "onInputMethodAdded", :params [{:name "input-method-id", :type "string"}]}
    {:id ::on-input-method-removed, :name "onInputMethodRemoved", :params [{:name "input-method-id", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))