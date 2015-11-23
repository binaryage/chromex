(ns chromex.autotest-private
  "API for integration testing. To be used on test images with a test component
   extension.
   
     * available since Chrome 25
     * https://developer.chrome.com/extensions/autotestPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro logout
  "Logout of a user session."
  ([] (gen-call :function ::logout (meta &form))))

(defmacro restart
  "Restart the browser."
  ([] (gen-call :function ::restart (meta &form))))

(defmacro shutdown
  "Shutdown the browser.
   
     |force| - if set, ignore ongoing downloads and onunbeforeunload handlers."
  ([force] (gen-call :function ::shutdown (meta &form) force)))

(defmacro login-status
  "Get login status."
  ([#_callback] (gen-call :function ::login-status (meta &form))))

(defmacro lock-screen
  "Locks the screen."
  ([] (gen-call :function ::lock-screen (meta &form))))

(defmacro get-extensions-info
  "Get info about installed extensions."
  ([#_callback] (gen-call :function ::get-extensions-info (meta &form))))

(defmacro simulate-asan-memory-bug
  "Simulates a memory access bug for asan testing."
  ([] (gen-call :function ::simulate-asan-memory-bug (meta &form))))

(defmacro set-touchpad-sensitivity
  "Set the touchpad pointer sensitivity setting.
   
     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-touchpad-sensitivity (meta &form) value)))

(defmacro set-tap-to-click
  "Turn on/off tap-to-click for the touchpad.
   
     |enabled| - if set, enable tap-to-click."
  ([enabled] (gen-call :function ::set-tap-to-click (meta &form) enabled)))

(defmacro set-three-finger-click
  "Turn on/off three finger click for the touchpad.
   
     |enabled| - if set, enable three finger click."
  ([enabled] (gen-call :function ::set-three-finger-click (meta &form) enabled)))

(defmacro set-tap-dragging
  "Turn on/off tap dragging for the touchpad.
   
     |enabled| - if set, enable tap dragging."
  ([enabled] (gen-call :function ::set-tap-dragging (meta &form) enabled)))

(defmacro set-natural-scroll
  "Turn on/off Australian scrolling for devices other than wheel mouse.
   
     |enabled| - if set, enable Australian scrolling."
  ([enabled] (gen-call :function ::set-natural-scroll (meta &form) enabled)))

(defmacro set-mouse-sensitivity
  "Set the mouse pointer sensitivity setting.
   
     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-mouse-sensitivity (meta &form) value)))

(defmacro set-primary-button-right
  "Swap the primary mouse button for left click.
   
     |right| - if set, swap the primary mouse button."
  ([right] (gen-call :function ::set-primary-button-right (meta &form) right)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autotestPrivate",
   :since "25",
   :functions
   [{:id ::logout, :name "logout"}
    {:id ::restart, :name "restart"}
    {:id ::shutdown, :name "shutdown", :params [{:name "force", :type "boolean"}]}
    {:id ::login-status,
     :name "loginStatus",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "object"}]}}]}
    {:id ::lock-screen, :name "lockScreen"}
    {:id ::get-extensions-info,
     :name "getExtensionsInfo",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::simulate-asan-memory-bug, :name "simulateAsanMemoryBug"}
    {:id ::set-touchpad-sensitivity, :name "setTouchpadSensitivity", :params [{:name "value", :type "integer"}]}
    {:id ::set-tap-to-click, :name "setTapToClick", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-three-finger-click, :name "setThreeFingerClick", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-tap-dragging, :name "setTapDragging", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-natural-scroll, :name "setNaturalScroll", :params [{:name "enabled", :type "boolean"}]}
    {:id ::set-mouse-sensitivity, :name "setMouseSensitivity", :params [{:name "value", :type "integer"}]}
    {:id ::set-primary-button-right, :name "setPrimaryButtonRight", :params [{:name "right", :type "boolean"}]}]})

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