(ns chromex.app.app.window
  "Use the chrome.app.window API to create windows. Windows
   have an optional frame with title bar and size controls. They are not
   associated with any Chrome browser windows. See the 
   Window State Sample for a demonstration of these options.
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/app.window"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "The size and position of a window can be specified in a number of different ways. The most simple option is not specifying
   anything at all, in which case a default size and platform dependent position will be used.To set the position, size and
   constraints of the window, use the innerBounds or outerBounds properties. Inner bounds do not include window decorations.
   Outer bounds include the window's title bar and frame. Note that the padding between the inner and outer bounds is
   determined by the OS. Therefore setting the same property for both inner and outer bounds is considered an error (for
   example, setting both innerBounds.left and outerBounds.left).To automatically remember the positions of windows you can
   give them ids. If a window has an id, This id is used to remember the size and position of the window whenever it is moved
   or resized. This size and position is then used instead of the specified bounds on subsequent opening of a window with the
   same id. If you need to open a window with an id at a location other than the remembered default, you can create it hidden,
   move it to the desired location, then show it.
   
     |callback| - Called in the creating window (parent) before the load event is called in the created window (child). The
                  parent can set fields or functions on the child usable from onload. E.g.
                  background.js:function(createdWindow) { createdWindow.contentWindow.foo = function () { };
                  };window.js:window.onload = function () { foo(); }
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([url options #_callback] (gen-call :function ::create &form url options))
  ([url] `(create ~url :omit)))

(defmacro current
  "Returns an 'AppWindow' object for the current script context (ie JavaScript 'window' object). This can also be called on a
   handle to a script context for another page, for example: otherWindow.chrome.app.window.current()."
  ([] (gen-call :function ::current &form)))

(defmacro get-all
  "Gets an array of all currently created app windows. This method is new in Chrome 33."
  ([] (gen-call :function ::get-all &form)))

(defmacro get
  "Gets an 'AppWindow' with the given id. If no window with the given id exists null is returned. This method is new in Chrome
   33."
  ([id] (gen-call :function ::get &form id)))

(defmacro can-set-visible-on-all-workspaces
  "Whether the current platform supports windows being visible on all workspaces."
  ([] (gen-call :function ::can-set-visible-on-all-workspaces &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-bounds-changed-events
  "Fired when the window is resized.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-bounds-changed &form channel args)))
(defmacro tap-on-closed-events
  "Fired when the window is closed. Note, this should be listened to from a window other than the window being closed, for
   example from the background page. This is because the window being closed will be in the process of being torn down when
   the event is fired, which means not all APIs in the window's script context will be functional.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-closed &form channel args)))
(defmacro tap-on-fullscreened-events
  "Fired when the window is fullscreened (either via the AppWindow or HTML5 APIs).
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-fullscreened &form channel args)))
(defmacro tap-on-maximized-events
  "Fired when the window is maximized.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-maximized &form channel args)))
(defmacro tap-on-minimized-events
  "Fired when the window is minimized.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-minimized &form channel args)))
(defmacro tap-on-restored-events
  "Fired when the window is restored from being minimized or maximized.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-restored &form channel args)))

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
  {:namespace "chrome.app.window",
   :since "23",
   :functions
   [{:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "url", :type "string"}
      {:name "options", :optional? true, :type "app.window.CreateWindowOptions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "created-window", :type "AppWindow"}]}}]}
    {:id ::current, :name "current", :return-type "app.window.AppWindow"}
    {:id ::get-all, :name "getAll", :since "33", :return-type "[array-of-app.window.AppWindows]"}
    {:id ::get, :name "get", :since "33", :return-type "app.window.AppWindow", :params [{:name "id", :type "string"}]}
    {:id ::can-set-visible-on-all-workspaces,
     :name "canSetVisibleOnAllWorkspaces",
     :since "42",
     :return-type "boolean"}],
   :events
   [{:id ::on-bounds-changed, :name "onBoundsChanged", :since "26"}
    {:id ::on-closed, :name "onClosed", :since "26"}
    {:id ::on-fullscreened, :name "onFullscreened", :since "27"}
    {:id ::on-maximized, :name "onMaximized", :since "26"}
    {:id ::on-minimized, :name "onMinimized", :since "26"}
    {:id ::on-restored, :name "onRestored", :since "26"}]})

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