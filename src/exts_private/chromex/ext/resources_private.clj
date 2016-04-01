(ns chromex.ext.resources-private
  "resourcesPrivate.

     * available since Chrome 47"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-strings
  "Gets localized strings for a component extension. Includes default WebUI loadTimeData values for text and language settings
   (fontsize, fontfamily, language, textdirection). See chrome/browser/extensions/api/resources_private/resources_private_api.cc for instructions on adding a new component to
   this
   API.

     |component| - Internal chrome component to get strings for.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([component] (gen-call :function ::get-strings &form component)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.resources-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.resourcesPrivate",
   :since "47",
   :functions
   [{:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params
     [{:name "component", :type "unknown-type"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))