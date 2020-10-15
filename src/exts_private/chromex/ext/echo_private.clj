(ns chromex.ext.echo-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-offer-info
  "Sets the offer info in Local State.

     |id| - The service id of the echo offer.
     |offer-info| - The offer info."
  ([id offer-info] (gen-call :function ::set-offer-info &form id offer-info)))

(defmacro get-offer-info
  "Check in Local State for the offer info.

     |id| - The service id of the offer eligibility check.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The returned offer info. If the offer info is not available, api will raise error.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::get-offer-info &form id)))

(defmacro get-registration-code
  "Get the group or coupon code from underlying storage.

     |type| - Type of coupon code requested to be read (coupon or group).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The coupon code.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([type] (gen-call :function ::get-registration-code &form type)))

(defmacro get-oobe-timestamp
  "Get the OOBE timestamp.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - The OOBE timestamp.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-oobe-timestamp &form)))

(defmacro get-user-consent
  "If device policy allows user to redeem offer, displays a native dialog asking user for a consent to verify device's
   eligibility for the offer. If the device policy forbids user to redeem offers, displays a native dialog informing user the
   offer redeeming is disabled.

     |consent-requester| - Information about the service requesting user consent.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - Whether the user consent was given.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([consent-requester] (gen-call :function ::get-user-consent &form consent-requester)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.echo-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.echoPrivate",
   :since "38",
   :functions
   [{:id ::set-offer-info,
     :name "setOfferInfo",
     :params [{:name "id", :type "string"} {:name "offer-info", :type "object"}]}
    {:id ::get-offer-info,
     :name "getOfferInfo",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-registration-code,
     :name "getRegistrationCode",
     :callback? true,
     :params
     [{:name "type", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-oobe-timestamp,
     :name "getOobeTimestamp",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-user-consent,
     :name "getUserConsent",
     :callback? true,
     :params
     [{:name "consent-requester", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))