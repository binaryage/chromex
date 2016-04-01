(ns chromex.app.preferences-private
  "  * available since Chrome 29"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-easy-unlock-proximity-required
  "If true, a remote Easy Unlock device can only unlock the local device if it is in very close proximity (roughly, within a
   foot). This preference's value is a boolean, defaulting to false."
  ([] (gen-call :property ::easy-unlock-proximity-required &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.preferences-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.preferencesPrivate",
   :since "29",
   :properties
   [{:id ::easy-unlock-proximity-required,
     :name "easyUnlockProximityRequired",
     :since "40",
     :return-type "types.private.ChromeDirectSetting"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))