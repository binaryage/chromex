(ns chromex.ext.web-request-internal
  "  * available since Chrome 21"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-event-listener
  "Used internally to implement the special form of addListener for the webRequest events.

     |callback| - ?
     |filter| - A set of filters that restricts the events that will be sent to this listener.
     |extra-info-spec| - Array of extra information that should be passed to the listener function.
     |event-name| - ?
     |sub-event-name| - ?
     |web-view-instance-id| - ?"
  ([callback filter extra-info-spec event-name sub-event-name web-view-instance-id] (gen-call :function ::add-event-listener &form callback filter extra-info-spec event-name sub-event-name web-view-instance-id)))

(defmacro event-handled
  "Used internally to send a response for a blocked event.

     |event-name| - ?
     |sub-event-name| - ?
     |request-id| - ?
     |response| - ?"
  ([event-name sub-event-name request-id response] (gen-call :function ::event-handled &form event-name sub-event-name request-id response))
  ([event-name sub-event-name request-id] `(event-handled ~event-name ~sub-event-name ~request-id :omit)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.web-request-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webRequestInternal",
   :since "21",
   :functions
   [{:id ::add-event-listener,
     :name "addEventListener",
     :params
     [{:name "callback", :type "function"}
      {:name "filter", :type "webRequest.RequestFilter"}
      {:name "extra-info-spec", :optional? true, :type "[array-of-unknown-types]"}
      {:name "event-name", :type "string"}
      {:name "sub-event-name", :type "string"}
      {:name "web-view-instance-id", :type "integer"}]}
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))