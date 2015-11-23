(ns chromex.bookmarks
  "Use the chrome.bookmarks API to create, organize, and otherwise manipulate bookmarks. Also see Override Pages,
   which you can use to create a custom Bookmark Manager page.
   
     * available since Chrome 5
     * https://developer.chrome.com/extensions/bookmarks"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-max-write-operations-per-hour ([] (gen-call :property ::max-write-operations-per-hour (meta &form))))

(defmacro get-max-sustained-write-operations-per-minute ([] (gen-call :property ::max-sustained-write-operations-per-minute (meta &form))))

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves the specified BookmarkTreeNode(s).
   
     |idOrIdList| - A single string-valued id, or an array of string-valued ids"
  ([id-or-id-list #_callback] (gen-call :function ::get (meta &form) id-or-id-list)))

(defmacro get-children
  "Retrieves the children of the specified BookmarkTreeNode id."
  ([id #_callback] (gen-call :function ::get-children (meta &form) id)))

(defmacro get-recent
  "Retrieves the recently added bookmarks.
   
     |numberOfItems| - The maximum number of items to return."
  ([number-of-items #_callback] (gen-call :function ::get-recent (meta &form) number-of-items)))

(defmacro get-tree
  "Retrieves the entire Bookmarks hierarchy."
  ([#_callback] (gen-call :function ::get-tree (meta &form))))

(defmacro get-sub-tree
  "Retrieves part of the Bookmarks hierarchy, starting at the specified node.
   
     |id| - The ID of the root of the subtree to retrieve."
  ([id #_callback] (gen-call :function ::get-sub-tree (meta &form) id)))

(defmacro search
  "Searches for BookmarkTreeNodes matching the given query. Queries specified with an object produce BookmarkTreeNodes
   matching all specified properties.
   
     |query| - Either a string of words and quoted phrases that are matched against bookmark URLs and titles, or an
               object. If an object, the properties query, url, and title may be specified and bookmarks matching
               all specified properties will be produced."
  ([query #_callback] (gen-call :function ::search (meta &form) query)))

(defmacro create
  "Creates a bookmark or folder under the specified parentId.  If url is NULL or missing, it will be a folder."
  ([bookmark #_callback] (gen-call :function ::create (meta &form) bookmark)))

(defmacro move
  "Moves the specified BookmarkTreeNode to the provided location."
  ([id destination #_callback] (gen-call :function ::move (meta &form) id destination)))

(defmacro update
  "Updates the properties of a bookmark or folder. Specify only the properties that you want to change; unspecified
   properties will be left unchanged.  Note: Currently, only 'title' and 'url' are supported."
  ([id changes #_callback] (gen-call :function ::update (meta &form) id changes)))

(defmacro remove
  "Removes a bookmark or an empty bookmark folder."
  ([id #_callback] (gen-call :function ::remove (meta &form) id)))

(defmacro remove-tree
  "Recursively removes a bookmark folder."
  ([id #_callback] (gen-call :function ::remove-tree (meta &form) id)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-created-events
  "Fired when a bookmark or folder is created."
  ([channel] (gen-call :event ::on-created (meta &form) channel)))

(defmacro tap-on-removed-events
  "Fired when a bookmark or folder is removed.  When a folder is removed recursively, a single notification is fired
   for the folder, and none for its contents."
  ([channel] (gen-call :event ::on-removed (meta &form) channel)))

(defmacro tap-on-changed-events
  "Fired when a bookmark or folder changes.  Note: Currently, only title and url changes trigger this."
  ([channel] (gen-call :event ::on-changed (meta &form) channel)))

(defmacro tap-on-moved-events
  "Fired when a bookmark or folder is moved to a different parent folder."
  ([channel] (gen-call :event ::on-moved (meta &form) channel)))

(defmacro tap-on-children-reordered-events
  "Fired when the children of a folder have changed their order due to the order being sorted in the UI.  This is not
   called as a result of a move()."
  ([channel] (gen-call :event ::on-children-reordered (meta &form) channel)))

(defmacro tap-on-import-began-events
  "Fired when a bookmark import session is begun.  Expensive observers should ignore onCreated updates until
   onImportEnded is fired.  Observers should still handle other notifications immediately."
  ([channel] (gen-call :event ::on-import-began (meta &form) channel)))

(defmacro tap-on-import-ended-events
  "Fired when a bookmark import session is ended."
  ([channel] (gen-call :event ::on-import-ended (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bookmarks",
   :since "5",
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
     :since "14",
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
    {:id ::on-removed,
     :name "onRemoved",
     :params [{:name "id", :type "string"} {:name "remove-info", :type "object"}]}
    {:id ::on-changed,
     :name "onChanged",
     :params [{:name "id", :type "string"} {:name "change-info", :type "object"}]}
    {:id ::on-moved,
     :name "onMoved",
     :params [{:name "id", :type "string"} {:name "move-info", :type "object"}]}
    {:id ::on-children-reordered,
     :name "onChildrenReordered",
     :params [{:name "id", :type "string"} {:name "reorder-info", :type "object"}]}
    {:id ::on-import-began, :name "onImportBegan"}
    {:id ::on-import-ended, :name "onImportEnded"}]})

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