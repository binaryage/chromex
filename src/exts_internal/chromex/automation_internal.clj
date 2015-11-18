(ns chromex.automation-internal
  "This is the implementation layer of the chrome.automation API, and is
   essentially a translation of the internal accessibility tree update system
   into an extension API.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/automationInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro enable-tab
  "Enable automation of the tab with the given id, or the active tab if no tab id is given, and retrieves
   accessibility tree id for use in future updates.
   
     |callback| - Returns the accessibility tree id of the web contents who's accessibility was enabled using
                  enableTab()."
  [args #_callback]
  (gen-call :function ::enable-tab (meta &form) args))

(defmacro enable-frame
  "Enable automation of the frame with the given tree id."
  [tree-id]
  (gen-call :function ::enable-frame (meta &form) tree-id))

(defmacro enable-desktop
  "Enables desktop automation.
   
     |callback| - Callback called when enableDesktop() returns."
  [routing-id #_callback]
  (gen-call :function ::enable-desktop (meta &form) routing-id))

(defmacro perform-action
  "Performs an action on an automation node."
  [args opt-args]
  (gen-call :function ::perform-action (meta &form) args opt-args))

(defmacro query-selector
  "Performs a query selector query.
   
     |callback| - Callback called when querySelector() returns."
  [args #_callback]
  (gen-call :function ::query-selector (meta &form) args))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-accessibility-event
  "Fired when an accessibility event occurs"
  [channel]
  (gen-call :event ::on-accessibility-event (meta &form) channel))

(defmacro tap-on-accessibility-tree-destroyed [channel]
  (gen-call :event ::on-accessibility-tree-destroyed (meta &form) channel))

(defmacro tap-on-tree-change [channel]
  (gen-call :event ::on-tree-change (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.automationInternal",
   :since "48",
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
     [{:name "tree-id", :type "integer"}
      {:name "node-id", :type "integer"}
      {:name "change-type", :type "string"}]}]})

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