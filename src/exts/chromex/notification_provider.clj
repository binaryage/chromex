(ns chromex.notification-provider
  "Use the chrome.notificationProvider API to intercept
   notifications that would otherwise go into the Chrome Notification Center,
   get notifiers' information, and inform notifiers about users' actions on the
   notifications.
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/notificationProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro notify-on-cleared
  "Inform the notifier that the user cleared a notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was closed.
     |callback| - Called to indicate whether a matching notification existed."
  [notifier-id notification-id #_callback]
  (gen-call :function ::notify-on-cleared (meta &form) notifier-id notification-id))

(defmacro notify-on-clicked
  "Inform the notifier that the user clicked in a non-button area of a notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was clicked on.
     |callback| - Called to indicate whether a matching notification existed."
  [notifier-id notification-id #_callback]
  (gen-call :function ::notify-on-clicked (meta &form) notifier-id notification-id))

(defmacro notify-on-button-clicked
  "Inform the notifier that the user pressed a button in the notification sent from that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notificationId| - The id of the notification that was clicked on its button.
     |buttonIndex| - The index of the button that was clicked.
     |callback| - Called to indicate whether a matching notification existed."
  [notifier-id notification-id button-index #_callback]
  (gen-call :function ::notify-on-button-clicked (meta &form) notifier-id notification-id button-index))

(defmacro notify-on-permission-level-changed
  "Inform the notifier that the user changed the permission level of that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notifierType| - The type of the notifier that sent the notification.
     |level| - The perission level of the notifier
     |callback| - Called to indicate whether the permission level was changed."
  [notifier-id notifier-type level #_callback]
  (gen-call :function ::notify-on-permission-level-changed (meta &form) notifier-id notifier-type level))

(defmacro notify-on-show-settings
  "Inform the notifier that the user chose to see advanced settings of that notifier.
   
     |notifierId| - The id of the notifier that sent the notification.
     |notifierType| - The type of the notifier that sent the notification.
     |callback| - Called to indicate whether the notifier has extra settings."
  [notifier-id notifier-type #_callback]
  (gen-call :function ::notify-on-show-settings (meta &form) notifier-id notifier-type))

(defmacro get-notifier
  "To get a notifier from it's notifier ID.
   
     |callback| - Returns the notifier object of the given ID."
  [#_callback]
  (gen-call :function ::get-notifier (meta &form)))

(defmacro get-all-notifiers
  "To get all the notifiers that could send notifications.
   
     |callback| - Returns the set of notifiers currently in the system."
  [#_callback]
  (gen-call :function ::get-all-notifiers (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-created
  "A new notification is created."
  [channel]
  (gen-call :event ::on-created (meta &form) channel))

(defmacro tap-on-updated
  "A notification is updated by the notifier."
  [channel]
  (gen-call :event ::on-updated (meta &form) channel))

(defmacro tap-on-cleared
  "A notification is cleared by the notifier."
  [channel]
  (gen-call :event ::on-cleared (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.notificationProvider",
   :since "47",
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