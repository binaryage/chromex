(ns chromex.ext.devtools.inspected-window
  "Use the chrome.devtools.inspectedWindow API to interact with the inspected window: obtain the tab ID for the inspected
   page, evaluate the code in the context of the inspected window, reload the page, or obtain the list of resources within
   the page.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/devtools.inspectedWindow"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-tab-id
  "The ID of the tab being inspected. This ID may be used with chrome.tabs.* API.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#property-tabId."
  ([] (gen-call :property ::tab-id &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro eval
  "Evaluates a JavaScript expression in the context of the main frame of the inspected page. The expression must evaluate to a
   JSON-compliant object, otherwise an exception is thrown. The eval function can report either a DevTools-side error or a
   JavaScript exception that occurs during evaluation. In either case, the result parameter of the callback is undefined. In
   the case of a DevTools-side error, the isException parameter is non-null and has isError set to true and code set to an
   error code. In the case of a JavaScript error, isException is set to true and value is set to the string value of thrown
   object.

     |expression| - An expression to evaluate.
     |options| - The options parameter can contain one or more options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result exception-info] where:

     |result| - The result of evaluation.
     |exception-info| - An object providing details if an exception occurred while evaluating the expression.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#method-eval."
  ([expression options] (gen-call :function ::eval &form expression options))
  ([expression] `(eval ~expression :omit)))

(defmacro reload
  "Reloads the inspected page.

     |reload-options| - https://developer.chrome.com/extensions/devtools.inspectedWindow#property-reload-reloadOptions.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#method-reload."
  ([reload-options] (gen-call :function ::reload &form reload-options))
  ([] `(reload :omit)))

(defmacro get-resources
  "Retrieves the list of resources from the inspected page.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [resources] where:

     |resources| - The resources within the page.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#method-getResources."
  ([] (gen-call :function ::get-resources &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-resource-added-events
  "Fired when a new resource is added to the inspected page.

   Events will be put on the |channel| with signature [::on-resource-added [resource]] where:

     |resource| - https://developer.chrome.com/extensions/devtools.inspectedWindow#property-onResourceAdded-resource.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#event-onResourceAdded."
  ([channel & args] (apply gen-call :event ::on-resource-added &form channel args)))

(defmacro tap-on-resource-content-committed-events
  "Fired when a new revision of the resource is committed (e.g. user saves an edited version of the resource in the Developer
   Tools).

   Events will be put on the |channel| with signature [::on-resource-content-committed [resource content]] where:

     |resource| - https://developer.chrome.com/extensions/devtools.inspectedWindow#property-onResourceContentCommitted-resource.
     |content| - New content of the resource.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/devtools.inspectedWindow#event-onResourceContentCommitted."
  ([channel & args] (apply gen-call :event ::on-resource-content-committed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.devtools.inspected-window namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.inspectedWindow",
   :since "38",
   :properties [{:id ::tab-id, :name "tabId", :return-type "integer"}],
   :functions
   [{:id ::eval,
     :name "eval",
     :callback? true,
     :params
     [{:name "expression", :type "string"}
      {:name "options", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "object"} {:name "exception-info", :type "object"}]}}]}
    {:id ::reload, :name "reload", :params [{:name "reload-options", :optional? true, :type "object"}]}
    {:id ::get-resources,
     :name "getResources",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "resources", :type "[array-of-devtools.inspectedWindow.Resources]"}]}}]}],
   :events
   [{:id ::on-resource-added,
     :name "onResourceAdded",
     :params [{:name "resource", :type "devtools.inspectedWindow.Resource"}]}
    {:id ::on-resource-content-committed,
     :name "onResourceContentCommitted",
     :params [{:name "resource", :type "devtools.inspectedWindow.Resource"} {:name "content", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))