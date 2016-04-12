(ns chromex.ext.dashboard-private
  "  * available since Chrome 46"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro show-permission-prompt-for-delegated-install
  "Shows a permission prompt for the given extension, for installing to a different account.

     |details| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - A string result code, which will be empty upon success. The possible values in the case of errors include
                'unknown_error', 'user_cancelled', 'manifest_error', 'icon_error', 'invalid_id', and 'invalid_icon_url'.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([details] (gen-call :function ::show-permission-prompt-for-delegated-install &form details)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.dashboard-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.dashboardPrivate",
   :since "46",
   :functions
   [{:id ::show-permission-prompt-for-delegated-install,
     :name "showPermissionPromptForDelegatedInstall",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "dashboardPrivate.Result"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))