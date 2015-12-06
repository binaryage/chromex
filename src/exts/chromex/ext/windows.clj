(ns chromex.ext.windows
  "Use the chrome.windows API to interact with browser windows. You can use this API to create, modify, and rearrange windows
   in the browser.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/windows"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-window-id-none
  "The windowId value that represents the absence of a chrome browser window."
  ([] (gen-call :property ::window-id-none &form)))

(defmacro get-window-id-current
  "The windowId value that represents the current window."
  ([] (gen-call :property ::window-id-current &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets details about a window.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([window-id get-info #_callback] (gen-call :function ::get &form window-id get-info))
  ([window-id] `(get ~window-id :omit)))

(defmacro get-current
  "Gets the current window.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([get-info #_callback] (gen-call :function ::get-current &form get-info))
  ([] `(get-current :omit)))

(defmacro get-last-focused
  "Gets the window that was most recently focused &mdash; typically the window 'on top'.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([get-info #_callback] (gen-call :function ::get-last-focused &form get-info))
  ([] `(get-last-focused :omit)))

(defmacro get-all
  "Gets all windows.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([get-info #_callback] (gen-call :function ::get-all &form get-info))
  ([] `(get-all :omit)))

(defmacro create
  "Creates (opens) a new browser with any optional sizing, position or default URL provided.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([create-data #_callback] (gen-call :function ::create &form create-data))
  ([] `(create :omit)))

(defmacro update
  "Updates the properties of a window. Specify only the properties that you want to change; unspecified properties will be
   left unchanged.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([window-id update-info #_callback] (gen-call :function ::update &form window-id update-info)))

(defmacro remove
  "Removes (closes) a window, and all the tabs inside it.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([window-id #_callback] (gen-call :function ::remove &form window-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "Fired when a window is created.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))
(defmacro tap-on-removed-events
  "Fired when a window is removed (closed).
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-removed &form channel args)))
(defmacro tap-on-focus-changed-events
  "Fired when the currently focused window changes. Will be chrome.windows.WINDOW_ID_NONE if all chrome windows have lost
   focus. Note: On some Linux window managers, WINDOW_ID_NONE will always be sent immediately preceding a switch from one
   chrome window to another.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-focus-changed &form channel args)))

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
  {:namespace "chrome.windows",
   :since "5",
   :properties
   [{:id ::window-id-none, :name "WINDOW_ID_NONE", :since "6", :return-type "unknown-type"}
    {:id ::window-id-current, :name "WINDOW_ID_CURRENT", :since "18", :return-type "unknown-type"}],
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "window-id", :type "integer"}
      {:name "get-info", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "window", :type "windows.Window"}]}}]}
    {:id ::get-current,
     :name "getCurrent",
     :callback? true,
     :params
     [{:name "get-info", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "window", :type "windows.Window"}]}}]}
    {:id ::get-last-focused,
     :name "getLastFocused",
     :callback? true,
     :params
     [{:name "get-info", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "window", :type "windows.Window"}]}}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "get-info", :optional? true, :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "windows", :type "[array-of-windows.Windows]"}]}}]}
    {:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "create-data", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "window", :optional? true, :type "windows.Window"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "window-id", :type "integer"}
      {:name "update-info", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "window", :type "windows.Window"}]}}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params [{:name "window-id", :type "integer"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-created, :name "onCreated", :params [{:name "window", :type "windows.Window"}]}
    {:id ::on-removed, :name "onRemoved", :params [{:name "window-id", :type "integer"}]}
    {:id ::on-focus-changed, :name "onFocusChanged", :params [{:name "window-id", :type "integer"}]}]})

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