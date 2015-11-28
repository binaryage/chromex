(ns chromex.tab-capture
  "Use the chrome.tabCapture API to interact with tab media
   streams.
   
     * available since Chrome 31
     * https://developer.chrome.com/extensions/tabCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro capture
  "Captures the visible area of the currently active tab.  Capture can only be started on the currently active tab after the
   extension has been invoked.  Capture is maintained across page navigations within the tab, and stops when the tab is
   closed, or the media stream is closed by the extension.
   
     |options| - Configures the returned media stream.
     |callback| - Callback with either the tab capture stream or   null.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([options #_callback] (gen-call :function ::capture &form options)))

(defmacro get-captured-tabs
  "Returns a list of tabs that have requested capture or are being captured, i.e. status != stopped and status != error. This
   allows extensions to inform the user that there is an existing tab capture that would prevent a new tab capture from
   succeeding (or to prevent redundant requests for the same tab).
   
     |callback| - Callback invoked with CaptureInfo[] for captured tabs.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-captured-tabs &form)))

(defmacro capture-offscreen-tab
  "Creates an off-screen tab and navigates it to the given |startUrl|. Then, capture is started and a MediaStream is returned
   via |callback|.Off-screen tabs are isolated from the user's normal browser experience. They do not show up in the browser
   window or tab strip, nor are they visible to extensions (e.g., via the chrome.tabs.* APIs).An off-screen tab remains alive
   until one of three events occurs: 1. All MediaStreams providing its captured content are closed; 2. the page self-closes
   (e.g., via window.close()); 3. the extension that called captureOffscreenTab() is unloaded.Sandboxing: The off-screen tab
   does not have any access whatsoever to the local user profile (including cookies, HTTP auth, etc.).  Instead, it is
   provided its own sandboxed profile.  Also, it cannot access any interactive resources such as keyboard/mouse input, media
   recording devices (e.g., web cams), copy/paste to/from the system clipboard, etc.Note: This is a new API, currently only
   available in Canary/Dev channel, and may change without notice.
   
     |options| - Constraints for the capture and returned MediaStream.
     |callback| - null on error.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([start-url options #_callback] (gen-call :function ::capture-offscreen-tab &form start-url options)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-status-changed-events
  "Event fired when the capture status of a tab changes. This allows extension authors to keep track of the capture status of
   tabs to keep UI elements like page actions in sync.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-status-changed &form channel args)))

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
  {:namespace "chrome.tabCapture",
   :since "31",
   :functions
   [{:id ::capture,
     :name "capture",
     :callback? true,
     :params
     [{:name "options", :type "tabCapture.CaptureOptions"}
      {:name "callback", :type :callback, :callback {:params [{:name "stream", :type "LocalMediaStream"}]}}]}
    {:id ::get-captured-tabs,
     :name "getCapturedTabs",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-tabCapture.CaptureInfos]"}]}}]}
    {:id ::capture-offscreen-tab,
     :name "captureOffscreenTab",
     :since "47",
     :callback? true,
     :params
     [{:name "start-url", :type "string"}
      {:name "options", :type "tabCapture.CaptureOptions"}
      {:name "callback", :type :callback, :callback {:params [{:name "stream", :type "LocalMediaStream"}]}}]}],
   :events
   [{:id ::on-status-changed, :name "onStatusChanged", :params [{:name "info", :type "tabCapture.CaptureInfo"}]}]})

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