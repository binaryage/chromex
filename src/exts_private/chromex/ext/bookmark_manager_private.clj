(ns chromex.ext.bookmark-manager-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro copy
  "Copies the given bookmarks into the clipboard.

     |id-list| - An array of string-valued ids

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id-list] (gen-call :function ::copy &form id-list)))

(defmacro cut
  "Cuts the given bookmarks into the clipboard.

     |id-list| - An array of string-valued ids

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id-list] (gen-call :function ::cut &form id-list)))

(defmacro paste
  "Pastes bookmarks from the clipboard into the parent folder after the last selected node.

     |parent-id| - ?
     |selected-id-list| - An array of string-valued ids for selected bookmarks.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-id selected-id-list] (gen-call :function ::paste &form parent-id selected-id-list))
  ([parent-id] `(paste ~parent-id :omit)))

(defmacro can-paste
  "Whether there are any bookmarks that can be pasted.

     |parent-id| - The ID of the folder to paste into.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-id] (gen-call :function ::can-paste &form parent-id)))

(defmacro sort-children
  "Sorts the children of a given folder.

     |parent-id| - The ID of the folder to sort the children of."
  ([parent-id] (gen-call :function ::sort-children &form parent-id)))

(defmacro start-drag
  "Begins dragging a set of bookmarks.

     |id-list| - An array of string-valued ids.
     |drag-node-index| - The index of the dragged node in |idList

     |is-from-touch| - True if the drag was initiated from touch.
     |x| - The clientX of the dragStart event
     |y| - The clientY of the dragStart event"
  ([id-list drag-node-index is-from-touch x y] (gen-call :function ::start-drag &form id-list drag-node-index is-from-touch x y)))

(defmacro drop
  "Performs the drop action of the drag and drop session.

     |parent-id| - The ID of the folder that the drop was made.
     |index| - The index of the position to drop at. If left out the dropped items will be placed at the end of the existing
               children.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([parent-id index] (gen-call :function ::drop &form parent-id index))
  ([parent-id] `(drop ~parent-id :omit)))

(defmacro get-subtree
  "Retrieves a bookmark hierarchy from the given node.  If the node id is empty, it is the full tree.  If foldersOnly is true,
   it will only return folders, not actual bookmarks.

     |id| - ID of the root of the tree to pull.  If empty, the entire tree will be returned.
     |folders-only| - Pass true to only return folders.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [results] where:

     |results| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id folders-only] (gen-call :function ::get-subtree &form id folders-only)))

(defmacro remove-trees
  "Recursively removes list of bookmarks nodes.

     |id-list| - An array of string-valued ids.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id-list] (gen-call :function ::remove-trees &form id-list)))

(defmacro undo
  "Performs an undo of the last change to the bookmark model."
  ([] (gen-call :function ::undo &form)))

(defmacro redo
  "Performs a redo of last undone change to the bookmark model."
  ([] (gen-call :function ::redo &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-drag-enter-events
  "Fired when dragging bookmarks over the document.

   Events will be put on the |channel| with signature [::on-drag-enter [bookmark-node-data]] where:

     |bookmark-node-data| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-drag-enter &form channel args)))

(defmacro tap-on-drag-leave-events
  "Fired when the drag and drop leaves the document.

   Events will be put on the |channel| with signature [::on-drag-leave [bookmark-node-data]] where:

     |bookmark-node-data| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-drag-leave &form channel args)))

(defmacro tap-on-drop-events
  "Fired when the user drops bookmarks on the document.

   Events will be put on the |channel| with signature [::on-drop [bookmark-node-data]] where:

     |bookmark-node-data| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-drop &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.bookmark-manager-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bookmarkManagerPrivate",
   :since "38",
   :functions
   [{:id ::copy,
     :name "copy",
     :callback? true,
     :params [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::cut,
     :name "cut",
     :callback? true,
     :params [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
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
    {:id ::start-drag,
     :name "startDrag",
     :params
     [{:name "id-list", :type "[array-of-strings]"}
      {:name "drag-node-index", :since "71", :type "integer"}
      {:name "is-from-touch", :type "boolean"}
      {:name "x", :since "77", :type "integer"}
      {:name "y", :since "77", :type "integer"}]}
    {:id ::drop,
     :name "drop",
     :callback? true,
     :params
     [{:name "parent-id", :type "string"}
      {:name "index", :optional? true, :type "integer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-subtree,
     :name "getSubtree",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "folders-only", :type "boolean"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "results", :type "[array-of-bookmarks.BookmarkTreeNodes]"}]}}]}
    {:id ::remove-trees,
     :name "removeTrees",
     :callback? true,
     :params [{:name "id-list", :type "[array-of-strings]"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::undo, :name "undo"}
    {:id ::redo, :name "redo"}],
   :events
   [{:id ::on-drag-enter,
     :name "onDragEnter",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}
    {:id ::on-drag-leave,
     :name "onDragLeave",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}
    {:id ::on-drop,
     :name "onDrop",
     :params [{:name "bookmark-node-data", :type "bookmarkManagerPrivate.BookmarkNodeData"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))