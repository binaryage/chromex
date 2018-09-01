(ns chromex.ext.enterprise.hardware-platform
  "Use the chrome.enterprise.hardwarePlatform API to get the
   manufacturer and model of the hardware platform where the browser runs.
   Note: This API is only available to extensions installed by enterprise
   policy.

     * available since Chrome master
     * https://developer.chrome.com/extensions/enterprise.hardwarePlatform"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-hardware-platform-info
  "Obtains the manufacturer and model for the hardware platform and, if the extension is authorized, returns it via
   |callback|.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - https://developer.chrome.com/extensions/enterprise.hardwarePlatform#property-callback-info.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/enterprise.hardwarePlatform#method-getHardwarePlatformInfo."
  ([] (gen-call :function ::get-hardware-platform-info &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.enterprise.hardware-platform namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.hardwarePlatform",
   :since "master",
   :functions
   [{:id ::get-hardware-platform-info,
     :name "getHardwarePlatformInfo",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))