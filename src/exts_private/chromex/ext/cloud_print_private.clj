(ns chromex.ext.cloud-print-private
  "  * available since Chrome 23"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro setup-connector
  "Setup Cloud Print Connector.

     |user-email| - The email address of the user.
     |robot-email| - The email address of the robot account.
     |credentials| - The login credentials(OAuth2 Auth code).
     |user-settings| - Options configured by user."
  ([user-email robot-email credentials user-settings] (gen-call :function ::setup-connector &form user-email robot-email credentials user-settings)))

(defmacro get-host-name
  "Returns local hostname.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Host name.

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-host-name &form)))

(defmacro get-printers
  "Returns local printers.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - List of printer names.

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-printers &form)))

(defmacro get-client-id
  "Gets the Client ID used to access Google service APIs.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Client ID.

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-client-id &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cloud-print-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cloudPrintPrivate",
   :since "23",
   :functions
   [{:id ::setup-connector,
     :name "setupConnector",
     :since "24",
     :params
     [{:name "user-email", :type "string"}
      {:name "robot-email", :type "string"}
      {:name "credentials", :type "string"}
      {:name "user-settings", :type "cloudPrintPrivate.UserSettings"}]}
    {:id ::get-host-name,
     :name "getHostName",
     :since "24",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-printers,
     :name "getPrinters",
     :since "24",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-strings]"}]}}]}
    {:id ::get-client-id,
     :name "getClientId",
     :since "27",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))