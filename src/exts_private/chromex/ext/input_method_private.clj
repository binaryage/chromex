(ns chromex.ext.input-method-private
  "  * available since Chrome 14
     * https://developer.chrome.com/extensions/inputMethodPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-input-method-config
  "Gets configurations for input methods.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [config] where:
   
     |config| - The input method config object.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-getInputMethodConfig."
  ([#_callback] (gen-call :function ::get-input-method-config &form)))

(defmacro get-input-methods
  "Gets all whitelisted input methods.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [inputMethods] where:
   
     |inputMethods| - Whitelisted input method objects.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-getInputMethods."
  ([#_callback] (gen-call :function ::get-input-methods &form)))

(defmacro get-current-input-method
  "Gets the current input method.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [inputMethodId] where:
   
     |inputMethodId| - Current input method.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-getCurrentInputMethod."
  ([#_callback] (gen-call :function ::get-current-input-method &form)))

(defmacro set-current-input-method
  "Sets the current input method.
   
     |inputMethodId| - The input method ID to be set as current input method.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-setCurrentInputMethod."
  ([input-method-id #_callback] (gen-call :function ::set-current-input-method &form input-method-id)))

(defmacro fetch-all-dictionary-words
  "Fetches a list of all the words currently in the dictionary.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [words] where:
   
     |words| - List of dictionary words.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-fetchAllDictionaryWords."
  ([#_callback] (gen-call :function ::fetch-all-dictionary-words &form)))

(defmacro add-word-to-dictionary
  "Adds a single word to be stored in the dictionary.
   
     |word| - A new word to add to the dictionary.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-addWordToDictionary."
  ([word #_callback] (gen-call :function ::add-word-to-dictionary &form word)))

(defmacro get-encrypt-sync-enabled
  "Gets whether the encrypt sync is enabled.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [enabled] where:
   
     |enabled| - The result of whether enabled.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-getEncryptSyncEnabled."
  ([#_callback] (gen-call :function ::get-encrypt-sync-enabled &form)))

(defmacro set-xkb-layout
  "Sets the XKB layout for the given input method.
   
     |xkb_name| - The XKB layout name.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-setXkbLayout."
  ([xkb-name #_callback] (gen-call :function ::set-xkb-layout &form xkb-name)))

(defmacro notify-ime-menu-item-activated
  "Fires the input.ime.onMenuItemActivated event.
   
     |engineID| - ID of the engine to use.
     |name| - Name of the MenuItem which was activated
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#method-notifyImeMenuItemActivated."
  ([engine-id name] (gen-call :function ::notify-ime-menu-item-activated &form engine-id name)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when the input method is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

(defmacro tap-on-composition-bounds-changed-events
  "Fired when the composition bounds or cursor bounds are changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onCompositionBoundsChanged."
  ([channel & args] (apply gen-call :event ::on-composition-bounds-changed &form channel args)))

(defmacro tap-on-dictionary-loaded-events
  "Fired when the custom spelling dictionary is loaded.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onDictionaryLoaded."
  ([channel & args] (apply gen-call :event ::on-dictionary-loaded &form channel args)))

(defmacro tap-on-dictionary-changed-events
  "Fired when words are added or removed from the custom spelling dictionary.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onDictionaryChanged."
  ([channel & args] (apply gen-call :event ::on-dictionary-changed &form channel args)))

(defmacro tap-on-ime-menu-activation-changed-events
  "Fired when the IME menu is activated or deactivated.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onImeMenuActivationChanged."
  ([channel & args] (apply gen-call :event ::on-ime-menu-activation-changed &form channel args)))

(defmacro tap-on-ime-menu-list-changed-events
  "Fired when the input method or the list of active input method IDs is changed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onImeMenuListChanged."
  ([channel & args] (apply gen-call :event ::on-ime-menu-list-changed &form channel args)))

(defmacro tap-on-ime-menu-items-changed-events
  "Fired when the input.ime.setMenuItems or input.ime.updateMenuItems API is called.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/inputMethodPrivate#event-onImeMenuItemsChanged."
  ([channel & args] (apply gen-call :event ::on-ime-menu-items-changed &form channel args)))

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
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "enabled", :type "boolean"}]}}]}
    {:id ::set-xkb-layout,
     :name "setXkbLayout",
     :since "49",
     :callback? true,
     :params [{:name "xkb-name", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::notify-ime-menu-item-activated,
     :name "notifyImeMenuItemActivated",
     :since "master",
     :params [{:name "engine-id", :type "string"} {:name "name", :type "string"}]}],
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
     :params [{:name "added", :type "[array-of-strings]"} {:name "removed", :type "[array-of-strings]"}]}
    {:id ::on-ime-menu-activation-changed,
     :name "onImeMenuActivationChanged",
     :since "50",
     :params [{:name "activation", :type "boolean"}]}
    {:id ::on-ime-menu-list-changed, :name "onImeMenuListChanged", :since "50"}
    {:id ::on-ime-menu-items-changed,
     :name "onImeMenuItemsChanged",
     :since "master",
     :params [{:name "engine-id", :type "string"} {:name "items", :type "[array-of-inputMethodPrivate.MenuItems]"}]}]})

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