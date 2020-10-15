(ns chromex.ext.web-navigation
  "Use the chrome.webNavigation API to receive notifications about the status of navigation requests in-flight.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/webNavigation"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-frame
  "Retrieves information about the given frame. A frame refers to an &lt;iframe&gt; or a &lt;frame&gt; of a web page and is
   identified by a tab ID and a frame ID.

     |details| - Information about the frame to retrieve information about.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [details] where:

     |details| - Information about the requested frame, null if the specified frame ID and/or tab ID are invalid.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/webNavigation#method-getFrame."
  ([details] (gen-call :function ::get-frame &form details)))

(defmacro get-all-frames
  "Retrieves information about all frames of a given tab.

     |details| - Information about the tab to retrieve all frames from.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [details] where:

     |details| - A list of frames in the given tab, null if the specified tab ID is invalid.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/webNavigation#method-getAllFrames."
  ([details] (gen-call :function ::get-all-frames &form details)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-before-navigate-events
  "Fired when a navigation is about to occur.

   Events will be put on the |channel| with signature [::on-before-navigate [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onBeforeNavigate-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onBeforeNavigate."
  ([channel & args] (apply gen-call :event ::on-before-navigate &form channel args)))

(defmacro tap-on-committed-events
  "Fired when a navigation is committed. The document (and the resources it refers to, such as images and subframes) might
   still be downloading, but at least part of the document has been received from the server and the browser has decided to
   switch to the new document.

   Events will be put on the |channel| with signature [::on-committed [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onCommitted-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onCommitted."
  ([channel & args] (apply gen-call :event ::on-committed &form channel args)))

(defmacro tap-on-dom-content-loaded-events
  "Fired when the page's DOM is fully constructed, but the referenced resources may not finish loading.

   Events will be put on the |channel| with signature [::on-dom-content-loaded [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onDOMContentLoaded-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onDOMContentLoaded."
  ([channel & args] (apply gen-call :event ::on-dom-content-loaded &form channel args)))

(defmacro tap-on-completed-events
  "Fired when a document, including the resources it refers to, is completely loaded and initialized.

   Events will be put on the |channel| with signature [::on-completed [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onCompleted-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onCompleted."
  ([channel & args] (apply gen-call :event ::on-completed &form channel args)))

(defmacro tap-on-error-occurred-events
  "Fired when an error occurs and the navigation is aborted. This can happen if either a network error occurred, or the user
   aborted the navigation.

   Events will be put on the |channel| with signature [::on-error-occurred [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onErrorOccurred-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onErrorOccurred."
  ([channel & args] (apply gen-call :event ::on-error-occurred &form channel args)))

(defmacro tap-on-created-navigation-target-events
  "Fired when a new window, or a new tab in an existing window, is created to host a navigation.

   Events will be put on the |channel| with signature [::on-created-navigation-target [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onCreatedNavigationTarget-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onCreatedNavigationTarget."
  ([channel & args] (apply gen-call :event ::on-created-navigation-target &form channel args)))

(defmacro tap-on-reference-fragment-updated-events
  "Fired when the reference fragment of a frame was updated. All future events for that frame will use the updated URL.

   Events will be put on the |channel| with signature [::on-reference-fragment-updated [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onReferenceFragmentUpdated-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onReferenceFragmentUpdated."
  ([channel & args] (apply gen-call :event ::on-reference-fragment-updated &form channel args)))

(defmacro tap-on-tab-replaced-events
  "Fired when the contents of the tab is replaced by a different (usually previously pre-rendered) tab.

   Events will be put on the |channel| with signature [::on-tab-replaced [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onTabReplaced-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onTabReplaced."
  ([channel & args] (apply gen-call :event ::on-tab-replaced &form channel args)))

(defmacro tap-on-history-state-updated-events
  "Fired when the frame's history was updated to a new URL. All future events for that frame will use the updated URL.

   Events will be put on the |channel| with signature [::on-history-state-updated [details]] where:

     |details| - https://developer.chrome.com/extensions/webNavigation#property-onHistoryStateUpdated-details.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/webNavigation#event-onHistoryStateUpdated."
  ([channel & args] (apply gen-call :event ::on-history-state-updated &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.web-navigation namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webNavigation",
   :since "38",
   :functions
   [{:id ::get-frame,
     :name "getFrame",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :optional? true, :type "object"}]}}]}
    {:id ::get-all-frames,
     :name "getAllFrames",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "details", :optional? true, :type "[array-of-objects]"}]}}]}],
   :events
   [{:id ::on-before-navigate, :name "onBeforeNavigate", :params [{:name "details", :type "object"}]}
    {:id ::on-committed, :name "onCommitted", :params [{:name "details", :type "object"}]}
    {:id ::on-dom-content-loaded, :name "onDOMContentLoaded", :params [{:name "details", :type "object"}]}
    {:id ::on-completed, :name "onCompleted", :params [{:name "details", :type "object"}]}
    {:id ::on-error-occurred, :name "onErrorOccurred", :params [{:name "details", :type "object"}]}
    {:id ::on-created-navigation-target, :name "onCreatedNavigationTarget", :params [{:name "details", :type "object"}]}
    {:id ::on-reference-fragment-updated,
     :name "onReferenceFragmentUpdated",
     :params [{:name "details", :type "object"}]}
    {:id ::on-tab-replaced, :name "onTabReplaced", :params [{:name "details", :type "object"}]}
    {:id ::on-history-state-updated, :name "onHistoryStateUpdated", :params [{:name "details", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))