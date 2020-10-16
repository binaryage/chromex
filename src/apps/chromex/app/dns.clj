(ns chromex.app.dns
  "Use the chrome.dns API for dns resolution.

     * available since Chrome 88
     * https://developer.chrome.com/apps/dns"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro resolve
  "Resolves the given hostname or IP address literal.

     |hostname| - The hostname to resolve.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [resolve-info] where:

     |resolve-info| - https://developer.chrome.com/apps/dns#property-callback-resolveInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/dns#method-resolve."
  ([hostname] (gen-call :function ::resolve &form hostname)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.dns namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.dns",
   :since "88",
   :functions
   [{:id ::resolve,
     :name "resolve",
     :callback? true,
     :params
     [{:name "hostname", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "resolve-info", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))