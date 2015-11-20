(ns chromex.settings-private
  "Use the chrome.settingsPrivate API to get or set preferences
   from the settings UI.
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/settingsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro set-pref
  "Sets a settings value.
   
     |name| - The name of the pref.
     |value| - The new value of the pref.
     |pageId| - The user metrics identifier or null.
     |callback| - The callback for whether the pref was set or not."
  [name value page-id #_callback]
  (gen-call :function ::set-pref (meta &form) name value page-id))

(defmacro get-all-prefs
  "Gets an array of all the prefs."
  [#_callback]
  (gen-call :function ::get-all-prefs (meta &form)))

(defmacro get-pref
  "Gets the value of a specific pref."
  [name #_callback]
  (gen-call :function ::get-pref (meta &form) name))

(defmacro get-default-zoom-percent
  "Gets the page zoom factor as an integer percentage."
  [#_callback]
  (gen-call :function ::get-default-zoom-percent (meta &form)))

(defmacro set-default-zoom-percent
  "Sets the page zoom factor from a zoom percentage."
  [percent #_callback]
  (gen-call :function ::set-default-zoom-percent (meta &form) percent))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-prefs-changed
  "Fired when a set of prefs has changed.|prefs| The prefs that changed."
  [channel]
  (gen-call :event ::on-prefs-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.settingsPrivate",
   :since "47",
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
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "pref", :type "settingsPrivate.PrefObject"}]}}]}
    {:id ::get-default-zoom-percent,
     :name "getDefaultZoomPercent",
     :since "48",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "percent", :type "integer"}]}}]}
    {:id ::set-default-zoom-percent,
     :name "setDefaultZoomPercent",
     :since "48",
     :callback? true,
     :params
     [{:name "percent", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}],
   :events
   [{:id ::on-prefs-changed,
     :name "onPrefsChanged",
     :params [{:name "prefs", :type "[array-of-settingsPrivate.PrefObjects]"}]}]})

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