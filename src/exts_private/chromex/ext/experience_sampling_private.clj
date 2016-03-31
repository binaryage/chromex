(ns chromex.ext.experience-sampling-private
  "The experienceSamplingPrivate API listens for when various types of UI elements are displayed and closed (interstitials,
   infobars, etc.) and pass these events along to whitelisted extensions, along with information about the user’s decisions.

     * available since Chrome 39"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-browser-info
  "Retrives information about the current browser context (experimental variation information), passing a BrowserInfo object
   to the callback function.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [info] where:

     |info| - The current browser context.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-browser-info &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-displayed-events
  "Fired when an interesting piece of UI is shown to the user.

   Events will be put on the |channel| with signature [::on-displayed [element]] where:

     |element| - The interesting piece of UI.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-displayed &form channel args)))

(defmacro tap-on-decision-events
  "Fired when a user makes a decision about an interesting piece of UI.

   Events will be put on the |channel| with signature [::on-decision [element decision]] where:

     |element| - The interesting piece of UI.
     |decision| - The decision the user made.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-decision &form channel args)))

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
  {:namespace "chrome.experienceSamplingPrivate",
   :since "39",
   :functions
   [{:id ::get-browser-info,
     :name "getBrowserInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "info", :type "experienceSamplingPrivate.BrowserInfo"}]}}]}],
   :events
   [{:id ::on-displayed, :name "onDisplayed", :params [{:name "element", :type "experienceSamplingPrivate.UIElement"}]}
    {:id ::on-decision,
     :name "onDecision",
     :params
     [{:name "element", :type "experienceSamplingPrivate.UIElement"}
      {:name "decision", :type "experienceSamplingPrivate.UserDecision"}]}]})

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