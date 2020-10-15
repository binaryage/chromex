(ns chromex.app.identity-private
  "identityPrivate.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-consent-result
  "The Identity API application with specified window_id returns the consent result to the browser.

     |result| - ?
     |window-id| - ?"
  ([result window-id] (gen-call :function ::set-consent-result &form result window-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-web-flow-request-events
  "Fired when a web flow dialog should be displayed.

   Events will be put on the |channel| with signature [::on-web-flow-request [key url mode partition]] where:

     |key| - A unique identifier that the caller can use to locate the dialog window.
     |url| - A URL that will be loaded in the webview.
     |mode| - 'interactive' or 'silent'. The window will be displayed if the mode is 'interactive'.
     |partition| - A name used for the webview partition.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-web-flow-request &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.identity-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.identityPrivate",
   :since "38",
   :functions
   [{:id ::set-consent-result,
     :name "setConsentResult",
     :since "81",
     :params [{:name "result", :type "string"} {:name "window-id", :type "string"}]}],
   :events
   [{:id ::on-web-flow-request,
     :name "onWebFlowRequest",
     :params
     [{:name "key", :type "string"}
      {:name "url", :type "string"}
      {:name "mode", :type "string"}
      {:name "partition", :since "84", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))