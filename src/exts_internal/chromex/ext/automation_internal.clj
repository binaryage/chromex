(ns chromex.ext.automation-internal
  "This is the implementation layer of the chrome.automation API, and is
   essentially a translation of the internal accessibility tree update system
   into an extension API.
   
     * available since Chrome 49
     * https://developer.chrome.com/extensions/automationInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro enable-tab
  "Enable automation of the tab with the given id, or the active tab if no tab id is given, and retrieves accessibility tree
   id for use in future updates.
   
     |callback| - Returns the accessibility tree id of the web contents who's accessibility was enabled using enableTab().
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([args #_callback] (gen-call :function ::enable-tab &form args)))

(defmacro enable-frame
  "Enable automation of the frame with the given tree id."
  ([tree-id] (gen-call :function ::enable-frame &form tree-id)))

(defmacro enable-desktop
  "Enables desktop automation.
   
     |callback| - Callback called when enableDesktop() returns.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([routing-id #_callback] (gen-call :function ::enable-desktop &form routing-id)))

(defmacro perform-action
  "Performs an action on an automation node."
  ([args opt-args] (gen-call :function ::perform-action &form args opt-args)))

(defmacro query-selector
  "Performs a query selector query.
   
     |callback| - Callback called when querySelector() returns.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([args #_callback] (gen-call :function ::query-selector &form args)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-accessibility-event-events
  "Fired when an accessibility event occurs
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-accessibility-event &form channel args)))
(defmacro tap-on-accessibility-tree-destroyed-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-accessibility-tree-destroyed &form channel args)))
(defmacro tap-on-tree-change-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-tree-change &form channel args)))
(defmacro tap-on-child-tree-id-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-child-tree-id &form channel args)))
(defmacro tap-on-nodes-removed-events
  "
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-nodes-removed &form channel args)))

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
  {:namespace "chrome.automationInternal",
   :since "49",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))