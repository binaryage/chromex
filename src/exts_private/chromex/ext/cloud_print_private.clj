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
     |userSettings| - Options configured by user."
  ([user-email robot-email credentials user-settings] (gen-call :function ::setup-connector &form user-email robot-email credentials user-settings)))

(defmacro get-host-name
  "Returns local hostname.
   
     |callback| - Called to return host name.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-host-name &form)))

(defmacro get-printers
  "Returns local printers.
   
     |callback| - Called to return printers.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-printers &form)))

(defmacro get-client-id
  "Gets the Client ID used to access Google service APIs.
   
     |callback| - Called to return the client ID.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
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