(ns chromex.app.notification-provider
  "Use the chrome.notificationProvider API to intercept
   notifications that would otherwise go into the Chrome Notification Center,
   get notifiers' information, and inform notifiers about users' actions on the
   notifications.

     * available since Chrome 50
     * https://developer.chrome.com/apps/notificationProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro notify-on-cleared
  "Inform the notifier that the user cleared a notification sent from that notifier.

     |notifier-id| - The id of the notifier that sent the notification.
     |notification-id| - The id of the notification that was closed.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [was-cleared] where:

     |was-cleared| - https://developer.chrome.com/apps/notificationProvider#property-callback-wasCleared.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-notifyOnCleared."
  ([notifier-id notification-id] (gen-call :function ::notify-on-cleared &form notifier-id notification-id)))

(defmacro notify-on-clicked
  "Inform the notifier that the user clicked in a non-button area of a notification sent from that notifier.

     |notifier-id| - The id of the notifier that sent the notification.
     |notification-id| - The id of the notification that was clicked on.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [match-exists] where:

     |match-exists| - https://developer.chrome.com/apps/notificationProvider#property-callback-matchExists.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-notifyOnClicked."
  ([notifier-id notification-id] (gen-call :function ::notify-on-clicked &form notifier-id notification-id)))

(defmacro notify-on-button-clicked
  "Inform the notifier that the user pressed a button in the notification sent from that notifier.

     |notifier-id| - The id of the notifier that sent the notification.
     |notification-id| - The id of the notification that was clicked on its button.
     |button-index| - The index of the button that was clicked.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [match-exists] where:

     |match-exists| - https://developer.chrome.com/apps/notificationProvider#property-callback-matchExists.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-notifyOnButtonClicked."
  ([notifier-id notification-id button-index] (gen-call :function ::notify-on-button-clicked &form notifier-id notification-id button-index)))

(defmacro notify-on-permission-level-changed
  "Inform the notifier that the user changed the permission level of that notifier.

     |notifier-id| - The id of the notifier that sent the notification.
     |notifier-type| - The type of the notifier that sent the notification.
     |level| - The perission level of the notifier

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [was-changed] where:

     |was-changed| - https://developer.chrome.com/apps/notificationProvider#property-callback-wasChanged.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-notifyOnPermissionLevelChanged."
  ([notifier-id notifier-type level] (gen-call :function ::notify-on-permission-level-changed &form notifier-id notifier-type level)))

(defmacro notify-on-show-settings
  "Inform the notifier that the user chose to see advanced settings of that notifier.

     |notifier-id| - The id of the notifier that sent the notification.
     |notifier-type| - The type of the notifier that sent the notification.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [has-settings] where:

     |has-settings| - https://developer.chrome.com/apps/notificationProvider#property-callback-hasSettings.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-notifyOnShowSettings."
  ([notifier-id notifier-type] (gen-call :function ::notify-on-show-settings &form notifier-id notifier-type)))

(defmacro get-notifier
  "To get a notifier from it's notifier ID.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [notifier] where:

     |notifier| - https://developer.chrome.com/apps/notificationProvider#property-callback-notifier.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-getNotifier."
  ([] (gen-call :function ::get-notifier &form)))

(defmacro get-all-notifiers
  "To get all the notifiers that could send notifications.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [notifiers] where:

     |notifiers| - https://developer.chrome.com/apps/notificationProvider#property-callback-notifiers.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/notificationProvider#method-getAllNotifiers."
  ([] (gen-call :function ::get-all-notifiers &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "A new notification is created.

   Events will be put on the |channel| with signature [::on-created [notifier-id notification-id options]] where:

     |notifier-id| - The id of the notifier that sent the new notification.
     |notification-id| - The id of the newly created notification.
     |options| - The content of the notification: type, title, message etc.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notificationProvider#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-updated-events
  "A notification is updated by the notifier.

   Events will be put on the |channel| with signature [::on-updated [notifier-id notification-id options]] where:

     |notifier-id| - The id of the notifier that sent the updated notification.
     |notification-id| - The id of the updated notification.
     |options| - The content of the notification: type, title, message etc.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notificationProvider#event-onUpdated."
  ([channel & args] (apply gen-call :event ::on-updated &form channel args)))

(defmacro tap-on-cleared-events
  "A notification is cleared by the notifier.

   Events will be put on the |channel| with signature [::on-cleared [notifier-id notification-id]] where:

     |notifier-id| - The id of the notifier that cleared the notification.
     |notification-id| - The id of the cleared notification.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/notificationProvider#event-onCleared."
  ([channel & args] (apply gen-call :event ::on-cleared &form channel args)))

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
  {:namespace "chrome.notificationProvider",
   :since "50",
   :functions
   [{:id ::notify-on-cleared,
     :name "notifyOnCleared",
     :callback? true,
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notification-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "was-cleared", :type "boolean"}]}}]}
    {:id ::notify-on-clicked,
     :name "notifyOnClicked",
     :callback? true,
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notification-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "match-exists", :type "boolean"}]}}]}
    {:id ::notify-on-button-clicked,
     :name "notifyOnButtonClicked",
     :callback? true,
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notification-id", :type "string"}
      {:name "button-index", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "match-exists", :type "boolean"}]}}]}
    {:id ::notify-on-permission-level-changed,
     :name "notifyOnPermissionLevelChanged",
     :callback? true,
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notifier-type", :type "notificationProvider.NotifierType"}
      {:name "level", :type "notificationProvider.NotifierPermissionLevel"}
      {:name "callback", :type :callback, :callback {:params [{:name "was-changed", :type "boolean"}]}}]}
    {:id ::notify-on-show-settings,
     :name "notifyOnShowSettings",
     :callback? true,
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notifier-type", :type "notificationProvider.NotifierType"}
      {:name "callback", :type :callback, :callback {:params [{:name "has-settings", :type "boolean"}]}}]}
    {:id ::get-notifier,
     :name "getNotifier",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "notifier", :type "notificationProvider.Notifier"}]}}]}
    {:id ::get-all-notifiers,
     :name "getAllNotifiers",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "notifiers", :type "[array-of-notificationProvider.Notifiers]"}]}}]}],
   :events
   [{:id ::on-created,
     :name "onCreated",
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notification-id", :type "string"}
      {:name "options", :type "notifications.NotificationOptions"}]}
    {:id ::on-updated,
     :name "onUpdated",
     :params
     [{:name "notifier-id", :type "string"}
      {:name "notification-id", :type "string"}
      {:name "options", :type "notifications.NotificationOptions"}]}
    {:id ::on-cleared,
     :name "onCleared",
     :params [{:name "notifier-id", :type "string"} {:name "notification-id", :type "string"}]}]})

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