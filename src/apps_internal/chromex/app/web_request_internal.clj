(ns chromex.app.web-request-internal
  "  * available since Chrome 21
     * https://developer.chrome.com/extensions/webRequestInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-event-listener
  "Used internally to implement the special form of addListener for the webRequest events.
   
     |filter| - A set of filters that restricts the events that will be sent to this listener.
     |extraInfoSpec| - Array of extra information that should be passed to the listener function.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([filter extra-info-spec event-name sub-event-name web-view-instance-id #_callback] (gen-call :function ::add-event-listener &form filter extra-info-spec event-name sub-event-name web-view-instance-id)))

(defmacro event-handled
  "Used internally to send a response for a blocked event."
  ([event-name sub-event-name request-id response] (gen-call :function ::event-handled &form event-name sub-event-name request-id response))
  ([event-name sub-event-name request-id] `(event-handled ~event-name ~sub-event-name ~request-id :omit)))

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
  {:namespace "chrome.webRequestInternal",
   :since "21",
   :functions
   [{:id ::add-event-listener,
     :name "addEventListener",
     :callback? true,
     :params
     [{:name "filter", :type "webRequest.RequestFilter"}
      {:name "extra-info-spec", :optional? true, :type "[array-of-unknown-types]"}
      {:name "event-name", :type "string"}
      {:name "sub-event-name", :type "string"}
      {:name "web-view-instance-id", :type "integer"}
      {:name "callback", :type :callback}]}
    {:id ::event-handled,
     :name "eventHandled",
     :params
     [{:name "event-name", :type "string"}
      {:name "sub-event-name", :type "string"}
      {:name "request-id", :type "string"}
      {:name "response", :optional? true, :type "webRequest.BlockingResponse"}]}]})

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