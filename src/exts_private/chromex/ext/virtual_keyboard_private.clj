(ns chromex.ext.virtual-keyboard-private
  "  * available since Chrome 31"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro insert-text
  "Inserts text into the currently focused text field.

     |text| - The text that will be inserted.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([text] (gen-call :function ::insert-text &form text)))

(defmacro send-key-event
  "Sends a fabricated key event to the focused input field.

     |key-event| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([key-event] (gen-call :function ::send-key-event &form key-event)))

(defmacro hide-keyboard
  "Hides the virtual keyboard.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::hide-keyboard &form)))

(defmacro set-hotrod-keyboard
  "Sets the state of the hotrod virtual keyboard. This API should only be used by hotrod.

     |enable| - ?"
  ([enable] (gen-call :function ::set-hotrod-keyboard &form enable)))

(defmacro lock-keyboard
  "Sets the lock state of the virtual keyboard. A locked keyboard remains visible even after a text area loses input focus.

     |lock| - ?"
  ([lock] (gen-call :function ::lock-keyboard &form lock)))

(defmacro keyboard-loaded
  "Inform the system that the keyboard has loaded.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::keyboard-loaded &form)))

(defmacro get-keyboard-config
  "Gets the virtual keyboard configuration.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [config] where:

     |config| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-keyboard-config &form)))

(defmacro open-settings
  "Opens chrome://settings/languages page."
  ([] (gen-call :function ::open-settings &form)))

(defmacro set-mode
  "Sets the virtual keyboard mode.

     |mode| - The value of the virtual keyboard mode to set to."
  ([mode] (gen-call :function ::set-mode &form mode)))

(defmacro set-keyboard-state
  "Requests the virtual keyboard to change state.

     |state| - The value of the virtual keyboard state to change to."
  ([state] (gen-call :function ::set-keyboard-state &form state)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-text-input-box-focused-events
  "This event is sent when focus enters a text input box.

   Events will be put on the |channel| with signature [::on-text-input-box-focused [context]] where:

     |context| - Describes the text input box that has acquired focus. Note only the type of text input box is passed. This
                 API is intended to be used by non-ime virtual keyboard only. Normal ime virtual keyboard should use
                 chrome.input.ime.onFocus to get the more detailed InputContext.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-text-input-box-focused &form channel args)))

(defmacro tap-on-bounds-changed-events
  "This event is sent when virtual keyboard bounds changed and overscroll/resize is enabled.

   Events will be put on the |channel| with signature [::on-bounds-changed [bounds]] where:

     |bounds| - The virtual keyboard bounds

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-bounds-changed &form channel args)))

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
  {:namespace "chrome.virtualKeyboardPrivate",
   :since "31",
   :functions
   [{:id ::insert-text,
     :name "insertText",
     :callback? true,
     :params [{:name "text", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::send-key-event,
     :name "sendKeyEvent",
     :callback? true,
     :params
     [{:name "key-event", :type "virtualKeyboardPrivate.VirtualKeyboardEvent"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::hide-keyboard,
     :name "hideKeyboard",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::set-hotrod-keyboard, :name "setHotrodKeyboard", :since "46", :params [{:name "enable", :type "boolean"}]}
    {:id ::lock-keyboard, :name "lockKeyboard", :since "33", :params [{:name "lock", :type "boolean"}]}
    {:id ::keyboard-loaded,
     :name "keyboardLoaded",
     :since "32",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-keyboard-config,
     :name "getKeyboardConfig",
     :since "34",
     :callback? true,
     :params
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "config", :type "object"}]}}]}
    {:id ::open-settings, :name "openSettings", :since "37"}
    {:id ::set-mode,
     :name "setMode",
     :since "43",
     :params [{:name "mode", :type "virtualKeyboardPrivate.KeyboardMode"}]}
    {:id ::set-keyboard-state,
     :name "setKeyboardState",
     :since "46",
     :params [{:name "state", :type "virtualKeyboardPrivate.KeyboardState"}]}],
   :events
   [{:id ::on-text-input-box-focused, :name "onTextInputBoxFocused", :params [{:name "context", :type "object"}]}
    {:id ::on-bounds-changed,
     :name "onBoundsChanged",
     :since "44",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}]})

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