(ns chromex.web-navigation
  "Use the chrome.webNavigation API to receive notifications about the status of navigation requests in-flight.
   
     * available since Chrome 16
     * https://developer.chrome.com/extensions/webNavigation"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-frame
  "Retrieves information about the given frame. A frame refers to an &lt;iframe&gt; or a &lt;frame&gt; of a web page
   and is identified by a tab ID and a frame ID.
   
     |details| - Information about the frame to retrieve information about."
  [details #_callback]
  (gen-call :function ::get-frame (meta &form) details))

(defmacro get-all-frames
  "Retrieves information about all frames of a given tab.
   
     |details| - Information about the tab to retrieve all frames from."
  [details #_callback]
  (gen-call :function ::get-all-frames (meta &form) details))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-before-navigate
  "Fired when a navigation is about to occur."
  [channel]
  (gen-call :event ::on-before-navigate (meta &form) channel))

(defmacro tap-on-committed
  "Fired when a navigation is committed. The document (and the resources it refers to, such as images and subframes)
   might still be downloading, but at least part of the document has been received from the server and the browser has
   decided to switch to the new document."
  [channel]
  (gen-call :event ::on-committed (meta &form) channel))

(defmacro tap-on-dom-content-loaded
  "Fired when the page's DOM is fully constructed, but the referenced resources may not finish loading."
  [channel]
  (gen-call :event ::on-dom-content-loaded (meta &form) channel))

(defmacro tap-on-completed
  "Fired when a document, including the resources it refers to, is completely loaded and initialized."
  [channel]
  (gen-call :event ::on-completed (meta &form) channel))

(defmacro tap-on-error-occurred
  "Fired when an error occurs and the navigation is aborted. This can happen if either a network error occurred, or
   the user aborted the navigation."
  [channel]
  (gen-call :event ::on-error-occurred (meta &form) channel))

(defmacro tap-on-created-navigation-target
  "Fired when a new window, or a new tab in an existing window, is created to host a navigation."
  [channel]
  (gen-call :event ::on-created-navigation-target (meta &form) channel))

(defmacro tap-on-reference-fragment-updated
  "Fired when the reference fragment of a frame was updated. All future events for that frame will use the updated
   URL."
  [channel]
  (gen-call :event ::on-reference-fragment-updated (meta &form) channel))

(defmacro tap-on-tab-replaced
  "Fired when the contents of the tab is replaced by a different (usually previously pre-rendered) tab."
  [channel]
  (gen-call :event ::on-tab-replaced (meta &form) channel))

(defmacro tap-on-history-state-updated
  "Fired when the frame's history was updated to a new URL. All future events for that frame will use the updated URL."
  [channel]
  (gen-call :event ::on-history-state-updated (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webNavigation",
   :since "16",
   :functions
   [{:id ::get-frame,
     :name "getFrame",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::get-all-frames,
     :name "getAllFrames",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "details", :type "[array-of-objects]"}]}}]}],
   :events
   [{:id ::on-before-navigate, :name "onBeforeNavigate", :params [{:name "details", :type "object"}]}
    {:id ::on-committed, :name "onCommitted", :params [{:name "details", :type "object"}]}
    {:id ::on-dom-content-loaded, :name "onDOMContentLoaded", :params [{:name "details", :type "object"}]}
    {:id ::on-completed, :name "onCompleted", :params [{:name "details", :type "object"}]}
    {:id ::on-error-occurred, :name "onErrorOccurred", :params [{:name "details", :type "object"}]}
    {:id ::on-created-navigation-target,
     :name "onCreatedNavigationTarget",
     :params [{:name "details", :type "object"}]}
    {:id ::on-reference-fragment-updated,
     :name "onReferenceFragmentUpdated",
     :params [{:name "details", :type "object"}]}
    {:id ::on-tab-replaced, :name "onTabReplaced", :since "22", :params [{:name "details", :type "object"}]}
    {:id ::on-history-state-updated,
     :name "onHistoryStateUpdated",
     :since "22",
     :params [{:name "details", :type "object"}]}]})

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