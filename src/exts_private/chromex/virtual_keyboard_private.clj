(ns chromex.virtual-keyboard-private
  "  * available since Chrome 31
     * https://developer.chrome.com/extensions/virtualKeyboardPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro insert-text
  "Inserts text into the currently focused text field.
   
     |text| - The text that will be inserted.
     |callback| - Called when the insertion is completed."
  [text #_callback]
  (gen-call :function ::insert-text (meta &form) text))

(defmacro send-key-event
  "Sends a fabricated key event to the focused input field.
   
     |callback| - Called after processing the event."
  [key-event #_callback]
  (gen-call :function ::send-key-event (meta &form) key-event))

(defmacro hide-keyboard
  "Hides the virtual keyboard.
   
     |callback| - Called when the keyboard is hidden."
  [#_callback]
  (gen-call :function ::hide-keyboard (meta &form)))

(defmacro set-hotrod-keyboard
  "Sets the state of the hotrod virtual keyboard. This API should only be used by hotrod."
  [enable]
  (gen-call :function ::set-hotrod-keyboard (meta &form) enable))

(defmacro lock-keyboard
  "Sets the lock state of the virtual keyboard. A locked keyboard remains visible even after a text area loses input
   focus."
  [lock]
  (gen-call :function ::lock-keyboard (meta &form) lock))

(defmacro keyboard-loaded
  "Inform the system that the keyboard has loaded.
   
     |callback| - Called when load acknowledgement is complete."
  [#_callback]
  (gen-call :function ::keyboard-loaded (meta &form)))

(defmacro get-keyboard-config
  "Gets the virtual keyboard configuration.
   
     |callback| - Called when querying virtual keyboard configuration is complete."
  [#_callback]
  (gen-call :function ::get-keyboard-config (meta &form)))

(defmacro open-settings
  "Opens chrome://settings/languages page."
  []
  (gen-call :function ::open-settings (meta &form)))

(defmacro set-mode
  "Sets the virtual keyboard mode.
   
     |mode| - The value of the virtual keyboard mode to set to."
  [mode]
  (gen-call :function ::set-mode (meta &form) mode))

(defmacro set-keyboard-state
  "Requests the virtual keyboard to change state.
   
     |state| - The value of the virtual keyboard state to change to."
  [state]
  (gen-call :function ::set-keyboard-state (meta &form) state))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-text-input-box-focused-events
  "This event is sent when focus enters a text input box."
  [channel]
  (gen-call :event ::on-text-input-box-focused (meta &form) channel))

(defmacro tap-on-bounds-changed-events
  "This event is sent when virtual keyboard bounds changed and overscroll/resize is enabled."
  [channel]
  (gen-call :event ::on-bounds-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

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
    {:id ::set-hotrod-keyboard,
     :name "setHotrodKeyboard",
     :since "46",
     :params [{:name "enable", :type "boolean"}]}
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
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "config", :type "object"}]}}]}
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
   [{:id ::on-text-input-box-focused,
     :name "onTextInputBoxFocused",
     :params [{:name "context", :type "object"}]}
    {:id ::on-bounds-changed,
     :name "onBoundsChanged",
     :since "44",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}]})

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