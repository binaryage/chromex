(ns chromex.ext.history
  "Use the chrome.history API to interact with the browser's record of visited pages. You can add, remove, and query for URLs
   in the browser's history. To override the history page with your own version, see Override Pages.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/history"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro search
  "Searches the history for the last visit time of each page matching the query.
   
     |query| - See https://developer.chrome.com/extensions/history#property-search-query.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [results] where:
   
     |results| - See https://developer.chrome.com/extensions/history#property-callback-results.
   
   See https://developer.chrome.com/extensions/history#method-search."
  ([query #_callback] (gen-call :function ::search &form query)))

(defmacro get-visits
  "Retrieves information about visits to a URL.
   
     |details| - See https://developer.chrome.com/extensions/history#property-getVisits-details.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [results] where:
   
     |results| - See https://developer.chrome.com/extensions/history#property-callback-results.
   
   See https://developer.chrome.com/extensions/history#method-getVisits."
  ([details #_callback] (gen-call :function ::get-visits &form details)))

(defmacro add-url
  "Adds a URL to the history at the current time with a transition type of 'link'.
   
     |details| - See https://developer.chrome.com/extensions/history#property-addUrl-details.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/history#method-addUrl."
  ([details #_callback] (gen-call :function ::add-url &form details)))

(defmacro delete-url
  "Removes all occurrences of the given URL from the history.
   
     |details| - See https://developer.chrome.com/extensions/history#property-deleteUrl-details.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/history#method-deleteUrl."
  ([details #_callback] (gen-call :function ::delete-url &form details)))

(defmacro delete-range
  "Removes all items within the specified date range from the history.  Pages will not be removed from the history unless all
   visits fall within the range.
   
     |range| - See https://developer.chrome.com/extensions/history#property-deleteRange-range.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/history#method-deleteRange."
  ([range #_callback] (gen-call :function ::delete-range &form range)))

(defmacro delete-all
  "Deletes all items from the history.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].
   
   See https://developer.chrome.com/extensions/history#method-deleteAll."
  ([#_callback] (gen-call :function ::delete-all &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-visited-events
  "Fired when a URL is visited, providing the HistoryItem data for that URL.  This event fires before the page has loaded.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/history#event-onVisited."
  ([channel & args] (apply gen-call :event ::on-visited &form channel args)))

(defmacro tap-on-visit-removed-events
  "Fired when one or more URLs are removed from the history service.  When all visits have been removed the URL is purged from
   history.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.
   
   See https://developer.chrome.com/extensions/history#event-onVisitRemoved."
  ([channel & args] (apply gen-call :event ::on-visit-removed &form channel args)))

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