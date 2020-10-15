(ns chromex.ext.bookmarks
  "Use the chrome.bookmarks API to create, organize, and otherwise manipulate bookmarks. Also see Override Pages, which you
   can use to create a custom Bookmark Manager page.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/bookmarks"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-write-operations-per-hour
  "https://developer.chrome.com/extensions/bookmarks#property-MAX_WRITE_OPERATIONS_PER_HOUR."
  ([] (gen-call :property ::max-write-operations-per-hour &form)))

(defmacro get-max-sustained-write-operations-per-minute
  "https://developer.chrome.com/extensions/bookmarks#property-MAX_SUSTAINED_WRITE_OPERATIONS_PER_MINUTE."
  ([] (gen-call :property ::max-sustained-write-operations-per-minute &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves the specified BookmarkTreeNode(s).

     |id-or-id-list| - A single string-valued id, or an array of string-valued ids

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-get."
  ([id-or-id-list] (gen-call :function ::get &form id-or-id-list)))

(defmacro get-children
  "Retrieves the children of the specified BookmarkTreeNode id.

     |id| - https://developer.chrome.com/extensions/bookmarks#property-getChildren-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-getChildren."
  ([id] (gen-call :function ::get-children &form id)))

(defmacro get-recent
  "Retrieves the recently added bookmarks.

     |number-of-items| - The maximum number of items to return.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-getRecent."
  ([number-of-items] (gen-call :function ::get-recent &form number-of-items)))

(defmacro get-tree
  "Retrieves the entire Bookmarks hierarchy.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-getTree."
  ([] (gen-call :function ::get-tree &form)))

(defmacro get-sub-tree
  "Retrieves part of the Bookmarks hierarchy, starting at the specified node.

     |id| - The ID of the root of the subtree to retrieve.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-getSubTree."
  ([id] (gen-call :function ::get-sub-tree &form id)))

(defmacro search
  "Searches for BookmarkTreeNodes matching the given query. Queries specified with an object produce BookmarkTreeNodes
   matching all specified properties.

     |query| - Either a string of words and quoted phrases that are matched against bookmark URLs and titles, or an object.
               If an object, the properties query, url, and title may be specified and bookmarks matching all specified
               properties will be produced.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - https://developer.chrome.com/extensions/bookmarks#property-callback-results.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-search."
  ([query] (gen-call :function ::search &form query)))

(defmacro create
  "Creates a bookmark or folder under the specified parentId.  If url is NULL or missing, it will be a folder.

     |bookmark| - https://developer.chrome.com/extensions/bookmarks#property-create-bookmark.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/bookmarks#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-create."
  ([bookmark] (gen-call :function ::create &form bookmark)))

(defmacro move
  "Moves the specified BookmarkTreeNode to the provided location.

     |id| - https://developer.chrome.com/extensions/bookmarks#property-move-id.
     |destination| - https://developer.chrome.com/extensions/bookmarks#property-move-destination.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/bookmarks#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-move."
  ([id destination] (gen-call :function ::move &form id destination)))

(defmacro update
  "Updates the properties of a bookmark or folder. Specify only the properties that you want to change; unspecified properties
   will be left unchanged.  Note: Currently, only 'title' and 'url' are supported.

     |id| - https://developer.chrome.com/extensions/bookmarks#property-update-id.
     |changes| - https://developer.chrome.com/extensions/bookmarks#property-update-changes.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/bookmarks#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-update."
  ([id changes] (gen-call :function ::update &form id changes)))

(defmacro remove
  "Removes a bookmark or an empty bookmark folder.

     |id| - https://developer.chrome.com/extensions/bookmarks#property-remove-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-remove."
  ([id] (gen-call :function ::remove &form id)))

(defmacro remove-tree
  "Recursively removes a bookmark folder.

     |id| - https://developer.chrome.com/extensions/bookmarks#property-removeTree-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/bookmarks#method-removeTree."
  ([id] (gen-call :function ::remove-tree &form id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-created-events
  "Fired when a bookmark or folder is created.

   Events will be put on the |channel| with signature [::on-created [id bookmark]] where:

     |id| - https://developer.chrome.com/extensions/bookmarks#property-onCreated-id.
     |bookmark| - https://developer.chrome.com/extensions/bookmarks#property-onCreated-bookmark.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onCreated."
  ([channel & args] (apply gen-call :event ::on-created &form channel args)))

(defmacro tap-on-removed-events
  "Fired when a bookmark or folder is removed.  When a folder is removed recursively, a single notification is fired for the
   folder, and none for its contents.

   Events will be put on the |channel| with signature [::on-removed [id remove-info]] where:

     |id| - https://developer.chrome.com/extensions/bookmarks#property-onRemoved-id.
     |remove-info| - https://developer.chrome.com/extensions/bookmarks#property-onRemoved-removeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onRemoved."
  ([channel & args] (apply gen-call :event ::on-removed &form channel args)))

(defmacro tap-on-changed-events
  "Fired when a bookmark or folder changes.  Note: Currently, only title and url changes trigger this.

   Events will be put on the |channel| with signature [::on-changed [id change-info]] where:

     |id| - https://developer.chrome.com/extensions/bookmarks#property-onChanged-id.
     |change-info| - https://developer.chrome.com/extensions/bookmarks#property-onChanged-changeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

(defmacro tap-on-moved-events
  "Fired when a bookmark or folder is moved to a different parent folder.

   Events will be put on the |channel| with signature [::on-moved [id move-info]] where:

     |id| - https://developer.chrome.com/extensions/bookmarks#property-onMoved-id.
     |move-info| - https://developer.chrome.com/extensions/bookmarks#property-onMoved-moveInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onMoved."
  ([channel & args] (apply gen-call :event ::on-moved &form channel args)))

(defmacro tap-on-children-reordered-events
  "Fired when the children of a folder have changed their order due to the order being sorted in the UI.  This is not called
   as a result of a move().

   Events will be put on the |channel| with signature [::on-children-reordered [id reorder-info]] where:

     |id| - https://developer.chrome.com/extensions/bookmarks#property-onChildrenReordered-id.
     |reorder-info| - https://developer.chrome.com/extensions/bookmarks#property-onChildrenReordered-reorderInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onChildrenReordered."
  ([channel & args] (apply gen-call :event ::on-children-reordered &form channel args)))

(defmacro tap-on-import-began-events
  "Fired when a bookmark import session is begun.  Expensive observers should ignore onCreated updates until onImportEnded is
   fired.  Observers should still handle other notifications immediately.

   Events will be put on the |channel| with signature [::on-import-began []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onImportBegan."
  ([channel & args] (apply gen-call :event ::on-import-began &form channel args)))

(defmacro tap-on-import-ended-events
  "Fired when a bookmark import session is ended.

   Events will be put on the |channel| with signature [::on-import-ended []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/bookmarks#event-onImportEnded."
  ([channel & args] (apply gen-call :event ::on-import-ended &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.bookmarks namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bookmarks",
   :since "38",
   :properties
   [{:id ::max-write-operations-per-hour,
     :name "MAX_WRITE_OPERATIONS_PER_HOUR",
     :since "38",
     :deprecated "Bookmark write operations are no longer limited by Chrome.",
     :return-type "unknown-type"}
    {:id ::max-sustained-write-operations-per-minute,
     :name "MAX_SUSTAINED_WRITE_OPERATIONS_PER_MINUTE",
     :since "38",
     :deprecated "Bookmark write operations are no longer limited by Chrome.",
     :return-type "unknown-type"}],
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "id-or-id-list", :type "string-or-[array-of-strings]"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::get-children,
     :name "getChildren",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::get-recent,
     :name "getRecent",
     :callback? true,
     :params
     [{:name "number-of-items", :type "integer"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::get-tree,
     :name "getTree",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::get-sub-tree,
     :name "getSubTree",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::search,
     :name "search",
     :callback? true,
     :params
     [{:name "query", :type "string-or-object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::create,
     :name "create",
     :callback? true,
     :params
     [{:name "bookmark", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "bookmarks.BookmarkTreeNode"}]}}]}
    {:id ::move,
     :name "move",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "destination", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "bookmarks.BookmarkTreeNode"}]}}]}
    {:id ::update,
     :name "update",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "changes", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "bookmarks.BookmarkTreeNode"}]}}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-tree,
     :name "removeTree",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-created,
     :name "onCreated",
     :params [{:name "id", :type "string"} {:name "bookmark", :type "bookmarks.BookmarkTreeNode"}]}
    {:id ::on-removed, :name "onRemoved", :params [{:name "id", :type "string"} {:name "remove-info", :type "object"}]}
    {:id ::on-changed, :name "onChanged", :params [{:name "id", :type "string"} {:name "change-info", :type "object"}]}
    {:id ::on-moved, :name "onMoved", :params [{:name "id", :type "string"} {:name "move-info", :type "object"}]}
    {:id ::on-children-reordered,
     :name "onChildrenReordered",
     :params [{:name "id", :type "string"} {:name "reorder-info", :type "object"}]}
    {:id ::on-import-began, :name "onImportBegan"}
    {:id ::on-import-ended, :name "onImportEnded"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))