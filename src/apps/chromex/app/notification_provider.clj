(ns chromex.app.notification-provider
  "Use the chrome.notificationProvider API to intercept
   notifications that would otherwise go into the Chrome Notification Center,
   get notifiers' information, and inform notifiers about users' actions on the
   notifications.
   
     * available since Chrome 50
     * https://developer.chrome.com/extensions/notificationProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro notify-on-cleared
  "Inform the notifier that the user cleared a notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was closed.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [wasCleared] where:
   
     |wasCleared| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-wasCleared.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-notifyOnCleared."
  ([notifier-id notification-id #_callback] (gen-call :function ::notify-on-cleared &form notifier-id notification-id)))

(defmacro notify-on-clicked
  "Inform the notifier that the user clicked in a non-button area of a notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was clicked on.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [matchExists] where:
   
     |matchExists| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-matchExists.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-notifyOnClicked."
  ([notifier-id notification-id #_callback] (gen-call :function ::notify-on-clicked &form notifier-id notification-id)))

(defmacro notify-on-button-clicked
  "Inform the notifier that the user pressed a button in the notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was clicked on its button.
     |buttonIndex| - The index of the button that was clicked.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [matchExists] where:
   
     |matchExists| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-matchExists.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-notifyOnButtonClicked."
  ([notifier-id notification-id button-index #_callback] (gen-call :function ::notify-on-button-clicked &form notifier-id notification-id button-index)))

(defmacro notify-on-permission-level-changed
  "Inform the notifier that the user changed the permission level of that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notifierType| - The type of the notifier that sent the notification.
     |level| - The perission level of the notifier
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [wasChanged] where:
   
     |wasChanged| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-wasChanged.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-notifyOnPermissionLevelChanged."
  ([notifier-id notifier-type level #_callback] (gen-call :function ::notify-on-permission-level-changed &form notifier-id notifier-type level)))

(defmacro notify-on-show-settings
  "Inform the notifier that the user chose to see advanced settings of that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notifierType| - The type of the notifier that sent the notification.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [hasSettings] where:
   
     |hasSettings| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-hasSettings.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-notifyOnShowSettings."
  ([notifier-id notifier-type #_callback] (gen-call :function ::notify-on-show-settings &form notifier-id notifier-type)))

(defmacro get-notifier
  "To get a notifier from it's notifier ID.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [notifier] where:
   
     |notifier| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-notifier.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-getNotifier."
  ([#_callback] (gen-call :function ::get-notifier &form)))

(defmacro get-all-notifiers
  "To get all the notifiers that could send notifications.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [notifiers] where:
   
     |notifiers| - See https://developer.chrome.com/extensions/notificationProvider#property-callback-notifiers.
   
   See https://developer.chrome.com/extensions/notificationProvider#method-getAllNotifiers."
  ([#_callback] (gen-call :function ::get-all-notifiers &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "A new notification is created.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/notificationProvider#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-updated-events
  "A notification is updated by the notifier.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/notificationProvider#event-onUpdated."
  ([channel & args] (apply gen-call :event ::on-updated &form channel args)))

(defmacro tap-on-cleared-events
  "A notification is cleared by the notifier.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/notificationProvider#event-onCleared."
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