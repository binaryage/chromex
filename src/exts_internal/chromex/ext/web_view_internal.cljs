(ns chromex.ext.web-view-internal (:require-macros [chromex.ext.web-view-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn execute-script* [config instance-id src details]
  (gen-wrap :function ::execute-script config instance-id src details))

(defn insert-css* [config instance-id src details]
  (gen-wrap :function ::insert-css config instance-id src details))

(defn add-content-scripts* [config instance-id content-script-list]
  (gen-wrap :function ::add-content-scripts config instance-id content-script-list))

(defn remove-content-scripts* [config instance-id script-name-list]
  (gen-wrap :function ::remove-content-scripts config instance-id script-name-list))

(defn set-zoom* [config instance-id zoom-factor]
  (gen-wrap :function ::set-zoom config instance-id zoom-factor))

(defn get-zoom* [config instance-id]
  (gen-wrap :function ::get-zoom config instance-id))

(defn set-zoom-mode* [config instance-id zoom-mode]
  (gen-wrap :function ::set-zoom-mode config instance-id zoom-mode))

(defn get-zoom-mode* [config instance-id]
  (gen-wrap :function ::get-zoom-mode config instance-id))

(defn find* [config instance-id search-text options]
  (gen-wrap :function ::find config instance-id search-text options))

(defn stop-finding* [config instance-id action]
  (gen-wrap :function ::stop-finding config instance-id action))

(defn load-data-with-base-url* [config instance-id data-url base-url virtual-url]
  (gen-wrap :function ::load-data-with-base-url config instance-id data-url base-url virtual-url))

(defn go* [config instance-id relative-index]
  (gen-wrap :function ::go config instance-id relative-index))

(defn override-user-agent* [config instance-id user-agent-override]
  (gen-wrap :function ::override-user-agent config instance-id user-agent-override))

(defn reload* [config instance-id]
  (gen-wrap :function ::reload config instance-id))

(defn set-allow-transparency* [config instance-id allow]
  (gen-wrap :function ::set-allow-transparency config instance-id allow))

(defn set-allow-scaling* [config instance-id allow]
  (gen-wrap :function ::set-allow-scaling config instance-id allow))

(defn set-name* [config instance-id frame-name]
  (gen-wrap :function ::set-name config instance-id frame-name))

(defn set-permission* [config instance-id request-id action user-input]
  (gen-wrap :function ::set-permission config instance-id request-id action user-input))

(defn navigate* [config instance-id src]
  (gen-wrap :function ::navigate config instance-id src))

(defn stop* [config instance-id]
  (gen-wrap :function ::stop config instance-id))

(defn terminate* [config instance-id]
  (gen-wrap :function ::terminate config instance-id))

(defn clear-data* [config instance-id options data-to-remove]
  (gen-wrap :function ::clear-data config instance-id options data-to-remove))

