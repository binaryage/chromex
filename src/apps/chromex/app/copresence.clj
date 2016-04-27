(ns chromex.app.copresence
  "Use the chrome.copresence API to communicate with other nearby
   devices using Google's copresence service.

     * available since Chrome 52
     * https://developer.chrome.com/apps/copresence"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro execute
  "Executes a set of copresence operations in one batch. They will either all be executed, or none will be executed (due to an
   error in one or more of them). Publish/Subscribe operations are executed in the order that they exist in the array.
   Unpublish and Unsubscribe are processsed at the end, again, in the order that they exist in the array.

     |operations| - https://developer.chrome.com/apps/copresence#property-execute-operations.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - https://developer.chrome.com/apps/copresence#property-callback-status.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/copresence#method-execute."
  ([operations] (gen-call :function ::execute &form operations)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-messages-received-events
  "Fired when new messages arrive.

   Events will be put on the |channel| with signature [::on-messages-received [subscription-id messages]] where:

     |subscription-id| - https://developer.chrome.com/apps/copresence#property-onMessagesReceived-subscriptionId.
     |messages| - https://developer.chrome.com/apps/copresence#property-onMessagesReceived-messages.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/copresence#event-onMessagesReceived."
  ([channel & args] (apply gen-call :event ::on-messages-received &form channel args)))

(defmacro tap-on-status-updated-events
  "Fired when a new copresence status update is available.

   Events will be put on the |channel| with signature [::on-status-updated [status]] where:

     |status| - https://developer.chrome.com/apps/copresence#property-onStatusUpdated-status.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/copresence#event-onStatusUpdated."
  ([channel & args] (apply gen-call :event ::on-status-updated &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.copresence namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.copresence",
   :since "52",
   :functions
   [{:id ::execute,
     :name "execute",
     :callback? true,
     :params
     [{:name "operations", :type "[array-of-copresence.Operations]"}
      {:name "callback", :type :callback, :callback {:params [{:name "status", :type "unknown-type"}]}}]}],
   :events
   [{:id ::on-messages-received,
     :name "onMessagesReceived",
     :params [{:name "subscription-id", :type "string"} {:name "messages", :type "[array-of-copresence.Messages]"}]}
    {:id ::on-status-updated, :name "onStatusUpdated", :params [{:name "status", :type "unknown-type"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))