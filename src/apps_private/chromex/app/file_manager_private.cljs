(ns chromex.app.file-manager-private (:require-macros [chromex.app.file-manager-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn logout-user-for-reauthentication* [config]
  (gen-wrap :function ::logout-user-for-reauthentication config))

(defn cancel-dialog* [config]
  (gen-wrap :function ::cancel-dialog config))

(defn execute-task* [config task-id entries]
  (gen-wrap :function ::execute-task config task-id entries))

(defn set-default-task* [config task-id entries mime-types]
  (gen-wrap :function ::set-default-task config task-id entries mime-types))

(defn get-file-tasks* [config entries]
  (gen-wrap :function ::get-file-tasks config entries))

(defn get-mime-type* [config entry]
  (gen-wrap :function ::get-mime-type config entry))

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

(defn add-file-watch* [config entry]
  (gen-wrap :function ::add-file-watch config entry))

(defn remove-file-watch* [config entry]
  (gen-wrap :function ::remove-file-watch config entry))

(defn enable-external-file-scheme* [config]
  (gen-wrap :function ::enable-external-file-scheme config))

(defn grant-access* [config entry-urls]
  (gen-wrap :function ::grant-access config entry-urls))

(defn select-files* [config selected-paths should-return-local-path]
  (gen-wrap :function ::select-files config selected-paths should-return-local-path))

(defn select-file* [config selected-path index for-opening should-return-local-path]
  (gen-wrap :function ::select-file config selected-path index for-opening should-return-local-path))

(defn get-entry-properties* [config entries names]
  (gen-wrap :function ::get-entry-properties config entries names))

(defn pin-drive-file* [config entry pin]
  (gen-wrap :function ::pin-drive-file config entry pin))

(defn ensure-file-downloaded* [config entry]
  (gen-wrap :function ::ensure-file-downloaded config entry))

(defn resolve-isolated-entries* [config entries]
  (gen-wrap :function ::resolve-isolated-entries config entries))

(defn add-mount* [config source]
  (gen-wrap :function ::add-mount config source))

(defn remove-mount* [config volume-id]
  (gen-wrap :function ::remove-mount config volume-id))

(defn mark-cache-as-mounted* [config source-path is-mounted]
  (gen-wrap :function ::mark-cache-as-mounted config source-path is-mounted))

(defn get-volume-metadata-list* [config]
  (gen-wrap :function ::get-volume-metadata-list config))

(defn cancel-file-transfers* [config entries]
  (gen-wrap :function ::cancel-file-transfers config entries))

(defn start-copy* [config entry parent-entry new-name]
  (gen-wrap :function ::start-copy config entry parent-entry new-name))

(defn cancel-copy* [config copy-id]
  (gen-wrap :function ::cancel-copy config copy-id))

(defn get-size-stats* [config volume-id]
  (gen-wrap :function ::get-size-stats config volume-id))

(defn format-volume* [config volume-id]
  (gen-wrap :function ::format-volume config volume-id))

(defn rename-volume* [config volume-id new-name]
  (gen-wrap :function ::rename-volume config volume-id new-name))

(defn get-preferences* [config]
  (gen-wrap :function ::get-preferences config))

(defn set-preferences* [config change-info]
  (gen-wrap :function ::set-preferences config change-info))

(defn search-drive* [config search-params]
  (gen-wrap :function ::search-drive config search-params))

(defn search-drive-metadata* [config search-params]
  (gen-wrap :function ::search-drive-metadata config search-params))

(defn search-files-by-hashes* [config volume-id hash-list]
  (gen-wrap :function ::search-files-by-hashes config volume-id hash-list))

(defn zip-selection* [config entries parent-entry dest-name]
  (gen-wrap :function ::zip-selection config entries parent-entry dest-name))

(defn get-drive-connection-state* [config]
  (gen-wrap :function ::get-drive-connection-state config))

(defn validate-path-name-length* [config parent-entry name]
  (gen-wrap :function ::validate-path-name-length config parent-entry name))

(defn zoom* [config operation]
  (gen-wrap :function ::zoom config operation))

(defn request-access-token* [config refresh]
  (gen-wrap :function ::request-access-token config refresh))

(defn request-web-store-access-token* [config]
  (gen-wrap :function ::request-web-store-access-token config))

(defn get-download-url* [config entry]
  (gen-wrap :function ::get-download-url config entry))

(defn request-drive-share* [config entry share-type]
  (gen-wrap :function ::request-drive-share config entry share-type))

(defn get-profiles* [config]
  (gen-wrap :function ::get-profiles config))

(defn open-inspector* [config type]
  (gen-wrap :function ::open-inspector config type))

(defn open-settings-subpage* [config sub-page]
  (gen-wrap :function ::open-settings-subpage config sub-page))

(defn compute-checksum* [config entry]
  (gen-wrap :function ::compute-checksum config entry))

(defn is-uma-enabled* [config]
  (gen-wrap :function ::is-uma-enabled config))

(defn set-entry-tag* [config entry visibility key value]
  (gen-wrap :function ::set-entry-tag config entry visibility key value))

(defn is-piex-loader-enabled* [config]
  (gen-wrap :function ::is-piex-loader-enabled config))

(defn get-providers* [config]
  (gen-wrap :function ::get-providers config))

(defn add-provided-file-system* [config provider-id]
  (gen-wrap :function ::add-provided-file-system config provider-id))

(defn configure-volume* [config volume-id]
  (gen-wrap :function ::configure-volume config volume-id))

(defn get-custom-actions* [config entries]
  (gen-wrap :function ::get-custom-actions config entries))

(defn execute-custom-action* [config entries action-id]
  (gen-wrap :function ::execute-custom-action config entries action-id))

(defn get-directory-size* [config entry]
  (gen-wrap :function ::get-directory-size config entry))

(defn get-recent-files* [config restriction]
  (gen-wrap :function ::get-recent-files config restriction))

(defn is-crostini-enabled* [config]
  (gen-wrap :function ::is-crostini-enabled config))

(defn mount-crostini* [config]
  (gen-wrap :function ::mount-crostini config))

(defn share-path-with-crostini* [config entry]
  (gen-wrap :function ::share-path-with-crostini config entry))

(defn get-crostini-shared-paths* [config]
  (gen-wrap :function ::get-crostini-shared-paths config))

(defn get-linux-package-info* [config entry]
  (gen-wrap :function ::get-linux-package-info config entry))

(defn install-linux-package* [config entry]
  (gen-wrap :function ::install-linux-package config entry))

(defn get-thumbnail* [config entry crop-to-square]
  (gen-wrap :function ::get-thumbnail config entry crop-to-square))

(defn detect-character-encoding* [config bytes]
  (gen-wrap :function ::detect-character-encoding config bytes))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-mount-completed* [config channel & args]
  (gen-wrap :event ::on-mount-completed config channel args))

(defn on-file-transfers-updated* [config channel & args]
  (gen-wrap :event ::on-file-transfers-updated config channel args))

(defn on-copy-progress* [config channel & args]
  (gen-wrap :event ::on-copy-progress config channel args))

(defn on-directory-changed* [config channel & args]
  (gen-wrap :event ::on-directory-changed config channel args))

(defn on-preferences-changed* [config channel & args]
  (gen-wrap :event ::on-preferences-changed config channel args))

(defn on-drive-connection-status-changed* [config channel & args]
  (gen-wrap :event ::on-drive-connection-status-changed config channel args))

(defn on-device-changed* [config channel & args]
  (gen-wrap :event ::on-device-changed config channel args))

(defn on-drive-sync-error* [config channel & args]
  (gen-wrap :event ::on-drive-sync-error config channel args))

(defn on-apps-updated* [config channel & args]
  (gen-wrap :event ::on-apps-updated config channel args))

