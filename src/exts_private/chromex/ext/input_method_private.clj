(ns chromex.ext.input-method-private
  "  * available since Chrome 15"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-input-method-config
  "Gets configurations for input methods.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [config] where:

     |config| - The input method config object.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-input-method-config &form)))

(defmacro get-input-methods
  "Gets all whitelisted input methods.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [input-methods] where:

     |input-methods| - Whitelisted input method objects.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-input-methods &form)))

(defmacro get-current-input-method
  "Gets the current input method.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [input-method-id] where:

     |input-method-id| - Current input method.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-current-input-method &form)))

(defmacro set-current-input-method
  "Sets the current input method.

     |input-method-id| - The input method ID to be set as current input method.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([input-method-id] (gen-call :function ::set-current-input-method &form input-method-id)))

(defmacro fetch-all-dictionary-words
  "Fetches a list of all the words currently in the dictionary.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [words] where:

     |words| - List of dictionary words.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::fetch-all-dictionary-words &form)))

(defmacro add-word-to-dictionary
  "Adds a single word to be stored in the dictionary.

     |word| - A new word to add to the dictionary.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([word] (gen-call :function ::add-word-to-dictionary &form word)))

(defmacro get-encrypt-sync-enabled
  "Gets whether the encrypt sync is enabled.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [enabled] where:

     |enabled| - The result of whether enabled.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-encrypt-sync-enabled &form)))

(defmacro set-xkb-layout
  "Sets the XKB layout for the given input method.

     |xkb-name| - The XKB layout name.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([xkb-name] (gen-call :function ::set-xkb-layout &form xkb-name)))

(defmacro notify-ime-menu-item-activated
  "Fires the input.ime.onMenuItemActivated event.

     |engine-id| - ID of the engine to use.
     |name| - Name of the MenuItem which was activated"
  ([engine-id name] (gen-call :function ::notify-ime-menu-item-activated &form engine-id name)))

(defmacro show-input-view
  "Shows the input view window. If the input view window is already shown, this function will do nothing.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::show-input-view &form)))

(defmacro open-options-page
  "Opens the options page for the input method extension. If the input method does not have options, this function will do
   nothing.

     |input-method-id| - ID of the input method to open options for."
  ([input-method-id] (gen-call :function ::open-options-page &form input-method-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when the input method is changed.

   Events will be put on the |channel| with signature [::on-changed [new-input-method-id]] where:

     |new-input-method-id| - New input method which is being used.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

(defmacro tap-on-composition-bounds-changed-events
  "Fired when the composition bounds or cursor bounds are changed.

   Events will be put on the |channel| with signature [::on-composition-bounds-changed [first-bounds bounds-list]] where:

     |first-bounds| - The bounds information for the first character in composition.
     |bounds-list| - List of bounds information.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-composition-bounds-changed &form channel args)))

(defmacro tap-on-dictionary-loaded-events
  "Fired when the custom spelling dictionary is loaded.

   Events will be put on the |channel| with signature [::on-dictionary-loaded []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-dictionary-loaded &form channel args)))

(defmacro tap-on-dictionary-changed-events
  "Fired when words are added or removed from the custom spelling dictionary.

   Events will be put on the |channel| with signature [::on-dictionary-changed [added removed]] where:

     |added| - List of added words.
     |removed| - List of removed words.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-dictionary-changed &form channel args)))

(defmacro tap-on-ime-menu-activation-changed-events
  "Fired when the IME menu is activated or deactivated.

   Events will be put on the |channel| with signature [::on-ime-menu-activation-changed [activation]] where:

     |activation| - Whether the IME menu is currently active.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-ime-menu-activation-changed &form channel args)))

(defmacro tap-on-ime-menu-list-changed-events
  "Fired when the input method or the list of active input method IDs is changed.

   Events will be put on the |channel| with signature [::on-ime-menu-list-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-ime-menu-list-changed &form channel args)))

(defmacro tap-on-ime-menu-items-changed-events
  "Fired when the input.ime.setMenuItems or input.ime.updateMenuItems API is called.

   Events will be put on the |channel| with signature [::on-ime-menu-items-changed [engine-id items]] where:

     |engine-id| - ID of the engine to use
     |items| - MenuItems to add or update.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-ime-menu-items-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.input-method-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.inputMethodPrivate",
   :since "15",
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
     :since "51",
     :params [{:name "engine-id", :type "string"} {:name "name", :type "string"}]}
    {:id ::show-input-view,
     :name "showInputView",
     :since "51",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::open-options-page,
     :name "openOptionsPage",
     :since "52",
     :params [{:name "input-method-id", :type "string"}]}],
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
     :since "51",
     :params [{:name "engine-id", :type "string"} {:name "items", :type "[array-of-inputMethodPrivate.MenuItems]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))