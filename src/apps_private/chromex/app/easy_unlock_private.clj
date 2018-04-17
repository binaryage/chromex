(ns chromex.app.easy-unlock-private
  "chrome.easyUnlockPrivate API that provides hooks to Chrome to
   be used by Easy Unlock component app.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-strings
  "Gets localized strings required to render the API.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [strings] where:

     |strings| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-strings &form)))

(defmacro show-error-bubble
  "Shows an error bubble with the given |message|, anchored to an edge of the given |anchorRect| -- typically the right edge,
   but possibly a different edge if there is not space for the bubble to the right of the anchor rectangle. If the
   |link_range| is non-empty, renders the text within the |message| that is contained in the |link_range| as a link with the
   given |link_target| URL.

     |message| - ?
     |link-range| - ?
     |link-target| - ?
     |anchor-rect| - ?"
  ([message link-range link-target anchor-rect] (gen-call :function ::show-error-bubble &form message link-range link-target anchor-rect)))

(defmacro hide-error-bubble
  "Hides the currently visible error bubble, if there is one."
  ([] (gen-call :function ::hide-error-bubble &form)))

(defmacro find-setup-connection
  "Finds and connects the remote BLE device that is advertising: |setupServiceUUID|. Returns when a connection is found or
   |timeOut| seconds have elapsed.

     |setup-service-uuid| - ?
     |time-out| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [connection-id] where:

     |connection-id| - The identifier of the connection found. To be used in future calls refering to this connection.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([setup-service-uuid time-out] (gen-call :function ::find-setup-connection &form setup-service-uuid time-out)))

(defmacro setup-connection-disconnect
  "Disconnects the connection with |connectionId|.

     |connection-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([connection-id] (gen-call :function ::setup-connection-disconnect &form connection-id)))

(defmacro setup-connection-send
  "Sends |data| through the connection with |connnectionId|.

     |connection-id| - ?
     |data| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([connection-id data] (gen-call :function ::setup-connection-send &form connection-id data)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-connection-status-changed-events
  "Event fired when |connectionId| change status.

   Events will be put on the |channel| with signature [::on-connection-status-changed [connection-id old-status new-status]]
   where:

     |connection-id| - ?
     |old-status| - ?
     |new-status| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-connection-status-changed &form channel args)))

(defmacro tap-on-data-received-events
  "Event fired when |connectionId| receives |data|.

   Events will be put on the |channel| with signature [::on-data-received [connection-id data]] where:

     |connection-id| - ?
     |data| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-data-received &form channel args)))

(defmacro tap-on-send-completed-events
  "Event fired when |connectionId| sends |data|. |success| is true if the send operation was successful.

   Events will be put on the |channel| with signature [::on-send-completed [connection-id data success]] where:

     |connection-id| - ?
     |data| - ?
     |success| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-send-completed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.easy-unlock-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.easyUnlockPrivate",
   :since "38",
   :functions
   [{:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "strings", :type "object"}]}}]}
    {:id ::show-error-bubble,
     :name "showErrorBubble",
     :since "42",
     :params
     [{:name "message", :type "string"}
      {:name "link-range", :type "object"}
      {:name "link-target", :type "string"}
      {:name "anchor-rect", :type "object"}]}
    {:id ::hide-error-bubble, :name "hideErrorBubble", :since "43"}
    {:id ::find-setup-connection,
     :name "findSetupConnection",
     :since "47",
     :callback? true,
     :params
     [{:name "setup-service-uuid", :type "string"}
      {:name "time-out", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "connection-id", :type "integer"}]}}]}
    {:id ::setup-connection-disconnect,
     :name "setupConnectionDisconnect",
     :since "47",
     :callback? true,
     :params [{:name "connection-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::setup-connection-send,
     :name "setupConnectionSend",
     :since "47",
     :callback? true,
     :params
     [{:name "connection-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-connection-status-changed,
     :name "onConnectionStatusChanged",
     :since "47",
     :params
     [{:name "connection-id", :type "integer"}
      {:name "old-status", :type "easyUnlockPrivate.ConnectionStatus"}
      {:name "new-status", :type "easyUnlockPrivate.ConnectionStatus"}]}
    {:id ::on-data-received,
     :name "onDataReceived",
     :since "47",
     :params [{:name "connection-id", :type "integer"} {:name "data", :type "ArrayBuffer"}]}
    {:id ::on-send-completed,
     :name "onSendCompleted",
     :since "47",
     :params
     [{:name "connection-id", :type "integer"}
      {:name "data", :type "ArrayBuffer"}
      {:name "success", :type "boolean"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))