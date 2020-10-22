(ns chromex.ext.input.ime
  "Use the chrome.input.ime API to implement a custom IME for Chrome OS. This allows your extension to handle keystrokes, set
   the composition, and manage the candidate window.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/input.ime"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-composition
  "Set the current composition. If this extension does not own the active IME, this fails.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setComposition-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setComposition."
  ([parameters] (gen-call :function ::set-composition &form parameters)))

(defmacro clear-composition
  "Clear the current composition. If this extension does not own the active IME, this fails.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-clearComposition-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-clearComposition."
  ([parameters] (gen-call :function ::clear-composition &form parameters)))

(defmacro commit-text
  "Commits the provided text to the current input.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-commitText-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-commitText."
  ([parameters] (gen-call :function ::commit-text &form parameters)))

(defmacro send-key-events
  "Sends the key events.  This function is expected to be used by virtual keyboards.  When key(s) on a virtual keyboard is
   pressed by a user, this function is used to propagate that event to the system.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-sendKeyEvents-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-sendKeyEvents."
  ([parameters] (gen-call :function ::send-key-events &form parameters)))

(defmacro hide-input-view
  "Hides the input view window, which is popped up automatically by system. If the input view window is already hidden, this
   function will do nothing.

   https://developer.chrome.com/extensions/input.ime#method-hideInputView."
  ([] (gen-call :function ::hide-input-view &form)))

(defmacro set-candidate-window-properties
  "Sets the properties of the candidate window. This fails if the extension doesn't own the active IME

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setCandidateWindowProperties-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setCandidateWindowProperties."
  ([parameters] (gen-call :function ::set-candidate-window-properties &form parameters)))

(defmacro set-candidates
  "Sets the current candidate list. This fails if this extension doesn't own the active IME

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setCandidates-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setCandidates."
  ([parameters] (gen-call :function ::set-candidates &form parameters)))

(defmacro set-cursor-position
  "Set the position of the cursor in the candidate window. This is a no-op if this extension does not own the active IME.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setCursorPosition-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setCursorPosition."
  ([parameters] (gen-call :function ::set-cursor-position &form parameters)))

(defmacro set-assistive-window-properties
  "Shows/Hides an assistive window with the given properties.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setAssistiveWindowProperties-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/input.ime#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setAssistiveWindowProperties."
  ([parameters] (gen-call :function ::set-assistive-window-properties &form parameters)))

(defmacro set-assistive-window-button-highlighted
  "Highlights/Unhighlights a button in an assistive window.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setAssistiveWindowButtonHighlighted-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setAssistiveWindowButtonHighlighted."
  ([parameters] (gen-call :function ::set-assistive-window-button-highlighted &form parameters)))

(defmacro set-menu-items
  "Adds the provided menu items to the language menu when this IME is active.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-setMenuItems-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-setMenuItems."
  ([parameters] (gen-call :function ::set-menu-items &form parameters)))

(defmacro update-menu-items
  "Updates the state of the MenuItems specified

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-updateMenuItems-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-updateMenuItems."
  ([parameters] (gen-call :function ::update-menu-items &form parameters)))

(defmacro delete-surrounding-text
  "Deletes the text around the caret.

     |parameters| - https://developer.chrome.com/extensions/input.ime#property-deleteSurroundingText-parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/input.ime#method-deleteSurroundingText."
  ([parameters] (gen-call :function ::delete-surrounding-text &form parameters)))

(defmacro key-event-handled
  "Indicates that the key event received by onKeyEvent is handled.  This should only be called if the onKeyEvent listener is
   asynchronous.

     |request-id| - Request id of the event that was handled.  This should come from keyEvent.requestId
     |response| - True if the keystroke was handled, false if not

   https://developer.chrome.com/extensions/input.ime#method-keyEventHandled."
  ([request-id response] (gen-call :function ::key-event-handled &form request-id response)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-activate-events
  "This event is sent when an IME is activated. It signals that the IME will be receiving onKeyPress events.

   Events will be put on the |channel| with signature [::on-activate [engine-id screen]] where:

     |engine-id| - ID of the engine receiving the event
     |screen| - The screen type under which the IME is activated.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onActivate."
  ([channel & args] (apply gen-call :event ::on-activate &form channel args)))

(defmacro tap-on-deactivated-events
  "This event is sent when an IME is deactivated. It signals that the IME will no longer be receiving onKeyPress events.

   Events will be put on the |channel| with signature [::on-deactivated [engine-id]] where:

     |engine-id| - ID of the engine receiving the event

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onDeactivated."
  ([channel & args] (apply gen-call :event ::on-deactivated &form channel args)))

(defmacro tap-on-focus-events
  "This event is sent when focus enters a text box. It is sent to all extensions that are listening to this event, and enabled
   by the user.

   Events will be put on the |channel| with signature [::on-focus [context]] where:

     |context| - Describes the text field that has acquired focus.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onFocus."
  ([channel & args] (apply gen-call :event ::on-focus &form channel args)))

(defmacro tap-on-blur-events
  "This event is sent when focus leaves a text box. It is sent to all extensions that are listening to this event, and enabled
   by the user.

   Events will be put on the |channel| with signature [::on-blur [context-id]] where:

     |context-id| - The ID of the text field that has lost focus. The ID is invalid after this call

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onBlur."
  ([channel & args] (apply gen-call :event ::on-blur &form channel args)))

(defmacro tap-on-input-context-update-events
  "This event is sent when the properties of the current InputContext change, such as the the type. It is sent to all
   extensions that are listening to this event, and enabled by the user.

   Events will be put on the |channel| with signature [::on-input-context-update [context]] where:

     |context| - An InputContext object describing the text field that has changed.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onInputContextUpdate."
  ([channel & args] (apply gen-call :event ::on-input-context-update &form channel args)))

(defmacro tap-on-key-event-events
  "Fired when a key event is sent from the operating system. The event will be sent to the extension if this extension owns
   the active IME. The listener function should return true if the event was handled false if it was not.  If the event will
   be evaluated asynchronously, this function must return undefined and the IME must later call keyEventHandled() with the
   result.

   Events will be put on the |channel| with signature [::on-key-event [engine-id key-data request-id]] where:

     |engine-id| - ID of the engine receiving the event
     |key-data| - Data on the key event
     |request-id| - ID of the request. If the event listener returns undefined, then keyEventHandled must be called later with
                    this requestId.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onKeyEvent."
  ([channel & args] (apply gen-call :event ::on-key-event &form channel args)))

(defmacro tap-on-candidate-clicked-events
  "This event is sent if this extension owns the active IME.

   Events will be put on the |channel| with signature [::on-candidate-clicked [engine-id candidate-id button]] where:

     |engine-id| - ID of the engine receiving the event
     |candidate-id| - ID of the candidate that was clicked.
     |button| - Which mouse buttons was clicked.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onCandidateClicked."
  ([channel & args] (apply gen-call :event ::on-candidate-clicked &form channel args)))

(defmacro tap-on-menu-item-activated-events
  "Called when the user selects a menu item

   Events will be put on the |channel| with signature [::on-menu-item-activated [engine-id name]] where:

     |engine-id| - ID of the engine receiving the event
     |name| - Name of the MenuItem which was activated

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onMenuItemActivated."
  ([channel & args] (apply gen-call :event ::on-menu-item-activated &form channel args)))

(defmacro tap-on-surrounding-text-changed-events
  "Called when the editable string around caret is changed or when the caret position is moved. The text length is limited to
   100 characters for each back and forth direction.

   Events will be put on the |channel| with signature [::on-surrounding-text-changed [engine-id surrounding-info]] where:

     |engine-id| - ID of the engine receiving the event
     |surrounding-info| - The surrounding information.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onSurroundingTextChanged."
  ([channel & args] (apply gen-call :event ::on-surrounding-text-changed &form channel args)))

(defmacro tap-on-reset-events
  "This event is sent when chrome terminates ongoing text input session.

   Events will be put on the |channel| with signature [::on-reset [engine-id]] where:

     |engine-id| - ID of the engine receiving the event

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onReset."
  ([channel & args] (apply gen-call :event ::on-reset &form channel args)))

(defmacro tap-on-assistive-window-button-clicked-events
  "This event is sent when a button in an assistive window is clicked.

   Events will be put on the |channel| with signature [::on-assistive-window-button-clicked [details]] where:

     |details| - https://developer.chrome.com/extensions/input.ime#property-onAssistiveWindowButtonClicked-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/input.ime#event-onAssistiveWindowButtonClicked."
  ([channel & args] (apply gen-call :event ::on-assistive-window-button-clicked &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.input.ime namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.input.ime",
   :since "38",
   :functions
   [{:id ::set-composition,
     :name "setComposition",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::clear-composition,
     :name "clearComposition",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::commit-text,
     :name "commitText",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::send-key-events,
     :name "sendKeyEvents",
     :callback? true,
     :params [{:name "parameters", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::hide-input-view, :name "hideInputView"}
    {:id ::set-candidate-window-properties,
     :name "setCandidateWindowProperties",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::set-candidates,
     :name "setCandidates",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::set-cursor-position,
     :name "setCursorPosition",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::set-assistive-window-properties,
     :name "setAssistiveWindowProperties",
     :since "85",
     :callback? true,
     :params
     [{:name "parameters", :type "object"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::set-assistive-window-button-highlighted,
     :name "setAssistiveWindowButtonHighlighted",
     :since "86",
     :callback? true,
     :params [{:name "parameters", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-menu-items,
     :name "setMenuItems",
     :callback? true,
     :params
     [{:name "parameters", :type "input.ime.MenuParameters"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::update-menu-items,
     :name "updateMenuItems",
     :callback? true,
     :params
     [{:name "parameters", :type "input.ime.MenuParameters"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::delete-surrounding-text,
     :name "deleteSurroundingText",
     :callback? true,
     :params [{:name "parameters", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::key-event-handled,
     :name "keyEventHandled",
     :params [{:name "request-id", :type "string"} {:name "response", :type "boolean"}]}],
   :events
   [{:id ::on-activate,
     :name "onActivate",
     :params [{:name "engine-id", :type "string"} {:name "screen", :type "input.ime.ScreenType"}]}
    {:id ::on-deactivated, :name "onDeactivated", :params [{:name "engine-id", :type "string"}]}
    {:id ::on-focus, :name "onFocus", :params [{:name "context", :type "input.ime.InputContext"}]}
    {:id ::on-blur, :name "onBlur", :params [{:name "context-id", :type "integer"}]}
    {:id ::on-input-context-update,
     :name "onInputContextUpdate",
     :params [{:name "context", :type "input.ime.InputContext"}]}
    {:id ::on-key-event,
     :name "onKeyEvent",
     :params
     [{:name "engine-id", :type "string"}
      {:name "key-data", :type "input.ime.KeyboardEvent"}
      {:name "request-id", :since "79", :type "string"}]}
    {:id ::on-candidate-clicked,
     :name "onCandidateClicked",
     :params
     [{:name "engine-id", :type "string"}
      {:name "candidate-id", :type "integer"}
      {:name "button", :type "input.ime.MouseButton"}]}
    {:id ::on-menu-item-activated,
     :name "onMenuItemActivated",
     :params [{:name "engine-id", :type "string"} {:name "name", :type "string"}]}
    {:id ::on-surrounding-text-changed,
     :name "onSurroundingTextChanged",
     :params [{:name "engine-id", :type "string"} {:name "surrounding-info", :type "object"}]}
    {:id ::on-reset, :name "onReset", :params [{:name "engine-id", :type "string"}]}
    {:id ::on-assistive-window-button-clicked,
     :name "onAssistiveWindowButtonClicked",
     :since "85",
     :params [{:name "details", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))