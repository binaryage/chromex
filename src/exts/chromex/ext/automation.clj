(ns chromex.ext.automation
  "The chrome.automation API allows developers to access the
   automation (accessibility) tree for the browser. The tree resembles the DOM
   tree, but only exposes the semantic structure of a page. It can be
   used to programmatically interact with a page by examining names, roles, and
   states, listening for events, and performing actions on nodes.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/automation"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-tree
  "Get the automation tree for the tab with the given tabId, or the current tab if no tabID is given, enabling automation if
   necessary. Returns a tree with a placeholder root node; listen for the 'loadComplete' event to get a notification that the
   tree has fully loaded (the previous root node reference will stop working at or before this point).
   
     |callback| - Called when the AutomationNode for the page is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([tab-id #_callback] (gen-call :function ::get-tree &form tab-id))
  ([] `(get-tree :omit)))

(defmacro get-desktop
  "Get the automation tree for the whole desktop which consists of all on screen views. Note this API is currently only
   supported on Chrome OS.
   
     |callback| - Called when the AutomationNode for the page is available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-desktop &form)))

(defmacro add-tree-change-observer
  "Add a tree change observer. Tree change observers are static/global, they listen to changes across all trees.
   
     |observer| - A listener for changes on the AutomationNode tree.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_observer] (gen-call :function ::add-tree-change-observer &form)))

(defmacro remove-tree-change-observer
  "Remove a tree change observer.
   
     |observer| - A listener for changes on the AutomationNode tree.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_observer] (gen-call :function ::remove-tree-change-observer &form)))

(defmacro set-document-selection
  "Sets the selection in a tree. This creates a selection in a single tree (anchorObject and focusObject must have the same
   root). Everything in the tree between the two node/offset pairs gets included in the selection. The anchor is where the
   user started the selection, while the focus is the point at which the selection gets extended e.g. when dragging with a
   mouse or using the keyboard. For nodes with the role staticText, the offset gives the character offset within the value
   where the selection starts or ends, respectively."
  ([params] (gen-call :function ::set-document-selection &form params)))

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
  {:namespace "chrome.automation",
   :since "48",
   :functions
   [{:id ::get-tree,
     :name "getTree",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "root-node", :type "automation.AutomationNode"}]}}]}
    {:id ::get-desktop,
     :name "getDesktop",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "root-node", :type "automation.AutomationNode"}]}}]}
    {:id ::add-tree-change-observer,
     :name "addTreeChangeObserver",
     :callback? true,
     :params
     [{:name "observer", :type :callback, :callback {:params [{:name "tree-change", :type "automation.TreeChange"}]}}]}
    {:id ::remove-tree-change-observer,
     :name "removeTreeChangeObserver",
     :callback? true,
     :params
     [{:name "observer", :type :callback, :callback {:params [{:name "tree-change", :type "automation.TreeChange"}]}}]}
    {:id ::set-document-selection,
     :name "setDocumentSelection",
     :params [{:name "params", :type "automation.SetDocumentSelectionParams"}]}]})

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