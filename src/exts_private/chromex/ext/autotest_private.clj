(ns chromex.ext.autotest-private
  "API for integration testing. To be used on test images with a test component
   extension.

     * available since Chrome 27"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro logout
  "Logout of a user session."
  ([] (gen-call :function ::logout &form)))

(defmacro restart
  "Restart the browser."
  ([] (gen-call :function ::restart &form)))

(defmacro shutdown
  "Shutdown the browser.

     |force| - if set, ignore ongoing downloads and onunbeforeunload handlers."
  ([force] (gen-call :function ::shutdown &form force)))

(defmacro login-status
  "Get login status.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::login-status &form)))

(defmacro lock-screen
  "Locks the screen."
  ([] (gen-call :function ::lock-screen &form)))

(defmacro get-extensions-info
  "Get info about installed extensions.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-extensions-info &form)))

(defmacro simulate-asan-memory-bug
  "Simulates a memory access bug for asan testing."
  ([] (gen-call :function ::simulate-asan-memory-bug &form)))

(defmacro set-touchpad-sensitivity
  "Set the touchpad pointer sensitivity setting.

     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-touchpad-sensitivity &form value)))

(defmacro set-tap-to-click
  "Turn on/off tap-to-click for the touchpad.

     |enabled| - if set, enable tap-to-click."
  ([enabled] (gen-call :function ::set-tap-to-click &form enabled)))

(defmacro set-three-finger-click
  "Turn on/off three finger click for the touchpad.

     |enabled| - if set, enable three finger click."
  ([enabled] (gen-call :function ::set-three-finger-click &form enabled)))

(defmacro set-tap-dragging
  "Turn on/off tap dragging for the touchpad.

     |enabled| - if set, enable tap dragging."
  ([enabled] (gen-call :function ::set-tap-dragging &form enabled)))

(defmacro set-natural-scroll
  "Turn on/off Australian scrolling for devices other than wheel mouse.

     |enabled| - if set, enable Australian scrolling."
  ([enabled] (gen-call :function ::set-natural-scroll &form enabled)))

(defmacro set-mouse-sensitivity
  "Set the mouse pointer sensitivity setting.

     |value| - the pointer sensitivity setting index."
  ([value] (gen-call :function ::set-mouse-sensitivity &form value)))

(defmacro set-primary-button-right
  "Swap the primary mouse button for left click.

     |right| - if set, swap the primary mouse button."
  ([right] (gen-call :function ::set-primary-button-right &form right)))

(defmacro set-mouse-reverse-scroll
  "Turn on/off reverse scrolling for mice.

     |enabled| - if set, enable reverse scrolling."
  ([enabled] (gen-call :function ::set-mouse-reverse-scroll &form enabled)))

(defmacro get-visible-notifications
  "Get visible notifications on the system.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [notifications] where:

     |notifications| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-visible-notifications &form)))

(defmacro get-play-store-state
  "Get state of the Play Store.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-play-store-state &form)))

(defmacro get-printer-list
  "Get list of available printers

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [printers] where:

     |printers| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-printer-list &form)))

(defmacro is-app-shown
  "Returns true if requested app is shown in Chrome.

     |app-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [app-shown] where:

     |app-shown| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id] (gen-call :function ::is-app-shown &form app-id)))

(defmacro is-arc-provisioned
  "Returns true if ARC is provisioned.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [arc-provisioned] where:

     |arc-provisioned| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-arc-provisioned &form)))

(defmacro get-arc-app
  "Gets information about the requested ARC app.

     |app-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [package] where:

     |package| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id] (gen-call :function ::get-arc-app &form app-id)))

(defmacro get-arc-package
  "Gets information about requested ARC package.

     |package-name| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [package] where:

     |package| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([package-name] (gen-call :function ::get-arc-package &form package-name)))

(defmacro launch-arc-app
  "Launches ARC app with optional intent. Returns true if ARC is active, app exists and launch request is passed to Android.

     |app-id| - ?
     |intent| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [app-launched] where:

     |app-launched| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id intent] (gen-call :function ::launch-arc-app &form app-id intent)))

(defmacro launch-app
  "Launches an application from the launcher with the given appId.

     |app-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id] (gen-call :function ::launch-app &form app-id)))

(defmacro close-app
  "Closes an application the given appId in case it was running.

     |app-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id] (gen-call :function ::close-app &form app-id)))

(defmacro update-printer
  "Update printer. Printer with empty ID is considered new.

     |printer| - ?"
  ([printer] (gen-call :function ::update-printer &form printer)))

(defmacro remove-printer
  "Remove printer.

     |printer-id| - ?"
  ([printer-id] (gen-call :function ::remove-printer &form printer-id)))

(defmacro set-play-store-enabled
  "Enable/disable the Play Store.

     |enabled| - if set, enable the Play Store.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([enabled] (gen-call :function ::set-play-store-enabled &form enabled)))

(defmacro get-histogram
  "Get details about a histogram displayed at chrome://histogram.

     |name| - Histogram name, e.g. 'Accessibility.CrosAutoclick'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [histogram] where:

     |histogram| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([name] (gen-call :function ::get-histogram &form name)))

(defmacro run-crostini-installer
  "Run the crostini installer GUI to install the default crostini vm / container and create sshfs mount.  The installer
   launches the crostini terminal app on completion.  The installer expects that crostini is not already installed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::run-crostini-installer &form)))

(defmacro run-crostini-uninstaller
  "Run the crostini uninstaller GUI to remove the default crostini vm / container. The callback is invoked upon completion.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::run-crostini-uninstaller &form)))

(defmacro set-crostini-enabled
  "Enable/disable Crostini in preferences.

     |enabled| - Enable Crostini.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([enabled] (gen-call :function ::set-crostini-enabled &form enabled)))

(defmacro take-screenshot
  "Takes a screenshot and returns the data in base64 encoded PNG format.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [base64-png] where:

     |base64-png| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::take-screenshot &form)))

(defmacro bootstrap-machine-learning-service
  "Makes a basic request to ML Service, triggering 1. ML Service daemon startup, and 2. the initial D-Bus -> Mojo IPC
   bootstrap.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::bootstrap-machine-learning-service &form)))

(defmacro set-assistant-enabled
  "Enable/disable the Google Assistant

     |enabled| - ?
     |timeout-ms| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([enabled timeout-ms] (gen-call :function ::set-assistant-enabled &form enabled timeout-ms)))

(defmacro send-assistant-text-query
  "Send a text query via Google Assistant.

     |query| - ?
     |timeout-ms| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([query timeout-ms] (gen-call :function ::send-assistant-text-query &form query timeout-ms)))

(defmacro set-crostini-app-scaled
  "Enable/disable a Crostini app's 'scaled' property.

     |app-id| - The Crostini application ID.
     |scaled| - The app is 'scaled' when shown, which means it uses low display           density.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id scaled] (gen-call :function ::set-crostini-app-scaled &form app-id scaled)))

(defmacro ensure-window-service-client-has-drawn-window
  "Ensure that the Window Service client identified by |clientName| has drawn any window. |callback| is invoked with true if
   the client has drawn anything or when it does so before the time out. Otherwise, an error is raised when timeout happens.

     |client-name| - ?
     |timeout-ms| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([client-name timeout-ms] (gen-call :function ::ensure-window-service-client-has-drawn-window &form client-name timeout-ms)))

(defmacro get-primary-display-scale-factor
  "Get the primary display scale factor. |callback| is invoked with the scale factor.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [scale-factor] where:

     |scale-factor| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-primary-display-scale-factor &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.autotest-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autotestPrivate",
   :since "27",
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
    {:id ::set-primary-button-right, :name "setPrimaryButtonRight", :params [{:name "right", :type "boolean"}]}
    {:id ::set-mouse-reverse-scroll,
     :name "setMouseReverseScroll",
     :since "62",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::get-visible-notifications,
     :name "getVisibleNotifications",
     :since "52",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "notifications", :type "[array-of-objects]"}]}}]}
    {:id ::get-play-store-state,
     :name "getPlayStoreState",
     :since "60",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-printer-list,
     :name "getPrinterList",
     :since "65",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "printers", :type "[array-of-autotestPrivate.Printers]"}]}}]}
    {:id ::is-app-shown,
     :name "isAppShown",
     :since "71",
     :callback? true,
     :params
     [{:name "app-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "app-shown", :type "boolean"}]}}]}
    {:id ::is-arc-provisioned,
     :name "isArcProvisioned",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "arc-provisioned", :type "boolean"}]}}]}
    {:id ::get-arc-app,
     :name "getArcApp",
     :since "73",
     :callback? true,
     :params
     [{:name "app-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "package", :type "object"}]}}]}
    {:id ::get-arc-package,
     :name "getArcPackage",
     :since "73",
     :callback? true,
     :params
     [{:name "package-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "package", :type "object"}]}}]}
    {:id ::launch-arc-app,
     :name "launchArcApp",
     :since "73",
     :callback? true,
     :params
     [{:name "app-id", :type "string"}
      {:name "intent", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "app-launched", :type "boolean"}]}}]}
    {:id ::launch-app,
     :name "launchApp",
     :since "71",
     :callback? true,
     :params [{:name "app-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::close-app,
     :name "closeApp",
     :since "73",
     :callback? true,
     :params [{:name "app-id", :type "string"} {:name "callback", :type :callback}]}
    {:id ::update-printer,
     :name "updatePrinter",
     :since "68",
     :params [{:name "printer", :type "autotestPrivate.Printer"}]}
    {:id ::remove-printer, :name "removePrinter", :since "68", :params [{:name "printer-id", :type "string"}]}
    {:id ::set-play-store-enabled,
     :name "setPlayStoreEnabled",
     :since "60",
     :callback? true,
     :params [{:name "enabled", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::get-histogram,
     :name "getHistogram",
     :since "71",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "histogram", :type "object"}]}}]}
    {:id ::run-crostini-installer,
     :name "runCrostiniInstaller",
     :since "70",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::run-crostini-uninstaller,
     :name "runCrostiniUninstaller",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::set-crostini-enabled,
     :name "setCrostiniEnabled",
     :since "71",
     :callback? true,
     :params [{:name "enabled", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::take-screenshot,
     :name "takeScreenshot",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "base64-png", :type "string"}]}}]}
    {:id ::bootstrap-machine-learning-service,
     :name "bootstrapMachineLearningService",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::set-assistant-enabled,
     :name "setAssistantEnabled",
     :since "72",
     :callback? true,
     :params
     [{:name "enabled", :type "boolean"} {:name "timeout-ms", :type "integer"} {:name "callback", :type :callback}]}
    {:id ::send-assistant-text-query,
     :name "sendAssistantTextQuery",
     :since "73",
     :callback? true,
     :params
     [{:name "query", :type "string"}
      {:name "timeout-ms", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "object"}]}}]}
    {:id ::set-crostini-app-scaled,
     :name "setCrostiniAppScaled",
     :since "73",
     :callback? true,
     :params [{:name "app-id", :type "string"} {:name "scaled", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::ensure-window-service-client-has-drawn-window,
     :name "ensureWindowServiceClientHasDrawnWindow",
     :since "master",
     :callback? true,
     :params
     [{:name "client-name", :type "string"}
      {:name "timeout-ms", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::get-primary-display-scale-factor,
     :name "getPrimaryDisplayScaleFactor",
     :since "master",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "scale-factor", :type "double"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))