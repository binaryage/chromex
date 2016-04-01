(ns chromex.ext.tabs (:require-macros [chromex.ext.tabs :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn tab-id-none* [config]
  (gen-wrap :property ::tab-id-none config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get* [config tab-id]
  (gen-wrap :function ::get config tab-id))

(defn get-current* [config]
  (gen-wrap :function ::get-current config))

(defn connect* [config tab-id connect-info]
  (gen-wrap :function ::connect config tab-id connect-info))

(defn send-request* [config tab-id request]
  (gen-wrap :function ::send-request config tab-id request))

(defn send-message* [config tab-id message options]
  (gen-wrap :function ::send-message config tab-id message options))

(defn get-selected* [config window-id]
  (gen-wrap :function ::get-selected config window-id))

(defn get-all-in-window* [config window-id]
  (gen-wrap :function ::get-all-in-window config window-id))

(defn create* [config create-properties]
  (gen-wrap :function ::create config create-properties))

(defn duplicate* [config tab-id]
  (gen-wrap :function ::duplicate config tab-id))

(defn query* [config query-info]
  (gen-wrap :function ::query config query-info))

(defn highlight* [config highlight-info]
  (gen-wrap :function ::highlight config highlight-info))

(defn update* [config tab-id update-properties]
  (gen-wrap :function ::update config tab-id update-properties))

(defn move* [config tab-ids move-properties]
  (gen-wrap :function ::move config tab-ids move-properties))

(defn reload* [config tab-id reload-properties]
  (gen-wrap :function ::reload config tab-id reload-properties))

(defn remove* [config tab-ids]
  (gen-wrap :function ::remove config tab-ids))

(defn detect-language* [config tab-id]
  (gen-wrap :function ::detect-language config tab-id))

(defn capture-visible-tab* [config window-id options]
  (gen-wrap :function ::capture-visible-tab config window-id options))

(defn execute-script* [config tab-id details]
  (gen-wrap :function ::execute-script config tab-id details))

(defn insert-css* [config tab-id details]
  (gen-wrap :function ::insert-css config tab-id details))

(defn set-zoom* [config tab-id zoom-factor]
  (gen-wrap :function ::set-zoom config tab-id zoom-factor))

(defn get-zoom* [config tab-id]
  (gen-wrap :function ::get-zoom config tab-id))

(defn set-zoom-settings* [config tab-id zoom-settings]
  (gen-wrap :function ::set-zoom-settings config tab-id zoom-settings))

(defn get-zoom-settings* [config tab-id]
  (gen-wrap :function ::get-zoom-settings config tab-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-created* [config channel & args]
  (gen-wrap :event ::on-created config channel args))

(defn on-updated* [config channel & args]
  (gen-wrap :event ::on-updated config channel args))

(defn on-moved* [config channel & args]
  (gen-wrap :event ::on-moved config channel args))

(defn on-selection-changed* [config channel & args]
  (gen-wrap :event ::on-selection-changed config channel args))

(defn on-active-changed* [config channel & args]
  (gen-wrap :event ::on-active-changed config channel args))

(defn on-activated* [config channel & args]
  (gen-wrap :event ::on-activated config channel args))

(defn on-highlight-changed* [config channel & args]
  (gen-wrap :event ::on-highlight-changed config channel args))

(defn on-highlighted* [config channel & args]
  (gen-wrap :event ::on-highlighted config channel args))

(defn on-detached* [config channel & args]
  (gen-wrap :event ::on-detached config channel args))

(defn on-attached* [config channel & args]
  (gen-wrap :event ::on-attached config channel args))

(defn on-removed* [config channel & args]
  (gen-wrap :event ::on-removed config channel args))

(defn on-replaced* [config channel & args]
  (gen-wrap :event ::on-replaced config channel args))

(defn on-zoom-change* [config channel & args]
  (gen-wrap :event ::on-zoom-change config channel args))

