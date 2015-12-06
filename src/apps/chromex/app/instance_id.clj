(ns chromex.app.instance-id
  "Use chrome.instanceID to access the Instance ID service.
   
     * available since Chrome 46
     * https://developer.chrome.com/extensions/instanceID"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-id
  "Retrieves an identifier for the app instance. The instance ID will be returned by the callback. The same ID will be
   returned as long as the application identity has not been revoked or expired.
   
     |callback| - Function called when the retrieval completes. It should check 'runtime.lastError' for error when
                  instanceID is empty.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-id &form)))

(defmacro get-creation-time
  "Retrieves the time when the InstanceID has been generated. The creation time will be returned by the callback.
   
     |callback| - Function called when the retrieval completes. It should check 'runtime.lastError' for error when
                  creationTime is zero.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-creation-time &form)))

(defmacro get-token
  "Return a token that allows the authorized entity to access the service defined by scope.
   
     |getTokenParams| - Parameters for getToken.
     |callback| - Function called when the retrieval completes. It should check 'runtime.lastError' for error when token is
                  empty.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([get-token-params #_callback] (gen-call :function ::get-token &form get-token-params)))

(defmacro delete-token
  "Revokes a granted token.
   
     |deleteTokenParams| - Parameters for deleteToken.
     |callback| - Function called when the token deletion completes. The token was revoked successfully if
                  'runtime.lastError' is not set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([delete-token-params #_callback] (gen-call :function ::delete-token &form delete-token-params)))

(defmacro delete-id
  "Resets the app instance identifier and revokes all tokens associated with it.
   
     |callback| - Function called when the deletion completes. The instance identifier was revoked successfully if
                  'runtime.lastError' is not set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::delete-id &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-token-refresh-events
  "Fired when all the granted tokens need to be refreshed.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
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