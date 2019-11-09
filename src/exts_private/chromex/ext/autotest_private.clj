(ns chromex.ext.autotest-private
  "API for integration testing. To be used on test images with a test component
   extension.

     * available since Chrome 33"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro initialize-events
  "Must be called to allow autotestPrivateAPI events to be fired."
  ([] (gen-call :function ::initialize-events &form)))

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

(defmacro get-all-enterprise-policies
  "Get state of the policies. Will contain device policies and policies from the active profile. The policy values are
   formatted as they would be for exporting in chrome://policy.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [all-policies] where:

     |all-policies| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-all-enterprise-policies &form)))

(defmacro refresh-enterprise-policies
  "Refreshes the Enterprise Policies.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::refresh-enterprise-policies &form)))

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

(defmacro get-arc-start-time
  "Get ARC start time.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [start-time] where:

     |start-time| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-arc-start-time &form)))

(defmacro get-arc-state
  "Get state of the ARC session.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-arc-state &form)))

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
  "Returns true if ARC is provisioned. [deprecated='Use getArcState()']

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

(defmacro get-clipboard-text-data
  "Get text from ui::Clipboard.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-clipboard-text-data &form)))

(defmacro set-clipboard-text-data
  "Set text in ui::Clipbaord.

     |data| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([data] (gen-call :function ::set-clipboard-text-data &form data)))

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

(defmacro export-crostini
  "Export the crostini container.

     |path| - The path in Downloads to save the export.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([path] (gen-call :function ::export-crostini &form path)))

(defmacro import-crostini
  "Import the crostini container.

     |path| - The path in Downloads to read the import.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([path] (gen-call :function ::import-crostini &form path)))

(defmacro register-component
  "Register a component with CrOSComponentManager.

     |name| - The name of the component.
     |path| - Path to the component."
  ([name path] (gen-call :function ::register-component &form name path)))

(defmacro take-screenshot
  "Takes a screenshot and returns the data in base64 encoded PNG format.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [base64-png] where:

     |base64-png| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::take-screenshot &form)))

(defmacro take-screenshot-for-display
  "Tasks a screenshot for a display.

     |display-id| - the display id of the display.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [base64-png] where:

     |base64-png| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id] (gen-call :function ::take-screenshot-for-display &form display-id)))

(defmacro bootstrap-machine-learning-service
  "Makes a basic request to ML Service, triggering 1. ML Service daemon startup, and 2. the initial D-Bus -> Mojo IPC
   bootstrap.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::bootstrap-machine-learning-service &form)))

(defmacro set-assistant-enabled
  "Enables/disables the Google Assistant.

     |enabled| - ?
     |timeout-ms| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([enabled timeout-ms] (gen-call :function ::set-assistant-enabled &form enabled timeout-ms)))

(defmacro enable-assistant-and-wait-for-ready
  "Bring up the Assistant service, and wait for the ready signal.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::enable-assistant-and-wait-for-ready &form)))

(defmacro send-assistant-text-query
  "Sends a text query via Google Assistant.

     |query| - ?
     |timeout-ms| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([query timeout-ms] (gen-call :function ::send-assistant-text-query &form query timeout-ms)))

(defmacro wait-for-assistant-query-status
  "Invokes |callback| once the current text/voice interaction is completed. Responds with the the query status if any valid
   response was caught before the timeout. This API should be called before sending the query. To use it for testing a voice
   query via OKG in Autotest, for example, you can do:// Enable hotword setting for Assistant.
   assistant_util.enable_hotword();// Call this API with your callback function.
   chrome.autotestPrivate.waitForAssistantQueryStatus(timeout_s,        function(status) {...});then start Assistant via OKG
   and send voice query before timeout.TODO(meilinw@): disable warmer welcome to avoid an unintended early return of this API
   when launching Assistant via hotkey. TODO(meilinw@): update the comment above to use Tast instead after adding API to
   enable hotword in Tast.

     |timeout-s| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([timeout-s] (gen-call :function ::wait-for-assistant-query-status &form timeout-s)))

(defmacro set-whitelisted-pref
  "Set value for the specified user pref in the pref tree.

     |pref-name| - ?
     |value| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([pref-name value] (gen-call :function ::set-whitelisted-pref &form pref-name value)))

(defmacro set-crostini-app-scaled
  "Enable/disable a Crostini app's 'scaled' property.

     |app-id| - The Crostini application ID.
     |scaled| - The app is 'scaled' when shown, which means it uses low display           density.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([app-id scaled] (gen-call :function ::set-crostini-app-scaled &form app-id scaled)))

(defmacro get-primary-display-scale-factor
  "Get the primary display scale factor. |callback| is invoked with the scale factor.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [scale-factor] where:

     |scale-factor| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-primary-display-scale-factor &form)))

(defmacro is-tablet-mode-enabled
  "Get the tablet mode enabled status. |callback| is invoked with the tablet mode enablement status.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [enabled] where:

     |enabled| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-tablet-mode-enabled &form)))

(defmacro set-tablet-mode-enabled
  "Enable/disable tablet mode. After calling this function, it won't be possible to physically switch to/from tablet mode
   since that functionality will be disabled.

     |enabled| - if set, enable tablet mode.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [enabled] where:

     |enabled| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([enabled] (gen-call :function ::set-tablet-mode-enabled &form enabled)))

(defmacro get-all-installed-apps
  "Get the list of all installed applications

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [apps] where:

     |apps| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-all-installed-apps &form)))

(defmacro get-shelf-items
  "Get the list of all shelf items

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [items] where:

     |items| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-shelf-items &form)))

(defmacro get-shelf-auto-hide-behavior
  "Get the shelf auto hide behavior.

     |display-id| - display that contains the shelf. |callback| is invoked with the shelf auto hide behavior. Possible
                    behavior values are: 'always', 'never' or 'hidden'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [behavior] where:

     |behavior| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id] (gen-call :function ::get-shelf-auto-hide-behavior &form display-id)))

(defmacro set-shelf-auto-hide-behavior
  "Set the shelf auto hide behavior.

     |display-id| - display that contains the shelf.
     |behavior| - an enum of 'always', 'never' or 'hidden'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id behavior] (gen-call :function ::set-shelf-auto-hide-behavior &form display-id behavior)))

(defmacro get-shelf-alignment
  "Get the shelf alignment.

     |display-id| - display that contains the shelf. |callback| is invoked with the shelf alignment type.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [alignment] where:

     |alignment| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id] (gen-call :function ::get-shelf-alignment &form display-id)))

(defmacro set-shelf-alignment
  "Set the shelf alignment.

     |display-id| - display that contains the shelf.
     |alignment| - the type of alignment to set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id alignment] (gen-call :function ::set-shelf-alignment &form display-id alignment)))

(defmacro set-overview-mode-state
  "Enter or exit the overview mode.

     |start| - whether entering to or exiting from the overview mode.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [finished] where:

     |finished| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([start] (gen-call :function ::set-overview-mode-state &form start)))

(defmacro show-virtual-keyboard-if-enabled
  "Show virtual keyboard of the current input method if it's available."
  ([] (gen-call :function ::show-virtual-keyboard-if-enabled &form)))

(defmacro set-arc-app-window-state
  "Send WM event to change the ARC app window's window state.

     |package-name| - the package name of the ARC app window.
     |change| - WM event type to send to the ARC app window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [current-type] where:

     |current-type| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([package-name change] (gen-call :function ::set-arc-app-window-state &form package-name change)))

(defmacro get-arc-app-window-state
  "Get ARC app window's window state.

     |package-name| - the package name of the ARC app window. |callback| is invoked with the window state.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [current-type] where:

     |current-type| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([package-name] (gen-call :function ::get-arc-app-window-state &form package-name)))

(defmacro get-arc-app-window-info
  "Get various information on an ARC window.

     |package-name| - the package name of the ARC app window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([package-name] (gen-call :function ::get-arc-app-window-info &form package-name)))

(defmacro arc-app-tracing-start
  "Start ARC performance tracing for the active ARC app window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::arc-app-tracing-start &form)))

(defmacro arc-app-tracing-stop-and-analyze
  "Stop ARC performance tracing if it was started and analyze results.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::arc-app-tracing-stop-and-analyze &form)))

(defmacro swap-windows-in-split-view
  "Swap the windows in the split view.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::swap-windows-in-split-view &form)))

(defmacro set-arc-app-window-focus
  "Set ARC app window focused.

     |package-name| - the package name of the ARC app window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([package-name] (gen-call :function ::set-arc-app-window-focus &form package-name)))

(defmacro wait-for-display-rotation
  "Invokes the callback when the display rotation animation is finished, or invokes it immediately if it is not animating. The
   callback argument is true if the display's rotation is same as |rotation|, or false otherwise.

     |display-id| - display that contains the shelf.
     |rotation| - the target rotation.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([display-id rotation] (gen-call :function ::wait-for-display-rotation &form display-id rotation)))

(defmacro get-app-window-list
  "Get information on all application windows. Callback will be called with the list of |AppWindowInfo| dictionary.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window-list] where:

     |window-list| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-app-window-list &form)))

(defmacro set-app-window-state
  "Send WM event to change the app window's window state.

     |id| - the id of the window
     |change| - WM event type to send to the app window.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [current-type] where:

     |current-type| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id change] (gen-call :function ::set-app-window-state &form id change)))

(defmacro close-app-window
  "Closes an app window given by 'id'.

     |id| - the id of the window

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::close-app-window &form id)))

(defmacro install-pwa-for-current-url
  "Installs the Progressive Web App (PWA) that is in the current URL.

     |timeout-ms| - Timeout in milliseconds for the operation to complete.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [app-id] where:

     |app-id| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([timeout-ms] (gen-call :function ::install-pwa-for-current-url &form timeout-ms)))

(defmacro activate-accelerator
  "Activates shortcut.

     |accelerator| - the accelerator to activate.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([accelerator] (gen-call :function ::activate-accelerator &form accelerator)))

(defmacro wait-for-launcher-state
  "Wwait until the launcher is transitionto the |launcherState|, if it's not in that state.

     |launcher-state| - the target launcher state.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([launcher-state] (gen-call :function ::wait-for-launcher-state &form launcher-state)))

(defmacro create-new-desk
  "Creates a new desk if the maximum number of desks has not been reached.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::create-new-desk &form)))

(defmacro activate-desk-at-index
  "Activates the desk at the given |index| triggering the activate-desk animation.

     |index| - the zero-based index of the desk desired to be activated.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([index] (gen-call :function ::activate-desk-at-index &form index)))

(defmacro remove-active-desk
  "Removes the currently active desk and triggers the remove-desk animation.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::remove-active-desk &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-clipboard-data-changed-events
  "Fired when the data in ui::Clipboard is changed.

   Events will be put on the |channel| with signature [::on-clipboard-data-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-clipboard-data-changed &form channel args)))

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
   :since "33",
   :functions
   [{:id ::initialize-events, :name "initializeEvents", :since "future"}
    {:id ::logout, :name "logout"}
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
    {:id ::get-all-enterprise-policies,
     :name "getAllEnterprisePolicies",
     :since "77",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "all-policies", :type "any"}]}}]}
    {:id ::refresh-enterprise-policies,
     :name "refreshEnterprisePolicies",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
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
    {:id ::get-arc-start-time,
     :name "getArcStartTime",
     :since "78",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "start-time", :type "double"}]}}]}
    {:id ::get-arc-state,
     :name "getArcState",
     :since "74",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
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
    {:id ::get-clipboard-text-data,
     :name "getClipboardTextData",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "data", :type "string"}]}}]}
    {:id ::set-clipboard-text-data,
     :name "setClipboardTextData",
     :since "future",
     :callback? true,
     :params [{:name "data", :type "string"} {:name "callback", :type :callback}]}
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
    {:id ::export-crostini,
     :name "exportCrostini",
     :since "75",
     :callback? true,
     :params [{:name "path", :type "string"} {:name "callback", :type :callback}]}
    {:id ::import-crostini,
     :name "importCrostini",
     :since "75",
     :callback? true,
     :params [{:name "path", :type "string"} {:name "callback", :type :callback}]}
    {:id ::register-component,
     :name "registerComponent",
     :since "78",
     :params [{:name "name", :type "string"} {:name "path", :type "string"}]}
    {:id ::take-screenshot,
     :name "takeScreenshot",
     :since "71",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "base64-png", :type "string"}]}}]}
    {:id ::take-screenshot-for-display,
     :name "takeScreenshotForDisplay",
     :since "future",
     :callback? true,
     :params
     [{:name "display-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "base64-png", :type "string"}]}}]}
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
    {:id ::enable-assistant-and-wait-for-ready,
     :name "enableAssistantAndWaitForReady",
     :since "master",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::send-assistant-text-query,
     :name "sendAssistantTextQuery",
     :since "73",
     :callback? true,
     :params
     [{:name "query", :type "string"}
      {:name "timeout-ms", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "autotestPrivate.AssistantQueryStatus"}]}}]}
    {:id ::wait-for-assistant-query-status,
     :name "waitForAssistantQueryStatus",
     :since "future",
     :callback? true,
     :params
     [{:name "timeout-s", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "autotestPrivate.AssistantQueryStatus"}]}}]}
    {:id ::set-whitelisted-pref,
     :name "setWhitelistedPref",
     :since "74",
     :callback? true,
     :params [{:name "pref-name", :type "string"} {:name "value", :type "any"} {:name "callback", :type :callback}]}
    {:id ::set-crostini-app-scaled,
     :name "setCrostiniAppScaled",
     :since "73",
     :callback? true,
     :params [{:name "app-id", :type "string"} {:name "scaled", :type "boolean"} {:name "callback", :type :callback}]}
    {:id ::get-primary-display-scale-factor,
     :name "getPrimaryDisplayScaleFactor",
     :since "73",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "scale-factor", :type "double"}]}}]}
    {:id ::is-tablet-mode-enabled,
     :name "isTabletModeEnabled",
     :since "74",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "enabled", :type "boolean"}]}}]}
    {:id ::set-tablet-mode-enabled,
     :name "setTabletModeEnabled",
     :since "74",
     :callback? true,
     :params
     [{:name "enabled", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "enabled", :type "boolean"}]}}]}
    {:id ::get-all-installed-apps,
     :name "getAllInstalledApps",
     :since "78",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "apps", :type "[array-of-objects]"}]}}]}
    {:id ::get-shelf-items,
     :name "getShelfItems",
     :since "78",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "items", :type "[array-of-objects]"}]}}]}
    {:id ::get-shelf-auto-hide-behavior,
     :name "getShelfAutoHideBehavior",
     :since "75",
     :callback? true,
     :params
     [{:name "display-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "behavior", :type "string"}]}}]}
    {:id ::set-shelf-auto-hide-behavior,
     :name "setShelfAutoHideBehavior",
     :since "75",
     :callback? true,
     :params
     [{:name "display-id", :type "string"} {:name "behavior", :type "string"} {:name "callback", :type :callback}]}
    {:id ::get-shelf-alignment,
     :name "getShelfAlignment",
     :since "76",
     :callback? true,
     :params
     [{:name "display-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "alignment", :type "autotestPrivate.ShelfAlignmentType"}]}}]}
    {:id ::set-shelf-alignment,
     :name "setShelfAlignment",
     :since "76",
     :callback? true,
     :params
     [{:name "display-id", :type "string"}
      {:name "alignment", :type "autotestPrivate.ShelfAlignmentType"}
      {:name "callback", :type :callback}]}
    {:id ::set-overview-mode-state,
     :name "setOverviewModeState",
     :since "78",
     :callback? true,
     :params
     [{:name "start", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "finished", :type "boolean"}]}}]}
    {:id ::show-virtual-keyboard-if-enabled, :name "showVirtualKeyboardIfEnabled", :since "75"}
    {:id ::set-arc-app-window-state,
     :name "setArcAppWindowState",
     :since "future",
     :deprecated "Use setAppWindowState",
     :callback? true,
     :params
     [{:name "package-name", :type "string"}
      {:name "change", :type "autotestPrivate.WindowStateChangeDict"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "current-type", :type "autotestPrivate.WindowStateType"}]}}]}
    {:id ::get-arc-app-window-state,
     :name "getArcAppWindowState",
     :since "78",
     :callback? true,
     :params
     [{:name "package-name", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "current-type", :type "autotestPrivate.WindowStateType"}]}}]}
    {:id ::get-arc-app-window-info,
     :name "getArcAppWindowInfo",
     :since "future",
     :deprecated "Use getAppWindowList",
     :callback? true,
     :params
     [{:name "package-name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::arc-app-tracing-start,
     :name "arcAppTracingStart",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::arc-app-tracing-stop-and-analyze,
     :name "arcAppTracingStopAndAnalyze",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}
    {:id ::swap-windows-in-split-view,
     :name "swapWindowsInSplitView",
     :since "78",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::set-arc-app-window-focus,
     :name "setArcAppWindowFocus",
     :since "78",
     :callback? true,
     :params [{:name "package-name", :type "string"} {:name "callback", :type :callback}]}
    {:id ::wait-for-display-rotation,
     :name "waitForDisplayRotation",
     :since "future",
     :callback? true,
     :params
     [{:name "display-id", :type "string"}
      {:name "rotation", :type "unknown-type"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::get-app-window-list,
     :name "getAppWindowList",
     :since "future",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "window-list", :type "[array-of-objects]"}]}}]}
    {:id ::set-app-window-state,
     :name "setAppWindowState",
     :since "future",
     :callback? true,
     :params
     [{:name "id", :type "integer"}
      {:name "change", :type "autotestPrivate.WindowStateChangeDict"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "current-type", :type "autotestPrivate.WindowStateType"}]}}]}
    {:id ::close-app-window,
     :name "closeAppWindow",
     :since "future",
     :callback? true,
     :params [{:name "id", :type "integer"} {:name "callback", :type :callback}]}
    {:id ::install-pwa-for-current-url,
     :name "installPWAForCurrentURL",
     :since "future",
     :callback? true,
     :params
     [{:name "timeout-ms", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "app-id", :type "string"}]}}]}
    {:id ::activate-accelerator,
     :name "activateAccelerator",
     :since "future",
     :callback? true,
     :params
     [{:name "accelerator", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::wait-for-launcher-state,
     :name "waitForLauncherState",
     :since "future",
     :callback? true,
     :params [{:name "launcher-state", :type "unknown-type"} {:name "callback", :type :callback}]}
    {:id ::create-new-desk,
     :name "createNewDesk",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::activate-desk-at-index,
     :name "activateDeskAtIndex",
     :since "future",
     :callback? true,
     :params
     [{:name "index", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::remove-active-desk,
     :name "removeActiveDesk",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}],
   :events [{:id ::on-clipboard-data-changed, :name "onClipboardDataChanged", :since "future"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))