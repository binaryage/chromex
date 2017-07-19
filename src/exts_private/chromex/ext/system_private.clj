(ns chromex.ext.system-private
  "  * available since Chrome 19"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-incognito-mode-availability
  "Returns whether the incognito mode is enabled, disabled or forced

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [value] where:

     |value| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-incognito-mode-availability &form)))

(defmacro get-update-status
  "Gets information about the system update.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - Details of the system update

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-update-status &form)))

(defmacro get-api-key
  "Gets Chrome's API key to use for requests to Google services.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [key] where:

     |key| - The API key.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-api-key &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-volume-changed-events
  "Fired when the volume is changed.

   Events will be put on the |channel| with signature [::on-volume-changed [volume]] where:

     |volume| - Information about the current state of the system volume control, including whether it is muted.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-volume-changed &form channel args)))

(defmacro tap-on-brightness-changed-events
  "Fired when the screen brightness is changed.

   Events will be put on the |channel| with signature [::on-brightness-changed [brightness]] where:

     |brightness| - Information about a change to the screen brightness.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-brightness-changed &form channel args)))

(defmacro tap-on-screen-unlocked-events
  "Fired when the screen is unlocked.

   Events will be put on the |channel| with signature [::on-screen-unlocked []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-screen-unlocked &form channel args)))

(defmacro tap-on-woke-up-events
  "Fired when the device wakes up from sleep.

   Events will be put on the |channel| with signature [::on-woke-up []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-woke-up &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.system-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.systemPrivate",
   :since "19",
   :functions
   [{:id ::get-incognito-mode-availability,
     :name "getIncognitoModeAvailability",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "value", :type "systemPrivate.GetIncognitoModeAvailabilityValue"}]}}]}
    {:id ::get-update-status,
     :name "getUpdateStatus",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "systemPrivate.UpdateStatus"}]}}]}
    {:id ::get-api-key,
     :name "getApiKey",
     :since "33",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "key", :type "string"}]}}]}],
   :events
   [{:id ::on-volume-changed, :name "onVolumeChanged", :params [{:name "volume", :type "systemPrivate.VolumeInfo"}]}
    {:id ::on-brightness-changed,
     :name "onBrightnessChanged",
     :params [{:name "brightness", :type "systemPrivate.BrightnessChangeInfo"}]}
    {:id ::on-screen-unlocked, :name "onScreenUnlocked"}
    {:id ::on-woke-up, :name "onWokeUp"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))