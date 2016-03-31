(ns chromex.ext.top-sites
  "Use the chrome.topSites API to access the top sites that are displayed on the new tab page.

     * available since Chrome 19
     * https://developer.chrome.com/extensions/topSites"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Gets a list of top sites.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [data] where:

     |data| - https://developer.chrome.com/extensions/topSites#property-callback-data.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/topSites#method-get."
  ([] (gen-call :function ::get &form)))

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
  {:namespace "chrome.topSites",
   :since "19",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))