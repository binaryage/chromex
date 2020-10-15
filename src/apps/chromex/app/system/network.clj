(ns chromex.app.system.network
  "Use the chrome.system.network API.

     * available since Chrome 38
     * https://developer.chrome.com/apps/system.network"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-network-interfaces
  "Retrieves information about local adapters on this system.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [network-interfaces] where:

     |network-interfaces| - Array of object containing network interfaces information.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/system.network#method-getNetworkInterfaces."
  ([] (gen-call :function ::get-network-interfaces &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.system.network namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.network",
   :since "38",
   :functions
   [{:id ::get-network-interfaces,
     :name "getNetworkInterfaces",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "network-interfaces", :type "[array-of-objects]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))