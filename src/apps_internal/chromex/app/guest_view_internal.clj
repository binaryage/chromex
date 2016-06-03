(ns chromex.app.guest-view-internal
  "  * available since Chrome 52"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create-guest
  "  |create-params| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [return-params] where:

     |return-params| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([create-params] (gen-call :function ::create-guest &form create-params)))

(defmacro destroy-guest
  "  |instance-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id] (gen-call :function ::destroy-guest &form instance-id)))

(defmacro set-size
  "  |instance-id| - The instance ID of the guest &lt;webview&gt; process. This not exposed to developers through the API.
     |params| - Size parameters.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([instance-id params] (gen-call :function ::set-size &form instance-id params)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.guest-view-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.guestViewInternal",
   :since "52",
   :functions
   [{:id ::create-guest,
     :name "createGuest",
     :callback? true,
     :params
     [{:name "create-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "return-params", :type "object"}]}}]}
    {:id ::destroy-guest,
     :name "destroyGuest",
     :callback? true,
     :params [{:name "instance-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-size,
     :name "setSize",
     :callback? true,
     :params
     [{:name "instance-id", :type "integer"}
      {:name "params", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))