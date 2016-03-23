(ns chromex.ext.accessibility-features
  "Use the chrome.accessibilityFeatures API to manage Chrome's accessibility features. This API relies on the ChromeSetting
   prototype of the type API for getting and setting individual accessibility features. In order to get feature states the
   extension must request accessibilityFeatures.read permission. For modifying feature state, the extension needs
   accessibilityFeatures.modify permission. Note that accessibilityFeatures.modify does not imply accessibilityFeatures.read
   permission.

     * available since Chrome 37
     * https://developer.chrome.com/extensions/accessibilityFeatures"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-spoken-feedback
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-spokenFeedback."
  ([] (gen-call :property ::spoken-feedback &form)))

(defmacro get-large-cursor
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-largeCursor."
  ([] (gen-call :property ::large-cursor &form)))

(defmacro get-sticky-keys
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-stickyKeys."
  ([] (gen-call :property ::sticky-keys &form)))

(defmacro get-high-contrast
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-highContrast."
  ([] (gen-call :property ::high-contrast &form)))

(defmacro get-screen-magnifier
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-screenMagnifier."
  ([] (gen-call :property ::screen-magnifier &form)))

(defmacro get-autoclick
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-autoclick."
  ([] (gen-call :property ::autoclick &form)))

(defmacro get-virtual-keyboard
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-virtualKeyboard."
  ([] (gen-call :property ::virtual-keyboard &form)))

(defmacro get-caret-highlight
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-caretHighlight."
  ([] (gen-call :property ::caret-highlight &form)))

(defmacro get-cursor-highlight
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-cursorHighlight."
  ([] (gen-call :property ::cursor-highlight &form)))

(defmacro get-focus-highlight
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-focusHighlight."
  ([] (gen-call :property ::focus-highlight &form)))

(defmacro get-select-to-speak
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-selectToSpeak."
  ([] (gen-call :property ::select-to-speak &form)))

(defmacro get-switch-access
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-switchAccess."
  ([] (gen-call :property ::switch-access &form)))

(defmacro get-animation-policy
  "An interface that allows access to a Chrome browser setting. See 'accessibilityFeatures' for an example.

   https://developer.chrome.com/extensions/accessibilityFeatures#property-animationPolicy."
  ([] (gen-call :property ::animation-policy &form)))

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
  {:namespace "chrome.accessibilityFeatures",
   :since "37",
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
    {:id ::focus-highlight, :name "focusHighlight", :since "51", :return-type "object"}
    {:id ::select-to-speak, :name "selectToSpeak", :since "51", :return-type "object"}
    {:id ::switch-access, :name "switchAccess", :since "51", :return-type "object"}
    {:id ::animation-policy, :name "animationPolicy", :since "42", :return-type "object"}]})

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