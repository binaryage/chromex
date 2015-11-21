(ns chromex.font-settings
  "Use the chrome.fontSettings API to manage Chrome's font settings.
   
     * available since Chrome 22
     * https://developer.chrome.com/extensions/fontSettings"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro clear-font
  "Clears the font set by this extension, if any."
  [details #_callback]
  (gen-call :function ::clear-font (meta &form) details))

(defmacro get-font
  "Gets the font for a given script and generic font family."
  [details #_callback]
  (gen-call :function ::get-font (meta &form) details))

(defmacro set-font
  "Sets the font for a given script and generic font family."
  [details #_callback]
  (gen-call :function ::set-font (meta &form) details))

(defmacro get-font-list
  "Gets a list of fonts on the system."
  [#_callback]
  (gen-call :function ::get-font-list (meta &form)))

(defmacro clear-default-font-size
  "Clears the default font size set by this extension, if any.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::clear-default-font-size (meta &form) details))

(defmacro get-default-font-size
  "Gets the default font size.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::get-default-font-size (meta &form) details))

(defmacro set-default-font-size
  "Sets the default font size."
  [details #_callback]
  (gen-call :function ::set-default-font-size (meta &form) details))

(defmacro clear-default-fixed-font-size
  "Clears the default fixed font size set by this extension, if any.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::clear-default-fixed-font-size (meta &form) details))

(defmacro get-default-fixed-font-size
  "Gets the default size for fixed width fonts.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::get-default-fixed-font-size (meta &form) details))

(defmacro set-default-fixed-font-size
  "Sets the default size for fixed width fonts."
  [details #_callback]
  (gen-call :function ::set-default-fixed-font-size (meta &form) details))

(defmacro clear-minimum-font-size
  "Clears the minimum font size set by this extension, if any.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::clear-minimum-font-size (meta &form) details))

(defmacro get-minimum-font-size
  "Gets the minimum font size.
   
     |details| - This parameter is currently unused."
  [details #_callback]
  (gen-call :function ::get-minimum-font-size (meta &form) details))

(defmacro set-minimum-font-size
  "Sets the minimum font size."
  [details #_callback]
  (gen-call :function ::set-minimum-font-size (meta &form) details))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-font-changed-events
  "Fired when a font setting changes."
  [channel]
  (gen-call :event ::on-font-changed (meta &form) channel))

(defmacro tap-on-default-font-size-changed-events
  "Fired when the default font size setting changes."
  [channel]
  (gen-call :event ::on-default-font-size-changed (meta &form) channel))

(defmacro tap-on-default-fixed-font-size-changed-events
  "Fired when the default fixed font size setting changes."
  [channel]
  (gen-call :event ::on-default-fixed-font-size-changed (meta &form) channel))

(defmacro tap-on-minimum-font-size-changed-events
  "Fired when the minimum font size setting changes."
  [channel]
  (gen-call :event ::on-minimum-font-size-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.fontSettings",
   :since "22",
   :functions
   [{:id ::clear-font,
     :name "clearFont",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-font,
     :name "getFont",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::set-font,
     :name "setFont",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-font-list,
     :name "getFontList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-fontSettings.FontNames]"}]}}]}
    {:id ::clear-default-font-size,
     :name "clearDefaultFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-default-font-size,
     :name "getDefaultFontSize",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::set-default-font-size,
     :name "setDefaultFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::clear-default-fixed-font-size,
     :name "clearDefaultFixedFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-default-fixed-font-size,
     :name "getDefaultFixedFontSize",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::set-default-fixed-font-size,
     :name "setDefaultFixedFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::clear-minimum-font-size,
     :name "clearMinimumFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::get-minimum-font-size,
     :name "getMinimumFontSize",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::set-minimum-font-size,
     :name "setMinimumFontSize",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}],
   :events
   [{:id ::on-font-changed, :name "onFontChanged", :params [{:name "details", :type "object"}]}
    {:id ::on-default-font-size-changed,
     :name "onDefaultFontSizeChanged",
     :params [{:name "details", :type "object"}]}
    {:id ::on-default-fixed-font-size-changed,
     :name "onDefaultFixedFontSizeChanged",
     :params [{:name "details", :type "object"}]}
    {:id ::on-minimum-font-size-changed,
     :name "onMinimumFontSizeChanged",
     :params [{:name "details", :type "object"}]}]})

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