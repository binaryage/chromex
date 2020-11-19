(ns chromex.app.accessibility-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-display-name-for-locale
  "Called to translate localeCodeToTranslate into human-readable string in the locale specified by displayLocaleCode

     |locale-code-to-translate| - ?
     |display-locale-code| - ?"
  ([locale-code-to-translate display-locale-code] (gen-call :function ::get-display-name-for-locale &form locale-code-to-translate display-locale-code)))

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

(defmacro forward-key-events-to-switch-access
  "When enabled, forwards key events to the Switch Access extension

     |should-forward| - ?"
  ([should-forward] (gen-call :function ::forward-key-events-to-switch-access &form should-forward)))

(defmacro update-switch-access-bubble
  "Shows the Switch Access menu next to the specified rectangle and with the given actions

     |bubble| - Which bubble to show/hide
     |show| - True if the bubble should be shown, false otherwise
     |anchor| - A rectangle indicating the bounds of the object the menu should be displayed next to.
     |actions| - The actions to be shown in the menu."
  ([bubble show anchor actions] (gen-call :function ::update-switch-access-bubble &form bubble show anchor actions))
  ([bubble show anchor] `(update-switch-access-bubble ~bubble ~show ~anchor :omit))
  ([bubble show] `(update-switch-access-bubble ~bubble ~show :omit :omit)))

(defmacro activate-point-scan
  "Activates point scanning in Switch Access."
  ([] (gen-call :function ::activate-point-scan &form)))

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

(defmacro set-select-to-speak-state
  "Called by the Select-to-Speak extension when Select-to-Speak has changed states, between selecting with the mouse,
   speaking, and inactive.

     |state| - ?"
  ([state] (gen-call :function ::set-select-to-speak-state &form state)))

(defmacro handle-scrollable-bounds-for-point-found
  "Called by the Accessibility Common extension when onScrollableBoundsForPointRequested has found a scrolling container.
   |rect| will be the bounds of the nearest scrollable ancestor of the node at the point requested using
   onScrollableBoundsForPointRequested.

     |rect| - ?"
  ([rect] (gen-call :function ::handle-scrollable-bounds-for-point-found &form rect)))

(defmacro move-magnifier-to-rect
  "Called by the Accessibility Common extension to move |rect| within the magnifier viewport (e.g. when focus has changed). If
   |rect| is already completely within the viewport, magnifier doesn't move. If any edge of |rect| is outside the viewport
   (e.g. if rect is larger than or extends partially beyond the viewport), magnifier will center the overflowing dimensions of
   the viewport on center of |rect| (e.g. center viewport vertically if |rect| extends beyond bottom of screen).

     |rect| - Rect to ensure visible in the magnified viewport."
  ([rect] (gen-call :function ::move-magnifier-to-rect &form rect)))

(defmacro toggle-dictation
  "Toggles dictation between active and inactive states."
  ([] (gen-call :function ::toggle-dictation &form)))

(defmacro set-virtual-keyboard-visible
  "Shows or hides the virtual keyboard.

     |is-visible| - ?"
  ([is-visible] (gen-call :function ::set-virtual-keyboard-visible &form is-visible)))

(defmacro open-settings-subpage
  "Opens a specified settings subpage. To open a page with url chrome://settings/manageAccessibility/tts, pass in the
   substring 'manageAccessibility/tts'.

     |subpage| - ?"
  ([subpage] (gen-call :function ::open-settings-subpage &form subpage)))

(defmacro perform-accelerator-action
  "Performs an accelerator action.

     |accelerator-action| - ?"
  ([accelerator-action] (gen-call :function ::perform-accelerator-action &form accelerator-action)))

(defmacro is-feature-enabled
  "Checks to see if an accessibility feature is enabled.

     |feature| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [feature-enabled] where:

     |feature-enabled| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([feature] (gen-call :function ::is-feature-enabled &form feature)))

(defmacro update-select-to-speak-panel
  "Updates properties of the Select-to-speak panel.

     |show| - True to show panel, false to hide it
     |anchor| - A rectangle indicating the bounds of the object the panel should be displayed next to.
     |is-paused| - True if Select-to-speak playback is paused."
  ([show anchor is-paused] (gen-call :function ::update-select-to-speak-panel &form show anchor is-paused))
  ([show anchor] `(update-select-to-speak-panel ~show ~anchor :omit))
  ([show] `(update-select-to-speak-panel ~show :omit :omit)))

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

   Events will be put on the |channel| with signature [::on-accessibility-gesture [gesture x y]] where:

     |gesture| - ?
     |x| - ?
     |y| - ?

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
  "Fired when Chrome OS wants to change the Select-to-Speak state, between selecting with the mouse, speaking, and inactive.

   Events will be put on the |channel| with signature [::on-select-to-speak-state-change-requested []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-select-to-speak-state-change-requested &form channel args)))

(defmacro tap-on-switch-access-command-events
  "Fired when Chrome OS has received a key event corresponding to a Switch Access command.

   Events will be put on the |channel| with signature [::on-switch-access-command [command]] where:

     |command| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-switch-access-command &form channel args)))

(defmacro tap-on-point-scan-set-events
  "Fired when Chrome OS has received the final point of point scanning.

   Events will be put on the |channel| with signature [::on-point-scan-set [point]] where:

     |point| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-point-scan-set &form channel args)))

(defmacro tap-on-announce-for-accessibility-events
  "Fired when an internal component within accessibility wants to force speech output for an accessibility extension. Do not
   use without approval from accessibility owners.

   Events will be put on the |channel| with signature [::on-announce-for-accessibility [announce-text]] where:

     |announce-text| - Text to be announced.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-announce-for-accessibility &form channel args)))

(defmacro tap-on-scrollable-bounds-for-point-requested-events
  "Fired when an internal component within accessibility wants to find the nearest scrolling container at a given screen
   coordinate. Used in Automatic Clicks.

   Events will be put on the |channel| with signature [::on-scrollable-bounds-for-point-requested [x y]] where:

     |x| - X screen coordinate of the point.
     |y| - Y screen coordinate of the point.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-scrollable-bounds-for-point-requested &form channel args)))

(defmacro tap-on-magnifier-bounds-changed-events
  "Fired when Chrome OS magnifier bounds are updated.

   Events will be put on the |channel| with signature [::on-magnifier-bounds-changed [magnifier-bounds]] where:

     |magnifier-bounds| - Updated bounds of magnifier viewport.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-magnifier-bounds-changed &form channel args)))

(defmacro tap-on-custom-spoken-feedback-toggled-events
  "Fired when a custom spoken feedback on the active window gets enabled or disabled. Called from ARC++ accessibility.

   Events will be put on the |channel| with signature [::on-custom-spoken-feedback-toggled [enabled]] where:

     |enabled| - True if the active window implements custom spoken feedback features.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-custom-spoken-feedback-toggled &form channel args)))

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
   :since "38",
   :functions
   [{:id ::get-display-name-for-locale,
     :name "getDisplayNameForLocale",
     :since "81",
     :return-type "string",
     :params [{:name "locale-code-to-translate", :type "string"} {:name "display-locale-code", :type "string"}]}
    {:id ::get-battery-description,
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
     :since "74",
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
    {:id ::forward-key-events-to-switch-access,
     :name "forwardKeyEventsToSwitchAccess",
     :since "73",
     :params [{:name "should-forward", :type "boolean"}]}
    {:id ::update-switch-access-bubble,
     :name "updateSwitchAccessBubble",
     :since "84",
     :params
     [{:name "bubble", :type "accessibilityPrivate.SwitchAccessBubble"}
      {:name "show", :type "boolean"}
      {:name "anchor", :optional? true, :type "accessibilityPrivate.ScreenRect"}
      {:name "actions", :optional? true, :type "[array-of-accessibilityPrivate.SwitchAccessMenuActions]"}]}
    {:id ::activate-point-scan, :name "activatePointScan", :since "future"}
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
    {:id ::set-select-to-speak-state,
     :name "setSelectToSpeakState",
     :since "87",
     :params [{:name "state", :type "accessibilityPrivate.SelectToSpeakState"}]}
    {:id ::handle-scrollable-bounds-for-point-found,
     :name "handleScrollableBoundsForPointFound",
     :since "87",
     :params [{:name "rect", :type "accessibilityPrivate.ScreenRect"}]}
    {:id ::move-magnifier-to-rect,
     :name "moveMagnifierToRect",
     :since "87",
     :params [{:name "rect", :type "accessibilityPrivate.ScreenRect"}]}
    {:id ::toggle-dictation, :name "toggleDictation", :since "71"}
    {:id ::set-virtual-keyboard-visible,
     :name "setVirtualKeyboardVisible",
     :since "75",
     :params [{:name "is-visible", :type "boolean"}]}
    {:id ::open-settings-subpage, :name "openSettingsSubpage", :since "77", :params [{:name "subpage", :type "string"}]}
    {:id ::perform-accelerator-action,
     :name "performAcceleratorAction",
     :since "87",
     :params [{:name "accelerator-action", :type "accessibilityPrivate.AcceleratorAction"}]}
    {:id ::is-feature-enabled,
     :name "isFeatureEnabled",
     :since "future",
     :callback? true,
     :params
     [{:name "feature", :type "accessibilityPrivate.AccessibilityFeature"}
      {:name "callback", :type :callback, :callback {:params [{:name "feature-enabled", :type "boolean"}]}}]}
    {:id ::update-select-to-speak-panel,
     :name "updateSelectToSpeakPanel",
     :since "master",
     :params
     [{:name "show", :type "boolean"}
      {:name "anchor", :optional? true, :type "accessibilityPrivate.ScreenRect"}
      {:name "is-paused", :optional? true, :type "boolean"}]}],
   :events
   [{:id ::on-introduce-chrome-vox, :name "onIntroduceChromeVox", :since "42"}
    {:id ::on-accessibility-gesture,
     :name "onAccessibilityGesture",
     :since "52",
     :params
     [{:name "gesture", :type "accessibilityPrivate.Gesture"}
      {:name "x", :since "86", :type "integer"}
      {:name "y", :since "86", :type "integer"}]}
    {:id ::on-two-finger-touch-start, :name "onTwoFingerTouchStart", :since "59"}
    {:id ::on-two-finger-touch-stop, :name "onTwoFingerTouchStop", :since "59"}
    {:id ::on-select-to-speak-state-change-requested, :name "onSelectToSpeakStateChangeRequested", :since "68"}
    {:id ::on-switch-access-command,
     :name "onSwitchAccessCommand",
     :since "77",
     :params [{:name "command", :type "accessibilityPrivate.SwitchAccessCommand"}]}
    {:id ::on-point-scan-set,
     :name "onPointScanSet",
     :since "master",
     :params [{:name "point", :type "accessibilityPrivate.PointScanPoint"}]}
    {:id ::on-announce-for-accessibility,
     :name "onAnnounceForAccessibility",
     :since "74",
     :params [{:name "announce-text", :type "[array-of-strings]"}]}
    {:id ::on-scrollable-bounds-for-point-requested,
     :name "onScrollableBoundsForPointRequested",
     :since "87",
     :params [{:name "x", :type "double"} {:name "y", :type "double"}]}
    {:id ::on-magnifier-bounds-changed,
     :name "onMagnifierBoundsChanged",
     :since "future",
     :params [{:name "magnifier-bounds", :type "accessibilityPrivate.ScreenRect"}]}
    {:id ::on-custom-spoken-feedback-toggled,
     :name "onCustomSpokenFeedbackToggled",
     :since "82",
     :params [{:name "enabled", :type "boolean"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))