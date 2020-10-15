(ns chromex.app.windows
  "Use the chrome.windows API to interact with browser windows. You can use this API to create, modify, and rearrange windows
   in the browser.

     * available since Chrome 38
     * https://developer.chrome.com/apps/windows"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-window-id-none
  "The windowId value that represents the absence of a chrome browser window.

   https://developer.chrome.com/apps/windows#property-WINDOW_ID_NONE."
  ([] (gen-call :property ::window-id-none &form)))

(defmacro get-window-id-current
  "The windowId value that represents the current window.

   https://developer.chrome.com/apps/windows#property-WINDOW_ID_CURRENT."
  ([] (gen-call :property ::window-id-current &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets details about a window.

     |window-id| - https://developer.chrome.com/apps/windows#property-get-windowId.
     |get-info| - https://developer.chrome.com/apps/windows#property-get-getInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - https://developer.chrome.com/apps/windows#property-callback-window.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-get."
  ([window-id get-info] (gen-call :function ::get &form window-id get-info))
  ([window-id] `(get ~window-id :omit)))

(defmacro get-current
  "Gets the current window.

     |get-info| - https://developer.chrome.com/apps/windows#property-getCurrent-getInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - https://developer.chrome.com/apps/windows#property-callback-window.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-getCurrent."
  ([get-info] (gen-call :function ::get-current &form get-info))
  ([] `(get-current :omit)))

(defmacro get-last-focused
  "Gets the window that was most recently focused &mdash; typically the window 'on top'.

     |get-info| - https://developer.chrome.com/apps/windows#property-getLastFocused-getInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - https://developer.chrome.com/apps/windows#property-callback-window.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-getLastFocused."
  ([get-info] (gen-call :function ::get-last-focused &form get-info))
  ([] `(get-last-focused :omit)))

(defmacro get-all
  "Gets all windows.

     |get-info| - https://developer.chrome.com/apps/windows#property-getAll-getInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [windows] where:

     |windows| - https://developer.chrome.com/apps/windows#property-callback-windows.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-getAll."
  ([get-info] (gen-call :function ::get-all &form get-info))
  ([] `(get-all :omit)))

(defmacro create
  "Creates (opens) a new browser window with any optional sizing, position, or default URL provided.

     |create-data| - https://developer.chrome.com/apps/windows#property-create-createData.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - Contains details about the created window.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-create."
  ([create-data] (gen-call :function ::create &form create-data))
  ([] `(create :omit)))

(defmacro update
  "Updates the properties of a window. Specify only the properties that to be changed; unspecified properties are unchanged.

     |window-id| - https://developer.chrome.com/apps/windows#property-update-windowId.
     |update-info| - https://developer.chrome.com/apps/windows#property-update-updateInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [window] where:

     |window| - https://developer.chrome.com/apps/windows#property-callback-window.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-update."
  ([window-id update-info] (gen-call :function ::update &form window-id update-info)))

(defmacro remove
  "Removes (closes) a window and all the tabs inside it.

     |window-id| - https://developer.chrome.com/apps/windows#property-remove-windowId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/windows#method-remove."
  ([window-id] (gen-call :function ::remove &form window-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "Fired when a window is created.

   Events will be put on the |channel| with signature [::on-created [window]] where:

     |window| - Details of the created window.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/windows#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-removed-events
  "Fired when a window is removed (closed).

   Events will be put on the |channel| with signature [::on-removed [window-id]] where:

     |window-id| - ID of the removed window.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/windows#event-onRemoved."
  ([channel & args] (apply gen-call :event ::on-removed &form channel args)))

(defmacro tap-on-focus-changed-events
  "Fired when the currently focused window changes. Returns chrome.windows.WINDOW_ID_NONE if all Chrome windows have lost
   focus. Note: On some Linux window managers, WINDOW_ID_NONE is always sent immediately preceding a switch from one Chrome
   window to another.

   Events will be put on the |channel| with signature [::on-focus-changed [window-id]] where:

     |window-id| - ID of the newly-focused window.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/windows#event-onFocusChanged."
  ([channel & args] (apply gen-call :event ::on-focus-changed &form channel args)))

(defmacro tap-on-bounds-changed-events
  "Fired when a window has been resized; this event is only dispatched when the new bounds are committed, and not for
   in-progress changes.

   Events will be put on the |channel| with signature [::on-bounds-changed [window]] where:

     |window| - Details of the window. The tabs will not be populated for the window.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/windows#event-onBoundsChanged."
  ([channel & args] (apply gen-call :event ::on-bounds-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.windows namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.windows",
   :since "38",
   :properties
   [{:id ::window-id-none, :name "WINDOW_ID_NONE", :return-type "unknown-type"}
    {:id ::window-id-current, :name "WINDOW_ID_CURRENT", :return-type "unknown-type"}],
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
    {:id ::on-focus-changed, :name "onFocusChanged", :params [{:name "window-id", :type "integer"}]}
    {:id ::on-bounds-changed, :name "onBoundsChanged", :since "86", :params [{:name "window", :type "windows.Window"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))