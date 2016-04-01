(ns chromex.app.app-view-guest-internal
  "  * available since Chrome 40"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro attach-frame
  "Attaches the specified url to the AppView with the provided instance ID.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [instance-id] where:

     |instance-id| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::attach-frame &form)))

(defmacro deny-request
  "Denies the embedding request made by the AppView with the provided instance ID."
  ([] (gen-call :function ::deny-request &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.app-view-guest-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.appViewGuestInternal",
   :since "40",
   :functions
   [{:id ::attach-frame,
     :name "attachFrame",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "instance-id", :type "integer"}]}}]}
    {:id ::deny-request, :name "denyRequest"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))