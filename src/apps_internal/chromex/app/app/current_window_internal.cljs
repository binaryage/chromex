(ns chromex.app.app.current-window-internal (:require-macros [chromex.app.app.current-window-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn focus* [config]
  (gen-wrap :function ::focus config))

(defn fullscreen* [config]
  (gen-wrap :function ::fullscreen config))

(defn minimize* [config]
  (gen-wrap :function ::minimize config))

(defn maximize* [config]
  (gen-wrap :function ::maximize config))

(defn restore* [config]
  (gen-wrap :function ::restore config))

(defn draw-attention* [config]
  (gen-wrap :function ::draw-attention config))

(defn clear-attention* [config]
  (gen-wrap :function ::clear-attention config))

(defn show* [config focused]
  (gen-wrap :function ::show config focused))

(defn hide* [config]
  (gen-wrap :function ::hide config))

(defn set-bounds* [config bounds-type bounds]
  (gen-wrap :function ::set-bounds config bounds-type bounds))

(defn set-size-constraints* [config bounds-type constraints]
  (gen-wrap :function ::set-size-constraints config bounds-type constraints))

(defn set-icon* [config icon-url]
  (gen-wrap :function ::set-icon config icon-url))

(defn set-shape* [config region]
  (gen-wrap :function ::set-shape config region))

(defn set-always-on-top* [config always-on-top]
  (gen-wrap :function ::set-always-on-top config always-on-top))

(defn set-visible-on-all-workspaces* [config always-visible]
  (gen-wrap :function ::set-visible-on-all-workspaces config always-visible))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-closed* [config channel & args]
  (gen-wrap :event ::on-closed config channel args))
(defn on-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-bounds-changed config channel args))
(defn on-fullscreened* [config channel & args]
  (gen-wrap :event ::on-fullscreened config channel args))
(defn on-minimized* [config channel & args]
  (gen-wrap :event ::on-minimized config channel args))
(defn on-maximized* [config channel & args]
  (gen-wrap :event ::on-maximized config channel args))
(defn on-restored* [config channel & args]
  (gen-wrap :event ::on-restored config channel args))
(defn on-alpha-enabled-changed* [config channel & args]
  (gen-wrap :event ::on-alpha-enabled-changed config channel args))
(defn on-window-shown-for-tests* [config channel & args]
  (gen-wrap :event ::on-window-shown-for-tests config channel args))

