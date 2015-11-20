(ns chromex.file-browser-handler-internal
  "  * available since Chrome 21
     * https://developer.chrome.com/extensions/fileBrowserHandlerInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro select-file
  "Prompts user to select file path under which a new file will be created. If the user selects file, the file gets
   created or, if it already exists, truncated. The function has to be called with user gesture.
   
     |selectionParams| - Parameters that will be used to create new file.
     |callback| - Function called upon completion."
  [selection-params #_callback]
  (gen-call :function ::select-file (meta &form) selection-params))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fileBrowserHandlerInternal",
   :since "21",
   :functions
   [{:id ::select-file,
     :name "selectFile",
     :callback? true,
     :params
     [{:name "selection-params", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))