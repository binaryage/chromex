(ns chromex.ext.devtools.panels
  "Use the chrome.devtools.panels API to integrate your extension into Developer Tools window UI: create your own panels,
   access existing panels, and add sidebars.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/devtools.panels"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-elements
  "Elements panel.

   https://developer.chrome.com/extensions/devtools.panels#property-elements."
  ([] (gen-call :property ::elements &form)))

(defmacro get-sources
  "Sources panel.

   https://developer.chrome.com/extensions/devtools.panels#property-sources."
  ([] (gen-call :property ::sources &form)))

(defmacro get-theme-name
  "The name of the color theme set in user's DevTools settings. Possible values: default (the default) and dark.

   https://developer.chrome.com/extensions/devtools.panels#property-themeName."
  ([] (gen-call :property ::theme-name &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates an extension panel.

     |title| - Title that is displayed next to the extension icon in the Developer Tools toolbar.
     |icon-path| - Path of the panel's icon relative to the extension directory.
     |page-path| - Path of the panel's HTML page relative to the extension directory.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [panel] where:

     |panel| - An ExtensionPanel object representing the created panel.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.panels#method-create."
  ([title icon-path page-path] (gen-call :function ::create &form title icon-path page-path)))

(defmacro set-open-resource-handler
  "Specifies the function to be called when the user clicks a resource link in the Developer Tools window. To unset the
   handler, either call the method with no parameters or pass null as the parameter.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [resource] where:

     |resource| - A 'devtools.inspectedWindow.Resource' object for the resource that was clicked.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.panels#method-setOpenResourceHandler."
  ([] (gen-call :function ::set-open-resource-handler &form)))

(defmacro open-resource
  "Requests DevTools to open a URL in a Developer Tools panel.

     |url| - The URL of the resource to open.
     |line-number| - Specifies the line number to scroll to when the resource is loaded.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/devtools.panels#method-openResource."
  ([url line-number] (gen-call :function ::open-resource &form url line-number)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.devtools.panels namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.panels",
   :since "38",
   :properties
   [{:id ::elements, :name "elements", :return-type "devtools.panels.ElementsPanel"}
    {:id ::sources, :name "sources", :return-type "devtools.panels.SourcesPanel"}
    {:id ::theme-name, :name "themeName", :since "59", :return-type "string"}],
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "title", :type "string"}
      {:name "icon-path", :type "string"}
      {:name "page-path", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "panel", :type "devtools.panels.ExtensionPanel"}]}}]}
    {:id ::set-open-resource-handler,
     :name "setOpenResourceHandler",
     :callback? true,
     :params
     [{:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "resource", :type "devtools.inspectedWindow.Resource"}]}}]}
    {:id ::open-resource,
     :name "openResource",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "line-number", :type "integer"}
      {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))