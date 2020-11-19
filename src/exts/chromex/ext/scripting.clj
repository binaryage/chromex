(ns chromex.ext.scripting
  "Use the chrome.scripting API to execute script in different
   contexts.

     * available since Chrome master
     * https://developer.chrome.com/extensions/scripting"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro execute-script
  "Injects a script into a target context. The script will be run at document_end.

     |injection| - The details of the script which to inject.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/scripting#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/scripting#method-executeScript."
  ([injection] (gen-call :function ::execute-script &form injection)))

(defmacro insert-css
  "Inserts a CSS stylesheet into a target context.

     |injection| - The details of the styles to insert.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/scripting#method-insertCSS."
  ([injection] (gen-call :function ::insert-css &form injection)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.scripting namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.scripting",
   :since "master",
   :functions
   [{:id ::execute-script,
     :name "executeScript",
     :callback? true,
     :params
     [{:name "injection", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-objects]"}]}}]}
    {:id ::insert-css,
     :name "insertCSS",
     :callback? true,
     :params [{:name "injection", :type "object"} {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))