(ns chromex.ext.tab-capture
  "Use the chrome.tabCapture API to interact with tab media
   streams.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/tabCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro capture
  "Captures the visible area of the currently active tab.  Capture can only be started on the currently active tab after the
   extension has been invoked, similar to the way that activeTab works.  Capture is maintained across page navigations within
   the tab, and stops when the tab is closed, or the media stream is closed by the extension.

     |options| - Configures the returned media stream.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [stream] where:

     |stream| - https://developer.chrome.com/extensions/tabCapture#property-callback-stream.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabCapture#method-capture."
  ([options] (gen-call :function ::capture &form options)))

(defmacro get-captured-tabs
  "Returns a list of tabs that have requested capture or are being captured, i.e. status != stopped and status != error. This
   allows extensions to inform the user that there is an existing tab capture that would prevent a new tab capture from
   succeeding (or to prevent redundant requests for the same tab).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/tabCapture#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabCapture#method-getCapturedTabs."
  ([] (gen-call :function ::get-captured-tabs &form)))

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

     |start-url| - https://developer.chrome.com/extensions/tabCapture#property-captureOffscreenTab-startUrl.
     |options| - Constraints for the capture and returned MediaStream.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [stream] where:

     |stream| - https://developer.chrome.com/extensions/tabCapture#property-callback-stream.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabCapture#method-captureOffscreenTab."
  ([start-url options] (gen-call :function ::capture-offscreen-tab &form start-url options)))

(defmacro get-media-stream-id
  "Creates a stream ID to capture the target tab. Similar to chrome.tabCapture.capture() method, but returns a media stream
   ID, instead of a media stream, to the consumer tab.

     |options| - https://developer.chrome.com/extensions/tabCapture#property-getMediaStreamId-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [stream-id] where:

     |stream-id| - https://developer.chrome.com/extensions/tabCapture#property-callback-streamId.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabCapture#method-getMediaStreamId."
  ([options] (gen-call :function ::get-media-stream-id &form options))
  ([] `(get-media-stream-id :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-status-changed-events
  "Event fired when the capture status of a tab changes. This allows extension authors to keep track of the capture status of
   tabs to keep UI elements like page actions in sync.

   Events will be put on the |channel| with signature [::on-status-changed [info]] where:

     |info| - CaptureInfo with new capture status for the tab.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/tabCapture#event-onStatusChanged."
  ([channel & args] (apply gen-call :event ::on-status-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.tab-capture namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tabCapture",
   :since "38",
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
      {:name "callback", :type :callback, :callback {:params [{:name "stream", :type "LocalMediaStream"}]}}]}
    {:id ::get-media-stream-id,
     :name "getMediaStreamId",
     :since "71",
     :callback? true,
     :params
     [{:name "options", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "stream-id", :type "string"}]}}]}],
   :events
   [{:id ::on-status-changed, :name "onStatusChanged", :params [{:name "info", :type "tabCapture.CaptureInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))