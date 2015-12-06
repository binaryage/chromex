(ns chromex.ext.developer-private (:require-macros [chromex.ext.developer-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn auto-update* [config]
  (gen-wrap :function ::auto-update config))

(defn get-extensions-info* [config options]
  (gen-wrap :function ::get-extensions-info config options))

(defn get-extension-info* [config id]
  (gen-wrap :function ::get-extension-info config id))

(defn get-items-info* [config include-disabled include-terminated]
  (gen-wrap :function ::get-items-info config include-disabled include-terminated))

(defn get-profile-configuration* [config]
  (gen-wrap :function ::get-profile-configuration config))

(defn update-profile-configuration* [config update]
  (gen-wrap :function ::update-profile-configuration config update))

(defn show-permissions-dialog* [config extension-id]
  (gen-wrap :function ::show-permissions-dialog config extension-id))

(defn reload* [config extension-id options]
  (gen-wrap :function ::reload config extension-id options))

(defn update-extension-configuration* [config update]
  (gen-wrap :function ::update-extension-configuration config update))

(defn load-unpacked* [config options]
  (gen-wrap :function ::load-unpacked config options))

(defn load-directory* [config directory]
  (gen-wrap :function ::load-directory config directory))

(defn choose-path* [config select-type file-type]
  (gen-wrap :function ::choose-path config select-type file-type))

(defn pack-directory* [config path private-key-path flags]
  (gen-wrap :function ::pack-directory config path private-key-path flags))

(defn is-profile-managed* [config]
  (gen-wrap :function ::is-profile-managed config))

(defn request-file-source* [config properties]
  (gen-wrap :function ::request-file-source config properties))

(defn open-dev-tools* [config properties]
  (gen-wrap :function ::open-dev-tools config properties))

(defn delete-extension-errors* [config properties]
  (gen-wrap :function ::delete-extension-errors config properties))

(defn repair-extension* [config extension-id]
  (gen-wrap :function ::repair-extension config extension-id))

(defn show-options* [config extension-id]
  (gen-wrap :function ::show-options config extension-id))

(defn show-path* [config extension-id]
  (gen-wrap :function ::show-path config extension-id))

(defn set-shortcut-handling-suspended* [config is-suspended]
  (gen-wrap :function ::set-shortcut-handling-suspended config is-suspended))

(defn update-extension-command* [config update]
  (gen-wrap :function ::update-extension-command config update))

(defn enable* [config id enabled]
  (gen-wrap :function ::enable config id enabled))

(defn allow-incognito* [config extension-id allow]
  (gen-wrap :function ::allow-incognito config extension-id allow))

(defn allow-file-access* [config extension-id allow]
  (gen-wrap :function ::allow-file-access config extension-id allow))

(defn inspect* [config options]
  (gen-wrap :function ::inspect config options))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-item-state-changed* [config channel & args]
  (gen-wrap :event ::on-item-state-changed config channel args))
(defn on-profile-state-changed* [config channel & args]
  (gen-wrap :event ::on-profile-state-changed config channel args))

