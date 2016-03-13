(ns chromex.app.echo-private
  "  * available since Chrome 20
     * https://developer.chrome.com/extensions/echoPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-offer-info
  "Sets the offer info in Local State.
   
     |id| - The service id of the echo offer.
     |offerInfo| - The offer info.
   
   See https://developer.chrome.com/extensions/echoPrivate#method-setOfferInfo."
  ([id offer-info] (gen-call :function ::set-offer-info &form id offer-info)))

(defmacro get-offer-info
  "Check in Local State for the offer info.
   
     |id| - The service id of the offer eligibility check.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The returned offer info. If the offer info is not available, api will raise error.
   
   See https://developer.chrome.com/extensions/echoPrivate#method-getOfferInfo."
  ([id #_callback] (gen-call :function ::get-offer-info &form id)))

(defmacro get-registration-code
  "Get the group or coupon code from underlying storage.
   
     |type| - Type of coupon code requested to be read (coupon or group).
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The coupon code.
   
   See https://developer.chrome.com/extensions/echoPrivate#method-getRegistrationCode."
  ([type #_callback] (gen-call :function ::get-registration-code &form type)))

(defmacro get-oobe-timestamp
  "Get the OOBE timestamp.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - The OOBE timestamp.
   
   See https://developer.chrome.com/extensions/echoPrivate#method-getOobeTimestamp."
  ([#_callback] (gen-call :function ::get-oobe-timestamp &form)))

(defmacro get-user-consent
  "If device policy allows user to redeem offer, displays a native dialog asking user for a consent to verify device's
   eligibility for the offer. If the device policy forbids user to redeem offers, displays a native dialog informing user the
   offer redeeming is disabled.
   
     |consentRequester| - Information about the service requesting user consent.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - Whether the user consent was given.
   
   See https://developer.chrome.com/extensions/echoPrivate#method-getUserConsent."
  ([consent-requester #_callback] (gen-call :function ::get-user-consent &form consent-requester)))

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
  {:namespace "chrome.echoPrivate",
   :since "20",
   :functions
   [{:id ::set-offer-info,
     :name "setOfferInfo",
     :since "31",
     :params [{:name "id", :type "string"} {:name "offer-info", :type "object"}]}
    {:id ::get-offer-info,
     :name "getOfferInfo",
     :since "31",
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
     :since "26",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::get-user-consent,
     :name "getUserConsent",
     :since "27",
     :callback? true,
     :params
     [{:name "consent-requester", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}]})

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