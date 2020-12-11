(ns chromex.ext.virtual-keyboard-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro insert-text
  "Inserts text into the currently focused text field.

     |text| - The text that will be inserted.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([text] (gen-call :function ::insert-text &form text)))

(defmacro send-key-event
  "Sends a fabricated key event to the focused input field.

     |key-event| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([key-event] (gen-call :function ::send-key-event &form key-event)))

(defmacro hide-keyboard
  "Hides the virtual keyboard.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::keyboard-loaded &form)))

(defmacro get-keyboard-config
  "Gets the virtual keyboard configuration.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [config] where:

     |config| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-keyboard-config &form)))

(defmacro open-settings
  "Opens chrome://os-settings/osLanguages page."
  ([] (gen-call :function ::open-settings &form)))

(defmacro open-suggestion-settings
  "Opens chrome://os-settings/osLanguages/smartInputs page."
  ([] (gen-call :function ::open-suggestion-settings &form)))

(defmacro set-container-behavior
  "Sets the virtual keyboard container behavior

     |options| - Optional parameters for new container behavior.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - Whether the container mode changed successfully

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::set-container-behavior &form options)))

(defmacro set-draggable-area
  "Sets the virtual keyboard draggable area bounds.

     |bounds| - The value of draggable rect area of floating keyboard."
  ([bounds] (gen-call :function ::set-draggable-area &form bounds)))

(defmacro set-keyboard-state
  "Requests the virtual keyboard to change state.

     |state| - The value of the virtual keyboard state to change to."
  ([state] (gen-call :function ::set-keyboard-state &form state)))

(defmacro set-occluded-bounds
  "Sets the areas on the screen that are blocked by the virtual keyboard.

     |bounds-list| - List of rectangles representing regions occluded by the keyboard."
  ([bounds-list] (gen-call :function ::set-occluded-bounds &form bounds-list)))

(defmacro set-hit-test-bounds
  "Sets the areas on the keyboard window where events are handled. Any event outside of these areas are passed on to the
   window behind it.

     |bounds-list| - List of rectangles representing regions where events targeting the keyboard should be handled."
  ([bounds-list] (gen-call :function ::set-hit-test-bounds &form bounds-list)))

(defmacro set-area-to-remain-on-screen
  "Sets the area of the keyboard window that should not move off screen. Any area outside of this can be moved off the user's
   screen.

     |bounds| - The bounds of the area inside the keyboard window, relative to the window origin, that should not be moved
                off screen. Any area outside of this bounds can be moved off screen."
  ([bounds] (gen-call :function ::set-area-to-remain-on-screen &form bounds)))

(defmacro set-window-bounds-in-screen
  "Sets the bounds of the keyboard window in screen coordinates.

     |bounds| - A rectangle defining the new bounds of the window in screen coordinates."
  ([bounds] (gen-call :function ::set-window-bounds-in-screen &form bounds)))

(defmacro get-clipboard-history
  "Get the clipboard history

     |options| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [clipboard-history] where:

     |clipboard-history| - List of clipboard items corresponding to the requested items (or everything if 'itemIds' was not
                           specified).

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::get-clipboard-history &form options)))

(defmacro paste-clipboard-item
  "Pastes a clipboard item from the clipboard history.

     |item-id| - The unique id which identifies this clipboard item."
  ([item-id] (gen-call :function ::paste-clipboard-item &form item-id)))

(defmacro delete-clipboard-item
  "Deletes a clipboard item from the clipboard history.

     |item-id| - The unique id which identifies this clipboard item."
  ([item-id] (gen-call :function ::delete-clipboard-item &form item-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-bounds-changed-events
  "This event is sent when virtual keyboard bounds changed and overscroll/resize is enabled.

   Events will be put on the |channel| with signature [::on-bounds-changed [bounds]] where:

     |bounds| - The virtual keyboard bounds

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-bounds-changed &form channel args)))

(defmacro tap-on-keyboard-closed-events
  "Fired when the virtual keyboard window has been closed. For example, this can happen when turning off on-screen keyboard or
   exiting tablet mode.

   Events will be put on the |channel| with signature [::on-keyboard-closed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-keyboard-closed &form channel args)))

(defmacro tap-on-keyboard-config-changed-events
  "Fired when a configuration for virtual keyboard IME has changed, e.g. auto complete disabled.

   Events will be put on the |channel| with signature [::on-keyboard-config-changed [config]] where:

     |config| - The virtual keyboard config

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-keyboard-config-changed &form channel args)))

(defmacro tap-on-clipboard-history-changed-events
  "Fired when the list of items in the clipboard history changes.

   Events will be put on the |channel| with signature [::on-clipboard-history-changed [item-ids]] where:

     |item-ids| - A list of ids for all current items in the clipboard history.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-clipboard-history-changed &form channel args)))

(defmacro tap-on-clipboard-item-updated-events
  "Fired when the data in a specific clipboard item is updated (mainly used for sending updated rendered html image).

   Events will be put on the |channel| with signature [::on-clipboard-item-updated [clipboard-history-item]] where:

     |clipboard-history-item| - An existing clipboard history item with changed data

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-clipboard-item-updated &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.virtual-keyboard-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.virtualKeyboardPrivate",
   :since "38",
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
    {:id ::lock-keyboard, :name "lockKeyboard", :params [{:name "lock", :type "boolean"}]}
    {:id ::keyboard-loaded,
     :name "keyboardLoaded",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-keyboard-config,
     :name "getKeyboardConfig",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "config", :type "virtualKeyboardPrivate.KeyboardConfig"}]}}]}
    {:id ::open-settings, :name "openSettings"}
    {:id ::open-suggestion-settings, :name "openSuggestionSettings", :since "future"}
    {:id ::set-container-behavior,
     :name "setContainerBehavior",
     :since "67",
     :callback? true,
     :params
     [{:name "options", :type "virtualKeyboardPrivate.ContainerBehaviorOptions"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::set-draggable-area,
     :name "setDraggableArea",
     :since "64",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}
    {:id ::set-keyboard-state,
     :name "setKeyboardState",
     :since "46",
     :params [{:name "state", :type "virtualKeyboardPrivate.KeyboardState"}]}
    {:id ::set-occluded-bounds,
     :name "setOccludedBounds",
     :since "69",
     :params [{:name "bounds-list", :type "[array-of-virtualKeyboardPrivate.Boundss]"}]}
    {:id ::set-hit-test-bounds,
     :name "setHitTestBounds",
     :since "69",
     :params [{:name "bounds-list", :type "[array-of-virtualKeyboardPrivate.Boundss]"}]}
    {:id ::set-area-to-remain-on-screen,
     :name "setAreaToRemainOnScreen",
     :since "80",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}
    {:id ::set-window-bounds-in-screen,
     :name "setWindowBoundsInScreen",
     :since "84",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}
    {:id ::get-clipboard-history,
     :name "getClipboardHistory",
     :since "master",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "clipboard-history", :type "[array-of-virtualKeyboardPrivate.ClipboardItems]"}]}}]}
    {:id ::paste-clipboard-item,
     :name "pasteClipboardItem",
     :since "master",
     :params [{:name "item-id", :type "string"}]}
    {:id ::delete-clipboard-item,
     :name "deleteClipboardItem",
     :since "master",
     :params [{:name "item-id", :type "string"}]}],
   :events
   [{:id ::on-bounds-changed,
     :name "onBoundsChanged",
     :since "44",
     :params [{:name "bounds", :type "virtualKeyboardPrivate.Bounds"}]}
    {:id ::on-keyboard-closed, :name "onKeyboardClosed", :since "55"}
    {:id ::on-keyboard-config-changed,
     :name "onKeyboardConfigChanged",
     :since "63",
     :params [{:name "config", :type "virtualKeyboardPrivate.KeyboardConfig"}]}
    {:id ::on-clipboard-history-changed,
     :name "onClipboardHistoryChanged",
     :since "master",
     :params [{:name "item-ids", :type "[array-of-strings]"}]}
    {:id ::on-clipboard-item-updated,
     :name "onClipboardItemUpdated",
     :since "master",
     :params [{:name "clipboard-history-item", :type "virtualKeyboardPrivate.ClipboardItem"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))