(ns chromex.app.dom
  "Use the chrome.dom API to access special DOM APIs for Extensions

     * available since Chrome 88
     * https://developer.chrome.com/apps/dom"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-or-closed-shadow-root
  "Gets the open shadow root or the closed shadow root hosted by the specified element. If the element doesn't attach the
   shadow root, it will return null.

     |element| - https://developer.chrome.com/apps/dom#property-openOrClosedShadowRoot-element.

   https://developer.chrome.com/apps/dom#method-openOrClosedShadowRoot."
  ([element] (gen-call :function ::open-or-closed-shadow-root &form element)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.dom namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.dom",
   :since "future",
   :functions
   [{:id ::open-or-closed-shadow-root,
     :name "openOrClosedShadowRoot",
     :return-type "object",
     :params [{:name "element", :type "HTMLElement"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))