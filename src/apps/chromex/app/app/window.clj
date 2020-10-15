(ns chromex.app.app.window
  "Use the chrome.app.window API to create windows. Windows
   have an optional frame with title bar and size controls. They are not
   associated with any Chrome browser windows. See the 
   Window State Sample for a demonstration of these options.

     * available since Chrome 38
     * https://developer.chrome.com/apps/app.window"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

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

     |url| - https://developer.chrome.com/apps/app.window#property-create-url.
     |options| - https://developer.chrome.com/apps/app.window#property-create-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [created-window] where:

     |created-window| - https://developer.chrome.com/apps/app.window#property-callback-createdWindow.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/app.window#method-create."
  ([url options] (gen-call :function ::create &form url options))
  ([url] `(create ~url :omit)))

(defmacro current
  "Returns an 'AppWindow' object for the current script context (ie JavaScript 'window' object). This can also be called on a
   handle to a script context for another page, for example: otherWindow.chrome.app.window.current().

   https://developer.chrome.com/apps/app.window#method-current."
  ([] (gen-call :function ::current &form)))

(defmacro get-all
  "Gets an array of all currently created app windows. This method is new in Chrome 33.

   https://developer.chrome.com/apps/app.window#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro get
  "Gets an 'AppWindow' with the given id. If no window with the given id exists null is returned. This method is new in Chrome
   33.

     |id| - https://developer.chrome.com/apps/app.window#property-get-id.

   https://developer.chrome.com/apps/app.window#method-get."
  ([id] (gen-call :function ::get &form id)))

(defmacro can-set-visible-on-all-workspaces
  "Whether the current platform supports windows being visible on all workspaces.

   https://developer.chrome.com/apps/app.window#method-canSetVisibleOnAllWorkspaces."
  ([] (gen-call :function ::can-set-visible-on-all-workspaces &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-bounds-changed-events
  "Fired when the window is resized.

   Events will be put on the |channel| with signature [::on-bounds-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onBoundsChanged."
  ([channel & args] (apply gen-call :event ::on-bounds-changed &form channel args)))

(defmacro tap-on-closed-events
  "Fired when the window is closed. Note, this should be listened to from a window other than the window being closed, for
   example from the background page. This is because the window being closed will be in the process of being torn down when
   the event is fired, which means not all APIs in the window's script context will be functional.

   Events will be put on the |channel| with signature [::on-closed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onClosed."
  ([channel & args] (apply gen-call :event ::on-closed &form channel args)))

(defmacro tap-on-fullscreened-events
  "Fired when the window is fullscreened (either via the AppWindow or HTML5 APIs).

   Events will be put on the |channel| with signature [::on-fullscreened []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onFullscreened."
  ([channel & args] (apply gen-call :event ::on-fullscreened &form channel args)))

(defmacro tap-on-maximized-events
  "Fired when the window is maximized.

   Events will be put on the |channel| with signature [::on-maximized []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onMaximized."
  ([channel & args] (apply gen-call :event ::on-maximized &form channel args)))

(defmacro tap-on-minimized-events
  "Fired when the window is minimized.

   Events will be put on the |channel| with signature [::on-minimized []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onMinimized."
  ([channel & args] (apply gen-call :event ::on-minimized &form channel args)))

(defmacro tap-on-restored-events
  "Fired when the window is restored from being minimized or maximized.

   Events will be put on the |channel| with signature [::on-restored []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/app.window#event-onRestored."
  ([channel & args] (apply gen-call :event ::on-restored &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.app.window namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.app.window",
   :since "38",
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
    {:id ::get-all, :name "getAll", :return-type "[array-of-app.window.AppWindows]"}
    {:id ::get, :name "get", :return-type "app.window.AppWindow", :params [{:name "id", :type "string"}]}
    {:id ::can-set-visible-on-all-workspaces,
     :name "canSetVisibleOnAllWorkspaces",
     :since "42",
     :return-type "boolean"}],
   :events
   [{:id ::on-bounds-changed, :name "onBoundsChanged"}
    {:id ::on-closed, :name "onClosed"}
    {:id ::on-fullscreened, :name "onFullscreened"}
    {:id ::on-maximized, :name "onMaximized"}
    {:id ::on-minimized, :name "onMinimized"}
    {:id ::on-restored, :name "onRestored"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))