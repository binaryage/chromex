(ns chromex.appview-tag
  "Use the appview tag to embed other Chrome Apps within your
   Chrome App. (see Usage).
   
     * available since Chrome 43
     * https://developer.chrome.com/extensions/appviewTag"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro connect
  "Requests another app to be embedded.
   
     |app| - The extension id of the app to be embedded.
     |data| - Optional developer specified data that the app to be embedded   can use when making an embedding decision.
     |callback| - An optional function that's called after the embedding   request is completed.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([app data #_callback] (gen-call :function ::connect &form app data))
  ([app] `(connect ~app :omit)))

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
  {:namespace "<appview>",
   :since "43",
   :functions
   [{:id ::connect,
     :name "connect",
     :callback? true,
     :params
     [{:name "app", :type "string"}
      {:name "data", :optional? true, :type "any"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}]})

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