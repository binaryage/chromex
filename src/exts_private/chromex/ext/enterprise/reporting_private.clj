(ns chromex.ext.enterprise.reporting-private
  "Private API for reporting Chrome browser status to admin console.

     * available since Chrome 68"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro upload-chrome-desktop-report
  "Uploads the status of Chrome browser to the admin console by sending request to the DMServer. Sets runtime.lastError on
   failure.

     |report| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([report] (gen-call :function ::upload-chrome-desktop-report &form report)))

(defmacro get-device-id
  "Gets the identity of device that Chrome browser is running on. The ID is retrieved from the local device and used by the
   Google admin console.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [id] where:

     |id| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-device-id &form)))

(defmacro get-persistent-secret
  "Gets a randomly generated persistent secret (symmetric key) that can be used to encrypt the data stored with
   |setDeviceData|.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [secret] where:

     |secret| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-persistent-secret &form)))

(defmacro get-device-data
  "Gets the device data for |id|. Sets runtime.lastError on failure.

     |id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::get-device-data &form id)))

(defmacro set-device-data
  "Sets the device data for |id|. Sets runtime.lastError on failure. If the |data| parameter is undefined and there is already
   data associated with |id| it will be cleared.

     |id| - ?
     |data| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id data] (gen-call :function ::set-device-data &form id data))
  ([id] `(set-device-data ~id :omit)))

(defmacro get-device-info
  "Gets the device information (including disk encryption status, screen lock status, serial number, model).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [device-info] where:

     |device-info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-device-info &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.enterprise.reporting-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.reportingPrivate",
   :since "68",
   :functions
   [{:id ::upload-chrome-desktop-report,
     :name "uploadChromeDesktopReport",
     :callback? true,
     :params [{:name "report", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-device-id,
     :name "getDeviceId",
     :since "71",
     :callback? true,
     :params
     [{:name "callback", :optional? true, :type :callback, :callback {:params [{:name "id", :type "string"}]}}]}
    {:id ::get-persistent-secret,
     :name "getPersistentSecret",
     :since "80",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "secret", :type "ArrayBuffer"}]}}]}
    {:id ::get-device-data,
     :name "getDeviceData",
     :since "80",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "data", :type "ArrayBuffer"}]}}]}
    {:id ::set-device-data,
     :name "setDeviceData",
     :since "80",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "data", :optional? true, :type "ArrayBuffer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-device-info,
     :name "getDeviceInfo",
     :since "80",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "device-info", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))