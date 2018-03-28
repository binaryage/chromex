(ns chromex.ext.declarative-net-request
  "The chrome.declarativeNetRequest API is used to intercept and
   perform actions on a network request by specifying declarative rules.

     * available since Chrome 66
     * https://developer.chrome.com/extensions/declarativeNetRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-whitelisted-pages
  "Adds |page_patterns| to the set of whitelisted pages. Requests from these pages are not intercepted by the extension. These
   are persisted across browser sessions.

     |page-patterns| - Array of match patterns which are to be added to the whitelist.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-addWhitelistedPages."
  ([page-patterns] (gen-call :function ::add-whitelisted-pages &form page-patterns)))

(defmacro remove-whitelisted-pages
  "Removes |page_patterns| from the set of whitelisted pages.

     |page-patterns| - Array of match patterns which are to be removed from the whitelist.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-removeWhitelistedPages."
  ([page-patterns] (gen-call :function ::remove-whitelisted-pages &form page-patterns)))

(defmacro get-whitelisted-pages
  "Returns the current set of whitelisted pages.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-result.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getWhitelistedPages."
  ([] (gen-call :function ::get-whitelisted-pages &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.declarative-net-request namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.declarativeNetRequest",
   :since "66",
   :functions
   [{:id ::add-whitelisted-pages,
     :name "addWhitelistedPages",
     :since "master",
     :callback? true,
     :params
     [{:name "page-patterns", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-whitelisted-pages,
     :name "removeWhitelistedPages",
     :since "master",
     :callback? true,
     :params
     [{:name "page-patterns", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-whitelisted-pages,
     :name "getWhitelistedPages",
     :since "master",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-strings]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))