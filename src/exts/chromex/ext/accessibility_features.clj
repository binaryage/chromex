(ns chromex.ext.accessibility-features
  "Use the chrome.accessibilityFeatures API to manage Chrome's accessibility features. This API relies on the ChromeSetting
   prototype of the type API for getting and setting individual accessibility features. In order to get feature states the
   extension must request accessibilityFeatures.read permission. For modifying feature state, the extension needs
   accessibilityFeatures.modify permission. Note that accessibilityFeatures.modify does not imply accessibilityFeatures.read
   permission.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/accessibilityFeatures"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-spoken-feedback
  "ChromeOS only.Spoken feedback (text-to-speech). The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-spokenFeedback."
  ([] (gen-call :property ::spoken-feedback &form)))

(defmacro get-large-cursor
  "ChromeOS only.Enlarged cursor. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-largeCursor."
  ([] (gen-call :property ::large-cursor &form)))

(defmacro get-sticky-keys
  "ChromeOS only.Sticky modifier keys (like shift or alt). The value indicates whether the feature is enabled or not. get()
   requires accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-stickyKeys."
  ([] (gen-call :property ::sticky-keys &form)))

(defmacro get-high-contrast
  "ChromeOS only.High contrast rendering mode. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-highContrast."
  ([] (gen-call :property ::high-contrast &form)))

(defmacro get-screen-magnifier
  "ChromeOS only.Full screen magnification. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-screenMagnifier."
  ([] (gen-call :property ::screen-magnifier &form)))

(defmacro get-autoclick
  "ChromeOS only.Auto mouse click after mouse stops moving. The value indicates whether the feature is enabled or not. get()
   requires accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-autoclick."
  ([] (gen-call :property ::autoclick &form)))

(defmacro get-virtual-keyboard
  "ChromeOS only.Virtual on-screen keyboard. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-virtualKeyboard."
  ([] (gen-call :property ::virtual-keyboard &form)))

(defmacro get-caret-highlight
  "ChromeOS only.Caret highlighting. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-caretHighlight."
  ([] (gen-call :property ::caret-highlight &form)))

(defmacro get-cursor-highlight
  "ChromeOS only.Cursor highlighting. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-cursorHighlight."
  ([] (gen-call :property ::cursor-highlight &form)))

(defmacro get-cursor-color
  "ChromeOS only.Cursor color. The value indicates whether the feature is enabled or not, doesn't indicate the color of it.
   get() requires accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-cursorColor."
  ([] (gen-call :property ::cursor-color &form)))

(defmacro get-docked-magnifier
  "ChromeOS only.Docked magnifier. The value indicates whether docked magnifier feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-dockedMagnifier."
  ([] (gen-call :property ::docked-magnifier &form)))

(defmacro get-focus-highlight
  "ChromeOS only.Focus highlighting. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-focusHighlight."
  ([] (gen-call :property ::focus-highlight &form)))

(defmacro get-select-to-speak
  "ChromeOS only.Select-to-speak. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-selectToSpeak."
  ([] (gen-call :property ::select-to-speak &form)))

(defmacro get-switch-access
  "ChromeOS only.Switch Access. The value indicates whether the feature is enabled or not. get() requires
   accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-switchAccess."
  ([] (gen-call :property ::switch-access &form)))

(defmacro get-animation-policy
  "get() requires accessibilityFeatures.read permission. set() and clear() require accessibilityFeatures.modify permission.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-animationPolicy."
  ([] (gen-call :property ::animation-policy &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.accessibility-features namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.accessibilityFeatures",
   :since "38",
   :properties
   [{:id ::spoken-feedback, :name "spokenFeedback", :return-type "object"}
    {:id ::large-cursor, :name "largeCursor", :return-type "object"}
    {:id ::sticky-keys, :name "stickyKeys", :return-type "object"}
    {:id ::high-contrast, :name "highContrast", :return-type "object"}
    {:id ::screen-magnifier, :name "screenMagnifier", :return-type "object"}
    {:id ::autoclick, :name "autoclick", :return-type "object"}
    {:id ::virtual-keyboard, :name "virtualKeyboard", :return-type "object"}
    {:id ::caret-highlight, :name "caretHighlight", :since "51", :return-type "object"}
    {:id ::cursor-highlight, :name "cursorHighlight", :since "51", :return-type "object"}
    {:id ::cursor-color, :name "cursorColor", :since "85", :return-type "object"}
    {:id ::docked-magnifier, :name "dockedMagnifier", :since "future", :return-type "object"}
    {:id ::focus-highlight, :name "focusHighlight", :since "51", :return-type "object"}
    {:id ::select-to-speak, :name "selectToSpeak", :since "51", :return-type "object"}
    {:id ::switch-access, :name "switchAccess", :since "51", :return-type "object"}
    {:id ::animation-policy, :name "animationPolicy", :since "42", :return-type "object"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))