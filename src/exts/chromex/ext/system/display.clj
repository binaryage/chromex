(ns chromex.ext.system.display
  "Use the system.display API to query display metadata.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/system.display"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Requests the information for all attached display devices.

     |flags| - Options affecting how the information is returned.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [display-info] where:

     |display-info| - https://developer.chrome.com/extensions/system.display#property-callback-displayInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-getInfo."
  ([flags] (gen-call :function ::get-info &form flags))
  ([] `(get-info :omit)))

(defmacro get-display-layout
  "Requests the layout info for all displays. NOTE: This is only available to Chrome OS Kiosk apps and Web UI.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [layouts] where:

     |layouts| - https://developer.chrome.com/extensions/system.display#property-callback-layouts.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-getDisplayLayout."
  ([] (gen-call :function ::get-display-layout &form)))

(defmacro set-display-properties
  "Updates the properties for the display specified by |id|, according to the information provided in |info|. On failure,
   'runtime.lastError' will be set. NOTE: This is only available to Chrome OS Kiosk apps and Web UI.

     |id| - The display's unique identifier.
     |info| - The information about display properties that should be changed.     A property will be changed only if a new
              value for it is specified in     |info|.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-setDisplayProperties."
  ([id info] (gen-call :function ::set-display-properties &form id info)))

(defmacro set-display-layout
  "Set the layout for all displays. Any display not included will use the default layout. If a layout would overlap or be
   otherwise invalid it will be adjusted to a valid layout. After layout is resolved, an onDisplayChanged event will be
   triggered. NOTE: This is only available to Chrome OS Kiosk apps and Web UI.

     |layouts| - The layout information, required for all displays except     the primary display.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-setDisplayLayout."
  ([layouts] (gen-call :function ::set-display-layout &form layouts)))

(defmacro enable-unified-desktop
  "Enables/disables the unified desktop feature. If enabled while mirroring is active, the desktop mode will not change until
   mirroring is turned off. Otherwise, the desktop mode will switch to unified immediately. NOTE: This is only available to
   Chrome OS Kiosk apps and Web UI.

     |enabled| - True if unified desktop should be enabled.

   https://developer.chrome.com/extensions/system.display#method-enableUnifiedDesktop."
  ([enabled] (gen-call :function ::enable-unified-desktop &form enabled)))

(defmacro overscan-calibration-start
  "Starts overscan calibration for a display. This will show an overlay on the screen indicating the current overscan insets.
   If overscan calibration for display |id| is in progress this will reset calibration.

     |id| - The display's unique identifier.

   https://developer.chrome.com/extensions/system.display#method-overscanCalibrationStart."
  ([id] (gen-call :function ::overscan-calibration-start &form id)))

(defmacro overscan-calibration-adjust
  "Adjusts the current overscan insets for a display. Typically this should either move the display along an axis (e.g.
   left+right have the same value) or scale it along an axis (e.g. top+bottom have opposite values). Each Adjust call is
   cumulative with previous calls since Start.

     |id| - The display's unique identifier.
     |delta| - The amount to change the overscan insets.

   https://developer.chrome.com/extensions/system.display#method-overscanCalibrationAdjust."
  ([id delta] (gen-call :function ::overscan-calibration-adjust &form id delta)))

(defmacro overscan-calibration-reset
  "Resets the overscan insets for a display to the last saved value (i.e before Start was called).

     |id| - The display's unique identifier.

   https://developer.chrome.com/extensions/system.display#method-overscanCalibrationReset."
  ([id] (gen-call :function ::overscan-calibration-reset &form id)))

(defmacro overscan-calibration-complete
  "Complete overscan adjustments for a display  by saving the current values and hiding the overlay.

     |id| - The display's unique identifier.

   https://developer.chrome.com/extensions/system.display#method-overscanCalibrationComplete."
  ([id] (gen-call :function ::overscan-calibration-complete &form id)))

(defmacro show-native-touch-calibration
  "Displays the native touch calibration UX for the display with |id| as display id. This will show an overlay on the screen
   with required instructions on how to proceed. The callback will be invoked in case of successful calibration only. If the
   calibration fails, this will throw an error.

     |id| - The display's unique identifier.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - https://developer.chrome.com/extensions/system.display#property-callback-success.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-showNativeTouchCalibration."
  ([id] (gen-call :function ::show-native-touch-calibration &form id)))

(defmacro start-custom-touch-calibration
  "Starts custom touch calibration for a display. This should be called when using a custom UX for collecting calibration
   data. If another touch calibration is already in progress this will throw an error.

     |id| - The display's unique identifier.

   https://developer.chrome.com/extensions/system.display#method-startCustomTouchCalibration."
  ([id] (gen-call :function ::start-custom-touch-calibration &form id)))

(defmacro complete-custom-touch-calibration
  "Sets the touch calibration pairs for a display. These |pairs| would be used to calibrate the touch screen for display with
   |id| called in startCustomTouchCalibration(). Always call |startCustomTouchCalibration| before calling this method. If
   another touch calibration is already in progress this will throw an error.

     |pairs| - The pairs of point used to calibrate the display.
     |bounds| - Bounds of the display when the touch calibration was performed.     |bounds.left| and |bounds.top| values
                are ignored.

   https://developer.chrome.com/extensions/system.display#method-completeCustomTouchCalibration."
  ([pairs bounds] (gen-call :function ::complete-custom-touch-calibration &form pairs bounds)))

(defmacro clear-touch-calibration
  "Resets the touch calibration for the display and brings it back to its default state by clearing any touch calibration data
   associated with the display.

     |id| - The display's unique identifier.

   https://developer.chrome.com/extensions/system.display#method-clearTouchCalibration."
  ([id] (gen-call :function ::clear-touch-calibration &form id)))

(defmacro set-mirror-mode
  "Sets the display mode to the specified mirror mode. Each call resets the state from previous calls. Calling
   setDisplayProperties() will fail for the mirroring destination displays. NOTE: This is only available to Chrome OS Kiosk
   apps and Web UI.

     |info| - The information of the mirror mode that should be applied to the     display mode.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/system.display#method-setMirrorMode."
  ([info] (gen-call :function ::set-mirror-mode &form info)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-display-changed-events
  "Fired when anything changes to the display configuration.

   Events will be put on the |channel| with signature [::on-display-changed []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/system.display#event-onDisplayChanged."
  ([channel & args] (apply gen-call :event ::on-display-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.system.display namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.display",
   :since "38",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "flags", :optional? true, :since "59", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "display-info", :type "[array-of-objects]"}]}}]}
    {:id ::get-display-layout,
     :name "getDisplayLayout",
     :since "53",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "layouts", :type "[array-of-system.display.DisplayLayouts]"}]}}]}
    {:id ::set-display-properties,
     :name "setDisplayProperties",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "info", :type "object"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-display-layout,
     :name "setDisplayLayout",
     :since "53",
     :callback? true,
     :params
     [{:name "layouts", :type "[array-of-system.display.DisplayLayouts]"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::enable-unified-desktop,
     :name "enableUnifiedDesktop",
     :since "46",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::overscan-calibration-start,
     :name "overscanCalibrationStart",
     :since "53",
     :params [{:name "id", :type "string"}]}
    {:id ::overscan-calibration-adjust,
     :name "overscanCalibrationAdjust",
     :since "53",
     :params [{:name "id", :type "string"} {:name "delta", :type "system.display.Insets"}]}
    {:id ::overscan-calibration-reset,
     :name "overscanCalibrationReset",
     :since "53",
     :params [{:name "id", :type "string"}]}
    {:id ::overscan-calibration-complete,
     :name "overscanCalibrationComplete",
     :since "53",
     :params [{:name "id", :type "string"}]}
    {:id ::show-native-touch-calibration,
     :name "showNativeTouchCalibration",
     :since "57",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::start-custom-touch-calibration,
     :name "startCustomTouchCalibration",
     :since "57",
     :params [{:name "id", :type "string"}]}
    {:id ::complete-custom-touch-calibration,
     :name "completeCustomTouchCalibration",
     :since "57",
     :params [{:name "pairs", :type "object"} {:name "bounds", :type "system.display.Bounds"}]}
    {:id ::clear-touch-calibration, :name "clearTouchCalibration", :since "57", :params [{:name "id", :type "string"}]}
    {:id ::set-mirror-mode,
     :name "setMirrorMode",
     :since "65",
     :callback? true,
     :params [{:name "info", :type "object"} {:name "callback", :optional? true, :type :callback}]}],
   :events [{:id ::on-display-changed, :name "onDisplayChanged"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))