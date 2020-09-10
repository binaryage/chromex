(ns chromex.ext.enterprise.networking-attributes
  "Use the chrome.enterprise.networkingAttributes API to read
   information about your current network.
   Note: This API is only available to extensions force-installed by enterprise
   policy.

     * available since Chrome 85
     * https://developer.chrome.com/extensions/enterprise.networkingAttributes"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-network-details
  "Retrieves the network details of the device's default network. If the user is not affiliated or the device is not connected
   to a network, 'runtime.lastError' will be set with a failure reason.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [network-addresses] where:

     |network-addresses| - https://developer.chrome.com/extensions/enterprise.networkingAttributes#property-callback-networkAddresses.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/enterprise.networkingAttributes#method-getNetworkDetails."
  ([] (gen-call :function ::get-network-details &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.enterprise.networking-attributes namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.networkingAttributes",
   :since "85",
   :functions
   [{:id ::get-network-details,
     :name "getNetworkDetails",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "network-addresses", :type "enterprise.networkingAttributes.NetworkDetails"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))