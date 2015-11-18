(ns chromex.tabs (:require-macros [chromex.tabs :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -----------------------------------------------------------------------------------------------------

(defn tab-id-none* [config]
  (gen-wrap :property ::tab-id-none config))

; -- functions ------------------------------------------------------------------------------------------------------

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

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-created* [config channel]
  (gen-wrap :event ::on-created config channel))

(defn on-updated* [config channel]
  (gen-wrap :event ::on-updated config channel))

(defn on-moved* [config channel]
  (gen-wrap :event ::on-moved config channel))

(defn on-selection-changed* [config channel]
  (gen-wrap :event ::on-selection-changed config channel))

(defn on-active-changed* [config channel]
  (gen-wrap :event ::on-active-changed config channel))

(defn on-activated* [config channel]
  (gen-wrap :event ::on-activated config channel))

(defn on-highlight-changed* [config channel]
  (gen-wrap :event ::on-highlight-changed config channel))

(defn on-highlighted* [config channel]
  (gen-wrap :event ::on-highlighted config channel))

(defn on-detached* [config channel]
  (gen-wrap :event ::on-detached config channel))

(defn on-attached* [config channel]
  (gen-wrap :event ::on-attached config channel))

(defn on-removed* [config channel]
  (gen-wrap :event ::on-removed config channel))

(defn on-replaced* [config channel]
  (gen-wrap :event ::on-replaced config channel))

(defn on-zoom-change* [config channel]
  (gen-wrap :event ::on-zoom-change config channel))

