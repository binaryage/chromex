(ns chromex.ext.input-method-private
  "  * available since Chrome 14
     * https://developer.chrome.com/extensions/inputMethodPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-input-method-config
  "Gets configurations for input methods.
   
     |callback| - Callback which is called with the config object.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-input-method-config &form)))

(defmacro get-input-methods
  "Gets all whitelisted input methods.
   
     |callback| - Callback which is called with the input method objects.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-input-methods &form)))

(defmacro get-current-input-method
  "Gets the current input method.
   
     |callback| - Callback which is called with the current input method.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-current-input-method &form)))

(defmacro set-current-input-method
  "Sets the current input method.
   
     |inputMethodId| - The input method ID to be set as current input method.
     |callback| - Callback which is called once the current input method is set. If unsuccessful 'runtime.lastError' is set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([input-method-id #_callback] (gen-call :function ::set-current-input-method &form input-method-id)))

(defmacro fetch-all-dictionary-words
  "Fetches a list of all the words currently in the dictionary.
   
     |callback| - Callback which is called once the list of dictionary words are ready.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::fetch-all-dictionary-words &form)))

(defmacro add-word-to-dictionary
  "Adds a single word to be stored in the dictionary.
   
     |word| - A new word to add to the dictionary.
     |callback| - Callback which is called once the word is added. If unsuccessful 'runtime.lastError' is set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([word #_callback] (gen-call :function ::add-word-to-dictionary &form word)))

(defmacro get-encrypt-sync-enabled
  "Gets whether the encrypt sync is enabled.
   
     |callback| - Callback which is called to provide the result.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-encrypt-sync-enabled &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when the input method is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))
(defmacro tap-on-composition-bounds-changed-events
  "Fired when the composition bounds or cursor bounds are changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-composition-bounds-changed &form channel args)))
(defmacro tap-on-dictionary-loaded-events
  "Fired when the custom spelling dictionary is loaded.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-dictionary-loaded &form channel args)))
(defmacro tap-on-dictionary-changed-events
  "Fired when words are added or removed from the custom spelling dictionary.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-dictionary-changed &form channel args)))

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
     [{:name "callback", :type :callback, :callback {:params [{:name "input-methods", :type "[array-of-objects]"}]}}]}
    {:id ::get-current-input-method,
     :name "getCurrentInputMethod",
     :since "37",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "input-method-id", :type "string"}]}}]}
    {:id ::set-current-input-method,
     :name "setCurrentInputMethod",
     :since "37",
     :callback? true,
     :params [{:name "input-method-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::fetch-all-dictionary-words,
     :name "fetchAllDictionaryWords",
     :since "43",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "words", :type "[array-of-strings]"}]}}]}
    {:id ::add-word-to-dictionary,
     :name "addWordToDictionary",
     :since "43",
     :callback? true,
     :params [{:name "word", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-encrypt-sync-enabled,
     :name "getEncryptSyncEnabled",
     :since "45",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "enabled", :type "boolean"}]}}]}],
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