(ns chromex.app.desktop-capture
  "Desktop Capture API that can be used to capture content of screen, individual windows or tabs.

     * available since Chrome 34
     * https://developer.chrome.com/apps/desktopCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro choose-desktop-media
  "Shows desktop media picker UI with the specified set of sources.

     |sources| - Set of sources that should be shown to the user.
     |target-tab| - Optional tab for which the stream is created. If not specified then the resulting stream can be used
                    only by the calling extension. The stream can only be used by frames in the given tab whose security
                    origin matches tab.url.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [stream-id] where:

     |stream-id| - An opaque string that can be passed to getUserMedia() API to generate media stream that corresponds to the
                   source selected by the user. If user didn't select any source (i.e. canceled the prompt) then the callback
                   is called with an empty streamId. The created streamId can be used only once and expires after a few
                   seconds when it is not used.

   https://developer.chrome.com/apps/desktopCapture#method-chooseDesktopMedia."
  ([sources target-tab] (gen-call :function ::choose-desktop-media &form sources target-tab))
  ([sources] `(choose-desktop-media ~sources :omit)))

(defmacro cancel-choose-desktop-media
  "Hides desktop media picker dialog shown by chooseDesktopMedia().

     |desktop-media-request-id| - Id returned by chooseDesktopMedia()

   https://developer.chrome.com/apps/desktopCapture#method-cancelChooseDesktopMedia."
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
  {:namespace "chrome.desktopCapture",
   :since "34",
   :functions
   [{:id ::choose-desktop-media,
     :name "chooseDesktopMedia",
     :callback? true,
     :return-type "integer",
     :params
     [{:name "sources", :type "[array-of-desktopCapture.DesktopCaptureSourceTypes]"}
      {:name "target-tab", :optional? true, :type "tabs.Tab"}
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