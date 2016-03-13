(ns chromex.ext.devtools.panels
  "Use the chrome.devtools.panels API to integrate your extension into Developer Tools window UI: create your own panels,
   access existing panels, and add sidebars.
   
     * available since Chrome 18
     * https://developer.chrome.com/extensions/devtools.panels"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-elements
  "Elements panel.
   
   See https://developer.chrome.com/extensions/devtools.panels#property-elements."
  ([] (gen-call :property ::elements &form)))

(defmacro get-sources
  "Sources panel.
   
   See https://developer.chrome.com/extensions/devtools.panels#property-sources."
  ([] (gen-call :property ::sources &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates an extension panel.
   
     |title| - Title that is displayed next to the extension icon in the Developer Tools toolbar.
     |iconPath| - Path of the panel's icon relative to the extension directory.
     |pagePath| - Path of the panel's HTML page relative to the extension directory.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [panel] where:
   
     |panel| - An ExtensionPanel object representing the created panel.
   
   See https://developer.chrome.com/extensions/devtools.panels#method-create."
  ([title icon-path page-path #_callback] (gen-call :function ::create &form title icon-path page-path)))

(defmacro set-open-resource-handler
  "Specifies the function to be called when the user clicks a resource link in the Developer Tools window. To unset the
   handler, either call the method with no parameters or pass null as the parameter.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [resource] where:
   
     |resource| - A 'devtools.inspectedWindow.Resource' object for the resource that was clicked.
   
   See https://developer.chrome.com/extensions/devtools.panels#method-setOpenResourceHandler."
  ([#_callback] (gen-call :function ::set-open-resource-handler &form)))

(defmacro open-resource
  "Requests DevTools to open a URL in a Developer Tools panel.
   
     |url| - The URL of the resource to open.
     |lineNumber| - Specifies the line number to scroll to when the resource is loaded.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/devtools.panels#method-openResource."
  ([url line-number #_callback] (gen-call :function ::open-resource &form url line-number)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.devtools.panels",
   :since "18",
   :properties
   [{:id ::elements, :name "elements", :return-type "devtools.panels.ElementsPanel"}
    {:id ::sources, :name "sources", :since "38", :return-type "devtools.panels.SourcesPanel"}],
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
     :since "38",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "line-number", :type "integer"}
      {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))