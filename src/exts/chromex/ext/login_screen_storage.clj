(ns chromex.ext.login-screen-storage
  "Use the chrome.loginScreenStorage API to store persistent data
   from the login screen or inject data into the session.

     * available since Chrome 78
     * https://developer.chrome.com/extensions/loginScreenStorage"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro store-persistent-data
  "Stores persistent data from the login screen. This data can be accessed later using 'retrievePersistentData' by any
   extension from the specified extension ids. This method will fail if called while a user session is active.

     |extension-ids| - IDs of the extensions that should have access to the stored data.
     |data| - The data to store.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginScreenStorage#method-storePersistentData."
  ([extension-ids data] (gen-call :function ::store-persistent-data &form extension-ids data)))

(defmacro retrieve-persistent-data
  "Retrieves persistent data that was previously stored using 'storePersistentData' for the caller's extension ID.

     |owner-id| - ID of the extension that saved the data that the caller is trying to retrieve.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - https://developer.chrome.com/extensions/loginScreenStorage#property-callback-data.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginScreenStorage#method-retrievePersistentData."
  ([owner-id] (gen-call :function ::retrieve-persistent-data &form owner-id)))

(defmacro store-credentials
  "Stores credentials for later access from the user session. This method will fail if called while a user session is active.

     |extension-id| - ID of the in-session extension that should have access to these credentials. Credentials stored using
                      this method are deleted on session exit.
     |credentials| - The credentials to store.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginScreenStorage#method-storeCredentials."
  ([extension-id credentials] (gen-call :function ::store-credentials &form extension-id credentials)))

(defmacro retrieve-credentials
  "Retrieves credentials that were previosly stored using 'storeCredentials'. The caller's extension ID should be the same as
   the extension id passed to the 'storeCredentials'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - https://developer.chrome.com/extensions/loginScreenStorage#property-callback-data.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/loginScreenStorage#method-retrieveCredentials."
  ([] (gen-call :function ::retrieve-credentials &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.login-screen-storage namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.loginScreenStorage",
   :since "78",
   :functions
   [{:id ::store-persistent-data,
     :name "storePersistentData",
     :callback? true,
     :params
     [{:name "extension-ids", :type "[array-of-strings]"}
      {:name "data", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::retrieve-persistent-data,
     :name "retrievePersistentData",
     :callback? true,
     :params
     [{:name "owner-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "data", :type "string"}]}}]}
    {:id ::store-credentials,
     :name "storeCredentials",
     :callback? true,
     :params
     [{:name "extension-id", :type "string"}
      {:name "credentials", :type "string"}
      {:name "callback", :type :callback}]}
    {:id ::retrieve-credentials,
     :name "retrieveCredentials",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "data", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))