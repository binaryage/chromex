(ns chromex.app.search-engines-private
  "Use the chrome.searchEnginesPrivate API to get or set
   preferences from the settings UI.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/searchEnginesPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-search-engines
  "Gets a list of the search engines. Exactly one of the values should have default == true.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-search-engines &form)))

(defmacro set-selected-search-engine
  "Sets the search engine with the given GUID as the selected default."
  ([guid] (gen-call :function ::set-selected-search-engine &form guid)))

(defmacro add-other-search-engine
  "Adds a new 'other' (non-default) search engine with the given name, keyword, and URL."
  ([name keyword url] (gen-call :function ::add-other-search-engine &form name keyword url)))

(defmacro update-search-engine
  "Updates the search engine that has the given GUID, with the given name, keyword, and URL."
  ([guid name keyword url] (gen-call :function ::update-search-engine &form guid name keyword url)))

(defmacro remove-search-engine
  "Removes the search engine with the given GUID."
  ([guid] (gen-call :function ::remove-search-engine &form guid)))

(defmacro get-hotword-state
  "Gets the hotword state.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-hotword-state &form)))

(defmacro opt-into-hotwording
  "Opts in to hotwording; |retrain| indicates whether the user wants to retrain the hotword system with their voice by
   launching the audio verification app."
  ([retrain] (gen-call :function ::opt-into-hotwording &form retrain)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-search-engines-changed-events
  "Fires when the list of search engines changes or when the user selects a preferred default search engine. The new list of
   engines is passed along.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-search-engines-changed &form channel args)))

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
  {:namespace "chrome.searchEnginesPrivate",
   :since "48",
   :functions
   [{:id ::get-search-engines,
     :name "getSearchEngines",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "engines", :type "[array-of-searchEnginesPrivate.SearchEngines]"}]}}]}
    {:id ::set-selected-search-engine, :name "setSelectedSearchEngine", :params [{:name "guid", :type "string"}]}
    {:id ::add-other-search-engine,
     :name "addOtherSearchEngine",
     :params [{:name "name", :type "string"} {:name "keyword", :type "string"} {:name "url", :type "string"}]}
    {:id ::update-search-engine,
     :name "updateSearchEngine",
     :params
     [{:name "guid", :type "string"}
      {:name "name", :type "string"}
      {:name "keyword", :type "string"}
      {:name "url", :type "string"}]}
    {:id ::remove-search-engine, :name "removeSearchEngine", :params [{:name "guid", :type "string"}]}
    {:id ::get-hotword-state,
     :name "getHotwordState",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "state", :type "object"}]}}]}
    {:id ::opt-into-hotwording, :name "optIntoHotwording", :params [{:name "retrain", :type "boolean"}]}],
   :events
   [{:id ::on-search-engines-changed,
     :name "onSearchEnginesChanged",
     :params [{:name "engines", :type "[array-of-searchEnginesPrivate.SearchEngines]"}]}]})

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