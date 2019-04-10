(ns chromex.app.accessibility-private
  "  * available since Chrome 36"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-battery-description
  "Called to request battery status from Chrome OS system.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [battery-description] where:

     |battery-description| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-battery-description &form)))

(defmacro set-native-accessibility-enabled
  "Enables or disables native accessibility support. Once disabled, it is up to the calling extension to provide accessibility
   for web contents.

     |enabled| - True if native accessibility support should be enabled."
  ([enabled] (gen-call :function ::set-native-accessibility-enabled &form enabled)))

(defmacro set-focus-rings
  "Sets the given accessibility focus rings for this extension.

     |focus-rings| - Array of focus rings to draw."
  ([focus-rings] (gen-call :function ::set-focus-rings &form focus-rings)))

(defmacro set-highlights
  "Sets the bounds of the accessibility highlight.

     |rects| - Array of rectangles to draw the highlight around.
     |color| - CSS-style hex color string beginning with # like #FF9982 or #EEE."
  ([rects color] (gen-call :function ::set-highlights &form rects color)))

(defmacro set-keyboard-listener
  "Sets the calling extension as a listener of all keyboard events optionally allowing the calling extension to
   capture/swallow the key event via DOM apis. Returns false via callback when unable to set the listener.

     |enabled| - True if the caller wants to listen to key events; false to stop listening to events. Note that there is
                 only ever one extension listening to key events.
     |capture| - True if key events should be swallowed natively and not propagated if preventDefault() gets called by the
                 extension's background page."
  ([enabled capture] (gen-call :function ::set-keyboard-listener &form enabled capture)))

(defmacro darken-screen
  "Darkens or undarkens the screen.

     |enabled| - True to darken screen; false to undarken screen."
  ([enabled] (gen-call :function ::darken-screen &form enabled)))

(defmacro set-switch-access-keys
  "Change the keyboard keys captured by Switch Access.

     |key-codes| - The key codes for the keys that will be captured."
  ([key-codes] (gen-call :function ::set-switch-access-keys &form key-codes)))

(defmacro set-switch-access-menu-state
  "Shows or hides the Switch Access menu. If shown, it is at the indicated location.

     |show| - If true, show the menu. If false, hide the menu.
     |element-bounds| - Position of an element, in global screen coordinates, to place the menu next to.
     |item-count| - The number of items that need to be shown in the menu."
  ([show element-bounds item-count] (gen-call :function ::set-switch-access-menu-state &form show element-bounds item-count)))

(defmacro forward-key-events-to-switch-access
  "When enabled, forwards key events to the Switch Access extension

     |should-forward| - ?"
  ([should-forward] (gen-call :function ::forward-key-events-to-switch-access &form should-forward)))

(defmacro set-native-chrome-vox-arc-support-for-current-app
  "Sets current ARC app to use native ARC support.

     |enabled| - True for ChromeVox (native), false for TalkBack."
  ([enabled] (gen-call :function ::set-native-chrome-vox-arc-support-for-current-app &form enabled)))

(defmacro send-synthetic-key-event
  "Sends a fabricated key event.

     |key-event| - The event to send."
  ([key-event] (gen-call :function ::send-synthetic-key-event &form key-event)))

(defmacro enable-chrome-vox-mouse-events
  "Enables or disables mouse events in ChromeVox.

     |enabled| - True if ChromeVox should receive mouse events."
  ([enabled] (gen-call :function ::enable-chrome-vox-mouse-events &form enabled)))

(defmacro send-synthetic-mouse-event
  "Sends a fabricated mouse event.

     |mouse-event| - The event to send."
  ([mouse-event] (gen-call :function ::send-synthetic-mouse-event &form mouse-event)))

(defmacro on-select-to-speak-state-changed
  "Called by the Select-to-Speak extension when Select-to-Speak has changed states, between selecting with the mouse,
   speaking, and inactive.

     |state| - ?"
  ([state] (gen-call :function ::on-select-to-speak-state-changed &form state)))

(defmacro toggle-dictation
  "Toggles dictation between active and inactive states."
  ([] (gen-call :function ::toggle-dictation &form)))

(defmacro set-virtual-keyboard-visible
  "Shows or hides the virtual keyboard.

     |is-visible| - ?"
  ([is-visible] (gen-call :function ::set-virtual-keyboard-visible &form is-visible)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-introduce-chrome-vox-events
  "Fired whenever ChromeVox should output introduction.

   Events will be put on the |channel| with signature [::on-introduce-chrome-vox []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-introduce-chrome-vox &form channel args)))

(defmacro tap-on-accessibility-gesture-events
  "Fired when an accessibility gesture is detected by the touch exploration controller.

   Events will be put on the |channel| with signature [::on-accessibility-gesture [gesture]] where:

     |gesture| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-accessibility-gesture &form channel args)))

(defmacro tap-on-two-finger-touch-start-events
  "Fired when we first detect two fingers are held down, which can be used to toggle spoken feedback on some touch-only
   devices.

   Events will be put on the |channel| with signature [::on-two-finger-touch-start []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-two-finger-touch-start &form channel args)))

(defmacro tap-on-two-finger-touch-stop-events
  "Fired when the user is no longer holding down two fingers (including releasing one, holding down three, or moving them).

   Events will be put on the |channel| with signature [::on-two-finger-touch-stop []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-two-finger-touch-stop &form channel args)))

(defmacro tap-on-select-to-speak-state-change-requested-events
  "Called when Chrome OS wants to change the Select-to-Speak state, between selecting with the mouse, speaking, and inactive.

   Events will be put on the |channel| with signature [::on-select-to-speak-state-change-requested []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-select-to-speak-state-change-requested &form channel args)))

(defmacro tap-on-announce-for-accessibility-events
  "Called when an internal component within accessibility wants to force speech output for an accessibility extension. Do not
   use without approval from accessibility owners.

   Events will be put on the |channel| with signature [::on-announce-for-accessibility [announce-text]] where:

     |announce-text| - Text to be announced.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-announce-for-accessibility &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.accessibility-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.accessibilityPrivate",
   :since "36",
   :functions
   [{:id ::get-battery-description,
     :name "getBatteryDescription",
     :since "73",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "battery-description", :type "string"}]}}]}
    {:id ::set-native-accessibility-enabled,
     :name "setNativeAccessibilityEnabled",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-focus-rings,
     :name "setFocusRings",
     :since "future",
     :params [{:name "focus-rings", :type "[array-of-accessibilityPrivate.FocusRingInfos]"}]}
    {:id ::set-highlights,
     :name "setHighlights",
     :since "64",
     :params [{:name "rects", :type "[array-of-accessibilityPrivate.ScreenRects]"} {:name "color", :type "string"}]}
    {:id ::set-keyboard-listener,
     :name "setKeyboardListener",
     :since "48",
     :params [{:name "enabled", :type "boolean"} {:name "capture", :type "boolean"}]}
    {:id ::darken-screen, :name "darkenScreen", :since "59", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-switch-access-keys,
     :name "setSwitchAccessKeys",
     :since "61",
     :params [{:name "key-codes", :type "[array-of-integers]"}]}
    {:id ::set-switch-access-menu-state,
     :name "setSwitchAccessMenuState",
     :since "73",
     :params
     [{:name "show", :type "boolean"}
      {:name "element-bounds", :type "accessibilityPrivate.ScreenRect"}
      {:name "item-count", :since "future", :type "integer"}]}
    {:id ::forward-key-events-to-switch-access,
     :name "forwardKeyEventsToSwitchAccess",
     :since "73",
     :params [{:name "should-forward", :type "boolean"}]}
    {:id ::set-native-chrome-vox-arc-support-for-current-app,
     :name "setNativeChromeVoxArcSupportForCurrentApp",
     :since "63",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::send-synthetic-key-event,
     :name "sendSyntheticKeyEvent",
     :since "65",
     :params [{:name "key-event", :type "accessibilityPrivate.SyntheticKeyboardEvent"}]}
    {:id ::enable-chrome-vox-mouse-events,
     :name "enableChromeVoxMouseEvents",
     :since "71",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::send-synthetic-mouse-event,
     :name "sendSyntheticMouseEvent",
     :since "71",
     :params [{:name "mouse-event", :type "accessibilityPrivate.SyntheticMouseEvent"}]}
    {:id ::on-select-to-speak-state-changed,
     :name "onSelectToSpeakStateChanged",
     :since "68",
     :params [{:name "state", :type "accessibilityPrivate.SelectToSpeakState"}]}
    {:id ::toggle-dictation, :name "toggleDictation", :since "71"}
    {:id ::set-virtual-keyboard-visible,
     :name "setVirtualKeyboardVisible",
     :since "future",
     :params [{:name "is-visible", :type "boolean"}]}],
   :events
   [{:id ::on-introduce-chrome-vox, :name "onIntroduceChromeVox", :since "42"}
    {:id ::on-accessibility-gesture,
     :name "onAccessibilityGesture",
     :since "52",
     :params [{:name "gesture", :type "accessibilityPrivate.Gesture"}]}
    {:id ::on-two-finger-touch-start, :name "onTwoFingerTouchStart", :since "59"}
    {:id ::on-two-finger-touch-stop, :name "onTwoFingerTouchStop", :since "59"}
    {:id ::on-select-to-speak-state-change-requested, :name "onSelectToSpeakStateChangeRequested", :since "68"}
    {:id ::on-announce-for-accessibility,
     :name "onAnnounceForAccessibility",
     :since "future",
     :params [{:name "announce-text", :type "[array-of-strings]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))