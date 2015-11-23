(ns chromex.history
  "Use the chrome.history API to interact with the browser's record of visited pages. You can add, remove, and query
   for URLs in the browser's history. To override the history page with your own version, see Override Pages.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/history"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro search
  "Searches the history for the last visit time of each page matching the query."
  [query #_callback]
  (gen-call :function ::search (meta &form) query))

(defmacro get-visits
  "Retrieves information about visits to a URL."
  [details #_callback]
  (gen-call :function ::get-visits (meta &form) details))

(defmacro add-url
  "Adds a URL to the history at the current time with a transition type of 'link'."
  [details #_callback]
  (gen-call :function ::add-url (meta &form) details))

(defmacro delete-url
  "Removes all occurrences of the given URL from the history."
  [details #_callback]
  (gen-call :function ::delete-url (meta &form) details))

(defmacro delete-range
  "Removes all items within the specified date range from the history.  Pages will not be removed from the history
   unless all visits fall within the range."
  [range #_callback]
  (gen-call :function ::delete-range (meta &form) range))

(defmacro delete-all
  "Deletes all items from the history."
  [#_callback]
  (gen-call :function ::delete-all (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-visited-events
  "Fired when a URL is visited, providing the HistoryItem data for that URL.  This event fires before the page has
   loaded."
  [channel]
  (gen-call :event ::on-visited (meta &form) channel))

(defmacro tap-on-visit-removed-events
  "Fired when one or more URLs are removed from the history service.  When all visits have been removed the URL is
   purged from history."
  [channel]
  (gen-call :event ::on-visit-removed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.history",
   :since "5",
   :functions
   [{:id ::search,
     :name "search",
     :callback? true,
     :params
     [{:name "query", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-history.HistoryItems]"}]}}]}
    {:id ::get-visits,
     :name "getVisits",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-history.VisitItems]"}]}}]}
    {:id ::add-url,
     :name "addUrl",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::delete-url,
     :name "deleteUrl",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::delete-range,
     :name "deleteRange",
     :callback? true,
     :params [{:name "range", :type "object"} {:name "callback", :type :callback}]}
    {:id ::delete-all, :name "deleteAll", :callback? true, :params [{:name "callback", :type :callback}]}],
   :events
   [{:id ::on-visited, :name "onVisited", :params [{:name "result", :type "history.HistoryItem"}]}
    {:id ::on-visit-removed, :name "onVisitRemoved", :params [{:name "removed", :type "object"}]}]})

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