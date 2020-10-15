(ns chromex.ext.devtools.network
  "Use the chrome.devtools.network API to retrieve the information about network requests displayed by the Developer Tools in
   the Network panel.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/devtools.network"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-har
  "Returns HAR log that contains all known network requests.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [har-log] where:

     |har-log| - A HAR log. See HAR specification for details.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.network#method-getHAR."
  ([] (gen-call :function ::get-har &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-request-finished-events
  "Fired when a network request is finished and all request data are available.

   Events will be put on the |channel| with signature [::on-request-finished [request]] where:

     |request| - Description of a network request in the form of a HAR entry. See HAR specification for details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/devtools.network#event-onRequestFinished."
  ([channel & args] (apply gen-call :event ::on-request-finished &form channel args)))

(defmacro tap-on-navigated-events
  "Fired when the inspected window navigates to a new page.

   Events will be put on the |channel| with signature [::on-navigated [url]] where:

     |url| - URL of the new page.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/devtools.network#event-onNavigated."
  ([channel & args] (apply gen-call :event ::on-navigated &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.devtools.network namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.network",
   :since "38",
   :functions
   [{:id ::get-har,
     :name "getHAR",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "har-log", :type "object"}]}}]}],
   :events
   [{:id ::on-request-finished,
     :name "onRequestFinished",
     :params [{:name "request", :type "devtools.network.Request"}]}
    {:id ::on-navigated, :name "onNavigated", :params [{:name "url", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))