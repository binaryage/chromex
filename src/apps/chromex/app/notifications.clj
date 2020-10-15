(ns chromex.app.notifications
  "Use the chrome.notifications API to create rich notifications
   using templates and show these notifications to users in the system tray.

     * available since Chrome 38
     * https://developer.chrome.com/apps/notifications"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates and displays a notification.

     |notification-id| - Identifier of the notification. If not set or empty, an ID will automatically be generated. If it
                         matches an existing notification, this method first clears that notification before proceeding with
                         the create operation. The identifier may not be longer than 500 characters.The notificationId
                         parameter is required before Chrome 42.
     |options| - Contents of the notification.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [notification-id] where:

     |notification-id| - https://developer.chrome.com/apps/notifications#property-callback-notificationId.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notifications#method-create."
  ([notification-id options] (gen-call :function ::create &form notification-id options)))

(defmacro update
  "Updates an existing notification.

     |notification-id| - The id of the notification to be updated. This is returned by 'notifications.create' method.
     |options| - Contents of the notification to update to.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [was-updated] where:

     |was-updated| - https://developer.chrome.com/apps/notifications#property-callback-wasUpdated.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notifications#method-update."
  ([notification-id options] (gen-call :function ::update &form notification-id options)))

(defmacro clear
  "Clears the specified notification.

     |notification-id| - The id of the notification to be cleared. This is returned by 'notifications.create' method.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [was-cleared] where:

     |was-cleared| - https://developer.chrome.com/apps/notifications#property-callback-wasCleared.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notifications#method-clear."
  ([notification-id] (gen-call :function ::clear &form notification-id)))

(defmacro get-all
  "Retrieves all the notifications of this app or extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [notifications] where:

     |notifications| - https://developer.chrome.com/apps/notifications#property-callback-notifications.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notifications#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro get-permission-level
  "Retrieves whether the user has enabled notifications from this app or extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [level] where:

     |level| - https://developer.chrome.com/apps/notifications#property-callback-level.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notifications#method-getPermissionLevel."
  ([] (gen-call :function ::get-permission-level &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-closed-events
  "The notification closed, either by the system or by user action.

   Events will be put on the |channel| with signature [::on-closed [notification-id by-user]] where:

     |notification-id| - https://developer.chrome.com/apps/notifications#property-onClosed-notificationId.
     |by-user| - https://developer.chrome.com/apps/notifications#property-onClosed-byUser.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notifications#event-onClosed."
  ([channel & args] (apply gen-call :event ::on-closed &form channel args)))

(defmacro tap-on-clicked-events
  "The user clicked in a non-button area of the notification.

   Events will be put on the |channel| with signature [::on-clicked [notification-id]] where:

     |notification-id| - https://developer.chrome.com/apps/notifications#property-onClicked-notificationId.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notifications#event-onClicked."
  ([channel & args] (apply gen-call :event ::on-clicked &form channel args)))

(defmacro tap-on-button-clicked-events
  "The user pressed a button in the notification.

   Events will be put on the |channel| with signature [::on-button-clicked [notification-id button-index]] where:

     |notification-id| - https://developer.chrome.com/apps/notifications#property-onButtonClicked-notificationId.
     |button-index| - https://developer.chrome.com/apps/notifications#property-onButtonClicked-buttonIndex.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notifications#event-onButtonClicked."
  ([channel & args] (apply gen-call :event ::on-button-clicked &form channel args)))

(defmacro tap-on-permission-level-changed-events
  "The user changes the permission level.  As of Chrome 47, only ChromeOS has UI that dispatches this event.

   Events will be put on the |channel| with signature [::on-permission-level-changed [level]] where:

     |level| - https://developer.chrome.com/apps/notifications#property-onPermissionLevelChanged-level.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notifications#event-onPermissionLevelChanged."
  ([channel & args] (apply gen-call :event ::on-permission-level-changed &form channel args)))

(defmacro tap-on-show-settings-events
  "The user clicked on a link for the app's notification settings.  As of Chrome 47, only ChromeOS has UI that dispatches this
   event. As of Chrome 65, that UI has been removed from ChromeOS, too.

   Events will be put on the |channel| with signature [::on-show-settings []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notifications#event-onShowSettings."
  ([channel & args] (apply gen-call :event ::on-show-settings &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.notifications namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.notifications",
   :since "38",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "notification-id", :optional? true, :type "string"}
      {:name "options", :type "notifications.NotificationOptions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "notification-id", :type "string"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "notification-id", :type "string"}
      {:name "options", :type "notifications.NotificationOptions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "was-updated", :type "boolean"}]}}]}
    {:id ::clear,
     :name "clear",
     :callback? true,
     :params
     [{:name "notification-id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "was-cleared", :type "boolean"}]}}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "notifications", :type "object"}]}}]}
    {:id ::get-permission-level,
     :name "getPermissionLevel",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "level", :type "notifications.PermissionLevel"}]}}]}],
   :events
   [{:id ::on-closed,
     :name "onClosed",
     :params [{:name "notification-id", :type "string"} {:name "by-user", :type "boolean"}]}
    {:id ::on-clicked, :name "onClicked", :params [{:name "notification-id", :type "string"}]}
    {:id ::on-button-clicked,
     :name "onButtonClicked",
     :params [{:name "notification-id", :type "string"} {:name "button-index", :type "integer"}]}
    {:id ::on-permission-level-changed,
     :name "onPermissionLevelChanged",
     :params [{:name "level", :type "notifications.PermissionLevel"}]}
    {:id ::on-show-settings,
     :name "onShowSettings",
     :since "65",
     :deprecated "Custom notification settings button is no longer supported."}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))