(ns chromex.chromeos-info-private
  "  * available since Chrome 12
     * https://developer.chrome.com/extensions/chromeosInfoPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get
  "Fetches customization values for the given property names. See property names in the declaration of the returned
   dictionary.
   
     |propertyNames| - Chrome OS Property names"
  [property-names #_callback]
  (gen-call :function ::get (meta &form) property-names))

(defmacro set
  "Sets values for the given system property.
   
     |propertyName| - Chrome OS system property name
     |propertyValue| - Chrome OS system property value"
  [property-name property-value]
  (gen-call :function ::set (meta &form) property-name property-value))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.chromeosInfoPrivate",
   :since "12",
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "property-names", :type "[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "properties-dictionary", :type "object"}]}}]}
    {:id ::set,
     :name "set",
     :since "32",
     :params
     [{:name "property-name", :type "chromeosInfoPrivate.PropertyName"}
      {:name "property-value", :type "any"}]}]})

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