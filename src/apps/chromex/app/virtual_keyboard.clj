(ns chromex.app.virtual-keyboard
  "The chrome.virtualKeybaord API is a kiosk only API used to
   configure virtual keyboard layout and behavior in kiosk sessions.

     * available since Chrome 59
     * https://developer.chrome.com/apps/virtualKeyboard"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro restrict-features
  "Sets restrictions on features provided by the virtual keyboard.

     |restrictions| - Defines the set of enabled/disabled virtual keyboard     features.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/virtualKeyboard#method-restrictFeatures."
  ([restrictions] (gen-call :function ::restrict-features &form restrictions)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.virtual-keyboard namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.virtualKeyboard",
   :since "59",
   :functions
   [{:id ::restrict-features,
     :name "restrictFeatures",
     :callback? true,
     :params [{:name "restrictions", :type "object"} {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))