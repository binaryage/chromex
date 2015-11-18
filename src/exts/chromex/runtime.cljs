(ns chromex.runtime (:require-macros [chromex.runtime :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -----------------------------------------------------------------------------------------------------

(defn last-error* [config]
  (gen-wrap :property ::last-error config))

(defn id* [config]
  (gen-wrap :property ::id config))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-background-page* [config]
  (gen-wrap :function ::get-background-page config))

(defn open-options-page* [config]
  (gen-wrap :function ::open-options-page config))

(defn get-manifest* [config]
  (gen-wrap :function ::get-manifest config))

(defn get-url* [config path]
  (gen-wrap :function ::get-url config path))

(defn set-uninstall-url* [config url]
  (gen-wrap :function ::set-uninstall-url config url))

(defn reload* [config]
  (gen-wrap :function ::reload config))

(defn request-update-check* [config]
  (gen-wrap :function ::request-update-check config))

(defn restart* [config]
  (gen-wrap :function ::restart config))

(defn connect* [config extension-id connect-info]
  (gen-wrap :function ::connect config extension-id connect-info))

(defn connect-native* [config application]
  (gen-wrap :function ::connect-native config application))

(defn send-message* [config extension-id message options]
  (gen-wrap :function ::send-message config extension-id message options))

(defn send-native-message* [config application message]
  (gen-wrap :function ::send-native-message config application message))

(defn get-platform-info* [config]
  (gen-wrap :function ::get-platform-info config))

(defn get-package-directory-entry* [config]
  (gen-wrap :function ::get-package-directory-entry config))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-startup* [config channel]
  (gen-wrap :event ::on-startup config channel))

(defn on-installed* [config channel]
  (gen-wrap :event ::on-installed config channel))

(defn on-suspend* [config channel]
  (gen-wrap :event ::on-suspend config channel))

(defn on-suspend-canceled* [config channel]
  (gen-wrap :event ::on-suspend-canceled config channel))

(defn on-update-available* [config channel]
  (gen-wrap :event ::on-update-available config channel))

(defn on-browser-update-available* [config channel]
  (gen-wrap :event ::on-browser-update-available config channel))

(defn on-connect* [config channel]
  (gen-wrap :event ::on-connect config channel))

(defn on-connect-external* [config channel]
  (gen-wrap :event ::on-connect-external config channel))

(defn on-message* [config channel]
  (gen-wrap :event ::on-message config channel))

(defn on-message-external* [config channel]
  (gen-wrap :event ::on-message-external config channel))

(defn on-restart-required* [config channel]
  (gen-wrap :event ::on-restart-required config channel))

