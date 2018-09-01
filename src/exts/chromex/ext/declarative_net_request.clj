(ns chromex.ext.declarative-net-request
  "The chrome.declarativeNetRequest API is used to intercept and
   perform actions on a network request by specifying declarative rules.

     * available since Chrome 69
     * https://developer.chrome.com/extensions/declarativeNetRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-number-of-allowed-pages
  "The maximum number of allowed pages that an extension can add.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_ALLOWED_PAGES."
  ([] (gen-call :property ::max-number-of-allowed-pages &form)))

(defmacro get-max-number-of-rules
  "The maximum number of rules that an extension can specify in the rule resources file. Any excess rules will be ignored.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_RULES."
  ([] (gen-call :property ::max-number-of-rules &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro add-allowed-pages
  "Adds |page_patterns| to the set of allowed pages. Requests from these pages are not intercepted by the extension. These are
   persisted across browser sessions. Note: MAX_NUMBER_OF_ALLOWED_PAGES is the maximum number of allowed page an extension can
   add. Also, adding page patterns is atomic. In case of an error, no page pattern is added.

     |page-patterns| - Array of match patterns which are to be allowed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-addAllowedPages."
  ([page-patterns] (gen-call :function ::add-allowed-pages &form page-patterns)))

(defmacro remove-allowed-pages
  "Removes |page_patterns| from the set of allowed pages. Note: Removing page patterns is atomic. In case of an error, no page
   pattern is removed.

     |page-patterns| - Array of match patterns which are to removed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-removeAllowedPages."
  ([page-patterns] (gen-call :function ::remove-allowed-pages &form page-patterns)))

(defmacro get-allowed-pages
  "Returns the current set of allowed pages.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getAllowedPages."
  ([] (gen-call :function ::get-allowed-pages &form)))

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
   :since "69",
   :properties
   [{:id ::max-number-of-allowed-pages, :name "MAX_NUMBER_OF_ALLOWED_PAGES", :return-type "unknown-type"}
    {:id ::max-number-of-rules, :name "MAX_NUMBER_OF_RULES", :since "master", :return-type "unknown-type"}],
   :functions
   [{:id ::add-allowed-pages,
     :name "addAllowedPages",
     :callback? true,
     :params
     [{:name "page-patterns", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-allowed-pages,
     :name "removeAllowedPages",
     :callback? true,
     :params
     [{:name "page-patterns", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-allowed-pages,
     :name "getAllowedPages",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-strings]"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))