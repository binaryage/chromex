(ns chromex.cast.streaming.udp-transport
  "The chrome.webrtc.castUdpTransport API represents a UDP
   transport for Cast RTP streams. This API is not useful when standalone
   since it does not have send and receive methods.
   It is used to configure the UDP transport used in Cast session.
   
   Valid transport IDs are positive and non-zero.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/cast.streaming.udpTransport"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro destroy
  "Destroys a UDP transport.
   
     |transportId| - The transport ID."
  ([transport-id] (gen-call :function ::destroy &form transport-id)))

(defmacro set-destination
  "Sets parameters for this UDP transport. This can only be called once per transport.
   
     |transportId| - The transport ID.
     |destination| - The address and port to send packets to."
  ([transport-id destination] (gen-call :function ::set-destination &form transport-id destination)))

(defmacro set-options
  "Sets the options. Attributes of this object will be used to activate optional behaviours in the transport. Normally
   this is only used for experimentation. Must be called before setDestination.
   
     |transportId| - The transport ID that is created by chrome.cast.streaming.session.create().
     |options| - A dictionary of key-value pairs of options. See media/cast/net/cast_transport_sender_impl.h for
                 supported options."
  ([transport-id options] (gen-call :function ::set-options &form transport-id options)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cast.streaming.udpTransport",
   :since "48",
   :functions
   [{:id ::destroy, :name "destroy", :params [{:name "transport-id", :type "integer"}]}
    {:id ::set-destination,
     :name "setDestination",
     :params [{:name "transport-id", :type "integer"} {:name "destination", :type "object"}]}
    {:id ::set-options,
     :name "setOptions",
     :params [{:name "transport-id", :type "integer"} {:name "options", :type "object"}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))