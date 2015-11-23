(ns chromex.devtools.inspected-window
  "Use the chrome.devtools.inspectedWindow API to interact with the inspected window: obtain the tab ID for the
   inspected page, evaluate the code in the context of the inspected window, reload the page, or obtain the list of
   resources within the page.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/devtools.inspectedWindow"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-tab-id
  "The ID of the tab being inspected. This ID may be used with chrome.tabs.* API."
  ([] (gen-call :property ::tab-id (meta &form))))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro eval
  "Evaluates a JavaScript expression in the context of the main frame of the inspected page. The expression must
   evaluate to a JSON-compliant object, otherwise an exception is thrown. The eval function can report either a
   DevTools-side error or a JavaScript exception that occurs during evaluation. In either case, the result parameter
   of the callback is undefined. In the case of a DevTools-side error, the isException parameter is non-null and has
   isError set to true and code set to an error code. In the case of a JavaScript error, isException is set to true
   and value is set to the string value of thrown object.
   
     |expression| - An expression to evaluate.
     |options| - The options parameter can contain one or more options.
     |callback| - A function called when evaluation completes."
  ([expression options #_callback] (gen-call :function ::eval (meta &form) expression options))
  ([expression] `(eval ~expression :omit)))

(defmacro reload
  "Reloads the inspected page."
  ([reload-options] (gen-call :function ::reload (meta &form) reload-options))
  ([] `(reload :omit)))

(defmacro get-resources
  "Retrieves the list of resources from the inspected page.
   
     |callback| - A function that receives the list of resources when the request completes."
  ([#_callback] (gen-call :function ::get-resources (meta &form))))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-resource-added-events
  "Fired when a new resource is added to the inspected page."
  ([channel] (gen-call :event ::on-resource-added (meta &form) channel)))

(defmacro tap-on-resource-content-committed-events
  "Fired when a new revision of the resource is committed (e.g. user saves an edited version of the resource in the
   Developer Tools)."
  ([channel] (gen-call :event ::on-resource-content-committed (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.inspectedWindow",
   :since "18",
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
     :params
     [{:name "resource", :type "devtools.inspectedWindow.Resource"} {:name "content", :type "string"}]}]})

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