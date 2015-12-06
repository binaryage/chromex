(ns chromex.ext.preferences-private
  "  * available since Chrome 36
     * https://developer.chrome.com/extensions/preferencesPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-easy-unlock-proximity-required
  "If true, a remote Easy Unlock device can only unlock the local device if it is in very close proximity (roughly, within a
   foot). This preference's value is a boolean, defaulting to false."
  ([] (gen-call :property ::easy-unlock-proximity-required &form)))

(defmacro get-google-geolocation-access-enabled
  "If enabled, Google services can access the user's location. This preference's value is a boolean, defaulting to false."
  ([] (gen-call :property ::google-geolocation-access-enabled &form)))

(defmacro get-data-reduction-update-daily-lengths
  "Flag to indicate that dataReductionDailyContentLength and dataReductionDailyReceivedLength must be updated with their
   latest values. This preference's value is a boolean, defaulting to false."
  ([] (gen-call :property ::data-reduction-update-daily-lengths &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-sync-categories-without-passphrase
  "Returns a list of sync categories the user has enabled without using a custom passphrase for encryption. The possible
   values are those that can be returned from syncer::ModelTypeToString in sync/syncable/model_type.cc.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-sync-categories-without-passphrase &form)))

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
  {:namespace "chrome.preferencesPrivate",
   :since "36",
   :properties
   [{:id ::easy-unlock-proximity-required,
     :name "easyUnlockProximityRequired",
     :since "40",
     :return-type "types.private.ChromeDirectSetting"}
    {:id ::google-geolocation-access-enabled,
     :name "googleGeolocationAccessEnabled",
     :return-type "types.private.ChromeDirectSetting"}
    {:id ::data-reduction-update-daily-lengths,
     :name "dataReductionUpdateDailyLengths",
     :since "40",
     :return-type "types.private.ChromeDirectSetting"}],
   :functions
   [{:id ::get-sync-categories-without-passphrase,
     :name "getSyncCategoriesWithoutPassphrase",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "categories", :type "[array-of-strings]"}]}}]}]})

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