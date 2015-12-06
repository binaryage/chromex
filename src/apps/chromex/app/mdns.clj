(ns chromex.app.mdns
  "Use the chrome.mdns API to discover services over mDNS.
   This comprises a subset of the features of the NSD spec:
   http://www.w3.org/TR/discovery-api/
   
     * available since Chrome 31
     * https://developer.chrome.com/extensions/mdns"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-service-instances-per-event
  "The maximum number of service instances that will be included in onServiceList events.  If more instances are available,
   they may be truncated from the onServiceList event."
  ([] (gen-call :property ::max-service-instances-per-event &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro force-discovery
  "Immediately issues a multicast DNS query for all service types. |callback| is invoked immediately. At a later time, queries
   will be sent, and any service events will be fired.
   
     |callback| - Callback invoked after ForceDiscovery() has started.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::force-discovery &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-service-list-events
  "Event fired to inform clients of the current complete set of known available services. Clients should only need to store
   the list from the most recent event. The service type that the extension is interested in discovering should be specified
   as the event filter with the 'serviceType' key. Not specifying an event filter will not start any discovery listeners.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-service-list &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mdns",
   :since "31",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))