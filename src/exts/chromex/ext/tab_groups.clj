(ns chromex.ext.tab-groups
  "Use the chrome.tabGroups API to interact with the browser's tab grouping system. You can use this API to modify and
   rearrange tab groups in the browser. To group and ungroup tabs, or to query what tabs are in groups, use the chrome.tabs
   API.

     * available since Chrome 88
     * https://developer.chrome.com/extensions/tabGroups"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-tab-group-id-none
  "An ID that represents the absence of a group.

   https://developer.chrome.com/extensions/tabGroups#property-TAB_GROUP_ID_NONE."
  ([] (gen-call :property ::tab-group-id-none &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves details about the specified group.

     |group-id| - https://developer.chrome.com/extensions/tabGroups#property-get-groupId.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [group] where:

     |group| - https://developer.chrome.com/extensions/tabGroups#property-callback-group.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabGroups#method-get."
  ([group-id] (gen-call :function ::get &form group-id)))

(defmacro query
  "Gets all groups that have the specified properties, or all groups if no properties are specified.

     |query-info| - https://developer.chrome.com/extensions/tabGroups#property-query-queryInfo.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/tabGroups#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabGroups#method-query."
  ([query-info] (gen-call :function ::query &form query-info)))

(defmacro update
  "Modifies the properties of a group. Properties that are not specified in updateProperties are not modified.

     |group-id| - The ID of the group to modify.
     |update-properties| - https://developer.chrome.com/extensions/tabGroups#property-update-updateProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [group] where:

     |group| - Details about the updated group.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabGroups#method-update."
  ([group-id update-properties] (gen-call :function ::update &form group-id update-properties)))

(defmacro move
  "Moves the group and all its tabs within its window, or to a new window.

     |group-id| - The ID of the group to move.
     |move-properties| - https://developer.chrome.com/extensions/tabGroups#property-move-moveProperties.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [group] where:

     |group| - Details about the moved group.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/tabGroups#method-move."
  ([group-id move-properties] (gen-call :function ::move &form group-id move-properties)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.tab-groups namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.tabGroups",
   :since "88",
   :properties [{:id ::tab-group-id-none, :name "TAB_GROUP_ID_NONE", :return-type "unknown-type"}],
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "group-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "group", :type "tabGroups.TabGroup"}]}}]}
    {:id ::query,
     :name "query",
     :callback? true,
     :params
     [{:name "query-info", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-tabGroups.TabGroups]"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "group-id", :type "integer"}
      {:name "update-properties", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "group", :optional? true, :type "tabGroups.TabGroup"}]}}]}
    {:id ::move,
     :name "move",
     :callback? true,
     :params
     [{:name "group-id", :type "integer"}
      {:name "move-properties", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "group", :optional? true, :type "tabGroups.TabGroup"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))