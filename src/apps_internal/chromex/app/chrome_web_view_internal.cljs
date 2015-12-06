(ns chromex.app.chrome-web-view-internal (:require-macros [chromex.app.chrome-web-view-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn context-menus-create* [config create-properties]
  (gen-wrap :function ::context-menus-create config create-properties))

(defn context-menus-update* [config id update-properties]
  (gen-wrap :function ::context-menus-update config id update-properties))

(defn context-menus-remove* [config menu-item-id]
  (gen-wrap :function ::context-menus-remove config menu-item-id))

(defn context-menus-remove-all* [config]
  (gen-wrap :function ::context-menus-remove-all config))

(defn show-context-menu* [config instance-id request-id items-to-show]
  (gen-wrap :function ::show-context-menu config instance-id request-id items-to-show))

