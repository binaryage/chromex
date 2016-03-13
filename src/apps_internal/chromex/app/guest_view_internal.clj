(ns chromex.app.guest-view-internal
  "  * available since Chrome 50
     * https://developer.chrome.com/extensions/guestViewInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create-guest
  "  |createParams| - See https://developer.chrome.com/extensions/guestViewInternal#property-createGuest-createParams.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [returnParams] where:
   
     |returnParams| - See https://developer.chrome.com/extensions/guestViewInternal#property-callback-returnParams.
   
   See https://developer.chrome.com/extensions/guestViewInternal#method-createGuest."
  ([create-params #_callback] (gen-call :function ::create-guest &form create-params)))

(defmacro destroy-guest
  "  |instanceId| - See https://developer.chrome.com/extensions/guestViewInternal#property-destroyGuest-instanceId.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/guestViewInternal#method-destroyGuest."
  ([instance-id #_callback] (gen-call :function ::destroy-guest &form instance-id)))

(defmacro set-size
  "  |instanceId| - The instance ID of the guest &lt;webview&gt; process. This not exposed to developers through the API.
     |params| - Size parameters.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/guestViewInternal#method-setSize."
  ([instance-id params #_callback] (gen-call :function ::set-size &form instance-id params)))

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
  {:namespace "chrome.guestViewInternal",
   :since "50",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))