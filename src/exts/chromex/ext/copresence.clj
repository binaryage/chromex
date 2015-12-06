(ns chromex.ext.copresence
  "Use the chrome.copresence API to communicate with other nearby
   devices using Google's copresence service.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/copresence"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro execute
  "Executes a set of copresence operations in one batch. They will either all be executed, or none will be executed (due to an
   error in one or more of them). Publish/Subscribe operations are executed in the order that they exist in the array.
   Unpublish and Unsubscribe are processsed at the end, again, in the order that they exist in the array.
   
     |callback| - Callback to return the status of a completed batchExecute() call.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([operations #_callback] (gen-call :function ::execute &form operations)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-messages-received-events
  "Fired when new messages arrive.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-messages-received &form channel args)))
(defmacro tap-on-status-updated-events
  "Fired when a new copresence status update is available.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-status-updated &form channel args)))

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
  {:namespace "chrome.copresence",
   :since "48",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))