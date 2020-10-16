(ns chromex.app.diagnostics
  "Use the chrome.diagnostics API to query various properties of
   the environment that may be useful for diagnostics.

     * available since Chrome 88
     * https://developer.chrome.com/apps/diagnostics"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro send-packet
  "Send a packet of the given type with the given parameters.

     |options| - https://developer.chrome.com/apps/diagnostics#property-sendPacket-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/apps/diagnostics#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/diagnostics#method-sendPacket."
  ([options] (gen-call :function ::send-packet &form options)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.diagnostics namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.diagnostics",
   :since "88",
   :functions
   [{:id ::send-packet,
     :name "sendPacket",
     :callback? true,
     :params
     [{:name "options", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))