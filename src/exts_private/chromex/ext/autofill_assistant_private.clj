(ns chromex.ext.autofill-assistant-private
  "Use the chrome.autofillAssistantPrivate API to interact with
   the Autofill Assistant execution engine. Access is restricted to a set of
   extensions part of an allowlist.

   The correct usage of this API is to to first call create, then
   add listeners for the Events and then call start.
   onActionsChanged will be called when actions become available
   and can be executed via performAction.

     * available since Chrome master"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates the autofill assistant controller and cleans up and existing controller if applicable.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::create &form)))

(defmacro start
  "Starts the controller with the given parameters.

     |parameters| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parameters] (gen-call :function ::start &form parameters)))

(defmacro perform-action
  "Performs an action.

     |index| - The index into the |actions| array provided by          |onActionsChanged|.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([index] (gen-call :function ::perform-action &form index)))

(defmacro provide-user-data
  "Set user data to configure collect data actions.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::provide-user-data &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-status-message-changed-events
  "Fires when the status message changed.|message| The new status of the autofill assistant controller.

   Events will be put on the |channel| with signature [::on-status-message-changed [message]] where:

     |message| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-status-message-changed &form channel args)))

(defmacro tap-on-actions-changed-events
  "Fires when a set of actions has changed.|actions| The new list of available actions.

   Events will be put on the |channel| with signature [::on-actions-changed [actions]] where:

     |actions| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-actions-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.autofill-assistant-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autofillAssistantPrivate",
   :since "master",
   :functions
   [{:id ::create, :name "create", :callback? true, :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::start,
     :name "start",
     :callback? true,
     :params [{:name "parameters", :type "any"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::perform-action,
     :name "performAction",
     :callback? true,
     :params [{:name "index", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::provide-user-data,
     :name "provideUserData",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-status-message-changed, :name "onStatusMessageChanged", :params [{:name "message", :type "string"}]}
    {:id ::on-actions-changed, :name "onActionsChanged", :params [{:name "actions", :type "[array-of-objects]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))