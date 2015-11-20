(ns chromex.input-method-private
  "  * available since Chrome 14
     * https://developer.chrome.com/extensions/inputMethodPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-input-method-config
  "Gets configurations for input methods.
   
     |callback| - Callback which is called with the config object."
  [#_callback]
  (gen-call :function ::get-input-method-config (meta &form)))

(defmacro get-input-methods
  "Gets all whitelisted input methods.
   
     |callback| - Callback which is called with the input method objects."
  [#_callback]
  (gen-call :function ::get-input-methods (meta &form)))

(defmacro get-current-input-method
  "Gets the current input method.
   
     |callback| - Callback which is called with the current input method."
  [#_callback]
  (gen-call :function ::get-current-input-method (meta &form)))

(defmacro set-current-input-method
  "Sets the current input method.
   
     |inputMethodId| - The input method ID to be set as current input method.
     |callback| - Callback which is called once the current input method is set. If unsuccessful 'runtime.lastError'
                  is set."
  [input-method-id #_callback]
  (gen-call :function ::set-current-input-method (meta &form) input-method-id))

(defmacro fetch-all-dictionary-words
  "Fetches a list of all the words currently in the dictionary.
   
     |callback| - Callback which is called once the list of dictionary words are ready."
  [#_callback]
  (gen-call :function ::fetch-all-dictionary-words (meta &form)))

(defmacro add-word-to-dictionary
  "Adds a single word to be stored in the dictionary.
   
     |word| - A new word to add to the dictionary.
     |callback| - Callback which is called once the word is added. If unsuccessful 'runtime.lastError' is set."
  [word #_callback]
  (gen-call :function ::add-word-to-dictionary (meta &form) word))

(defmacro get-encrypt-sync-enabled
  "Gets whether the encrypt sync is enabled.
   
     |callback| - Callback which is called to provide the result."
  [#_callback]
  (gen-call :function ::get-encrypt-sync-enabled (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-changed
  "Fired when the input method is changed."
  [channel]
  (gen-call :event ::on-changed (meta &form) channel))

(defmacro tap-on-composition-bounds-changed
  "Fired when the composition bounds or cursor bounds are changed."
  [channel]
  (gen-call :event ::on-composition-bounds-changed (meta &form) channel))

(defmacro tap-on-dictionary-loaded
  "Fired when the custom spelling dictionary is loaded."
  [channel]
  (gen-call :event ::on-dictionary-loaded (meta &form) channel))

(defmacro tap-on-dictionary-changed
  "Fired when words are added or removed from the custom spelling dictionary."
  [channel]
  (gen-call :event ::on-dictionary-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.inputMethodPrivate",
   :since "14",
   :functions
   [{:id ::get-input-method-config,
     :name "getInputMethodConfig",
     :since "40",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "config", :type "object"}]}}]}
    {:id ::get-input-methods,
     :name "getInputMethods",
     :since "37",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "input-methods", :type "[array-of-objects]"}]}}]}
    {:id ::get-current-input-method,
     :name "getCurrentInputMethod",
     :since "37",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "input-method-id", :type "string"}]}}]}
    {:id ::set-current-input-method,
     :name "setCurrentInputMethod",
     :since "37",
     :callback? true,
     :params [{:name "input-method-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::fetch-all-dictionary-words,
     :name "fetchAllDictionaryWords",
     :since "43",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "words", :type "[array-of-strings]"}]}}]}
    {:id ::add-word-to-dictionary,
     :name "addWordToDictionary",
     :since "43",
     :callback? true,
     :params [{:name "word", :type "string"} {:name "callback", :type :callback}]}
    {:id ::get-encrypt-sync-enabled,
     :name "getEncryptSyncEnabled",
     :since "45",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "enabled", :type "boolean"}]}}]}],
   :events
   [{:id ::on-changed, :name "onChanged", :params [{:name "new-input-method-id", :type "string"}]}
    {:id ::on-composition-bounds-changed,
     :name "onCompositionBoundsChanged",
     :since "40",
     :params [{:name "first-bounds", :type "object"} {:name "bounds-list", :type "[array-of-objects]"}]}
    {:id ::on-dictionary-loaded, :name "onDictionaryLoaded", :since "43"}
    {:id ::on-dictionary-changed,
     :name "onDictionaryChanged",
     :since "43",
     :params [{:name "added", :type "[array-of-strings]"} {:name "removed", :type "[array-of-strings]"}]}]})

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