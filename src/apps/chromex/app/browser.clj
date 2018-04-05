(ns chromex.app.browser
  "Use the chrome.browser API to interact with the Chrome browser
   associated with the current application and Chrome profile.

     * available since Chrome 42
     * https://developer.chrome.com/apps/browser"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro open-tab
  "Opens a new tab in a browser window associated with the current application and Chrome profile. If no browser window for
   the Chrome profile is opened, a new one is opened prior to creating the new tab.

     |options| - Configures how the tab should be opened.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and a relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/browser#method-openTab."
  ([options] (gen-call :function ::open-tab &form options)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.browser namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.browser",
   :since "42",
   :functions
   [{:id ::open-tab,
     :name "openTab",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))