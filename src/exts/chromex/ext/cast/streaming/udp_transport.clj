(ns chromex.ext.cast.streaming.udp-transport
  "The chrome.webrtc.castUdpTransport API represents a UDP
   transport for Cast RTP streams. This API is not useful when standalone
   since it does not have send and receive methods.
   It is used to configure the UDP transport used in Cast session.

   Valid transport IDs are positive and non-zero.

     * available since Chrome 59
     * https://developer.chrome.com/extensions/cast.streaming.udpTransport"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro destroy
  "Destroys a UDP transport.

     |transport-id| - The transport ID.

   https://developer.chrome.com/extensions/cast.streaming.udpTransport#method-destroy."
  ([transport-id] (gen-call :function ::destroy &form transport-id)))

(defmacro set-destination
  "Sets parameters for this UDP transport. This can only be called once per transport.

     |transport-id| - The transport ID.
     |destination| - The address and port to send packets to.

   https://developer.chrome.com/extensions/cast.streaming.udpTransport#method-setDestination."
  ([transport-id destination] (gen-call :function ::set-destination &form transport-id destination)))

(defmacro set-options
  "Sets the options. Attributes of this object will be used to activate optional behaviours in the transport. Normally this is
   only used for experimentation. Must be called before setDestination.

     |transport-id| - The transport ID that is created by chrome.cast.streaming.session.create().
     |options| - A dictionary of key-value pairs of options. See media/cast/net/cast_transport_sender_impl.h for supported
                 options.

   https://developer.chrome.com/extensions/cast.streaming.udpTransport#method-setOptions."
  ([transport-id options] (gen-call :function ::set-options &form transport-id options)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cast.streaming.udp-transport namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.udpTransport",
   :since "59",
   :functions
   [{:id ::destroy, :name "destroy", :params [{:name "transport-id", :type "integer"}]}
    {:id ::set-destination,
     :name "setDestination",
     :params [{:name "transport-id", :type "integer"} {:name "destination", :type "object"}]}
    {:id ::set-options,
     :name "setOptions",
     :params [{:name "transport-id", :type "integer"} {:name "options", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))