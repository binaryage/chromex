(ns chromex.app.accessibility-private
  "  * available since Chrome 36"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-native-accessibility-enabled
  "Enables or disables native accessibility support. Once disabled, it is up to the calling extension to provide accessibility
   for web contents.

     |enabled| - True if native accessibility support should be enabled."
  ([enabled] (gen-call :function ::set-native-accessibility-enabled &form enabled)))

(defmacro set-focus-ring
  "Set the bounds of the accessibility focus ring.

     |rects| - Array of rectangles to draw the accessibility focus ring around.
     |color| - CSS-style hex color string beginning with # like #FF9982 or #EEE."
  ([rects color] (gen-call :function ::set-focus-ring &form rects color))
  ([rects] `(set-focus-ring ~rects :omit)))

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
  "Fired when  the user is no longer holding down two fingers (including releasing one, holding down three, or moving them).

   Events will be put on the |channel| with signature [::on-two-finger-touch-stop []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-two-finger-touch-stop &form channel args)))

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
   [{:id ::set-native-accessibility-enabled,
     :name "setNativeAccessibilityEnabled",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-focus-ring,
     :name "setFocusRing",
     :since "39",
     :params
     [{:name "rects", :type "[array-of-accessibilityPrivate.ScreenRects]"}
      {:name "color", :optional? true, :type "string"}]}
    {:id ::set-keyboard-listener,
     :name "setKeyboardListener",
     :since "48",
     :params [{:name "enabled", :type "boolean"} {:name "capture", :type "boolean"}]}
    {:id ::darken-screen, :name "darkenScreen", :since "59", :params [{:name "enabled", :type "boolean"}]}],
   :events
   [{:id ::on-introduce-chrome-vox, :name "onIntroduceChromeVox", :since "42"}
    {:id ::on-accessibility-gesture,
     :name "onAccessibilityGesture",
     :since "52",
     :params [{:name "gesture", :type "accessibilityPrivate.Gesture"}]}
    {:id ::on-two-finger-touch-start, :name "onTwoFingerTouchStart", :since "59"}
    {:id ::on-two-finger-touch-stop, :name "onTwoFingerTouchStop", :since "59"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))