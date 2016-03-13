(ns chromex.ext.cryptotoken-private
  "chrome.cryptotokenPrivate API that provides hooks to Chrome to
   be used by cryptotoken component extension.
   
     * available since Chrome 41
     * https://developer.chrome.com/extensions/cryptotokenPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro can-origin-assert-app-id
  "Checks whether the origin is allowed to assert the appId, according to the same origin policy defined at
   http://fidoalliance.org/specs/fido-u2f-v1.0-ps-20141009/     fido-appid-and-facets-ps-20141009.html |securityOrigin| is the
   origin as seen by the extension, and |appIdUrl| is the appId being asserted by the origin.
   
     |securityOrigin| - See
                        https://developer.chrome.com/extensions/cryptotokenPrivate#property-canOriginAssertAppId-securityOrig
                        in.
     |appIdUrl| - See https://developer.chrome.com/extensions/cryptotokenPrivate#property-canOriginAssertAppId-appIdUrl.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:
   
     |result| - See https://developer.chrome.com/extensions/cryptotokenPrivate#property-callback-result.
   
   See https://developer.chrome.com/extensions/cryptotokenPrivate#method-canOriginAssertAppId."
  ([security-origin app-id-url #_callback] (gen-call :function ::can-origin-assert-app-id &form security-origin app-id-url)))

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
  {:namespace "chrome.cryptotokenPrivate",
   :since "41",
   :functions
   [{:id ::can-origin-assert-app-id,
     :name "canOriginAssertAppId",
     :since "42",
     :callback? true,
     :params
     [{:name "security-origin", :type "string"}
      {:name "app-id-url", :type "string"}
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