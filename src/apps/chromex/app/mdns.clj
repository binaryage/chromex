(ns chromex.app.mdns
  "Use the chrome.mdns API to discover services over mDNS.
   This comprises a subset of the features of the NSD spec:
   http://www.w3.org/TR/discovery-api/

     * available since Chrome 38
     * https://developer.chrome.com/apps/mdns"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-service-instances-per-event
  "The maximum number of service instances that will be included in onServiceList events.  If more instances are available,
   they may be truncated from the onServiceList event.

   https://developer.chrome.com/apps/mdns#property-MAX_SERVICE_INSTANCES_PER_EVENT."
  ([] (gen-call :property ::max-service-instances-per-event &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro force-discovery
  "Immediately issues a multicast DNS query for all service types. |callback| is invoked immediately. At a later time, queries
   will be sent, and any service events will be fired.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/mdns#method-forceDiscovery."
  ([] (gen-call :function ::force-discovery &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-service-list-events
  "Event fired to inform clients of the current complete set of known available services. Clients should only need to store
   the list from the most recent event. The service type that the extension is interested in discovering should be specified
   as the event filter with the 'serviceType' key. Not specifying an event filter will not start any discovery listeners.

   Events will be put on the |channel| with signature [::on-service-list [services]] where:

     |services| - https://developer.chrome.com/apps/mdns#property-onServiceList-services.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/mdns#event-onServiceList."
  ([channel & args] (apply gen-call :event ::on-service-list &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.mdns namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mdns",
   :since "38",
   :properties
   [{:id ::max-service-instances-per-event,
     :name "MAX_SERVICE_INSTANCES_PER_EVENT",
     :since "44",
     :return-type "unknown-type"}],
   :functions
   [{:id ::force-discovery,
     :name "forceDiscovery",
     :since "45",
     :callback? true,
     :params [{:name "callback", :type :callback}]}],
   :events [{:id ::on-service-list, :name "onServiceList", :params [{:name "services", :type "[array-of-objects]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))