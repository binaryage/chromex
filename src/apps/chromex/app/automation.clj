(ns chromex.app.automation
  "The chrome.automation API allows developers to access the
   automation (accessibility) tree for the browser. The tree resembles the DOM
   tree, but only exposes the semantic structure of a page. It can be
   used to programmatically interact with a page by examining names, roles, and
   states, listening for events, and performing actions on nodes.

     * available since Chrome 88
     * https://developer.chrome.com/apps/automation"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-tree
  "Get the automation tree for the tab with the given tabId, or the current tab if no tabID is given, enabling automation if
   necessary. Returns a tree with a placeholder root node; listen for the 'loadComplete' event to get a notification that the
   tree has fully loaded (the previous root node reference will stop working at or before this point).

     |tab-id| - https://developer.chrome.com/apps/automation#property-getTree-tabId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [root-node] where:

     |root-node| - https://developer.chrome.com/apps/automation#property-callback-rootNode.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-getTree."
  ([tab-id] (gen-call :function ::get-tree &form tab-id))
  ([] `(get-tree :omit)))

(defmacro get-desktop
  "Get the automation tree for the whole desktop which consists of all on screen views. Note this API is currently only
   supported on Chrome OS.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [root-node] where:

     |root-node| - https://developer.chrome.com/apps/automation#property-callback-rootNode.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-getDesktop."
  ([] (gen-call :function ::get-desktop &form)))

(defmacro get-focus
  "Get the automation node that currently has focus, globally. Will return null if none of the nodes in any loaded trees have
   focus.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [focused-node] where:

     |focused-node| - https://developer.chrome.com/apps/automation#property-callback-focusedNode.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-getFocus."
  ([] (gen-call :function ::get-focus &form)))

(defmacro get-accessibility-focus
  "Get the automation node that currently has accessibility focus, globally. Will return null if none of the nodes in any
   loaded trees have accessibility focus.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [focused-node] where:

     |focused-node| - https://developer.chrome.com/apps/automation#property-callback-focusedNode.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-getAccessibilityFocus."
  ([] (gen-call :function ::get-accessibility-focus &form)))

(defmacro add-tree-change-observer
  "Add a tree change observer. Tree change observers are static/global, they listen to changes across all trees. Pass a filter
   to determine what specific tree changes to listen to, and note that listnening to all tree changes can be expensive.

     |filter| - https://developer.chrome.com/apps/automation#property-addTreeChangeObserver-filter.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tree-change] where:

     |tree-change| - https://developer.chrome.com/apps/automation#property-observer-treeChange.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-addTreeChangeObserver."
  ([filter] (gen-call :function ::add-tree-change-observer &form filter)))

(defmacro remove-tree-change-observer
  "Remove a tree change observer.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [tree-change] where:

     |tree-change| - https://developer.chrome.com/apps/automation#property-observer-treeChange.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/automation#method-removeTreeChangeObserver."
  ([] (gen-call :function ::remove-tree-change-observer &form)))

(defmacro set-document-selection
  "Sets the selection in a tree. This creates a selection in a single tree (anchorObject and focusObject must have the same
   root). Everything in the tree between the two node/offset pairs gets included in the selection. The anchor is where the
   user started the selection, while the focus is the point at which the selection gets extended e.g. when dragging with a
   mouse or using the keyboard. For nodes with the role staticText, the offset gives the character offset within the value
   where the selection starts or ends, respectively.

     |params| - https://developer.chrome.com/apps/automation#property-setDocumentSelection-params.

   https://developer.chrome.com/apps/automation#method-setDocumentSelection."
  ([params] (gen-call :function ::set-document-selection &form params)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.automation namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.automation",
   :since "88",
   :functions
   [{:id ::get-tree,
     :name "getTree",
     :callback? true,
     :params
     [{:name "tab-id", :optional? true, :type "integer"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "root-node", :type "automation.AutomationNode"}]}}]}
    {:id ::get-desktop,
     :name "getDesktop",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "root-node", :type "automation.AutomationNode"}]}}]}
    {:id ::get-focus,
     :name "getFocus",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "focused-node", :type "automation.AutomationNode"}]}}]}
    {:id ::get-accessibility-focus,
     :name "getAccessibilityFocus",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "focused-node", :type "automation.AutomationNode"}]}}]}
    {:id ::add-tree-change-observer,
     :name "addTreeChangeObserver",
     :callback? true,
     :params
     [{:name "filter", :type "unknown-type"}
      {:name "observer", :type :callback, :callback {:params [{:name "tree-change", :type "automation.TreeChange"}]}}]}
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))