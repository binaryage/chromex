(ns chromex.automation (:require-macros [chromex.automation :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-tree* [config tab-id]
  (gen-wrap :function ::get-tree config tab-id))

(defn get-desktop* [config]
  (gen-wrap :function ::get-desktop config))

(defn add-tree-change-observer* [config]
  (gen-wrap :function ::add-tree-change-observer config))

(defn remove-tree-change-observer* [config]
  (gen-wrap :function ::remove-tree-change-observer config))

(defn set-document-selection* [config params]
  (gen-wrap :function ::set-document-selection config params))

