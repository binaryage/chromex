(ns chromex.automation-internal (:require-macros [chromex.automation-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn enable-tab* [config args]
  (gen-wrap :function ::enable-tab config args))

(defn enable-frame* [config tree-id]
  (gen-wrap :function ::enable-frame config tree-id))

(defn enable-desktop* [config routing-id]
  (gen-wrap :function ::enable-desktop config routing-id))

(defn perform-action* [config args opt-args]
  (gen-wrap :function ::perform-action config args opt-args))

(defn query-selector* [config args]
  (gen-wrap :function ::query-selector config args))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-accessibility-event* [config channel]
  (gen-wrap :event ::on-accessibility-event config channel))

(defn on-accessibility-tree-destroyed* [config channel]
  (gen-wrap :event ::on-accessibility-tree-destroyed config channel))

(defn on-tree-change* [config channel]
  (gen-wrap :event ::on-tree-change config channel))

