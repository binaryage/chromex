(ns chromex.ext.runtime (:require-macros [chromex.ext.runtime :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn last-error* [config]
  (gen-wrap :property ::last-error config))

(defn id* [config]
  (gen-wrap :property ::id config))

; -- functions --------------------------------------------------------------------------------------------------------------

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

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-startup* [config channel & args]
  (gen-wrap :event ::on-startup config channel args))

(defn on-installed* [config channel & args]
  (gen-wrap :event ::on-installed config channel args))

(defn on-suspend* [config channel & args]
  (gen-wrap :event ::on-suspend config channel args))

(defn on-suspend-canceled* [config channel & args]
  (gen-wrap :event ::on-suspend-canceled config channel args))

(defn on-update-available* [config channel & args]
  (gen-wrap :event ::on-update-available config channel args))

(defn on-browser-update-available* [config channel & args]
  (gen-wrap :event ::on-browser-update-available config channel args))

(defn on-connect* [config channel & args]
  (gen-wrap :event ::on-connect config channel args))

(defn on-connect-external* [config channel & args]
  (gen-wrap :event ::on-connect-external config channel args))

(defn on-message* [config channel & args]
  (gen-wrap :event ::on-message config channel args))

(defn on-message-external* [config channel & args]
  (gen-wrap :event ::on-message-external config channel args))

(defn on-restart-required* [config channel & args]
  (gen-wrap :event ::on-restart-required config channel args))

