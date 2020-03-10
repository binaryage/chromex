(ns chromex.ext.webcam-private
  "Webcam Private API.

     * available since Chrome 40"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-serial-webcam
  "Open a serial port that controls a webcam.

     |path| - ?
     |protocol| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [webcam-id] where:

     |webcam-id| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([path protocol] (gen-call :function ::open-serial-webcam &form path protocol)))

(defmacro close-webcam
  "Close a serial port connection to a webcam.

     |webcam-id| - ?"
  ([webcam-id] (gen-call :function ::close-webcam &form webcam-id)))

(defmacro get
  "Retrieve webcam parameters. Will respond with a config holding the requested values that are available, or default values
   for those that aren't. If none of the requests succeed, will respond with an error.

     |webcam-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id] (gen-call :function ::get &form webcam-id)))

(defmacro set
  "A callback is included here which is invoked when the function responds. No configuration is returned through it.

     |webcam-id| - ?
     |config| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id config] (gen-call :function ::set &form webcam-id config)))

(defmacro reset
  "Reset a webcam. Note: the value of the parameter have no effect, it's the presence of the parameter that matters. E.g.:
   reset(webcamId, {pan: 0, tilt: 1}); will reset pan & tilt, but not zoom. A callback is included here which is invoked when
   the function responds. No configuration is returned through it.

     |webcam-id| - ?
     |config| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id config] (gen-call :function ::reset &form webcam-id config)))

(defmacro set-home
  "Set home preset for a webcam. A callback is included here which is invoked when the function responds.

     |webcam-id| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id] (gen-call :function ::set-home &form webcam-id)))

(defmacro restore-camera-preset
  "Restore the camera's position to that of the specified preset. A callback is included here which is invoked when the
   function responds.

     |webcam-id| - ?
     |preset-number| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id preset-number] (gen-call :function ::restore-camera-preset &form webcam-id preset-number)))

(defmacro set-camera-preset
  "Set the current camera's position to be stored for the specified preset. A callback is included here which is invoked when
   the function responds.

     |webcam-id| - ?
     |preset-number| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [configuration] where:

     |configuration| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([webcam-id preset-number] (gen-call :function ::set-camera-preset &form webcam-id preset-number)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.webcam-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.webcamPrivate",
   :since "40",
   :functions
   [{:id ::open-serial-webcam,
     :name "openSerialWebcam",
     :since "45",
     :callback? true,
     :params
     [{:name "path", :type "string"}
      {:name "protocol", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "webcam-id", :type "string"}]}}]}
    {:id ::close-webcam, :name "closeWebcam", :since "45", :params [{:name "webcam-id", :type "string"}]}
    {:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}
    {:id ::set,
     :name "set",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "config", :type "webcamPrivate.WebcamConfiguration"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}
    {:id ::reset,
     :name "reset",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "config", :type "webcamPrivate.WebcamConfiguration"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}
    {:id ::set-home,
     :name "setHome",
     :since "future",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}
    {:id ::restore-camera-preset,
     :name "restoreCameraPreset",
     :since "future",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "preset-number", :type "double"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}
    {:id ::set-camera-preset,
     :name "setCameraPreset",
     :since "future",
     :callback? true,
     :params
     [{:name "webcam-id", :type "string"}
      {:name "preset-number", :type "double"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "configuration", :type "webcamPrivate.WebcamCurrentConfiguration"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))