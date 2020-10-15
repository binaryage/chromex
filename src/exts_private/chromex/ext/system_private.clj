(ns chromex.ext.system-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-incognito-mode-availability
  "Returns whether the incognito mode is enabled, disabled or forced

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [value] where:

     |value| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-incognito-mode-availability &form)))

(defmacro get-update-status
  "Gets information about the system update.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - Details of the system update

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-update-status &form)))

(defmacro get-api-key
  "Gets Chrome's API key to use for requests to Google services.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [key] where:

     |key| - The API key.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-api-key &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.system-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.systemPrivate",
   :since "38",
   :functions
   [{:id ::get-incognito-mode-availability,
     :name "getIncognitoModeAvailability",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "value", :type "systemPrivate.GetIncognitoModeAvailabilityValue"}]}}]}
    {:id ::get-update-status,
     :name "getUpdateStatus",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "systemPrivate.UpdateStatus"}]}}]}
    {:id ::get-api-key,
     :name "getApiKey",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "key", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))