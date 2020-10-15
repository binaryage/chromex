(ns chromex.ext.top-sites
  "Use the chrome.topSites API to access the top sites (i.e. most visited sites) that are displayed on the new tab page.
   These do not include shortcuts customized by the user.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/topSites"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets a list of top sites.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - https://developer.chrome.com/extensions/topSites#property-callback-data.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/topSites#method-get."
  ([] (gen-call :function ::get &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.top-sites namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.topSites",
   :since "38",
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "data", :type "[array-of-topSites.MostVisitedURLs]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))