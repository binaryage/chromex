(ns chromex.ext.cloud-print-private
  "  * available since Chrome 22
     * https://developer.chrome.com/extensions/cloudPrintPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro setup-connector
  "Setup Cloud Print Connector.
   
     |userEmail| - The email address of the user.
     |robotEmail| - The email address of the robot account.
     |credentials| - The login credentials(OAuth2 Auth code).
     |userSettings| - Options configured by user.
   
   See https://developer.chrome.com/extensions/cloudPrintPrivate#method-setupConnector."
  ([user-email robot-email credentials user-settings] (gen-call :function ::setup-connector &form user-email robot-email credentials user-settings)))

(defmacro get-host-name
  "Returns local hostname.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - Host name.
   
   See https://developer.chrome.com/extensions/cloudPrintPrivate#method-getHostName."
  ([#_callback] (gen-call :function ::get-host-name &form)))

(defmacro get-printers
  "Returns local printers.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - List of printer names.
   
   See https://developer.chrome.com/extensions/cloudPrintPrivate#method-getPrinters."
  ([#_callback] (gen-call :function ::get-printers &form)))

(defmacro get-client-id
  "Gets the Client ID used to access Google service APIs.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - Client ID.
   
   See https://developer.chrome.com/extensions/cloudPrintPrivate#method-getClientId."
  ([#_callback] (gen-call :function ::get-client-id &form)))

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
  {:namespace "chrome.cloudPrintPrivate",
   :since "22",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))