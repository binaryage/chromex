(ns chromex.ext.settings-private
  "Use the chrome.settingsPrivate API to get or set preferences
   from the settings UI.

     * available since Chrome 54"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro set-pref
  "Sets a settings value.

     |name| - The name of the pref.
     |value| - The new value of the pref.
     |page-id| - The user metrics identifier or null.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([name value page-id] (gen-call :function ::set-pref &form name value page-id)))

(defmacro get-all-prefs
  "Gets an array of all the prefs.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [prefs] where:

     |prefs| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-all-prefs &form)))

(defmacro get-pref
  "Gets the value of a specific pref.

     |name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [pref] where:

     |pref| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([name] (gen-call :function ::get-pref &form name)))

(defmacro get-default-zoom-percent
  "Gets the page zoom factor as an integer percentage.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [percent] where:

     |percent| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-default-zoom-percent &form)))

(defmacro set-default-zoom-percent
  "Sets the page zoom factor from a zoom percentage.

     |percent| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([percent] (gen-call :function ::set-default-zoom-percent &form percent)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-prefs-changed-events
  "Fired when a set of prefs has changed.|prefs| The prefs that changed.

   Events will be put on the |channel| with signature [::on-prefs-changed [prefs]] where:

     |prefs| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-prefs-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.settings-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.settingsPrivate",
   :since "54",
   :functions
   [{:id ::set-pref,
     :name "setPref",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "value", :type "any"}
      {:name "page-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::get-all-prefs,
     :name "getAllPrefs",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "prefs", :type "[array-of-settingsPrivate.PrefObjects]"}]}}]}
    {:id ::get-pref,
     :name "getPref",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "pref", :type "settingsPrivate.PrefObject"}]}}]}
    {:id ::get-default-zoom-percent,
     :name "getDefaultZoomPercent",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "percent", :type "double"}]}}]}
    {:id ::set-default-zoom-percent,
     :name "setDefaultZoomPercent",
     :callback? true,
     :params
     [{:name "percent", :type "double"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "success", :type "boolean"}]}}]}],
   :events
   [{:id ::on-prefs-changed,
     :name "onPrefsChanged",
     :params [{:name "prefs", :type "[array-of-settingsPrivate.PrefObjects]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))