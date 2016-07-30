(ns chromex.ext.automation-internal
  "This is the implementation layer of the chrome.automation API, and is
   essentially a translation of the internal accessibility tree update system
   into an extension API.

     * available since Chrome 54"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro enable-tab
  "Enable automation of the tab with the given id, or the active tab if no tab id is given, and retrieves accessibility tree
   id for use in future updates.

     |args| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [tree-id] where:

     |tree-id| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([args] (gen-call :function ::enable-tab &form args)))

(defmacro enable-frame
  "Enable automation of the frame with the given tree id.

     |tree-id| - ?"
  ([tree-id] (gen-call :function ::enable-frame &form tree-id)))

(defmacro enable-desktop
  "Enables desktop automation.

     |routing-id| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([routing-id] (gen-call :function ::enable-desktop &form routing-id)))

(defmacro perform-action
  "Performs an action on an automation node.

     |args| - ?
     |opt-args| - ?"
  ([args opt-args] (gen-call :function ::perform-action &form args opt-args)))

(defmacro query-selector
  "Performs a query selector query.

     |args| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result-automation-node-id] where:

     |result-automation-node-id| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([args] (gen-call :function ::query-selector &form args)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-accessibility-event-events
  "Fired when an accessibility event occurs

   Events will be put on the |channel| with signature [::on-accessibility-event [update]] where:

     |update| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-accessibility-event &form channel args)))

(defmacro tap-on-accessibility-tree-destroyed-events
  "
   Events will be put on the |channel| with signature [::on-accessibility-tree-destroyed [tree-id]] where:

     |tree-id| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-accessibility-tree-destroyed &form channel args)))

(defmacro tap-on-tree-change-events
  "
   Events will be put on the |channel| with signature [::on-tree-change [observer-id tree-id node-id change-type]] where:

     |observer-id| - ?
     |tree-id| - ?
     |node-id| - ?
     |change-type| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-tree-change &form channel args)))

(defmacro tap-on-child-tree-id-events
  "
   Events will be put on the |channel| with signature [::on-child-tree-id [tree-id node-id]] where:

     |tree-id| - ?
     |node-id| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-child-tree-id &form channel args)))

(defmacro tap-on-nodes-removed-events
  "
   Events will be put on the |channel| with signature [::on-nodes-removed [tree-id node-i-ds]] where:

     |tree-id| - ?
     |node-i-ds| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-nodes-removed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.automation-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.automationInternal",
   :since "54",
   :functions
   [{:id ::enable-tab,
     :name "enableTab",
     :callback? true,
     :params
     [{:name "args", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "tree-id", :type "integer"}]}}]}
    {:id ::enable-frame, :name "enableFrame", :params [{:name "tree-id", :type "integer"}]}
    {:id ::enable-desktop,
     :name "enableDesktop",
     :callback? true,
     :params [{:name "routing-id", :type "integer"} {:name "callback", :type :callback}]}
    {:id ::perform-action,
     :name "performAction",
     :params [{:name "args", :type "object"} {:name "opt-args", :type "object"}]}
    {:id ::query-selector,
     :name "querySelector",
     :callback? true,
     :params
     [{:name "args", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result-automation-node-id", :type "integer"}]}}]}],
   :events
   [{:id ::on-accessibility-event, :name "onAccessibilityEvent", :params [{:name "update", :type "object"}]}
    {:id ::on-accessibility-tree-destroyed,
     :name "onAccessibilityTreeDestroyed",
     :params [{:name "tree-id", :type "integer"}]}
    {:id ::on-tree-change,
     :name "onTreeChange",
     :params
     [{:name "observer-id", :type "integer"}
      {:name "tree-id", :type "integer"}
      {:name "node-id", :type "integer"}
      {:name "change-type", :type "string"}]}
    {:id ::on-child-tree-id,
     :name "onChildTreeID",
     :params [{:name "tree-id", :type "integer"} {:name "node-id", :type "integer"}]}
    {:id ::on-nodes-removed,
     :name "onNodesRemoved",
     :params [{:name "tree-id", :type "integer"} {:name "node-i-ds", :type "[array-of-integers]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))