(ns chromex.dashboard-private
  "  * available since Chrome 46
     * https://developer.chrome.com/extensions/dashboardPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro show-permission-prompt-for-delegated-install
  "Shows a permission prompt for the given extension, for installing to a different account.
   
     |callback| - Called when the user has either accepted/rejected the dialog, or some error occurred (such as
                  invalid manifest or icon image data)."
  [details #_callback]
  (gen-call :function ::show-permission-prompt-for-delegated-install (meta &form) details))

(defmacro show-permission-prompt-for-delegated-bundle-install
  "Shows a permission prompt for the given bundle, for installing to a different account.
   
     |contents| - An array of extension details to be installed.
     |callback| - Called when the install process completes. Upon failures, chrome.runtime.lastError will be set to
                  'Invalid bundle', 'Invalid icon url', or 'User cancelled install'."
  [details contents #_callback]
  (gen-call :function ::show-permission-prompt-for-delegated-bundle-install (meta &form) details contents))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.dashboardPrivate",
   :since "46",
   :functions
   [{:id ::show-permission-prompt-for-delegated-install,
     :name "showPermissionPromptForDelegatedInstall",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "dashboardPrivate.Result"}]}}]}
    {:id ::show-permission-prompt-for-delegated-bundle-install,
     :name "showPermissionPromptForDelegatedBundleInstall",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "contents", :type "[array-of-objects]"}
      {:name "callback", :type :callback}]}]})

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