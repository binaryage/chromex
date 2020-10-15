(ns chromex.app.desktop-capture
  "Desktop Capture API that can be used to capture content of screen, individual windows or tabs.

     * available since Chrome 38
     * https://developer.chrome.com/apps/desktopCapture"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro choose-desktop-media
  "Shows desktop media picker UI with the specified set of sources.

     |sources| - Set of sources that should be shown to the user. The sources order in the set decides the tab order in the
                 picker.
     |target-tab| - Optional tab for which the stream is created. If not specified then the resulting stream can be used
                    only by the calling extension. The stream can only be used by frames in the given tab whose security
                    origin matches tab.url. The tab's origin must be a secure origin, e.g. HTTPS.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [stream-id options] where:

     |stream-id| - An opaque string that can be passed to getUserMedia() API to generate media stream that corresponds to the
                   source selected by the user. If user didn't select any source (i.e. canceled the prompt) then the callback
                   is called with an empty streamId. The created streamId can be used only once and expires after a few
                   seconds when it is not used.
     |options| - Contains properties that describe the stream.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

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
  "Taps all valid non-deprecated events in chromex.app.desktop-capture namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.desktopCapture",
   :since "38",
   :functions
   [{:id ::choose-desktop-media,
     :name "chooseDesktopMedia",
     :callback? true,
     :return-type "integer",
     :params
     [{:name "sources", :type "[array-of-desktopCapture.DesktopCaptureSourceTypes]"}
      {:name "target-tab", :optional? true, :type "tabs.Tab"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "stream-id", :type "string"} {:name "options", :type "object"}]}}]}
    {:id ::cancel-choose-desktop-media,
     :name "cancelChooseDesktopMedia",
     :params [{:name "desktop-media-request-id", :type "integer"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))