(ns chromex.webview-tag (:require-macros [chromex.webview-tag :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn content-window* [config]
  (gen-wrap :property ::content-window config))

(defn request* [config]
  (gen-wrap :property ::request config))

(defn context-menus* [config]
  (gen-wrap :property ::context-menus config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-content-scripts* [config content-script-list]
  (gen-wrap :function ::add-content-scripts config content-script-list))

(defn back* [config]
  (gen-wrap :function ::back config))

(defn can-go-back* [config]
  (gen-wrap :function ::can-go-back config))

(defn can-go-forward* [config]
  (gen-wrap :function ::can-go-forward config))

(defn clear-data* [config options types]
  (gen-wrap :function ::clear-data config options types))

(defn execute-script* [config details]
  (gen-wrap :function ::execute-script config details))

(defn find* [config search-text options]
  (gen-wrap :function ::find config search-text options))

(defn forward* [config]
  (gen-wrap :function ::forward config))

(defn get-process-id* [config]
  (gen-wrap :function ::get-process-id config))

(defn get-user-agent* [config]
  (gen-wrap :function ::get-user-agent config))

(defn get-zoom* [config]
  (gen-wrap :function ::get-zoom config))

(defn get-zoom-mode* [config]
  (gen-wrap :function ::get-zoom-mode config))

(defn go* [config relative-index]
  (gen-wrap :function ::go config relative-index))

(defn insert-css* [config details]
  (gen-wrap :function ::insert-css config details))

(defn is-user-agent-overridden* [config]
  (gen-wrap :function ::is-user-agent-overridden config))

(defn print* [config]
  (gen-wrap :function ::print config))

(defn reload* [config]
  (gen-wrap :function ::reload config))

(defn remove-content-scripts* [config script-name-list]
  (gen-wrap :function ::remove-content-scripts config script-name-list))

(defn set-user-agent-override* [config user-agent]
  (gen-wrap :function ::set-user-agent-override config user-agent))

(defn set-zoom* [config zoom-factor]
  (gen-wrap :function ::set-zoom config zoom-factor))

(defn set-zoom-mode* [config zoom-mode]
  (gen-wrap :function ::set-zoom-mode config zoom-mode))

(defn stop* [config]
  (gen-wrap :function ::stop config))

(defn stop-finding* [config action]
  (gen-wrap :function ::stop-finding config action))

(defn load-data-with-base-url* [config data-url base-url virtual-url]
  (gen-wrap :function ::load-data-with-base-url config data-url base-url virtual-url))

(defn terminate* [config]
  (gen-wrap :function ::terminate config))

