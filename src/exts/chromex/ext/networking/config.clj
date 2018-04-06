(ns chromex.ext.networking.config
  "Use the networking.config API to authenticate to captive
   portals.

     * available since Chrome 43
     * https://developer.chrome.com/extensions/networking.config"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-network-filter
  "Allows an extension to define network filters for the networks it can handle. A call to this function will remove all
   filters previously installed by the extension before setting the new list.

     |networks| - Network filters to set. Every NetworkInfo must             either have the SSID or HexSSID
                  set. Other fields will be ignored.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/networking.config#method-setNetworkFilter."
  ([networks] (gen-call :function ::set-network-filter &form networks)))

(defmacro finish-authentication
  "Called by the extension to notify the network config API that it finished a captive portal authentication attempt and hand
   over the result of the attempt. This function must only be called with the GUID of the latest 'onCaptivePortalDetected'
   event.

     |guid| - Unique network identifier obtained from         'onCaptivePortalDetected'.
     |result| - The result of the authentication attempt.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/networking.config#method-finishAuthentication."
  ([guid result] (gen-call :function ::finish-authentication &form guid result)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-captive-portal-detected-events
  "This event fires everytime a captive portal is detected on a network matching any of the currently registered network
   filters and the user consents to use the extension for authentication. Network filters may be set using the
   'setNetworkFilter'. Upon receiving this event the extension should start its authentication attempt with the captive
   portal. When the extension finishes its attempt, it must call 'finishAuthentication' with the GUID received with this event
   and the appropriate authentication result.

   Events will be put on the |channel| with signature [::on-captive-portal-detected [network-info]] where:

     |network-info| - Information about the network on which a captive portal was detected.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/networking.config#event-onCaptivePortalDetected."
  ([channel & args] (apply gen-call :event ::on-captive-portal-detected &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.networking.config namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.networking.config",
   :since "43",
   :functions
   [{:id ::set-network-filter,
     :name "setNetworkFilter",
     :callback? true,
     :params
     [{:name "networks", :type "[array-of-networking.config.NetworkInfos]"} {:name "callback", :type :callback}]}
    {:id ::finish-authentication,
     :name "finishAuthentication",
     :callback? true,
     :params
     [{:name "guid", :type "string"}
      {:name "result", :type "unknown-type"}
      {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-captive-portal-detected,
     :name "onCaptivePortalDetected",
     :params [{:name "network-info", :type "networking.config.NetworkInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))