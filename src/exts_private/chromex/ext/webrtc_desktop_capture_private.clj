(ns chromex.ext.webrtc-desktop-capture-private
  "Use the chrome.webrtcDesktopCapturePrivate API to capture
   desktop media requested from a WebView.

     * available since Chrome 44"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro choose-desktop-media
  "Shows desktop media picker UI with the specified set of sources.

     |sources| - ?
     |request| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [stream-id] where:

     |stream-id| - ?"
  ([sources request] (gen-call :function ::choose-desktop-media &form sources request)))

(defmacro cancel-choose-desktop-media
  "Hides desktop media picker dialog shown by chooseDesktopMedia().

     |desktop-media-request-id| - ?"
  ([desktop-media-request-id] (gen-call :function ::cancel-choose-desktop-media &form desktop-media-request-id)))

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
  {:namespace "chrome.webrtcDesktopCapturePrivate",
   :since "44",
   :functions
   [{:id ::choose-desktop-media,
     :name "chooseDesktopMedia",
     :callback? true,
     :return-type "integer",
     :params
     [{:name "sources", :type "[array-of-unknown-types]"}
      {:name "request", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "stream-id", :type "string"}]}}]}
    {:id ::cancel-choose-desktop-media,
     :name "cancelChooseDesktopMedia",
     :params [{:name "desktop-media-request-id", :type "integer"}]}]})

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