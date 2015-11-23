(ns chromex.bookmark-manager-private
  "  * available since Chrome 24
     * https://developer.chrome.com/extensions/bookmarkManagerPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro copy
  "Copies the given bookmarks into the clipboard.
   
     |idList| - An array of string-valued ids
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id-list #_callback] (gen-call :function ::copy &form id-list)))

(defmacro cut
  "Cuts the given bookmarks into the clipboard.
   
     |idList| - An array of string-valued ids
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id-list #_callback] (gen-call :function ::cut &form id-list)))

(defmacro paste
  "Pastes bookmarks from the clipboard into the parent folder after the last selected node.
   
     |selectedIdList| - An array of string-valued ids for selected bookmarks.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([parent-id selected-id-list #_callback] (gen-call :function ::paste &form parent-id selected-id-list))
  ([parent-id] `(paste ~parent-id :omit)))

(defmacro can-paste
  "Whether there are any bookmarks that can be pasted.
   
     |parentId| - The ID of the folder to paste into.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([parent-id #_callback] (gen-call :function ::can-paste &form parent-id)))

(defmacro sort-children
  "Sorts the children of a given folder.
   
     |parentId| - The ID of the folder to sort the children of."
  ([parent-id] (gen-call :function ::sort-children &form parent-id)))

(defmacro get-strings
  "Gets the i18n strings for the bookmark manager.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-strings &form)))

(defmacro start-drag
  "Begins dragging a set of bookmarks.
   
     |idList| - An array of string-valued ids.
     |isFromTouch| - True if the drag was initiated from touch."
  ([id-list is-from-touch] (gen-call :function ::start-drag &form id-list is-from-touch)))

(defmacro drop
  "Performs the drop action of the drag and drop session.
   
     |parentId| - The ID of the folder that the drop was made.
     |index| - The index of the position to drop at. If left out the dropped items will be placed at the end of the
               existing children."
  ([parent-id index] (gen-call :function ::drop &form parent-id index))
  ([parent-id] `(drop ~parent-id :omit)))

(defmacro get-subtree
  "Retrieves a bookmark hierarchy from the given node.  If the node id is empty, it is the full tree.  If foldersOnly
   is true, it will only return folders, not actual bookmarks.
   
     |id| - ID of the root of the tree to pull.  If empty, the entire tree will be returned.
     |foldersOnly| - Pass true to only return folders.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id folders-only #_callback] (gen-call :function ::get-subtree &form id folders-only)))

(defmacro can-edit
  "Whether bookmarks can be modified.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::can-edit &form)))

(defmacro can-open-new-windows
  "Whether bookmarks can be opened in new windows.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::can-open-new-windows &form)))

(defmacro remove-trees
  "Recursively removes list of bookmarks nodes.
   
     |idList| - An array of string-valued ids.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id-list #_callback] (gen-call :function ::remove-trees &form id-list)))

(defmacro record-launch ([] (gen-call :function ::record-launch &form)))

(defmacro create-with-meta-info
  "Mimics the functionality of bookmarks.create, but will additionally set the given meta info fields.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([bookmark meta-info #_callback] (gen-call :function ::create-with-meta-info &form bookmark meta-info)))

(defmacro get-meta-info
  "Gets meta info from a bookmark node.
   
     |id| - The id of the bookmark to retrieve meta info from. If omitted meta info for all nodes is returned.
     |key| - The key for the meta info to retrieve. If omitted, all fields are returned.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id key #_callback] (gen-call :function ::get-meta-info &form id key))
  ([id] `(get-meta-info ~id :omit))
  ([] `(get-meta-info :omit :omit)))

(defmacro set-meta-info
  "Sets a meta info value for a bookmark node.
   
     |id| - The id of the bookmark node to set the meta info on.
     |key| - The key of the meta info to set.
     |value| - The meta info to set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id key value #_callback] (gen-call :function ::set-meta-info &form id key value)))

(defmacro update-meta-info
  "Updates a set of meta info values for a bookmark node.
   
     |id| - The id of the bookmark node to update the meta info of.
     |metaInfoChanges| - A set of meta info key/value pairs to update.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([id meta-info-changes #_callback] (gen-call :function ::update-meta-info &form id meta-info-changes)))

(defmacro undo
  "Performs an undo of the last change to the bookmark model."
  ([] (gen-call :function ::undo &form)))

(defmacro redo
  "Performs a redo of last undone change to the bookmark model."
  ([] (gen-call :function ::redo &form)))

(defmacro get-undo-info
  "Gets the information for the undo if available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-undo-info &form)))

(defmacro get-redo-info
  "Gets the information for the redo if available.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-redo-info &form)))

(defmacro set-version
  "Sets the version to use when updating enhanced bookmarks.
   
     |version| - The version to set.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([version #_callback] (gen-call :function ::set-version &form version)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-drag-enter-events
  "Fired when dragging bookmarks over the document."
  ([channel] (gen-call :event ::on-drag-enter &form channel)))

(defmacro tap-on-drag-leave-events
  "Fired when the drag and drop leaves the document."
  ([channel] (gen-call :event ::on-drag-leave &form channel)))

(defmacro tap-on-drop-events
  "Fired when the user drops bookmarks on the document."
  ([channel] (gen-call :event ::on-drop &form channel)))

(defmacro tap-on-meta-info-changed-events
  "Fired when the meta info of a node changes."
  ([channel] (gen-call :event ::on-meta-info-changed &form channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bookmarkManagerPrivate",
   :since "24",
   :functions
   [{:id ::copy,
     :name "copy",
     :callback? true,
     :params
     [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::cut,
     :name "cut",
     :callback? true,
     :params
     [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::paste,
     :name "paste",
     :callback? true,
     :params
     [{:name "parent-id", :type "string"}
      {:name "selected-id-list", :optional? true, :type "[array-of-strings]"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::can-paste,
     :name "canPaste",
     :callback? true,
     :params
     [{:name "parent-id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::sort-children, :name "sortChildren", :params [{:name "parent-id", :type "string"}]}
    {:id ::get-strings,
     :name "getStrings",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::start-drag,
     :name "startDrag",
     :params [{:name "id-list", :type "[array-of-strings]"} {:name "is-from-touch", :type "boolean"}]}
    {:id ::drop,
     :name "drop",
     :params [{:name "parent-id", :type "string"} {:name "index", :optional? true, :type "integer"}]}
    {:id ::get-subtree,
     :name "getSubtree",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "folders-only", :type "boolean"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::can-edit,
     :name "canEdit",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::can-open-new-windows,
     :name "canOpenNewWindows",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::remove-trees,
     :name "removeTrees",
     :since "30",
     :callback? true,
     :params
     [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::record-launch, :name "recordLaunch", :since "27"}
    {:id ::create-with-meta-info,
     :name "createWithMetaInfo",
     :since "36",
     :callback? true,
     :params
     [{:name "bookmark", :type "bookmarks.CreateDetails"}
      {:name "meta-info", :type "bookmarkManagerPrivate.MetaInfoFields"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "bookmarks.BookmarkTreeNode"}]}}]}
    {:id ::get-meta-info,
     :name "getMetaInfo",
     :since "33",
     :callback? true,
     :params
     [{:name "id", :optional? true, :type "string"}
      {:name "key", :optional? true, :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "value", :optional? true, :type "string-or-object"}]}}]}
    {:id ::set-meta-info,
     :name "setMetaInfo",
     :since "33",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "key", :type "string"}
      {:name "value", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::update-meta-info,
     :name "updateMetaInfo",
     :since "36",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "meta-info-changes", :type "bookmarkManagerPrivate.MetaInfoFields"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::undo, :name "undo", :since "34"}
    {:id ::redo, :name "redo", :since "34"}
    {:id ::get-undo-info,
     :name "getUndoInfo",
     :since "34",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::get-redo-info,
     :name "getRedoInfo",
     :since "34",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::set-version,
     :name "setVersion",
     :since "40",
     :callback? true,
     :params [{:name "version", :type "string"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-drag-enter,
     :name "onDragEnter",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}
    {:id ::on-drag-leave,
     :name "onDragLeave",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}
    {:id ::on-drop,
     :name "onDrop",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}
    {:id ::on-meta-info-changed,
     :name "onMetaInfoChanged",
     :since "35",
     :params
     [{:name "id", :type "string"}
      {:name "meta-info-changes", :type "bookmarkManagerPrivate.MetaInfoFields"}]}]})

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