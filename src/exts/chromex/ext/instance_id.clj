(ns chromex.ext.instance-id
  "Use chrome.instanceID to access the Instance ID service.

     * available since Chrome 46
     * https://developer.chrome.com/extensions/instanceID"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-id
  "Retrieves an identifier for the app instance. The instance ID will be returned by the callback. The same ID will be
   returned as long as the application identity has not been revoked or expired.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [instance-id] where:

     |instance-id| - An Instance ID assigned to the app instance.

   https://developer.chrome.com/extensions/instanceID#method-getID."
  ([] (gen-call :function ::get-id &form)))

(defmacro get-creation-time
  "Retrieves the time when the InstanceID has been generated. The creation time will be returned by the callback.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [creation-time] where:

     |creation-time| - The time when the Instance ID has been generated, represented in milliseconds since the epoch.

   https://developer.chrome.com/extensions/instanceID#method-getCreationTime."
  ([] (gen-call :function ::get-creation-time &form)))

(defmacro get-token
  "Return a token that allows the authorized entity to access the service defined by scope.

     |get-token-params| - Parameters for getToken.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [token] where:

     |token| - A token assigned by the requested service.

   https://developer.chrome.com/extensions/instanceID#method-getToken."
  ([get-token-params] (gen-call :function ::get-token &form get-token-params)))

(defmacro delete-token
  "Revokes a granted token.

     |delete-token-params| - Parameters for deleteToken.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   https://developer.chrome.com/extensions/instanceID#method-deleteToken."
  ([delete-token-params] (gen-call :function ::delete-token &form delete-token-params)))

(defmacro delete-id
  "Resets the app instance identifier and revokes all tokens associated with it.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   https://developer.chrome.com/extensions/instanceID#method-deleteID."
  ([] (gen-call :function ::delete-id &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-token-refresh-events
  "Fired when all the granted tokens need to be refreshed.

   Events will be put on the |channel| with signature [::on-token-refresh []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/instanceID#event-onTokenRefresh."
  ([channel & args] (apply gen-call :event ::on-token-refresh &form channel args)))

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
  {:namespace "chrome.instanceID",
   :since "46",
   :functions
   [{:id ::get-id,
     :name "getID",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "instance-id", :type "string"}]}}]}
    {:id ::get-creation-time,
     :name "getCreationTime",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "creation-time", :type "double"}]}}]}
    {:id ::get-token,
     :name "getToken",
     :callback? true,
     :params
     [{:name "get-token-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "token", :type "string"}]}}]}
    {:id ::delete-token,
     :name "deleteToken",
     :callback? true,
     :params [{:name "delete-token-params", :type "object"} {:name "callback", :type :callback}]}
    {:id ::delete-id, :name "deleteID", :callback? true, :params [{:name "callback", :type :callback}]}],
   :events [{:id ::on-token-refresh, :name "onTokenRefresh"}]})

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